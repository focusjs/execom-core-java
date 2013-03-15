package eu.execom.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * User entity.
 * 
 * @author Dusko Vesin
 * 
 */
@Entity(name = "core_user")
public class User extends AbstractEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    public static final String EMAIL = "email";

    @Column(name = "password", nullable = false, unique = false)
    private String password;
    public static final String PASSWORD = "password";

    @Column(name = "recoveryPassword", nullable = true, unique = false)
    private String recoveryPassword;
    public static final String RECOVERYPASSWORD = "recoveryPassword";

    @Column(name = "authenticationCode", nullable = true, unique = true)
    private String authenticationCode;
    public static final String AUTHENTICATIONCODE = "authenticationCode";

    @Column(name = "activationCode", nullable = true, unique = true)
    private String activationCode;
    public static final String ACTIVATIONCODE = "activationCode";

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false, unique = false)
    private UserStatus status;
    public static final String STATUS = "status";

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false, unique = false)
    private UserRole role;
    public static final String ROLE = "role";

    @Column(name = "birthDate", nullable = false, unique = false)
    private Date birthDate;
    public static final String BIRTHDATE = "birthDate";

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false, unique = false)
    private Gender gender;
    public static final String GENDER = "gender";

    @Column(name = "firstName", nullable = false, unique = false)
    private String firstName;
    public static final String FIRSTNAME = "firstName";

    @Column(name = "lastName", nullable = false, unique = false)
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
     * @return the recoveryPassword
     */
    public String getRecoveryPassword() {
        return recoveryPassword;
    }

    /**
     * @param recoveryPassword
     *            the recoveryPassword to set
     */
    public void setRecoveryPassword(final String recoveryPassword) {
        this.recoveryPassword = recoveryPassword;
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

    /**
     * @return the activationCode
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * @param activationCode
     *            the activationCode to set
     */
    public void setActivationCode(final String activationCode) {
        this.activationCode = activationCode;
    }

    /**
     * @return the status
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(final UserStatus status) {
        this.status = status;
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
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
}
