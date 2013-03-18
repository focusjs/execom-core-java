package eu.execom.core.persistence;

import org.junit.Before;

import eu.execom.core.model.Country;

/**
 * {@link CountryHibernateDao} test.
 * 
 * @author Dusko Vesin
 */
public class CountryDaoTest extends AbstractDaoTransactionalTest<Country> {

    /**
     * This method is called before tests.
     */
    @Before
    public void setUp() {
    }

    @Override
    public Class<Country> getEntityClass() {
        return Country.class;
    }

    @Override
    public Country createValidObject(final Integer unique) {
        return createUniqueCountry(unique);
    }

}
