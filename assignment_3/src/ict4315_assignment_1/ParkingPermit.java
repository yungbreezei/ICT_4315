/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Date;
import java.util.Objects;

public class ParkingPermit {
	
	private String id;
	private Car car;
	private Date expiration;

    /**
     * Constructor to initialize a ParkingPermit
     */
    public ParkingPermit(String id, Car car, Date expiration) {
    	
        this.id = id; // The unique ID of the parking permit.
        this.car = car; // The car associated with the permit
        this.expiration = expiration; // The expiration date of the permit.
    }
    
    /*
     * Getters
     */	 
    public String getId() {
    	return id;
    }
    
    /*
     * Retrieves the car associated with the permit.
     */
    public Car getCar() {
    	return car;
    }
    
    public Date getExpiration() {
    	return expiration;
    }
    
    /*
     * Setters
     */	 
    public void setId(String id) {
    	this.id = id;
    }
    
    public void setCar(Car car) {
    	this.car = car;
    }
    
    public void setExpiration(Date expiration) {
    	this.expiration = expiration;
    }
    
    
    /*
     * Methods
     */	 
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingPermit parkingPermit = (ParkingPermit) o;
        return Objects.equals(id, parkingPermit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Parking Permit{" +
                "id='" + id + '\'' +
                ", Car='" + car + '\'' +
                ", Expiration Date='" + expiration + '\'' +
                '}';
    }
    
}
