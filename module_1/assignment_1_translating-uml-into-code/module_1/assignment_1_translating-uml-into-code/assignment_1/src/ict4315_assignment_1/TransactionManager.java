/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID; // Needed to generate unique transaction IDs


/**
 * This is the class that manages all the parking transactions.
 */
public class TransactionManager {
	
    private List<ParkingTransaction> transactions;

	    // Constructor to initialize the transaction manager
	    public TransactionManager() {
	    	
	        this.transactions = new ArrayList<>();
	    }
	    
	    /*
	     * Getters
	     */
	    public List<ParkingTransaction> getTransactions() {
	        return new ArrayList<>(transactions); // Return a copy for encapsulation
	    }
	    
	    /*
	     * Setters
	     */
	    public void setTransactions(List<ParkingTransaction> transactions) {
	    	this.transactions = transactions;
	    }
	    
	    /*
	     * Handles parking action and logs a new transaction
	     */
	    public ParkingTransaction park(Date date, ParkingPermit permit, ParkingLot parkingLot) {
	        // Generate unique transaction ID
	        String transactionId = UUID.randomUUID().toString();

	        // Calculate the amount charged based on car type
	        Money chargedAmount = parkingLot.getDailyRate(permit.getCar().getType());

	        // Create and store the transaction
	        ParkingTransaction transaction = new ParkingTransaction(transactionId, date, permit, parkingLot, chargedAmount);
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
