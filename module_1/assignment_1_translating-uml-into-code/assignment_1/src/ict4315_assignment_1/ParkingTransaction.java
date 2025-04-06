/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * This is the class that manages the parking transactions. It stores the permit
 * and which lot the vehicle is using.
 */
public class ParkingTransaction {
	
    /*
     * Attributes
     */	 
	private Date date;
	private ParkingPermit permit; // Parking permit used for this transaction
	private ParkingLot parkingLot; // The parking lot where the car is parked
	private Money chargedAmount; // The amount charged for this transaction
    

    /**
     * Constructor to initialize a new parking transaction.
     * @param permit The parking permit associated with this transaction.
     * @param parkingLot The parking lot where the car is parked.
     */
    public ParkingTransaction(Date date, ParkingPermit permit, 
    		ParkingLot parkingLot, Money chargedAmount) {
    	
    	this.date = date;
        this.permit = permit; 
        this.parkingLot = parkingLot;
        this.chargedAmount = chargedAmount;
    }
    
    /*
     * Getters
     */	 
    public Date getDate() {
    	return date;
    }
    
    public ParkingPermit getPermit() {
    	return permit;
    }
    
    public ParkingLot getParkingLot() {
    	return parkingLot;
    }
    
    public Money getChargedAmount() {
    	return chargedAmount;
    }
    
    /*
     * Setters
     */	
    public void setDate(Date date) {
    	this.date = date;
    }
    
    public void setPermit(ParkingPermit permit) {
    	this.permit = permit;
    }
    
    public void setParkingLot(ParkingLot parkingLot) {
    	this.parkingLot = parkingLot;
    }
    
    public void setChargedAmount(Money chargedAmount) {
    	this.chargedAmount = chargedAmount;
    }
    
    /*
     * Methods
     */	 
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingTransaction parkingTransaction = (ParkingTransaction) o;
        return Objects.equals(permit, parkingTransaction.permit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permit);
    }
    
    @Override
    public String toString() {
        return "Parking Transaction{" +
                "Permit='" + permit + '\'' +
                ", Date='" + date + '\'' +
                ", Parking Lot='" + parkingLot + '\'' +
                ", Charged Amount='" + chargedAmount + '\'' +
                '}';
    }
}
