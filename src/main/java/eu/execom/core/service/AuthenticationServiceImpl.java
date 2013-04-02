package eu.execom.core.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import eu.execom.core.dto.authentication.AuthenticationResponseDto;
import eu.execom.core.dto.authentication.CredentialsDto;
import eu.execom.core.model.User;
import eu.execom.core.persistence.UserDao;

/**
 * {@link AuthenticationService} implementation.
 * 
 * @author Dusko Vesin
 */
@Service
@Transactional
class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public AuthenticationResponseDto authenticate(final String authetnicationCode) throws AuthenticationException {
        Assert.notNull(authetnicationCode, "Authentication code cant be null");
        Assert.isTrue(!authetnicationCode.isEmpty(), "Authentication code cant be empty");

        User user = userDao.findByAuthenticationCode(authetnicationCode);
        if (user == null) {
            throw new AuthenticationException("Authentication code is not valid");
        }

        AuthenticationResponseDto responseDto = new AuthenticationResponseDto();
        responseDto.setName(user.getFirstName() + " " + user.getLastName());
        responseDto.setRole(user.getRole());
        responseDto.setAuthenticationCode(user.getAuthenticationCode());

        return responseDto;
    }

    @Override
    public AuthenticationResponseDto authenticate(final CredentialsDto credentials) throws AuthenticationException {
        Assert.notNull(credentials, "user login DTO can't be null");
        Assert.notNull(credentials.getPassword(), "password can't be null");
        Assert.notNull(credentials.getEmail(), "email name can't be null");

        final User user = userDao.findByEmail(credentials.getEmail());
        if (user == null) {
            // user name is not OK
            throw new AuthenticationException("User with email: " + credentials.getEmail() + " doesnt exist.");
        }
        if (passwordEncoder.isPasswordValid(user.getPassword(), credentials.getPassword(), credentials.getEmail())) {
            // password is OK
        } else if (passwordEncoder.isPasswordValid(user.getRecoveryPassword(), credentials.getPassword(),
                credentials.getEmail())) {
            // recovery password is OK, set it as new password
            user.setPassword(user.getRecoveryPassword());
            // reset recovery password
            user.setRecoveryPassword(null);
        } else {
            throw new AuthenticationException("Password is not OK");
        }

        // set new authcode
        user.setAuthenticationCode(passwordEncoder.encodePassword(user.getEmail(), Calendar.getInstance()));
        userDao.update(user);

        // set return values
        AuthenticationResponseDto responseDto = new AuthenticationResponseDto();
        responseDto.setName(user.getFirstName() + " " + user.getLastName());
        responseDto.setRole(user.getRole());
        responseDto.setAuthenticationCode(user.getAuthenticationCode());

        return responseDto;

    }

    @Override
    public void logout(final String authenticationCode) {
        Assert.notNull(authenticationCode, "user name can't be null");

        final User user = userDao.findByAuthenticationCode(authenticationCode);
        user.setAuthenticationCode(null);
        userDao.update(user);

    }
}
