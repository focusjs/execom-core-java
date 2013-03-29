package eu.execom.core.service;

import java.util.Date;

import junit.framework.Assert;

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
        initWizer();
        takeSnapshot(dto);

        // method
        registrationService.register(dto);

        // assert
        assertEmail(email, "Activation mail");

        final User user = getUserDao().findByEmail(email);
        assertObject(user, value(User.FIRSTNAME, dto.getFirstName()), value(User.EMAIL, dto.getEmail()),
                value(User.LASTNAME, dto.getLastName()), value(User.STATUS, UserStatus.PENDING_EMAIL_REGISTRATION),
                isNull(User.AUTHENTICATIONCODE), isNull(User.RECOVERYPASSWORD), notNull(User.ACTIVATIONCODE),
                value(User.ROLE, UserRole.USER), notNull(User.ID), notNull(User.PASSWORD), isNull(User.ADDRESS),
                value(User.GENDER, dto.getGender()), value(User.BIRTHDATE, dto.getBirthDate()));

    }

    /**
     * Test {@link RegistrationServiceImpl#activateUserWithActivationCode(String)} with existing code method.
     */
    @Test
    public void testActivateUserWithRegistrationCodeExisting() {

        // init
        registrationService.register(dto);

        User user = getUserDao().findByEmail(email);

        initWizer();
        takeSnapshot(dto);
        // method
        final boolean activateUserWithRegistrationCode = registrationService.activateUserWithActivationCode(user
                .getActivationCode());

        // assert
        Assert.assertTrue(activateUserWithRegistrationCode);

        user = getUserDao().findByEmail(email);
        assertEntityWithSnapshot(user, isNull(User.ACTIVATIONCODE), value(User.STATUS, UserStatus.ACTIVE));

    }

    /**
     * Test {@link RegistrationServiceImpl#activateUserWithActivationCode(String)} with non existing code method.
     */
    @Test
    public void testActivateUserWithActivationCodeNoneExisting() {

        initWizer();
        takeSnapshot();
        // method
        final boolean successful = registrationService.activateUserWithActivationCode("invalidCode");

        // assert
        Assert.assertFalse(successful);
    }

    /**
     * Test {@link RegistrationServiceImpl#sendRecoverPasswordEmail(com.vesin.backend.model.User, String)} method.
     */
    @Test
    public void testSendRecoverPasswordEmail() {

        // init
        registrationService.register(dto);
        initWizer();
        takeSnapshot();

        // method
        registrationService.resetPassword(dto.getEmail());

        // assert
        assertEmail(email, "Password recovery email.");

        final User user = getUserDao().findByEmail(email);
        assertEntityWithSnapshot(user, notNull(User.RECOVERYPASSWORD));

    }

    /**
     * Test {@link RegistrationServiceImpl#generateRandomPassword()} method.
     */
    @Test
    public void testGenerateRandomPassword() {

        initWizer();
        takeSnapshot();
        // method

        final String generateRandomPassword = registrationService.generateRandomPassword();

        // assert
        Assert.assertNotNull(generateRandomPassword);
        Assert.assertEquals(10, generateRandomPassword.length());

    }
}
