/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID; // Needed to generate unique transaction IDs

import ict4315.parking.charges.strategy.ParkingChargeStrategy;


/**
 * This is the class that manages all the parking transactions.
 */
public class TransactionManager {
	
    private List<ParkingTransaction> transactions;
    private ParkingChargeStrategy strategy;

	    // Constructor to initialize the transaction manager
	    public TransactionManager(ParkingChargeStrategy strategy) {
	    	
	        this.transactions = new ArrayList<>();
	        this.strategy = strategy;
	    }
	    
	    /*
	     * Getters
	     */
	    public List<ParkingTransaction> getTransactions() {
	        return new ArrayList<>(transactions); // Return a copy for encapsulation
	    }
	    public ParkingChargeStrategy getStrategy() {
	        return strategy;
	    }
	    
	    /*
	     * Setters
	     */
	    public void setTransactions(List<ParkingTransaction> transactions) {
	    	this.transactions = transactions;
	    }
	    
	    public void setStrategy(ParkingChargeStrategy strategy) {
	        this.strategy = strategy;
	    }
	    
	    /*
	     * Handles parking action and logs a new transaction
	     */
	    public ParkingTransaction park(LocalDateTime entryTime, LocalDateTime exitTime,
                ParkingPermit permit, ParkingLot parkingLot) {
	    	
	    	// Generate unique transaction ID
	        String transactionId = UUID.randomUUID().toString();
	        
	        Money baseRate = parkingLot.getBaseRate(); // e.g., $5.00 per day/hour
	        
	        ParkingChargeStrategy strategy = parkingLot.getChargeStrategy();

	        if (strategy == null) {
	            throw new IllegalArgumentException("Parking lot must have a charge strategy assigned.");
	        }

	        // Calculate the amount charged 
	        Money chargedAmount = strategy.calculateCharge(permit, entryTime, exitTime, baseRate);

	        // Create and record the transaction
	        ParkingTransaction transaction = new ParkingTransaction(
	            transactionId,
	            exitTime,
	            permit,
	            parkingLot,
	            chargedAmount
	        );

	        transactions.add(transaction);
	        return transaction;
	    }
	    
	    /*
	     * Returns total charges for a specific parking permit
	     */
	    public Money getParkingCharges(ParkingPermit permit) {
	        double total = transactions.stream()
	                .filter(t -> t.getPermit().equals(permit))
	                .mapToDouble(t -> t.getChargedAmount().getAmount())
	                .sum();

	        return new Money(total, "USD");
	    }
	    
	    /*
	     * Returns total charges for all transactions of a given customer
	     */
	    public Money getParkingCharges(Customer customer) {
	        double total = transactions.stream()
	                .filter(t -> t.getPermit().getCar().getOwner().equals(customer))
	                .mapToDouble(t -> t.getChargedAmount().getAmount())
	                .sum();

	        return new Money(total, "USD");
	    }
	}
