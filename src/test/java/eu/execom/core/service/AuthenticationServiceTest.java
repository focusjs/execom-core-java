package eu.execom.core.service;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import eu.execom.core.dto.authentication.AuthenticationResponseDto;
import eu.execom.core.dto.authentication.CredentialsDto;
import eu.execom.core.dto.authentication.RegistrationDto;
import eu.execom.core.model.Gender;
import eu.execom.core.model.User;

/**
 * Contains tests for authentication service.
 * 
 * @author Dusko Vesin
 */
public class AuthenticationServiceTest extends AbstractServiceTest {

    @Autowired
    private InitServiceImpl initServiceImpl;
    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;
    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@email.com";

    @Before
    public void setUp() {

        initServiceImpl.initAdminUser();

        // init
        final RegistrationDto dto = new RegistrationDto();
        dto.setEmail(EMAIL);
        dto.setPassword(PASSWORD);
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setGender(Gender.MALE);
        dto.setBirthDate(new Date());

        registrationServiceImpl.register(dto);

        final User user = getUserDao().findByEmail(EMAIL);
        registrationServiceImpl.activateUserWithActivationCode(user.getActivationCode());

        initWizer();
    }

    /**
     * Tests method {@link AuthenticationService#authenticate(CredentialsDto)} with valid credentials.
     * 
     * @throws AuthenticationException
     */
    @Test
    public void testAuthenticate_UserOK() throws AuthenticationException {

        // init
        final CredentialsDto loginDto = new CredentialsDto();
        loginDto.setEmail(EMAIL);
        loginDto.setPassword(PASSWORD);

        // method
        initWizer();
        takeSnapshot(loginDto);
        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(loginDto);

        final User user = getUserDao().findByEmail(EMAIL);
        // assert
        assertEntityWithSnapshot(user, notNull(User.AUTHENTICATIONCODE));

        Assert.assertNotNull(response);
        assertObject(response, value(AuthenticationResponseDto.NAME, user.getFirstName() + " " + user.getLastName()),
                value(AuthenticationResponseDto.AUTHENTICATIONCODE, user.getAuthenticationCode()),
                value(AuthenticationResponseDto.ROLE, user.getRole()));

    }

    /**
     * Tests method {@link AuthenticationService#authenticate(CredentialsDto)} when username not registered.
     * 
     * @throws AuthenticationException
     */
    @Test(expected = AuthenticationException.class)
    public void testAuthenticate_WrongUsername() throws AuthenticationException {

        // init
        final CredentialsDto loginDto = new CredentialsDto();
        loginDto.setEmail("dummy");
        loginDto.setPassword(PASSWORD);

        // method
        initWizer();
        takeSnapshot(loginDto);
        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(loginDto);

        // assert
        Assert.assertNull(response);
    }

    /**
     * Tests method {@link AuthenticationService#authenticate(CredentialsDto)} when password not correct.
     * 
     * @throws AuthenticationException
     */
    @Test(expected = AuthenticationException.class)
    public void testAuthenticate_WrongPassword() throws AuthenticationException {

        // init
        final CredentialsDto loginDto = new CredentialsDto();

        loginDto.setEmail(EMAIL);
        loginDto.setPassword("bad");

        // method
        initWizer();
        takeSnapshot(loginDto);
        authenticationServiceImpl.authenticate(loginDto);

        // assert
        Assert.fail();
    }

    /**
     * Tests method {@link AuthenticationService#authenticate(CredentialsDto)} with recovery password.
     * 
     * @throws AuthenticationException
     */
    @Test
    public void testAuthenticate_withRecoveryPassword() throws AuthenticationException {

        // init
        final String newPassword = registrationServiceImpl.resetPassword(EMAIL);

        final CredentialsDto loginDto = new CredentialsDto();
        loginDto.setEmail(EMAIL);
        loginDto.setPassword(newPassword);

        // method
        initWizer();
        takeSnapshot(loginDto);
        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(loginDto);

        // assert
        final User user = getUserDao().findByEmail(EMAIL);

        Assert.assertNotNull(response);
        assertObject(response, value(AuthenticationResponseDto.NAME, user.getFirstName() + " " + user.getLastName()),
                value(AuthenticationResponseDto.AUTHENTICATIONCODE, user.getAuthenticationCode()),
                value(AuthenticationResponseDto.ROLE, user.getRole()));

        assertEntityWithSnapshot(user, isNull(User.RECOVERYPASSWORD), notNull(User.AUTHENTICATIONCODE),
                value(User.PASSWORD, passwordEncoder.encodePassword(newPassword, EMAIL)));

    }

    /**
     * Test for {@link AuthenticationServiceImpl#authenticate(String)} method with existing code.
     * 
     * @throws AuthenticationException
     *             witch is throw in authentication fails
     */
    @Test
    public void testAuthenticatieExistingCode() throws AuthenticationException {

        // data preparation
        final CredentialsDto loginDto = new CredentialsDto();
        loginDto.setEmail(EMAIL);
        loginDto.setPassword(PASSWORD);

        authenticationServiceImpl.authenticate(loginDto);

        final User user = getUserDao().findByEmail(EMAIL);

        initWizer();
        takeSnapshot(loginDto);
        // method
        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(user.getAuthenticationCode());

        // assert
        Assert.assertNotNull(response);
        assertObject(response, value(AuthenticationResponseDto.NAME, user.getFirstName() + " " + user.getLastName()),
                value(AuthenticationResponseDto.AUTHENTICATIONCODE, user.getAuthenticationCode()),
                value(AuthenticationResponseDto.ROLE, user.getRole()));

    }

    /**
     * Test for {@link AuthenticationServiceImpl#authenticate(String)} method with not existing code.
     * 
     * @throws AuthenticationException
     *             witch is throw in authentication fails
     */
    @Test(expected = AuthenticationException.class)
    public void testAuthenticateNotExistingCode() throws AuthenticationException {

        authenticationServiceImpl.authenticate("bad code");
    }

    /**
     * Tests method {@link AuthenticationService#logout(String)} when password not correct.
     * 
     * @throws AuthenticationException
     */
    @Test
    public void testLogout() throws AuthenticationException {

        // init
        final User user = getUserDao().findByEmail(EMAIL);

        final CredentialsDto loginDto = new CredentialsDto();
        loginDto.setEmail(EMAIL);
        loginDto.setPassword(PASSWORD);

        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(loginDto);

        // method
        initWizer();
        takeSnapshot(loginDto);
        authenticationServiceImpl.logout(response.getAuthenticationCode());

        // assert
        assertEntityWithSnapshot(user, isNull(User.AUTHENTICATIONCODE));
    }

}
