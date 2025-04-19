/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.time.LocalDateTime;
import java.util.*;

import ict4315.parking.charges.strategy.FlatDailyRateStrategy;

public class ParkingOffice {

	private String parkingOfficeName; // Name of the parking office
    private List<Customer> listOfCustomers = new ArrayList<>(); // Stores customers
	private List<ParkingLot> listOfParkingLots = new ArrayList<>();; // Manages multiple parking lots
	private List<Car> listOfCars = new ArrayList<>(); // Stores the registered cars
	private Address parkingOfficeAddress;
		
    private final PermitManager permitManager = new PermitManager();
    private final TransactionManager transactionManager = 
    		new TransactionManager(new FlatDailyRateStrategy());

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
    
    public List<Car> listOfCars() {
    	return listOfCars;
    }
    
    public Address getParkingOfficeAddress() {
    	return parkingOfficeAddress;
    }
    public PermitManager getPermitManager() {
        return permitManager;
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
    public void setListOfCars(List<Car> listOfCars) {
    	this.listOfCars = listOfCars;
    }
    public void setParkingOfficeAddress(Address parkingOfficeAddress) {
    	this.parkingOfficeAddress = parkingOfficeAddress;
    }
    
    /*
     * Methods
     */	 
    // Register a customer with the parking office
    public String register(Customer customer) {
        // Check if the customer already exists
        for (Customer c : listOfCustomers) {
            if (c.getId().equals(customer.getId())) {
                return "Customer already exists";  // Return a message if the customer already exists
            }
        }
        
        // If the customer doesn't have an ID (i.e., new customer), generate one
        if (customer.getId() == null || customer.getId().isEmpty()) {
        	String customerId = UUID.randomUUID().toString();  // Generate ID if not provided
            customer.setId(customerId);
        }

        // Add customer to list
        listOfCustomers.add(customer);

        return "Customer registered successfully";  // Return success message
    }
    
    // Register a parking lot with the parking office
    public void register(ParkingLot lot) {
        listOfParkingLots.add(lot);
    }
    
    // Register a car and issue a parking permit
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
        
        // Generate and assign parking permit
        ParkingPermit permit = permitManager.register(car);
        
        // Add car to the list of cars (Ensure listOfCars exists in your ParkingOffice class)
        listOfCars.add(car);

        // Return the success message instead of the permit ID
        return "Car registered successfully"; 
    }
    
    public ParkingTransaction park(LocalDateTime entryTime, LocalDateTime exitTime, ParkingPermit permit, ParkingLot parkingLot) {
        if (!listOfParkingLots.contains(parkingLot)) {
            throw new IllegalArgumentException("Parking lot not registered");
        }
        return transactionManager.park(entryTime, exitTime, permit, parkingLot);
    }

    public Money getParkingCharges(ParkingPermit permit) {
        return transactionManager.getParkingCharges(permit);
    }

    public Money getParkingCharges(Customer customer) {
        return transactionManager.getParkingCharges(customer);
    }
    
    /*
     *  The helper methods, such as checking if a customer exists or 
     *  getting a customer by ID, to make the code cleaner and more readable.
     */
    public boolean customerExists(String customerId) {
        return listOfCustomers.stream()
                .anyMatch(c -> c.getId().equals(customerId));
    }
    public Customer getCustomerById(String customerId) {
        return listOfCustomers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst()
                .orElse(null);
    }
}
