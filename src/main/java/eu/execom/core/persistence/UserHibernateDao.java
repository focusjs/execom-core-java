package eu.execom.core.persistence;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import eu.execom.core.dto.UserSearchDto;
import eu.execom.core.dto.base.SearchOrder;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;
import eu.execom.core.persistence.base.AbstractHibernateDao;

/**
 * Hibernate implementation of {@link UserDao}.
 * 
 * @author Dusko Vesin
 * 
 */
@Repository
class UserHibernateDao extends AbstractHibernateDao<User> implements UserDao {

    @Override
    public final User findByEmail(final String email) {
        return findByCriteriaUnique(Restrictions.eq(User.EMAIL, email));
    }

    @Override
    public final User findByAuthenticationCode(final String authenticationCode) {
        return findByCriteriaUnique(Restrictions.eq(User.AUTHENTICATIONCODE, authenticationCode));
    }

    @Override
    public List<User> findByRole(final UserRole role) {
        return findByCriteria(Restrictions.eq(User.ROLE, role));
    }

    @Override
    public final User findByActivationCode(final String activationCode) {
        return findByCriteriaUnique(Restrictions.eq(User.ACTIVATIONCODE, activationCode));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final List<User> search(final UserSearchDto searchObject) {
        final Criteria criteria = getCurrentSession().createCriteria(getPersistentClass());

        if (StringUtils.isNotBlank(searchObject.getSortColumn())) {
            if (searchObject.getSearchOrder() == SearchOrder.ASCENDING) {
                criteria.addOrder(Order.asc(searchObject.getSortColumn()));
            } else if (searchObject.getSearchOrder() == SearchOrder.DESCENDING) {
                criteria.addOrder(Order.desc(searchObject.getSortColumn()));
            }
        }

        if (searchObject.getEmail() != null) {
            criteria.add(Restrictions.eq(UserSearchDto.EMAIL, searchObject.getEmail()));
        }
        if (searchObject.getGender() != null) {
            criteria.add(Restrictions.eq(UserSearchDto.GENDER, searchObject.getGender()));
        }
        if (searchObject.getRole() != null) {
            criteria.add(Restrictions.eq(UserSearchDto.ROLE, searchObject.getRole()));
        }

        criteria.setFirstResult(searchObject.getFrom());
        criteria.setMaxResults(searchObject.getMaxRowCount());

        return criteria.list();
    }

    @Override
    public Long searchCount(final UserSearchDto searchObject) {

        final Criteria criteria = getCurrentSession().createCriteria(getPersistentClass());
        criteria.setProjection(Projections.count(User.ID));

        if (searchObject.getEmail() != null) {
            criteria.add(Restrictions.eq(UserSearchDto.EMAIL, searchObject.getEmail()));
        }
        if (searchObject.getGender() != null) {
            criteria.add(Restrictions.eq(UserSearchDto.GENDER, searchObject.getGender()));
        }
        if (searchObject.getRole() != null) {
            criteria.add(Restrictions.eq(UserSearchDto.ROLE, searchObject.getRole()));
        }

        final Long result = (Long) criteria.uniqueResult();
        return result;
    }

}
