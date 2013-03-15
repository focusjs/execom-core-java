package eu.execom.core.service;

import eu.execom.core.dto.authentication.RegistrationDto;

/**
 * Registration service, contains all functionality that is necessary to register users.
 * 
 * @author Dusko Vesin
 */
public interface RegistrationService {

    Integer PASSWORD_LENGTH = 10;
    String PASSWORD_RECOVERY_EMAIL = "Password recovery email.";
    String ACTIVATION_MAIL = "Activation mail";

    /**
     * Register new friend to system.
     * 
     * @param dto
     *            registration DTO.
     * @return If everything OK return <code>true</code> else return <code>false</code>.
     */
    boolean register(RegistrationDto dto);

    /**
     * Send recover your password email.
     * 
     * @param email
     *            of user witch password should be reseted
     * @return generated password
     */
    String resetPassword(String email);

    /**
     * Activate user that match to provided registration code.
     * 
     * @param code
     *            activation code
     * @return <code>true</code> if user with code exist, else return <code>false</code>.
     */
    boolean activateUserWithActivationCode(String code);

    /**
     * Generate random password of 10 alpha numeric characters.
     * 
     * @return generated password.
     */
    String generateRandomPassword();

}
