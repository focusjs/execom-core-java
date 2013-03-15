package eu.execom.core.service;

import eu.execom.core.dto.UserAddDto;
import eu.execom.core.dto.UserEditDto;
import eu.execom.core.dto.UserSearchDto;
import eu.execom.core.dto.UserTableDto;
import eu.execom.core.dto.base.SearchResultDto;
import eu.execom.core.model.User;

/**
 * {@link User} service.
 * 
 * @author Dusko Vesin
 * 
 */
public interface UserService {

    /**
     * Convert {@link User} to {@link UserAddDto}.
     * 
     * @param entity
     *            to be converted
     * @return {@link UserAddDto}
     */
    UserAddDto convertToUserAddDtoFrom(User entity);

    /**
     * COnvert {@link User} to {@link UserEditDto}.
     * 
     * @param entity
     *            to be converted
     * @return {@link UserEditDto}.
     */
    UserEditDto convertToUserEditDtoFrom(User entity);

    /**
     * Convert {@link User} to {@link UserTableDto}.
     * 
     * @param entity
     *            to be converted
     * @return {@link UserTableDto}.
     */
    UserTableDto convertToUserTableDtoFrom(User entity);

    /**
     * Delete {@link User} with matching id.
     * 
     * @param id
     *            of user.
     */
    void delete(final Long id);

    /**
     * Add new {@link User} using values from provided DTO.
     * 
     * @param dto
     *            as input for new user.
     */
    void add(UserAddDto dto);

    /**
     * Edit {@link User} using values from provided DTO.
     * 
     * @param dto
     *            as input for editing user.
     */
    void edit(UserEditDto dto);

    /**
     * GEt {@link UserEditDto} of {@link User} with matching id.
     * 
     * @param id
     *            of the user
     * @return {@link UserEditDto}
     */
    UserEditDto getEditDto(Long id);

    /**
     * Search {@link User} according to data from search object.
     * 
     * @param searchObject
     *            with search data.
     * @return {@link SearchResultDto} with result
     */
    SearchResultDto<UserTableDto> search(final UserSearchDto searchObject);

    /**
     * Check is email already in use.
     * 
     * @param value
     *            email to be checked
     * @return <code>true</code> if it is, else return <code>false</code>
     */
    boolean isEmailInUse(final String value);

    /**
     * Check is authentication code already in use.
     * 
     * @param value
     *            authentication code to be checked
     * @return <code>true</code> if it is, else return <code>false</code>
     */
    boolean isAuthenticationCodeInUse(final String value);

    /**
     * Check is activation code in already in use.
     * 
     * @param value
     *            activation code to be checked
     * @return <code>true</code> if it is, else return <code>false</code>
     */
    boolean isActivationCodeInUse(final String value);
}
