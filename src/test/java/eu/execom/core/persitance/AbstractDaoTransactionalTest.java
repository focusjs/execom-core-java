package eu.execom.core.persitance;

import java.util.List;

import org.junit.Test;

import eu.execom.core.model.AbstractEntity;
import eu.execom.core.model.User;
import eu.execom.core.persistence.base.GenericDao;

/**
 * Abstract DAO tester. Should be inherited by all test that test any DAO who extends {@link GenericDao}.
 * 
 * @author Dusko Vesin
 * @param <T>
 *            {@link AbstractEntity} type.
 */
public abstract class AbstractDaoTransactionalTest<T extends AbstractEntity> extends AbstractPersistanceTest {

    /**
     * Tweak so we can create generic test.
     * 
     * @return entity class
     */
    public abstract Class<T> getEntityClass();

    /**
     * Test {@link GenericDao#findById(Long)}.
     */
    @Test
    public void testGetByIdAndSave() {

        final T createValidObject = createValidObject(1);
        getDao().save(createValidObject);

        takeSnapshot();
        final T byId = getDao().findById(createValidObject.getId());
        assertObjects(createValidObject, byId);

    }

    /**
     * Test {@link GenericDao#findAll()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findAll() {
        // init
        final T object1 = createValidObject(1);
        getDao().save(object1);
        final T object2 = createValidObject(2);
        getDao().save(object2);
        final T object3 = createValidObject(3);
        getDao().save(object3);

        // method
        takeSnapshot();
        final List<T> all = getDao().findAll();

        // assert
        assertObjects(all, object1, object2, object3);
    };

    /**
     * Test {@link GenericDao#update(AbstractEntity)}.
     */
    @Test
    public void testUpdate() {
        final T createValidObject = createValidObject(1);
        getDao().save(createValidObject);

        T byId = getDao().findById(createValidObject.getId());
        assertObjects(createValidObject, byId);

        takeSnapshot();
        getDao().update(createValidObject);
        byId = getDao().findById(createValidObject.getId());
        assertObjects(createValidObject, byId);
    }

    /**
     * Test {@link GenericDao#saveOrUpdate(AbstractEntity)}.
     */
    @Test
    public void testSaveOrUpdate() {

        final T createValidObject = createValidObject(1);
        getDao().save(createValidObject);

        T byId = getDao().findById(createValidObject.getId());
        assertObjects(createValidObject, byId);

        takeSnapshot();
        getDao().saveOrUpdate(createValidObject);
        byId = getDao().findById(createValidObject.getId());
        assertObjects(createValidObject, byId);
    };

    /**
     * Test {@link GenericDao#delete(AbstractEntity)}.
     */
    @Test
    public void delete() {
        final T createValidObject = createValidObject(1);
        getDao().save(createValidObject);

        takeSnapshot();
        getDao().delete(createValidObject);
        assertEntityAsDeleted(createValidObject);
    }

    /**
     * Test {@link GenericDao#deleteById(Long)}.
     */
    @Test
    public void deleteById() {
        final T createValidObject = createValidObject(1);
        getDao().save(createValidObject);

        takeSnapshot();
        getDao().deleteById(createValidObject.getId());

        assertEntityAsDeleted(createValidObject);
    }

    /**
     * Get DAO for entity class.
     * 
     * @return GenericDao<T>
     */
    @SuppressWarnings("unchecked")
    private GenericDao<T> getDao() {

        if (getEntityClass().equals(User.class)) {
            return (GenericDao<T>) getUserDao();
        } else {
            throw new IllegalStateException(getEntityClass() + " unsupported");
        }
    }

    /**
     * Create valid object.
     * 
     * @param unique
     *            identifier
     * @return T
     */
    public abstract T createValidObject(Integer unique);

}
