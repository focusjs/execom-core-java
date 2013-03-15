package eu.execom.core.dto.authentication;

import java.util.Date;

import eu.execom.core.dto.AbstractDto;
import eu.execom.core.model.Gender;

/**
 * Registration DTO contains all data necessary for registration new user in the system.
 * 
 * @author Dusko Vesin
 * 
 */
public class RegistrationDto extends AbstractDto {

    private String email;
    public static final String EMAIL = "email";

    private String password;
    public static final String PASSWORD = "password";

    private String passwordRe;
    public static final String PASSWORDRE = "passwordRe";

    private String firstName;
    public static final String FIRSTNAME = "firstName";

    private String lastName;
    public static final String LASTNAME = "lastName";

    private Gender gender;
    public static final String GENDER = "gender";

    private Date birthDate;
    public static final String BIRTHDATE = "birthDate";

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

}
