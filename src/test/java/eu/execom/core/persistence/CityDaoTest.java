package eu.execom.core.persistence;

import org.junit.Before;

import eu.execom.core.model.City;
import eu.execom.core.model.Country;

/**
 * {@link CityHibernateDao} test.
 * 
 * @author Dusko Vesin
 */
public class CityDaoTest extends AbstractDaoTransactionalTest<City> {

    private Country country;

    /**
     * This method is called before tests.
     */
    @Before
    public void setUp() {
        country = createUniqueCountry(1);
        getCountryDao().save(country);
    }

    @Override
    public Class<City> getEntityClass() {
        return City.class;
    }

    @Override
    public City createValidObject(final Integer unique) {
        return createUniqueCity(unique, country);
    }

}
