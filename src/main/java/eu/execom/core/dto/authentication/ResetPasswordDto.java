package eu.execom.core.dto.authentication;

import eu.execom.core.dto.AbstractDto;

/**
 * Reset password DTO with data that are necessary for data reseting.
 * 
 * @author Dusko Vesin
 * 
 */
public class ResetPasswordDto extends AbstractDto {

    private String email;
    public static final String EMAIL = "email";

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
}
