package eu.execom.core;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import eu.execom.core.dto.UserAddDto;
import eu.execom.core.dto.UserEditDto;
import eu.execom.core.dto.UserSearchDto;
import eu.execom.core.dto.UserTableDto;
import eu.execom.core.dto.UsersTypeCountDto;
import eu.execom.core.dto.authentication.AuthenticationResponseDto;
import eu.execom.core.dto.authentication.CredentialsDto;
import eu.execom.core.model.AbstractEntity;
import eu.execom.core.model.Address;
import eu.execom.core.model.City;
import eu.execom.core.model.Country;
import eu.execom.core.model.Gender;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;
import eu.execom.core.model.UserStatus;
import eu.execom.core.persistence.CityDao;
import eu.execom.core.persistence.CountryDao;
import eu.execom.core.persistence.UserDao;
import eu.execom.testutil.AbstractExecomRepositoryAssert;

/**
 * {@link AbstractExecomRepositoryAssert} implementation with specific adjustments for project.
 * 
 * @author Dusko Vesin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@EnableTransactionManagement(proxyTargetClass = true)
public abstract class ExecomCoreTest extends AbstractExecomRepositoryAssert<AbstractEntity, Long> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private CityDao cityDao;

    @Override
    protected void initEntityList(final List<Class<?>> entityTypes) {
        entityTypes.add(User.class);
        entityTypes.add(Country.class);
        entityTypes.add(City.class);
    }

    @After
    public final void checkTestValidity() {
        assertDbState();
    }

    @Override
    protected void initComplexTypes(final List<Class<?>> complexTypes) {
        complexTypes.add(UserAddDto.class);
        complexTypes.add(UserEditDto.class);
        complexTypes.add(CredentialsDto.class);
        complexTypes.add(AuthenticationResponseDto.class);
        complexTypes.add(UserSearchDto.class);
        complexTypes.add(UserTableDto.class);
        complexTypes.add(UsersTypeCountDto.class);
    }

    @Override
    protected void initIgnoredTypes(final List<Class<?>> ignoredTypes) {
        // no ignored types
    }

    @Override
    protected List<?> findAll(final Class<?> entityClass) {
        if (entityClass == User.class) {
            return userDao.findAll();
        }
        if (entityClass == Country.class) {
            return countryDao.findAll();
        }
        if (entityClass == City.class) {
            return cityDao.findAll();
        }
        throw new IllegalStateException("Unsupported entity class " + entityClass);
    }

    @Override
    protected AbstractEntity findById(final Class<?> entityClass, final Long id) {
        if (entityClass == User.class) {
            return userDao.findById(id);
        }
        if (entityClass == Country.class) {
            return countryDao.findById(id);
        }
        if (entityClass == City.class) {
            return cityDao.findById(id);
        }
        throw new IllegalStateException("Unsupported entity class " + entityClass);
    }

    @Override
    protected <T> void customAssertEquals(final T expected, final T actual) {
        assertEquals(expected, actual);
    }

    /**
     * @return the cityDao
     */
    public CityDao getCityDao() {
        return cityDao;
    }

    /**
     * @return the countryDao
     */
    public CountryDao getCountryDao() {
        return countryDao;
    }

    /**
     * @return the userDao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Create user entity with unique username.
     * 
     * @return {@link User}
     */
    protected User createUniqueUser(final int uniqueInt, final City city) {
        final User uniqueUser = new User();

        uniqueUser.setFirstName("First name " + uniqueInt);
        uniqueUser.setLastName("Last name " + uniqueInt);
        uniqueUser.setEmail("uniq" + uniqueInt + "@email.com");
        uniqueUser.setAuthenticationCode("authenticationCode" + uniqueInt);
        uniqueUser.setActivationCode("activationCode" + uniqueInt);
        uniqueUser.setPassword("uniq" + uniqueInt);
        uniqueUser.setStatus(UserStatus.ACTIVE);
        uniqueUser.setRole(UserRole.USER);
        uniqueUser.setGender(Gender.MALE);
        uniqueUser.setBirthDate(new Date());

        Address address = new Address();
        address.setCity(city);
        address.setStreet("Street" + uniqueUser);
        address.setStreetNumber("StreetNumber" + uniqueInt);
        uniqueUser.setAddress(address);

        return uniqueUser;
    }

    /**
     * Create unique country entity.
     * 
     * @return {@link Country}
     */
    protected Country createUniqueCountry(final int uniqueInt) {
        final Country country = new Country();
        country.setName("Country" + uniqueInt);
        return country;
    }

    protected City createUniqueCity(final Integer unique, final Country country) {
        City city = new City();
        city.setCountry(country);
        city.setLatitude("latitude");
        city.setLongitude("longitud");
        city.setName("name" + unique);
        city.setPostacityCode("postal" + unique);
        return city;
    }

    protected UserAddDto createUniqueAddUserDto(final int uniqueInt) {
        final UserAddDto uniqueUser = new UserAddDto();

        uniqueUser.setEmail("uniq" + uniqueInt + "@email.com");
        uniqueUser.setFirstName("First name " + uniqueInt);
        uniqueUser.setLastName("Last name " + uniqueInt);
        uniqueUser.setPassword("password" + uniqueInt);
        uniqueUser.setRole(UserRole.USER);
        uniqueUser.setGender(Gender.MALE);
        uniqueUser.setBirthDate(new Date());

        return uniqueUser;
    }

    protected UserEditDto createUniqueEditUserDto(final int uniqueInt) {
        final UserEditDto uniqueUser = new UserEditDto();

        uniqueUser.setFirstName("First name " + uniqueInt);
        uniqueUser.setLastName("Last name " + uniqueInt);
        uniqueUser.setPassword("uniq" + uniqueInt);
        uniqueUser.setStatus(UserStatus.ACTIVE);
        uniqueUser.setRole(UserRole.USER);
        uniqueUser.setGender(Gender.MALE);
        uniqueUser.setBirthDate(new Date());

        return uniqueUser;
    }

    public UserTableDto convertUserToTableDto(final User user1) {
        final UserTableDto user1Dto = new UserTableDto();
        user1Dto.setId(user1.getId());
        user1Dto.setEmail(user1.getEmail());
        return user1Dto;
    }

}
