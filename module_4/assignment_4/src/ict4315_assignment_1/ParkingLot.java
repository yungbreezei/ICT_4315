/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.time.LocalDateTime;
import java.util.Objects;

import ict4315.parking.charges.strategy.HourlyRateStrategy;
import ict4315.parking.charges.strategy.ParkingChargeStrategy;

public class ParkingLot {
		
		// Attributes
		private String id; // Unique identifier for the parking lot
		private String name; // Name for the parking lot
		private Address address; // Physical address of the parking lot
		
		private ParkingChargeStrategy chargeStrategy; // adding field to hold strategy
		private Money baseRate; // Base rate used as a foundation for charge strategies
	    private int capacity; // Parking lot car capacity

	    /**
	     * Constructor to initialize a ParkingLot.
	     */
	    // Constructor with ParkingChargeStrategy
	    public ParkingLot(String id, String name, Address address, 
	    		ParkingChargeStrategy chargeStrategy, double baseRate, int capacity) {
	        this.id = id;
	        this.name = name;
	        this.address = address;
	        this.chargeStrategy = chargeStrategy;
	        this.baseRate = new Money(baseRate, "USD");
	        this.capacity = capacity;
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
	    public Money getBaseRate() {
	        return baseRate;
	    }
	    public ParkingChargeStrategy getChargeStrategy() {
	    	return this.chargeStrategy;
	    }
	    public int getCapacity() {
	        return capacity;
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
	    public void setBaseRate(Money baseRate) {
	        this.baseRate = baseRate;
	    }
	    public void setChargeStrategy(ParkingChargeStrategy strategy) {
	        this.chargeStrategy = strategy;
	    }
	    public void setCapacity(int capacity) {
	        this.capacity = capacity;
	    }
   
	    /*
	     * Methods
	     */	 
	    public Money getDailyRate(CarType carType) {
	        return new Money(carType == CarType.SUV ? 15.00 : 10.00, "USD");
	    }
	    
	    public Money calculateCharge(ParkingPermit permit, LocalDateTime entryTime, 
	            LocalDateTime exitTime) {
	        
	        CarType carType = permit.getCar().getType(); // Get car type from permit
	        Money baseRate = getDailyRate(carType);      // Get base rate based on type

	        return chargeStrategy.calculateCharge(permit, entryTime, exitTime, baseRate);
	    }
	    

		public boolean isHourly() {
			return chargeStrategy.isHourlyBased();
		}
	    
	    /**
	     * Compares this parking lot with another for equality based on ID
	     * 
	     * @param o the other object
	     * @return true if IDs match, false otherwise
	     */
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        ParkingLot parkingLot = (ParkingLot) o;
	        return Objects.equals(id, parkingLot.id);
	    }
	    // hash code based on lot ID
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