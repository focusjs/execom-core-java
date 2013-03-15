package eu.execom.core.persistence.base;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import eu.execom.core.model.AbstractEntity;

/**
 * Generic data access object that provides commonly used methods for a single entity.
 * 
 * @author Dusko Vesin
 * @param <T>
 *            type of persisted entity this DAO will handle
 */
public interface GenericDao<T extends AbstractEntity> {

    /**
     * Retrieve all entities.
     * 
     * @return List<T>
     */
    List<T> findAll();

    /**
     * Find entities by given {@link Criteria}.
     * 
     * @param criterias
     *            Map<?,?>
     * @return List<T>
     */
    List<T> findByCriteria(Map<?, ?> criterias);

    /**
     * Find entities by given example entity.
     * 
     * @param exampleInstance
     *            T
     * @param excludeProperty
     *            String[]
     * @return List<T>
     */
    List<T> findByExample(T exampleInstance, String[] excludeProperty);

    /**
     * Save entity to database.
     * 
     * @param entity
     *            T
     */
    void save(T entity);

    /**
     * Update entity.
     * 
     * @param entity
     *            T
     */
    void update(T entity);

    /**
     * Save entity to database or update it if already exists in database.
     * 
     * @param entity
     *            T
     */
    void saveOrUpdate(T entity);

    /**
     * Delete entity.
     * 
     * @param entity
     *            T
     */
    void delete(T entity);

    /**
     * Add example criteria with given example entity to specified criteria.
     * 
     * @param example
     *            T
     * @param criteria
     *            Criteria
     */
    void addExampleCriteria(T example, Criteria criteria);

    /**
     * Add example criteria with given example entity and excluded properties to specified criteria.
     * 
     * @param example
     *            T
     * @param criteria
     *            Criteria
     * @param excludeProperty
     *            Property that should be excluded from example object
     */
    void addExampleCriteria(T example, Criteria criteria, String[] excludeProperty);

    /**
     * Merge entity.
     * 
     * @param entity
     *            T
     * @return T
     */
    T merge(T entity);

    /**
     * Count of all element.
     * 
     * @return count of all elements in the system
     */
    Long findAllCount();

    /**
     * Find entity by id value.
     * 
     * @param id
     *            of entity
     * @return T entity if it exist in the system, else return <code>null</code>
     */
    T findById(Long id);

    /**
     * Delete entity by id if it exist.
     * 
     * @param id
     *            of entity
     */
    void deleteById(Long id);

}
