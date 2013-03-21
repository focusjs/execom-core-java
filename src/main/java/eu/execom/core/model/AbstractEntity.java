package eu.execom.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract entity class, contains common fields for all entities.
 * 
 * @author Dusko Vesin
 * 
 */
@MappedSuperclass
public class AbstractEntity {

    /**
     * Path separator for hibernate aliases. Value: {@value DOT}
     */
    protected static final String DOT = ".";

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    public static final String ID = "id";

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Generate path from properties list.
     * 
     * @param properties
     *            hierarchy of a property
     * @return String representation of property path that looks like "property1.property2.property3"
     */
    public static String path(final String... properties) {
        StringBuilder pathBuilder = new StringBuilder();

        for (String string : properties) {
            pathBuilder.append(string).append(DOT);
        }
        String path = pathBuilder.toString();

        return path.substring(0, path.length() - 1);
    }
}
