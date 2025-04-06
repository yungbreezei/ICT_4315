/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Objects;
import java.util.Properties;

/**
 * Represents a customer who can own cars and parking permits within the system.
 */
public class Customer {
	
	private String id; // Unique identifier for the customer
	private String firstName; // Customer's name
	private String lastName; // Customer's name
	private String phoneNumber; // Customer's phone number (formatted)
    private Address address; // Address object representing customer's address
	
    /*
     * Constructs a new Customer.
     */
    public Customer(String id, String firstName, String lastName,  
    		String phoneNumber, Address address) {
    	
        this.id = id;
        this.firstName = firstName;        
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address; 
    }
    
    /*
     * Getters
     */	 
    public String getId() {
    	return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getCustomerName() {
        return firstName + " " + lastName;
    }
    
    public String getPhoneNumber() {
    	return phoneNumber;
    }
    
    public Address getAddress() {
    	return address;
    }  
    
    /*
     * Setters
     */	 
    public void setId(String id) {
    	this.id = id;
    }
    
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        // The new phone number (format: XXX-XXX-XXXX)
        if (!phoneNumber.matches("^\\d{3}-\\d{3}-\\d{4}$")) {
            throw new IllegalArgumentException("Invalid phone number format. "
            		+ "Expected: XXX-XXX-XXXX");
        }
        this.phoneNumber = phoneNumber; // Update phone number if valid
    }
    
    public void setAddress(Address address) {
    	this.address = address;
    }
    
    /*
     * Methods
     */	 
    public static Customer create(Properties properties) {
        return new Customer(
            properties.getProperty("id"),
            properties.getProperty("firstName"),
            properties.getProperty("lastName"),
            properties.getProperty("phoneNumber"),
            new Address(
                properties.getProperty("streetAddress1"),
                properties.getProperty("streetAddress2"),
                properties.getProperty("city"),
                properties.getProperty("state"),
                properties.getProperty("zip")
            )
        );
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
