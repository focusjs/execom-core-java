package eu.execom.core.dto.authentication;

import eu.execom.core.dto.AbstractDto;

/**
 * User credentials DTO contains necessary user data for authentication with.
 * 
 * @author Dusko Vesin
 * 
 */
public class CredentialsDto extends AbstractDto {

    private String email;
    public static final String EMAIL = "email";

    private String password;
    public static final String PASSWORD = "password";

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
