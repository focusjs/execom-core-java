package eu.execom.core.service;

import junit.framework.Assert;

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
        initWizer();
        takeSnapshot();
        final SearchResultDto<UserTableDto> searchResult = userService.search(userSearchDto);

        // assert
        assertLists(searchResult.getResults(), convertUserToTableDto(user2), convertUserToTableDto(user3),
                convertUserToTableDto(user4), convertUserToTableDto(user5));
    }

    /**
     * Test for {@link UserService#add(AddUserDto)}.
     */
    @Test
    public void testAdd() {
        // init
        final UserAddDto addUserDto = createUniqueAddUserDto(1);

        // method
        initWizer();
        takeSnapshot();
        userService.add(addUserDto);

        // assert
        assertObjects(getUserDao().findAllCount(), Long.valueOf(1));

        final User user = getUserDao().findByRole(UserRole.USER).get(0);
        assertObject(user, value(User.FIRSTNAME, addUserDto.getFirstName()), value(User.EMAIL, addUserDto.getEmail()),
                value(User.GENDER, addUserDto.getGender()), value(User.LASTNAME, addUserDto.getLastName()),
                value(User.STATUS, UserStatus.PENDING_EMAIL_REGISTRATION), isNull(User.AUTHENTICATIONCODE),
                isNull(User.RECOVERYPASSWORD), isNull(User.ADDRESS), isNull(User.ACTIVATIONCODE),
                value(User.ROLE, addUserDto.getRole()), notNull(User.ID), notNull(User.PASSWORD),
                value(User.BIRTHDATE, addUserDto.getBirthDate()));

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
        initWizer();
        takeSnapshot();
        userService.edit(userEditDto);

        // assert
        final User editUserLoaded = getUserDao().findById(userEditDto.getId());

        assertEntityWithSnapshot(editUserLoaded, value(User.FIRSTNAME, userEditDto.getFirstName()),
                value(User.LASTNAME, userEditDto.getLastName()), notNull(User.PASSWORD),
                value(User.STATUS, editUser.getStatus()), value(User.ID, userEditDto.getId()),
                value(User.ROLE, userEditDto.getRole()), value(User.GENDER, userEditDto.getGender()),
                value(User.BIRTHDATE, userEditDto.getBirthDate()));
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
        initWizer();
        takeSnapshot();
        final UserEditDto userEditDtoByUsername = userService.getEditDto(user.getId());

        // assert
        assertObject(userEditDtoByUsername, value(UserEditDto.ID, user.getId()),
                value(UserEditDto.FIRSTNAME, user.getFirstName()), value(UserEditDto.LASTNAME, user.getLastName()),
                value(UserEditDto.PASSWORD, user.getPassword()), value(UserEditDto.PASSWORDRE, user.getPassword()),
                value(UserEditDto.STATUS, user.getStatus()), value(UserEditDto.ROLE, user.getRole()),
                value(UserEditDto.GENDER, user.getGender()), value(UserEditDto.BIRTHDATE, user.getBirthDate()));
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
        initWizer();
        takeSnapshot();
        final boolean isSmailInUse = userService.isEmailInUse(user.getEmail());

        Assert.assertTrue(isSmailInUse);
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
        initWizer();
        takeSnapshot();
        final boolean isSmailInUse = userService.isEmailInUse("Invalid email");

        Assert.assertFalse(isSmailInUse);
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
        initWizer();
        takeSnapshot();
        final boolean isSmailInUse = userService.isActivationCodeInUse(user.getActivationCode());

        Assert.assertTrue(isSmailInUse);
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
        initWizer();
        takeSnapshot();
        final boolean isSmailInUse = userService.isActivationCodeInUse("Invalid email");

        Assert.assertFalse(isSmailInUse);
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
        initWizer();
        takeSnapshot();
        final boolean isSmailInUse = userService.isAuthenticationCodeInUse(user.getAuthenticationCode());

        Assert.assertTrue(isSmailInUse);
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
        initWizer();
        takeSnapshot();
        final boolean isSmailInUse = userService.isAuthenticationCodeInUse("Invalid email");

        Assert.assertFalse(isSmailInUse);
    }

}
