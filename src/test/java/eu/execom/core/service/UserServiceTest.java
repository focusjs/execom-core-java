package eu.execom.core.service;

import static eu.execom.testutil.property.Property.changed;
import static eu.execom.testutil.property.Property.notNull;
import static eu.execom.testutil.property.Property.nulll;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eu.execom.core.dto.UserAddDto;
import eu.execom.core.dto.UserEditDto;
import eu.execom.core.dto.UserSearchDto;
import eu.execom.core.dto.UserTableDto;
import eu.execom.core.dto.base.SearchOrder;
import eu.execom.core.dto.base.SearchResultDto;
import eu.execom.core.model.City;
import eu.execom.core.model.Country;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;
import eu.execom.core.model.UserStatus;

/**
 * Contains tests for authentication service.
 * 
 * @author Dusko Vesin
 */
public class UserServiceTest extends AbstractServiceTest {

    private Country country;
    private City city;

    @Autowired
    private UserServiceImpl userService;

    /**
     * Setting up environment for tests.
     */
    @Before
    public void setUp() {
        country = createUniqueCountry(1);
        getCountryDao().save(country);

        city = createUniqueCity(1, country);
        getCityDao().save(city);
    }

    /**
     * Test for {@link UserService#search(SearchUserDto)}.
     */
    @Test
    public void testSearch() {
        // init
        final int maxRowCount = 4;
        int unique = 0;
        final User user0 = createUniqueUser(unique++, city);
        final User user1 = createUniqueUser(unique++, city);
        final User user2 = createUniqueUser(unique++, city);
        final User user3 = createUniqueUser(unique++, city);
        final User user4 = createUniqueUser(unique++, city);
        final User user5 = createUniqueUser(unique++, city);
        final User user6 = createUniqueUser(unique++, city);
        final User user7 = createUniqueUser(unique++, city);
        getUserDao().save(user0);
        getUserDao().save(user1);
        getUserDao().save(user2);
        getUserDao().save(user3);
        getUserDao().save(user4);
        getUserDao().save(user5);
        getUserDao().save(user6);
        getUserDao().save(user7);

        final UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setFrom(2);
        userSearchDto.setMaxRowCount(maxRowCount);
        userSearchDto.setSortColumn(User.ID);
        userSearchDto.setSearchOrder(SearchOrder.ASCENDING);
        userSearchDto.setRole(UserRole.USER);

        // method
        takeSnapshot();
        final SearchResultDto<UserTableDto> searchResult = userService.search(userSearchDto);

        // assert
        assertObjects(searchResult.getResults(), convertUserToTableDto(user2), convertUserToTableDto(user3),
                convertUserToTableDto(user4), convertUserToTableDto(user5));
    }

    /**
     * Test for {@link UserService#add(AddUserDto)}.
     */
    @Test
    public void testAdd() {
        // init
        UserAddDto addUserDto = createUniqueAddUserDto(1);

        // method
        takeSnapshot();
        userService.add(addUserDto);

        // assert
        assertObjects(getUserDao().findAllCount(), Long.valueOf(1));

        final User user = getUserDao().findByRole(UserRole.USER).get(0);
        assertObject(user, changed(User.FIRSTNAME, addUserDto.getFirstName()),
                changed(User.EMAIL, addUserDto.getEmail()), changed(User.GENDER, addUserDto.getGender()),
                changed(User.LASTNAME, addUserDto.getLastName()),
                changed(User.STATUS, UserStatus.PENDING_EMAIL_REGISTRATION), nulll(User.AUTHENTICATIONCODE),
                nulll(User.RECOVERYPASSWORD), nulll(User.ADDRESS), nulll(User.ACTIVATIONCODE),
                changed(User.ROLE, addUserDto.getRole()), notNull(User.ID), notNull(User.PASSWORD),
                changed(User.BIRTHDATE, addUserDto.getBirthDate()));

    }

