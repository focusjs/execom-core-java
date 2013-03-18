package eu.execom.core.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Address bean contains country, street, street no, city.
 * 
 * @author Dusko Vesin
 */
@Embeddable
public class Address implements Serializable {

    public static final String STREET = "street";
    public static final String STREET_NUMBER = "streetNumber";
    public static final String CITY = "city";

    public static final int STREET_NUMBER_LENGTH = 40;
    public static final int STREET_LENGTH = 40;

    private static final long serialVersionUID = -396542357307164628L;

    @Basic
    @Column(name = "street", nullable = true, length = STREET_LENGTH)
    private String street;

    @Basic
    @Column(name = "street_number", nullable = true, length = STREET_NUMBER_LENGTH)
    private String streetNumber;

    @ManyToOne
    @JoinColumn(name = "city", nullable = true)
    private City city;

    /**
     * Default constructor.
     */
    public Address() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param address
     *            source object.
     */
    public Address(final Address address) {
        super();
        init(address);
    }

    public void init(final Address address) {
        street = address.street;
        streetNumber = address.streetNumber;
        city = address.city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(final String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(final City city) {
        this.city = city;
    }

}
