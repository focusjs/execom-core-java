package eu.execom.core.dto;

import eu.execom.core.dto.base.AbstractSearchDto;
import eu.execom.core.model.Gender;
import eu.execom.core.model.UserRole;

/**
 * User search object.
 * 
 * @author Dusko Vesin
 * 
 */
public class UserSearchDto extends AbstractSearchDto {

    private String email;
    public static final String EMAIL = "email";

    private Gender gender;
    public static final String GENDER = "gender";

    private UserRole role;
    public static final String ROLE = "role";

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
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender
     *            the gender to set
     */
    public void setGender(final Gender gender) {
        this.gender = gender;
    }

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

}
