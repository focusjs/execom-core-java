package eu.execom.core.service;

import eu.execom.core.dto.authentication.AuthenticationResponseDto;
import eu.execom.core.dto.authentication.CredentialsDto;

/**
 * Authentications service.
 * 
 * @author Dusko Vesin
 */
public interface AuthenticationService {

    /**
     * Authenticate user login data.
     * 
     * @param code
     *            authentication code
     * @return If user name and password matches to any user in system return {@link AuthenticationResponseDto} with
     *         filled status property .
     * @throws AuthenticationException
     *             if authetnicationCode is not OK.
     */
    AuthenticationResponseDto authenticate(String code) throws AuthenticationException;

    /**
     * Login user to system using user name and password.
     * 
     * @param credentials
     *            credentials DTO
     * @return If user name and password matches to any user in system return {@link AuthenticationResponseDto} with
     *         filled status property .
     * @throws AuthenticationException
     *             if user credentials are not OK.
     */
    AuthenticationResponseDto authenticate(CredentialsDto credentials) throws AuthenticationException;

    /**
     * Delete session info about user.
     * 
     * @param code
     *            authentication code.
     */
    void logout(String code);

}
