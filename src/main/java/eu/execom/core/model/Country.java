package eu.execom.core.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * CountryType, group users by country they belongs to.
 * 
 * @author Dusko Vesin
 */
@Entity(name = "core_country")
public class Country extends AbstractEntity {

    public static final int COUNTRY_NAME_LENGHT = 40;

    @Basic
    @Column(name = "name", nullable = false, unique = true, length = COUNTRY_NAME_LENGHT)
    private String name;
    public static final String NAME = "name";

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

}
