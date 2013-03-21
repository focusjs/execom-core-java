package eu.execom.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.execom.core.dto.UserAddDto;
import eu.execom.core.dto.UserEditDto;
import eu.execom.core.dto.UserSearchDto;
import eu.execom.core.dto.UserTableDto;
import eu.execom.core.dto.base.SearchResultDto;
import eu.execom.core.model.User;
import eu.execom.core.model.UserStatus;
import eu.execom.core.persistence.UserDao;

/**
 * {@link UserService} default implementation.
 * 
 * @author Dusko Vesin
 * 
 */
@Service
@Transactional
class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao dao;

    @Override
    public UserEditDto convertToUserEditDtoFrom(final User entity) {

        UserEditDto dto = new UserEditDto();
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setPasswordRe(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setBirthDate(entity.getBirthDate());
        dto.setGender(entity.getGender());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());

        return dto;
    }

    @Override
    public UserTableDto convertToUserTableDtoFrom(final User entity) {

        UserTableDto dto = new UserTableDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());

        return dto;
    }

    @Override
    public void delete(final Long id) {
        dao.delete(dao.findById(id));
    };

    @Override
    public void add(final UserAddDto dto) {

        final User entity = new User();
        entity.setStatus(UserStatus.PENDING_EMAIL_REGISTRATION);
        entity.setEmail(dto.getEmail());
        if (dto.getPassword() != entity.getPassword()) {
            String passwordEncoded = passwordEncoder.encodePassword(dto.getPassword(), entity.getEmail());
            entity.setPassword(passwordEncoded);
        }
        entity.setRole(dto.getRole());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        dao.save(entity);

    };

    @Override
    public void edit(final UserEditDto dto) {

        final User entity = dao.findById(dto.getId());
        entity.setId(dto.getId());
        if (dto.getPassword() != entity.getPassword()) {
            String passwordEncoded = passwordEncoder.encodePassword(dto.getPassword(), entity.getEmail());
            entity.setPassword(passwordEncoded);
        }
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        dao.update(entity);

    };

    @Override
    public UserEditDto getEditDto(final Long id) {

        User entity = dao.findById(id);
        return convertToUserEditDtoFrom(entity);

    };

    @Override
    public SearchResultDto<UserTableDto> search(final UserSearchDto searchObject) {

        List<UserTableDto> returnDtos = new ArrayList<UserTableDto>();
        for (User entity : dao.search(searchObject)) {
            returnDtos.add(convertToUserTableDtoFrom(entity));
        }

        final Long size = dao.searchCount(searchObject);
        return new SearchResultDto<UserTableDto>(returnDtos, size);

    };

    @Override
    public boolean isEmailInUse(final String value) {
        return dao.findByEmail(value) != null;
    }

    @Override
    public boolean isAuthenticationCodeInUse(final String value) {
        return dao.findByAuthenticationCode(value) != null;
    }

    @Override
    public boolean isActivationCodeInUse(final String value) {
        return dao.findByActivationCode(value) != null;
    }

}
