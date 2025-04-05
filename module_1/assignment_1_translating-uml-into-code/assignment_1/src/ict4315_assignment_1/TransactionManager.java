/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import week9Project.exceptions.CarAlreadyParkedException;
import week9Project.exceptions.InvalidParkingPassException;
import week9Project.exceptions.NoActiveTransactionException;

/**
 * This is the class that manages all the parking transactions.
 */
public class TransactionManager {
	
	private List<ParkingTransaction> transactions;

	    // Constructor to initialize the transaction manager
	    public TransactionManager(List<ParkingTransaction> transactions) {
	    	
	        this.transactions =  transactions;
	    }
	    
	    /*
	     * Getters
	     */	    
	    public List<ParkingTransaction> getTransactions(){
	    	return transactions;
	    }
	    
	    /*
	     * Setters
	     */	 
	    public void setTransactions(List<ParkingTransaction> transactions){
	    	this.transactions = transactions;
	    }
	    
	    /*
	     * Methods
	     */	 
	    public ParkingTransaction park(Calendar date, ParkingPermit permit, ParkingLot lot) {
	    	
	    }
	    
	    public Money getParkingCharges(ParkingPermit permit) {
	    	
	    }
	    
	    public Money getParkingCharges(Customer) {
	    	
	    }
	    
	    /**
	     * Creates a parking transaction, adds it to the transaction list, and associates it with the permit.
	     * @param date The date when the vehicle is parked.
	     * @param permit The parking permit for the vehicle.
	     * @param lot The parking lot where the vehicle is parked.
	     */
	    public ParkingTransaction park(Calendar date, ParkingPermit permit, ParkingLot lot) throws CarAlreadyParkedException {
	        // Store transactions by vehicle's license plate
	        String licensePlate = permit.getCar().getLicensePlate();
	        
	        // Check if the car is already parked in the lot
	        if (vehicleTransaction.containsKey(licensePlate)) {
	            throw new CarAlreadyParkedException("Car with license plate " + licensePlate + " is already parked.");
	        }
	        ParkingTransaction transaction = new ParkingTransaction(permit, lot);
	        transactions.add(transaction);
	        
	        vehicleTransaction.computeIfAbsent(licensePlate, k -> new ArrayList<>()).add(transaction);

	        return transaction;  // return The newly created ParkingTransaction.
	    }

	    /**
	     * Calculates total parking charges for a specific parking permit.
	     * @param permit The parking permit to calculate charges for.
	     */
	    public Money getParkingCharges(ParkingPermit permit) {
	    	
	        // Initialize total charges with a default value of 0
	        Money totalCharges = new Money(0.0);
	        String licensePlate = permit.getCar().getLicensePlate();
	        
	        if (vehicleTransaction.containsKey(licensePlate)) {
	            for (ParkingTransaction transaction : vehicleTransaction.get(licensePlate)) 
	            {
	                totalCharges = totalCharges.add(transaction.getCharge());
	            	
	            }
	        }

	        return totalCharges; // return The total parking charges as a Money object.
	    }
	    
	}
