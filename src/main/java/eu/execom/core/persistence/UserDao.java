package eu.execom.core.persistence;

import java.util.List;

import eu.execom.core.dto.UserSearchDto;
import eu.execom.core.dto.UsersTypeCountDto;
import eu.execom.core.model.Country;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;
import eu.execom.core.persistence.base.GenericDao;

/**
 * User DAO interface with set of methods for accessing and manipulating with {@link User} entities.
 * 
 * @author Dusko Vesin
 * 
 */
public interface UserDao extends GenericDao<User> {

    /**
     * Find user by its email address.
     * 
     * @param email
     *            of user.
     * @return matching user if exist, else return <code>null</code>.
     */
    User findByEmail(final String email);

    /**
     * Find user by its authentication code.
     * 
     * @param authenticationCode
     *            of user
     * @return matching user if exist, else return <code>null</code>.
     */
    User findByAuthenticationCode(final String authenticationCode);

    /**
     * Find user bu activation code.
     * 
     * @param activationCode
     *            activation code
     * @return matching user if exist, else return <code>null</code>.
     */
    User findByActivationCode(final String activationCode);

    /**
     * Search for all user that match to criteria provided in search object.
     * 
     * @param searchObject
     *            search object with criteria
     * @return matching {@link User}s
     */
    List<User> search(final UserSearchDto searchObject);

    /**
     * Search for count of all user that match to criteria provided in search object.
     * 
     * @param searchObject
     *            search object with criteria
     * @return count of matching {@link User}s
     */
    Long searchCount(final UserSearchDto searchObject);

    /**
     * Find users by its role.
     * 
     * @param role
     *            of user
     * @return {@link List} of matching {@link User}s
     */
    List<User> findByRole(UserRole role);

    /**
     * Find all users, with {@link UserRole#ADMIN} users first.
     * 
     * @return {@link List} of {@link User}
     */
    List<User> findAllAdminsFirst();

    /**
     * Get country of users per role in specified countries, if countries is null then count for all countries.
     * 
     * @param forCountries
     *            countries to which users belongs to
     * @return {@link List} of {@link UsersTypeCountDto}.
     */
    List<UsersTypeCountDto> getUserCountInCountriesPerRole(List<Country> forCountries);
}
