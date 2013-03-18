package eu.execom.core.persistence;

import org.springframework.stereotype.Repository;

import eu.execom.core.model.Country;
import eu.execom.core.persistence.base.AbstractHibernateDao;

/**
 * Hibernate implementation of {@link CountryDao}.
 * 
 * @author Dusko Vesin
 * 
 */
@Repository
class CountryHibernateDao extends AbstractHibernateDao<Country> implements CountryDao {

}
