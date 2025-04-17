/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.time.LocalDateTime;
import java.util.Objects;

import ict4315.parking.charges.strategy.ParkingChargeStrategy;

public class ParkingLot {
		
		// Attributes
		private String id; // Unique identifier for the parking lot
		private String name; // Name for the parking lot
		private Address address; // Physical address of the parking lot
		
		private ParkingChargeStrategy chargeStrategy; // adding field to hold strategy

	    
	    /**
	     * Constructor to initialize a ParkingLot.
	     */
		public ParkingLot(String id, String name, Address address, 
				ParkingChargeStrategy chargeStrategy) {
			this.id = id;
			this.name = name;
			this.address = address;
			this.chargeStrategy = chargeStrategy;
	    }
	    
	    /*
	     * Getters
	     */
	    public String getId() {
	    	return id;
	    }
	    public String getName() {
	    	return name;
	    }
	    public Address getAddress() {
	    	return address;
	    }
	    public ParkingChargeStrategy getChargeStrategy() {
	    	return this.chargeStrategy;
	    }
	    
	    /*
	     * Setters
	     */	    
	    public void setId(String id) {
	    	this.id = id;
	    }
	    public void setName(String name) {
	    	this.name = name;
	    }
	    public void setAddress(Address address) {
	    	this.address = address;
	    }
	    public void setChargeStrategy(ParkingChargeStrategy strategy) {
	        this.chargeStrategy = strategy;
	    }
   
	    /*
	     * Methods
	     */	 
	    public Money getDailyRate(CarType carType) {
	        // Simplified rate calculation
	        return new Money(carType == CarType.SUV ? 15.00 : 10.00, "USD");
	    }
	    
	    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime, 
	            LocalDateTime exitTime) {
	        
	        CarType carType = permit.getCar().getType(); // Get car type from permit
	        Money baseRate = getDailyRate(carType);      // Get base rate based on type

	        return chargeStrategy.calculateCharge(permit, entryTime, exitTime, baseRate);
	    }
   
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        ParkingLot parkingLot = (ParkingLot) o;
	        return Objects.equals(id, parkingLot.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }
	    
	    @Override
	    public String toString() {
	        return "Parking Lot{" +
	                "Parking Lot id='" + id + '\'' +
	                ", Parking Lot Name='" + name + '\'' +
	                ", Parking Lot Address='" + address + '\'' +
	                '}';
	    }
	}
	

