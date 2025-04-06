/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Objects;

public class ParkingLot {
		
		// Attributes
		private String id; // Unique identifier for the parking lot
		private String name; // Name for the parking lot
		private Address address; // Physical address of the parking lot
	    
	    /**
	     * Constructor to initialize a ParkingLot.
	     */
		public ParkingLot(String id, String name, Address address) {
			this.id = id;
			this.name = name;
			this.address = address;
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
   
	    /*
	     * Methods
	     */	 
	    public Money getDailyRate(CarType carType) {
	        // Simplified rate calculation
	        return new Money(carType == CarType.SUV ? 15.00 : 10.00, "USD");
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
	

