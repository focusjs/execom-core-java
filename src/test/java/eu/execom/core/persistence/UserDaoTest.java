package eu.execom.core.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.execom.core.dto.UsersTypeCountDto;
import eu.execom.core.model.City;
import eu.execom.core.model.Country;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;

/**
 * {@link UserHibernateDao} test.
 * 
 * @author Dusko Vesin
 */
public class UserDaoTest extends AbstractDaoTransactionalTest<User> {

    private Country country;
    private City city;

    /**
     * This method is called before tests.
     */
    @Before
    public void setUp() {
        country = createUniqueCountry(1);
        getCountryDao().save(country);

        city = createUniqueCity(1, country);
        getCityDao().save(city);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User createValidObject(final Integer unique) {
        return createUniqueUser(unique, city);
    }

    /**
     * Test {@link UserDao#getUserCountInCountriesPerRole(java.util.List)} with specified countries.
     */
    @Test
    public void testUsersCountInContriesPerRoleWithCountries() {

        // preparing data
        Country country2 = createUniqueCountry(2);
        getCountryDao().save(country2);

        City city2 = createUniqueCity(2, country2);
        getCityDao().save(city2);

        User user1 = createUniqueUser(1, city);
        getUserDao().save(user1);

        User user2 = createUniqueUser(2, city2);
        getUserDao().save(user2);

        ArrayList<Country> countries = new ArrayList<Country>();
        countries.add(country);

        takeSnapshot();

        // call method
        List<UsersTypeCountDto> userCountInCountriesPerRole = getUserDao().getUserCountInCountriesPerRole(countries);

        // assert
        UsersTypeCountDto usersTypeCountDto = new UsersTypeCountDto();
        usersTypeCountDto.setCount(1L);
        usersTypeCountDto.setRole(UserRole.USER);
        assertObjects(userCountInCountriesPerRole, usersTypeCountDto);
    }

    /**
     * Test {@link UserDao#getUserCountInCountriesPerRole(java.util.List)} without specified countries.
     */
    @Test
    public void testUsersCountInContriesPerRoleWithotCountries() {

        // preparing data
        Country country2 = createUniqueCountry(2);
        getCountryDao().save(country2);

        City city2 = createUniqueCity(2, country2);
        getCityDao().save(city2);

        User user1 = createUniqueUser(1, city);
        getUserDao().save(user1);

        User user2 = createUniqueUser(2, city2);
        getUserDao().save(user2);

        takeSnapshot();

        // call method
        List<UsersTypeCountDto> userCountInCountriesPerRole = getUserDao().getUserCountInCountriesPerRole(null);

        // assert
        UsersTypeCountDto usersTypeCountDto = new UsersTypeCountDto();
        usersTypeCountDto.setCount(2L);
        usersTypeCountDto.setRole(UserRole.USER);
        assertObjects(userCountInCountriesPerRole, usersTypeCountDto);
    }

    /**
     * Test {@link UserDao#findAllAdminsFirst(eu.execom.core.model.UserRole)} .
     */
    @Test
    public void test() {

        // prepare data
        User user1 = createUniqueUser(1, city);
        user1.setRole(UserRole.USER);
        getUserDao().save(user1);

        User user2 = createUniqueUser(2, city);
        user2.setRole(UserRole.ADMIN);
        getUserDao().save(user2);

        User user3 = createUniqueUser(3, city);
        user3.setRole(UserRole.USER);
        getUserDao().save(user3);

        User user4 = createUniqueUser(4, city);
        user4.setRole(UserRole.ADMIN);
        getUserDao().save(user4);

        takeSnapshot();
        // method
        List<User> users = getUserDao().findAllAdminsFirst();

        // assert
        assertObjects(users, user4, user2, user3, user1);

    }
}
