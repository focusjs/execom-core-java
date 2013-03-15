package eu.execom.core.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.execom.core.dto.authentication.RegistrationDto;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;
import eu.execom.core.model.UserStatus;
import eu.execom.core.persistence.UserDao;
import eu.execom.core.service.FileServiceImpl.ImageByteArrayDto;
import eu.execom.core.util.FreeMarkerGeneratorImpl;
import freemarker.template.Configuration;

/**
 * {@link RegistrationService} implementation.
 * 
 * @author Dusko Vesin
 */
@Service
@Transactional
class RegistrationServiceImpl implements RegistrationService {

    private static final char[] PASSWORD_ELEMENTS = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();

    private static final String HEADER = "HEADER";

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private FileServiceImpl fileUtil;
    @Autowired
    private Configuration configuration;

    @Value("${site.address}")
    private String siteUrl;

    @Override
    public boolean register(final RegistrationDto registrationDto) {
        if (userService.isEmailInUse(registrationDto.getEmail())) {
            return false;
        }

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setLastName(registrationDto.getLastName());
        user.setFirstName(registrationDto.getFirstName());
        user.setPassword(passwordEncoder.encodePassword(registrationDto.getPassword(), registrationDto.getEmail()));
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.PENDING_EMAIL_REGISTRATION);
        user.setGender(registrationDto.getGender());
        user.setBirthDate(registrationDto.getBirthDate());

        String activationCode = passwordEncoder.encodePassword(user.getEmail(), Calendar.getInstance()
                .getTimeInMillis());
        user.setActivationCode(activationCode);
        userDao.save(user);

        // Create the email message
        final Map<String, ImageByteArrayDto> files = new HashMap<String, ImageByteArrayDto>();
        files.put(HEADER, fileUtil.getEmailHeader());

        final Map<String, Object> templateVars = new HashMap<String, Object>();
        templateVars.put("user", user);
        templateVars.put("siteUrl", siteUrl);

        // send email
        mailService.sendEmail(user.getEmail(), user.getFirstName() + " " + user.getLastName(), ACTIVATION_MAIL,
                templateVars, files, new FreeMarkerGeneratorImpl(configuration, "activation_mail.html"));

        return true;
    }

    @Override
    public boolean activateUserWithActivationCode(final String code) {

        final User user = userDao.findByActivationCode(code);
        if (user == null) {
            return false;
        }

        user.setStatus(UserStatus.ACTIVE);
        user.setActivationCode(null);
        userDao.saveOrUpdate(user);

        return true;
    }

    @Override
    public String resetPassword(final String emaill) {

        String generateRandomPassword = generateRandomPassword();

        User user = userDao.findByEmail(emaill);
        user.setRecoveryPassword(passwordEncoder.encodePassword(generateRandomPassword, user.getEmail()));
        userDao.update(user);

        // set images
        final Map<String, ImageByteArrayDto> files = new HashMap<String, ImageByteArrayDto>();
        files.put(HEADER, fileUtil.getEmailHeader());

        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("email", user.getEmail());
        variables.put("recoveryPassword", user.getRecoveryPassword());

        mailService.sendEmail(user.getEmail(), user.getFirstName() + " " + user.getLastName(), PASSWORD_RECOVERY_EMAIL,
                variables, files, new FreeMarkerGeneratorImpl(configuration, "password_recovery_mail.html"));

        return generateRandomPassword;

    }

    @Override
    public String generateRandomPassword() {

        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            char c = PASSWORD_ELEMENTS[random.nextInt(PASSWORD_ELEMENTS.length)];
            sb.append(c);
        }
        return sb.toString();

    }
}
