package eu.execom.core.persistence;

import org.springframework.stereotype.Repository;

import eu.execom.core.model.City;
import eu.execom.core.persistence.base.AbstractHibernateDao;

/**
 * Hibernate implementation of {@link CityDao}.
 * 
 * @author Dusko Vesin
 * 
 */
@Repository
class CityHibernateDao extends AbstractHibernateDao<City> implements CityDao {

}
