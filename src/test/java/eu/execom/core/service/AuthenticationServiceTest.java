package eu.execom.core.service;

import static eu.execom.testutil.property.Property.changed;
import static eu.execom.testutil.property.Property.notNull;
import static eu.execom.testutil.property.Property.nulll;

import java.util.Date;

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
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail(EMAIL);
        dto.setPassword(PASSWORD);
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setGender(Gender.MALE);
        dto.setBirthDate(new Date());

        registrationServiceImpl.register(dto);

        User user = getUserDao().findByEmail(EMAIL);
        registrationServiceImpl.activateUserWithActivationCode(user.getActivationCode());

        takeSnapshot();
    }

    @Autowired
    private UserService userService;

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
        takeSnapshot();
        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(loginDto);

        User user = getUserDao().findByEmail(EMAIL);
        // assert
        assertEntityWithSnapshot(user, notNull(User.AUTHENTICATIONCODE));

        assertNotNull(response);
        assertObject(response, changed(AuthenticationResponseDto.NAME, user.getFirstName() + " " + user.getLastName()),
                changed(AuthenticationResponseDto.AUTHENTICATIONCODE, user.getAuthenticationCode()),
                changed(AuthenticationResponseDto.ROLE, user.getRole()));

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
        takeSnapshot();
        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(loginDto);

        // assert
        assertNull(response);
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
        takeSnapshot();
        authenticationServiceImpl.authenticate(loginDto);

        // assert
        fail();
    }

    /**
     * Tests method {@link AuthenticationService#authenticate(CredentialsDto)} with recovery password.
     * 
     * @throws AuthenticationException
     */
    @Test
    public void testAuthenticate_withRecoveryPassword() throws AuthenticationException {

        // init
        String newPassword = registrationServiceImpl.resetPassword(EMAIL);

        final CredentialsDto loginDto = new CredentialsDto();
        loginDto.setEmail(EMAIL);
        loginDto.setPassword(newPassword);

        // method
        takeSnapshot();
        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(loginDto);

        // assert
        User user = getUserDao().findByEmail(EMAIL);

        assertNotNull(response);
        assertObject(response, changed(AuthenticationResponseDto.NAME, user.getFirstName() + " " + user.getLastName()),
                changed(AuthenticationResponseDto.AUTHENTICATIONCODE, user.getAuthenticationCode()),
                changed(AuthenticationResponseDto.ROLE, user.getRole()));

        assertEntityWithSnapshot(user, nulll(User.RECOVERYPASSWORD), notNull(User.AUTHENTICATIONCODE),
                changed(User.PASSWORD, passwordEncoder.encodePassword(newPassword, EMAIL)));

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

        User user = getUserDao().findByEmail(EMAIL);

        takeSnapshot();
        // method
        AuthenticationResponseDto response = authenticationServiceImpl.authenticate(user.getAuthenticationCode());

        // assert
        assertNotNull(response);
        assertObject(response, changed(AuthenticationResponseDto.NAME, user.getFirstName() + " " + user.getLastName()),
                changed(AuthenticationResponseDto.AUTHENTICATIONCODE, user.getAuthenticationCode()),
                changed(AuthenticationResponseDto.ROLE, user.getRole()));

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
        User user = getUserDao().findByEmail(EMAIL);

        final CredentialsDto loginDto = new CredentialsDto();
        loginDto.setEmail(EMAIL);
        loginDto.setPassword(PASSWORD);

        final AuthenticationResponseDto response = authenticationServiceImpl.authenticate(loginDto);

        // method
        takeSnapshot();
        authenticationServiceImpl.logout(response.getAuthenticationCode());

        // assert
        assertEntityWithSnapshot(user, nulll(User.AUTHENTICATIONCODE));
    }

}
