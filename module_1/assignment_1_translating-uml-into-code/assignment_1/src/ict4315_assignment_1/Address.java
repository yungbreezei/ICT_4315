/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Objects;
import java.util.regex.Pattern;

public class Address {

    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zip;
    
    /*
     * Constructor for an address with primary address, city, state, and ZIP code.
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
    
    /*
     * Setters
     */	 
    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }
    
    public void setStreetAddress2(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
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


    /**
     * Returns the full address as a formatted string.
     */
    public String getAddressInfo() {
        // Use a StringBuilder for better performance with string concatenation
        StringBuilder info = new StringBuilder(streetAddress1);
        
        // Include the secondary address line if it's provided
        if (streetAddress2 != null && !streetAddress2.trim().isEmpty()) {
            info.append(", ").append(streetAddress2);
        }
        
        // Append the city, state, and ZIP code
        info.append(", ").append(city)
            .append(", ").append(state)
            .append(" ").append(zip);
        
        return info.toString();
    }
    
}
