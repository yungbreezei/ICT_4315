/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.*;

public class ParkingOffice {

	private String parkingOfficeName; // Name of the parking office
    private List<Customer> listOfCustomers = new ArrayList<>(); // Stores customers
	private List<ParkingLot> listOfParkingLots = new ArrayList<>();; // Manages multiple parking lots
	private Address parkingOfficeAddress;
		
    private final PermitManager permitManager = new PermitManager();
    private final TransactionManager transactionManager = new TransactionManager();

    /**
     * Initializes a ParkingOffice with the provided name and address.
     * @param listOfCustomers 
     * @param listOfParkingLots 
     * @param officeName The name of the parking office.
     * @param address The address of the parking office.
     */
    public ParkingOffice(String parkingOfficeName, Address parkingOfficeAddress) {
        this.parkingOfficeName = parkingOfficeName;
        this.parkingOfficeAddress = parkingOfficeAddress;
    }
    
    /*
     * Getters
     */
    public String getParkingOfficeName() {
    	return parkingOfficeName;
    }
    
    public List<Customer> getListOfCustomers() {
        return new ArrayList<>(listOfCustomers); // Return a copy for encapsulation
    }
    
    public List<ParkingLot> getListOfParkingLots() {
    	return listOfParkingLots;
    }
    
    public Address getParkingOfficeAddress() {
    	return parkingOfficeAddress;
    }
    
    /*
     * Setters
     */
    public void setParkingOfficeName(String parkingOfficeName) {
    	this.parkingOfficeName = parkingOfficeName;
    }
    public void setListOfCustomers(List<Customer> listOfCustomers) {
    	this.listOfCustomers = listOfCustomers;
    }
    public void setListOfParkingLots(List<ParkingLot> listOfParkingLots) {
    	this.listOfParkingLots = listOfParkingLots;
    }
    public void setParkingOfficeAddress(Address parkingOfficeAddress) {
    	this.parkingOfficeAddress = parkingOfficeAddress;
    }
    
    /*
     * Methods
     */	 
    public String register(Customer customer) {
        // Create new customer with generated ID
        String customerId = UUID.randomUUID().toString();
        Customer registeredCustomer = new Customer(
            customerId,
            customer.getCustomerName(),
            customer.getCustomerName(),
            customer.getPhoneNumber(),
            customer.getAddress()
        );
        listOfCustomers.add(registeredCustomer);
        return customerId;
    }
    
    public void register(ParkingLot lot) {
        listOfParkingLots.add(lot);
    }

    public String register(Car car) {
    	
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        if (car.getOwner() == null) {
            throw new IllegalArgumentException("Car must have an owner");
        }
        
        // Verify customer is registered by ID
        boolean customerRegistered = listOfCustomers.stream()
            .anyMatch(c -> c.getId().equals(car.getOwner().getId()));
        
        if (!customerRegistered) {
            throw new IllegalArgumentException("Customer not registered");
        }
        
        ParkingPermit permit = permitManager.register(car);
        return permit.getId();
    }
    
    public ParkingTransaction park(Date date, ParkingPermit permit, ParkingLot parkingLot) {
        if (!listOfParkingLots.contains(parkingLot)) {
            throw new IllegalArgumentException("Parking lot not registered");
        }
        return transactionManager.park(date, permit, parkingLot);
    }

    public Money getParkingCharges(ParkingPermit permit) {
        return transactionManager.getParkingCharges(permit);
    }

    public Money getParkingCharges(Customer customer) {
        return transactionManager.getParkingCharges(customer);
    }
}
