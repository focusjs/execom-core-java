package eu.execom.core.dto.authentication;

import eu.execom.core.dto.AbstractDto;
import eu.execom.core.model.UserRole;

/**
 * Authentication response DTO object with data that represent authenticated user.
 * 
 * @author Dusko Vesin
 * 
 */
public class AuthenticationResponseDto extends AbstractDto {

    private UserRole role;
    public static final String ROLE = "role";

    private String name;
    public static final String NAME = "name";

    private String authenticationCode;
    public static final String AUTHENTICATIONCODE = "authenticationCode";

    /**
     * @return the role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(final UserRole role) {
        this.role = role;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the authenticationCode
     */
    public String getAuthenticationCode() {
        return authenticationCode;
    }

    /**
     * @param authenticationCode
     *            the authenticationCode to set
     */
    public void setAuthenticationCode(final String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }

}
