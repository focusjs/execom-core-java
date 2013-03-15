package eu.execom.core.dto;


/**
 * User DTO for displaying users info in a table.
 * 
 * @author Dusko Vesin
 * 
 */
public class UserTableDto extends AbstractDto {

    private Long id;
    public static final String ID = "id";

    private String email;
    public static final String EMAIL = "email";

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

}
