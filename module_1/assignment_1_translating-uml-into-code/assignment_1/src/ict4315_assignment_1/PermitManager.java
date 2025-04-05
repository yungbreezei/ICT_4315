/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import week9Project.exceptions.DuplicatePermitException;
import week9Project.exceptions.PermitExpiredException;
import week9Project.exceptions.PermitNotFoundException;

/**
 * This is the manager class which manages all the parking permits.
 */
public class PermitManager {
	
	private List<ParkingPermit> permits;
	
	public PermitManager(List<ParkingPermit> permits) {
		
		this.permits = permits;
	}
	
	
    /*
     * Getters
     */	 
	public List<ParkingPermit> getPermits(){
		return permits;
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
	public ParkingPermit register(Car) {
		
	}
	
	

  /**
  * This method will create an object of ParkingPermit class and will add it
  * to the permits collection.
  * Note: Assume that the expiration date will be one year from the current date.
  */  
    public ParkingPermit register(Car car) throws DuplicatePermitException {
        // Check if the car already has a permit
        for (ParkingPermit permit : permits.values()) {
            if (permit.getCar().equals(car)) {
                throw new DuplicatePermitException("A permit already exists for car with license plate: " + car.getLicensePlate());
            }
        }
        String permitId = generatePermitId(); // Generate a unique permit ID
        
        Calendar expirationDate = Calendar.getInstance(); // Set expiration date to one year from now
        expirationDate.add(Calendar.YEAR, 1); // Set expiration to one year from now
        
        Calendar registrationDate = Calendar.getInstance();// Set registration date to the current date
        
        // Create a new ParkingPermit with all required fields
        ParkingPermit permit = new ParkingPermit(permitId, car, expirationDate, registrationDate);
        
        permits.put(permitId, permit);// Store the permit in the map
        return permit; // Return the created permit to confirm registration
    }
    
}
