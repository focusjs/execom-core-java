package eu.execom.core.dto;

import eu.execom.core.model.UserRole;

/**
 * DTO that contains information about count of specific {@link UserRole}s.
 * 
 * @author Dusko Vesin
 * 
 */
public class UsersTypeCountDto extends AbstractDto {

    private UserRole role;
    private Long count;

    /**
     * @return the role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(final UserRole role) {
        this.role = role;
    }

    /**
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * @param count
     *            the count to set
     */
    public void setCount(final Long count) {
        this.count = count;
    }

}
