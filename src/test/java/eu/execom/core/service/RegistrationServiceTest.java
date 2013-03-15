package eu.execom.core.service;

import static eu.execom.testutil.property.Property.changed;
import static eu.execom.testutil.property.Property.notNull;
import static eu.execom.testutil.property.Property.nulll;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eu.execom.core.dto.authentication.RegistrationDto;
import eu.execom.core.model.Gender;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;
import eu.execom.core.model.UserStatus;

/**
 * Test {@link RegistrationServiceImpl}.
 * 
 * @author Dusko Vesin
 * 
 */
public class RegistrationServiceTest extends AbstractServiceTest {

    private final String email = "email@email.com";
    private RegistrationDto dto;

    @Autowired
    private InitServiceImpl initService;
    @Autowired
    private RegistrationServiceImpl registrationService;

    @Before
    public void setUp() {
        initService.initAdminUser();

        dto = new RegistrationDto();
        dto.setEmail(email);
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setPassword("password");
        dto.setPasswordRe("password");
        dto.setBirthDate(new Date());
        dto.setGender(Gender.MALE);

    }

    /**
     * Test {@link RegistrationServiceImpl#register(RegistrationDto)} method.
     */
    @Test
    public void testRegster() {

        // init
        takeSnapshot();

        // method
        registrationService.register(dto);

        // assert
        assertEmail(email, "Activation mail");

        User user = getUserDao().findByEmail(email);
        assertObject(user, changed(User.FIRSTNAME, dto.getFirstName()), changed(User.EMAIL, dto.getEmail()),
                changed(User.LASTNAME, dto.getLastName()), changed(User.STATUS, UserStatus.PENDING_EMAIL_REGISTRATION),
                nulll(User.AUTHENTICATIONCODE), nulll(User.RECOVERYPASSWORD), notNull(User.ACTIVATIONCODE),
                changed(User.ROLE, UserRole.USER), notNull(User.ID), notNull(User.PASSWORD),
                changed(User.GENDER, dto.getGender()), changed(User.BIRTHDATE, dto.getBirthDate()));

    }

    /**
     * Test {@link RegistrationServiceImpl#activateUserWithActivationCode(String)} with existing code method.
     */
    @Test
    public void testActivateUserWithRegistrationCodeExisting() {

        // init
        registrationService.register(dto);

        User user = getUserDao().findByEmail(email);

        takeSnapshot();
        // method
        boolean activateUserWithRegistrationCode = registrationService.activateUserWithActivationCode(user
                .getActivationCode());

        // assert
        assertTrue(activateUserWithRegistrationCode);

        user = getUserDao().findByEmail(email);
        assertEntityWithSnapshot(user, nulll(User.ACTIVATIONCODE), changed(User.STATUS, UserStatus.ACTIVE));

    }

    /**
     * Test {@link RegistrationServiceImpl#activateUserWithActivationCode(String)} with non existing code method.
     */
    @Test
    public void testActivateUserWithActivationCodeNoneExisting() {

        takeSnapshot();
        // method
        boolean successful = registrationService.activateUserWithActivationCode("invalidCode");

        // assert
        assertFalse(successful);
    }

    /**
     * Test {@link RegistrationServiceImpl#sendRecoverPasswordEmail(com.vesin.backend.model.User, String)} method.
     */
    @Test
    public void testSendRecoverPasswordEmail() {

        // init
        registrationService.register(dto);
        takeSnapshot();

        // method
        registrationService.resetPassword(dto.getEmail());

        // assert
        assertEmail(email, "Password recovery email.");

        User user = getUserDao().findByEmail(email);
        assertEntityWithSnapshot(user, notNull(User.RECOVERYPASSWORD));

    }

    /**
     * Test {@link RegistrationServiceImpl#generateRandomPassword()} method.
     */
    @Test
    public void testGenerateRandomPassword() {

        takeSnapshot();
        // method

        String generateRandomPassword = registrationService.generateRandomPassword();

        // assert
        assertNotNull(generateRandomPassword);
        assertEquals(10, generateRandomPassword.length());

    }
}
