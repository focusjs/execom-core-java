package eu.execom.core.dto;

import java.util.Date;

import eu.execom.core.model.Gender;
import eu.execom.core.model.UserRole;

/**
 * Add user DTO object.
 * 
 * @author Dusko Vesin
 * 
 */
public class UserAddDto extends AbstractDto {

    private String email;
    public static final String EMAIL = "email";

    private String password;
    public static final String PASSWORD = "password";

    private String passwordRe;
    public static final String PASSWORDRE = "passwordRe";

    private UserRole role;
    public static final String ROLE = "role";

    private Date birthDate;
    public static final String BIRTHDATE = "birthDate";

    private Gender gender;
    public static final String GENDER = "gender";

    private String firstName;
    public static final String FIRSTNAME = "firstName";

    private String lastName;
    public static final String LASTNAME = "lastName";

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

    /**
     * @return the passwordRe
     */
    public String getPasswordRe() {
        return passwordRe;
    }

    /**
     * @param passwordRe
     *            the passwordRe to set
     */
    public void setPasswordRe(final String passwordRe) {
        this.passwordRe = passwordRe;
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

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate
     *            the birthDate to set
     */
    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

}
