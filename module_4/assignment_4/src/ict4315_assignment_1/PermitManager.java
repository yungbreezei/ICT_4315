/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This is the manager class which manages all the parking permits.
 */
public class PermitManager {
	
	private List<ParkingPermit> permits;
	
    // No-argument constructor
    public PermitManager() {
        this.permits = new ArrayList<>();
    }
	
    /*
     * Getters
     */
    public List<ParkingPermit> getPermits() {
        return new ArrayList<>(permits); // Return a copy for encapsulation
    }
    
    /*
     * Setters
     */
    public void setPermits(List<ParkingPermit> permits) {
    	this.permits = permits;
    }
    
    /*
     * Methods
     */	 
	public ParkingPermit register(Car car) {
	    // Check if the car is already registered by matching its license plate
	    for (ParkingPermit permit : permits) {
	        if (permit.getCar().getLicensePlate().equals(car.getLicensePlate())) {
	            throw new IllegalArgumentException("Car is already registered.");
	        }
	    }
	    
        String permitId = UUID.randomUUID().toString();
        // Set expiration to 1 year from now
        LocalDateTime expiration = LocalDateTime.now().plusYears(1);
        ParkingPermit permit = new ParkingPermit(permitId, car, expiration);
        permits.add(permit);
        return permit;
    }  
}
