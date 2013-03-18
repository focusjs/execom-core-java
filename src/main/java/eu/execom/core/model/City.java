package eu.execom.core.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * City, group users by country they belongs to.
 * 
 * @author Dusko Vesin
 */
@Entity(name = "core_city")
@Table(name = "core_city", uniqueConstraints = {@UniqueConstraint(columnNames = {City.NAME, City.COUNTRY}),
        @UniqueConstraint(columnNames = {City.POSTAL_CODE, City.COUNTRY})})
public class City extends AbstractEntity {

    public static final String DOT = ".";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country", nullable = false)
    private Country country;
    public static final String COUNTRY = "country";

    @Basic
    @Column(name = "name", nullable = false, unique = false)
    private String name;
    public static final String NAME = "name";

    @Basic
    @Column(name = "postalCode", nullable = false, unique = false)
    private String postacityCode;
    public static final String POSTAL_CODE = "postalCode";

    @Basic
    @Column(name = "latitude", nullable = true, unique = false)
    private String latitude;
    public static final String LATITUDE = "latitude";

    @Basic
    @Column(name = "longitude", nullable = true, unique = false)
    private String longitude;
    public static final String LONGITUDE = "longitude";

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(final Country country) {
        this.country = country;
    }

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

    /**
     * @return the postacityCode
     */
    public String getPostacityCode() {
        return postacityCode;
    }

    /**
     * @param postacityCode
     *            the postacityCode to set
     */
    public void setPostacityCode(final String postacityCode) {
        this.postacityCode = postacityCode;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

}
