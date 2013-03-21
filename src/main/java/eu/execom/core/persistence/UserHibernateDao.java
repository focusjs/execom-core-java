package eu.execom.core.persistence;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import eu.execom.core.dto.UserSearchDto;
import eu.execom.core.dto.UsersTypeCountDto;
import eu.execom.core.dto.base.SearchOrder;
import eu.execom.core.model.Address;
import eu.execom.core.model.City;
import eu.execom.core.model.Country;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;
import eu.execom.core.persistence.base.AbstractHibernateDao;
import eu.execom.core.persistence.transformer.UsersRoleCountDtoResultTransformer;

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

    @Override
    @SuppressWarnings("unchecked")
    public List<UsersTypeCountDto> getUserCountInCountriesPerRole(final List<Country> forCountries) {
        final Criteria criteria = getCurrentSession().createCriteria(getPersistentClass());

        if (forCountries != null) {

            criteria.createAlias(User.path(User.ADDRESS, Address.CITY), Address.CITY);
            criteria.createAlias(User.path(Address.CITY, City.COUNTRY), City.COUNTRY);

            criteria.add(Restrictions.in(User.path(Address.CITY, City.COUNTRY), forCountries));
        }

        final ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.count(User.ROLE));
        projectionList.add(Projections.property(User.ROLE));
        projectionList.add(Projections.groupProperty(User.ROLE));

        criteria.setProjection(projectionList);
        criteria.setResultTransformer(new UsersRoleCountDtoResultTransformer());

        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAllAdminsFirst() {
        final String hql = "FROM eu.execom.core.model.User user order by case when user.role = 'ADMIN' then '0' else '1' end asc";
        final Query query = getCurrentSession().createQuery(hql);
        return query.list();
    }

}
