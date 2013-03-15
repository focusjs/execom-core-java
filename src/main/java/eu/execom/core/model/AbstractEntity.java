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

}
