/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Objects;

public class Address {

    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zip;
    
    /*
     * Constructor for an address with street addresses, city, state, and ZIP code.
     */
    public Address(String streetAddress1, String streetAddress2, String city,
    		String state, String zip) {
    	
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    
    /*
     * Getters
     */	 
    public String getStreetAddress1() {
        return streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
    
    public String getAddressInfo() {
        return streetAddress1 + " " + streetAddress2 + " " + city + " " +
    state + " " + zip;
    }
    
    /*
     * Setters
     */	 
    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }
    
    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2  = streetAddress2 ;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
       this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetAddress1, address.streetAddress1) &&
               Objects.equals(streetAddress2, address.streetAddress2) &&
               Objects.equals(city, address.city) &&
               Objects.equals(state, address.state) &&
               Objects.equals(zip, address.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress1, streetAddress2, city, state, zip);
    }

    /**
     * Returns the full address as a formatted string.
     */
    @Override
    public String toString() {
        return streetAddress1 + (streetAddress2 != null ? ", " + streetAddress2 : "") + ", " +
               city + ", " + state + " " + zip;
    }
}
