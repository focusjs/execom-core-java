package eu.execom.core.persistence.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import eu.execom.core.model.AbstractEntity;

/**
 * This class provides default implementations for the {@link GenericDao} methods.
 * 
 * @author Dusko Vesin
 * @param <T>
 *            type of the persisted entity this DAO will handle
 */
public abstract class AbstractHibernateDao<T extends AbstractEntity> implements GenericDao<T> {

    /**
     * Field PERCENT. (value is ""%"") Value: {@value PERCENT}
     */
    protected static final String PERCENT = "%";

    private final Class<T> persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Default constructor.
     */
    @SuppressWarnings("unchecked")
    public AbstractHibernateDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(final Long id) {
        return (T) getCurrentSession().get(persistentClass, id);
    }

    @Override
    public void deleteById(final Long id) {
        getCurrentSession().delete(findById(id));
    }

    @Override
    public void save(final T entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(final T entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void saveOrUpdate(final T entity) {
        getCurrentSession().saveOrUpdate(merge(entity));
    }

    @SuppressWarnings("unchecked")
    @Override
    public T merge(final T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    @Override
    public void delete(final T entity) {
        getCurrentSession().delete(entity);
        getCurrentSession().flush();
    }

    @Override
    public List<T> findAll() {
        return findByCriteria();
    }

    @Override
    public Long findAllCount() {
        final Criteria criteria = getCurrentSession().createCriteria(getPersistentClass());
        criteria.setProjection(Projections.count(AbstractEntity.ID));
        final Long result = (Long) criteria.uniqueResult();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(final Map<?, ?> criterias) {
        final Criteria criteria = getCurrentSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.allEq(criterias));
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByExample(final T exampleInstance, final String[] excludeProperty) {
        final Criteria crit = getCurrentSession().createCriteria(getPersistentClass());
        final Example example = exampleCriteria(exampleInstance, excludeProperty);
        crit.add(example);
        return crit.list();
    }

    @Override
    public void addExampleCriteria(final T example, final Criteria criteria) {
        addExampleCriteria(example, criteria, new String[] {});
    }

    @Override
    public void addExampleCriteria(final T example, final Criteria criteria, final String[] excludeProperty) {
        if (example != null) {
            criteria.add(exampleCriteria(example, excludeProperty));
        }
    }

    /**
     * Get persistence class.
     * 
     * @return Class
     */
    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * Use this inside subclasses as a convenience method.
     * 
     * @param criterion
     *            Criterion[]
     * @return List
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(final Criterion... criterion) {
        final Criteria crit = getCurrentSession().createCriteria(getPersistentClass());
        for (final Criterion c : criterion) {
            crit.add(c);
        }
        return crit.list();
    }

    /**
     * Use this inside subclasses as a convenience method.
     * 
     * @param criterion
     *            Criterion[]
     * @return T
     */
    @SuppressWarnings("unchecked")
    protected T findByCriteriaUnique(final Criterion... criterion) {
        final Criteria crit = getCurrentSession().createCriteria(getPersistentClass());
        for (final Criterion c : criterion) {
            crit.add(c);
        }
        return (T) crit.uniqueResult();
    }

    /**
     * Example criteria will be generated from example instance with possibility of fine tuning using excluded
     * properties.
     * 
     * @param exampleInstance
     *            T
     * @param excludeProperty
     *            String[]
     * @return Example
     */
    private Example exampleCriteria(final T exampleInstance, final String[] excludeProperty) {
        final Example example = Example.create(exampleInstance).excludeZeroes().enableLike(MatchMode.ANYWHERE)
                .ignoreCase();
        for (final String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        return example;
    }

    /**
     * @param query
     *            String
     * @param namedParams
     *            String[]
     * @param params
     *            Object[]
     * @return int
     */
    protected int executeQuery(final String query, final String[] namedParams, final Object[] params) {
        final Query q = getCurrentSession().createQuery(query);

        if (namedParams != null) {
            for (int i = 0; i < namedParams.length; i++) {
                q.setParameter(namedParams[i], params[i]);
            }
        }

        return q.executeUpdate();
    }

    /**
     * This method will execute an HQL query without named parameters and parameters and return the number of affected
     * entities.
     * 
     * @param query
     *            String
     * @return int
     */
    protected int executeQuery(final String query) {
        return executeQuery(query, null, null);
    }

    /**
     * This method will execute a Named HQL query and return the number of affected entities.
     * 
     * @param namedQuery
     *            String
     * @param namedParams
     *            String[]
     * @param params
     *            Object[]
     * @return int
     */
    protected int executeNamedQuery(final String namedQuery, final String[] namedParams, final Object[] params) {
        final Query q = getCurrentSession().getNamedQuery(namedQuery);

        if (namedParams != null) {
            for (int i = 0; i < namedParams.length; i++) {
                q.setParameter(namedParams[i], params[i]);
            }
        }

        return q.executeUpdate();
    }

    /**
     * Surrounds parameter String likeParameter with {@value #PERCENT}.
     * 
     * @param likeParameter
     *            String
     * @return String
     */
    protected String surroundWithPercent(final String likeParameter) {
        return PERCENT + likeParameter + PERCENT;
    }

    /**
     * @return current session
     */
    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * This method will execute a Named HQL query without named parameters and parameters and return the number of
     * affected entities.
     * 
     * @param namedQuery
     *            String
     * @return int
     */
    protected int executeNamedQuery(final String namedQuery) {
        return executeNamedQuery(namedQuery, null, null);
    }

}
