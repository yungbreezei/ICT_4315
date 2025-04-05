/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingOffice {

	private String parkingOfficeName; // Name of the parking office
	private List<Customer> listOfCustomers; // Stores customers
	private List<ParkingLot> listOfParkingLots; // Manages multiple parking lots
	private Address parkingOfficeAddress;
	

    /**
     * Initializes a ParkingOffice with the provided name and address.
     * @param listOfCustomers 
     * @param listOfParkingLots 
     * @param officeName The name of the parking office.
     * @param address The address of the parking office.
     */
    public ParkingOffice(String parkingOfficeName, Address parkingOfficeAddress, 
    		List<Customer> listOfCustomers, List<ParkingLot> listOfParkingLots) {
    	
        this.parkingOfficeName = parkingOfficeName;
        this.listOfCustomers = listOfCustomers; // Initializes customer registry
        this.listOfParkingLots = listOfParkingLots;  // Initializes the list of parking lots
        this.parkingOfficeAddress = parkingOfficeAddress;
    }
    
    /*
     * Getters
     */
    public String getParkingOfficeName() {
    	return parkingOfficeName;
    }
    public List<Customer> getListOfCustomers() {
    	return listOfCustomers;
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
        listOfCustomers.add(customer);
        return "Customer registered: " + customer;
    }

    public String register(Car car) {
        // Register car logic
        return "Car registered: " + car;
    }
    
    public ParkingPermit register(Car) {
    	
    }
    
    public ParkingTransaction park(Date, ParkingPermit, ParkingLot) {
    	
    }
    
    public Money getParkingCharges(ParkingPermit) {
    	
    }
    
    public Money getParkingCharges(Customer) {
    	
    }
}
