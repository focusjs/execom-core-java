package eu.execom.core.persitance;

import org.junit.Before;

import eu.execom.core.model.User;
import eu.execom.core.persistence.UserDao;

/**
 * {@link UserDao} test.
 * 
 * @author Dusko Vesin
 */
public class UserDaoTest extends AbstractDaoTransactionalTest<User> {

    /**
     * This method is called before tests.
     */
    @Before
    public void setUp() {
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User createValidObject(final Integer unique) {
        return createUniqueUser(unique);
    }

}