    /**
     * Test for {@link UserService#edit(UserEditDto)}.
     */
    @Test
    public void testUpdateUser() {

        // init
        final User editUser = createUniqueUser(1, city);
        getUserDao().save(editUser);

        final UserEditDto userEditDto = createUniqueEditUserDto(2);
        userEditDto.setId(editUser.getId());

        // method
        takeSnapshot();
        userService.edit(userEditDto);

        // assert
        final User editUserLoaded = getUserDao().findById(userEditDto.getId());

        assertEntityWithSnapshot(editUserLoaded, changed(User.FIRSTNAME, userEditDto.getFirstName()),
                changed(User.LASTNAME, userEditDto.getLastName()), notNull(User.PASSWORD),
                changed(User.STATUS, editUser.getStatus()), changed(User.ID, userEditDto.getId()),
                changed(User.ROLE, userEditDto.getRole()), changed(User.GENDER, userEditDto.getGender()),
                changed(User.BIRTHDATE, userEditDto.getBirthDate()));
    }

    /**
     * Test for {@link UserService#getEditDTO(Long)}.
     */
    @Test
    public void testGetUserEditDto() {
        // init
        final User user = createUniqueUser(2, city);
        getUserDao().save(user);

        // method
        takeSnapshot();
        final UserEditDto userEditDtoByUsername = userService.getEditDto(user.getId());

        // assert
        assertObject(userEditDtoByUsername, changed(UserEditDto.ID, user.getId()),
                changed(UserEditDto.FIRSTNAME, user.getFirstName()), changed(UserEditDto.LASTNAME, user.getLastName()),
                changed(UserEditDto.PASSWORD, user.getPassword()), changed(UserEditDto.PASSWORDRE, user.getPassword()),
                changed(UserEditDto.STATUS, user.getStatus()), changed(UserEditDto.ROLE, user.getRole()),
                changed(UserEditDto.GENDER, user.getGender()), changed(UserEditDto.BIRTHDATE, user.getBirthDate()));
    }

    /**
     * Test for {@link UserService#isEmailInUse(String)}.
     */
    @Test
    public void testIsEmailInUseExisting() {

        // init
        final User user = createUniqueUser(2, city);
        getUserDao().save(user);

        // method
        takeSnapshot();
        final boolean isSmailInUse = userService.isEmailInUse(user.getEmail());

        assertTrue(isSmailInUse);
    }

    /**
     * Test for {@link UserService#isEmailInUse(String)}.
     */
    @Test
    public void testIsEmailInUseNonExisting() {

        // init
        final User user = createUniqueUser(2, city);
        getUserDao().save(user);

        // method
        takeSnapshot();
        final boolean isSmailInUse = userService.isEmailInUse("Invalid email");

        assertFalse(isSmailInUse);
    }

    /**
     * Test for {@link UserService#isActivationCodeInUse(String)}.
     */
    @Test
    public void testIsActivationCodeInUseExisting() {

        // init
        final User user = createUniqueUser(2, city);
        getUserDao().save(user);

        // method
        takeSnapshot();
        final boolean isSmailInUse = userService.isActivationCodeInUse(user.getActivationCode());

        assertTrue(isSmailInUse);
    }

    /**
     * Test for {@link UserService#isActivationCodeInUse(String)}.
     */
    @Test
    public void testIsActivationCodeInUseNonExisting() {

        // init
        final User user = createUniqueUser(2, city);
        getUserDao().save(user);

        // method
        takeSnapshot();
        final boolean isSmailInUse = userService.isActivationCodeInUse("Invalid email");

        assertFalse(isSmailInUse);
    }

    /**
     * Test for {@link UserService#isAuthenticationCodeInUse(String)}.
     */
    @Test
    public void testIsAuthenticationCodeInUseExisting() {

        // init
        final User user = createUniqueUser(2, city);
        getUserDao().save(user);

        // method
        takeSnapshot();
        final boolean isSmailInUse = userService.isAuthenticationCodeInUse(user.getAuthenticationCode());

        assertTrue(isSmailInUse);
    }

    /**
     * Test for {@link UserService#isAuthenticationCodeInUse(String)}.
     */
    @Test
    public void testIsAuthenticationCodeInUseNonExisting() {

        // init
        final User user = createUniqueUser(2, city);
        getUserDao().save(user);

        // method
        takeSnapshot();
        final boolean isSmailInUse = userService.isAuthenticationCodeInUse("Invalid email");

        assertFalse(isSmailInUse);
    }

}
