package eu.execom.core.persistence.transformer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import eu.execom.core.dto.UsersTypeCountDto;
import eu.execom.core.model.UserRole;

/**
 * {@link ResultTransformer} for {@link UsersTypeCountDto}.
 * 
 * @author Dusko Vesin
 * 
 */
public class UsersRoleCountDtoResultTransformer implements ResultTransformer {

    private static final long serialVersionUID = -8779052405817441815L;

    @Override
    public Object transformTuple(final Object[] tuple, final String[] aliases) {
        final UsersTypeCountDto usersTypeCountDto = new UsersTypeCountDto();

        usersTypeCountDto.setCount((Long) tuple[0]);
        usersTypeCountDto.setRole((UserRole) tuple[1]);

        return usersTypeCountDto;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List transformList(final List collection) {
        final List<UsersTypeCountDto> resultList = new ArrayList<UsersTypeCountDto>();
        for (final Object object : collection) {
            Assert.isInstanceOf(UsersTypeCountDto.class, object);
            final UsersTypeCountDto usersNetworkTypeCountDto = (UsersTypeCountDto) object;
            resultList.add(usersNetworkTypeCountDto);
        }
        return resultList;
    }

}
