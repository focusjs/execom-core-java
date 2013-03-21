package eu.execom.core.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.execom.core.dto.UserAddDto;
import eu.execom.core.model.Gender;
import eu.execom.core.model.User;
import eu.execom.core.model.UserRole;
import eu.execom.core.persistence.UserDao;

/**
 * {@link InitService} default implementation.
 * 
 * @author Dusko Vesin
 * 
 */
@Service
@Transactional
public class InitServiceImpl implements InitService {

    private static final String ADMIN_USERNAME = "vesindusko@gmail.com";

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Value("${init.db}")
    private boolean init;

    /**
     * Initialize the system according to init property.
     */
    @PostConstruct
    public final void init() {
        if (init) {
            initAdminUser();
            initUser();
        }
    }

    /**
     * Initialize user with role ADMIN in DB if there is no {@link User} with role ADMIN in DB.
     */
    @Override
    public void initAdminUser() {
        final List<User> admins = userDao.findByRole(UserRole.ADMIN);
        if (admins.size() == 0) {
            final UserAddDto admin = new UserAddDto();
            admin.setEmail(ADMIN_USERNAME);
            admin.setPassword("password");
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setRole(UserRole.ADMIN);
            admin.setGender(Gender.MALE);
            admin.setBirthDate(new Date());
            userService.add(admin);
        }
    }

    /**
     * Initialize user with role USER in DB if there is no {@link User} with role USER in DB.
     */
    public void initUser() {
        final List<User> users = userDao.findByRole(UserRole.USER);
        if (users.size() == 0) {
            final UserAddDto user = new UserAddDto();
            user.setEmail("user@testniserver.com");
            user.setPassword("password");
            user.setFirstName("Fisrt broker");
            user.setLastName("Last broker");
            user.setRole(UserRole.USER);
            user.setGender(Gender.MALE);
            user.setBirthDate(new Date());
            userService.add(user);
        }
    }

}
