/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.ParkingOffice;
import ict4315_assignment_1.Customer;
import ict4315_assignment_1.ParkingLot;
import ict4315_assignment_1.ParkingPermit;
import ict4315_assignment_1.ParkingTransaction;
import ict4315_assignment_1.Money;
import ict4315_assignment_1.Address;
import ict4315_assignment_1.Car;
import ict4315_assignment_1.CarType;

import java.util.Date;

class ParkingOfficeTest {

    private ParkingOffice parkingOffice;
    private Customer customer;
    private ParkingLot parkingLot;
    private Car car;
    private ParkingPermit parkingPermit;
    private Address address;

    @BeforeEach
    public void setUp() {
        // Setup a basic parking office with address
        address = new Address("123 Office St", "Suite 500", "Chicago", "IL", "60601");
        parkingOffice = new ParkingOffice("Downtown Parking", address);

        // Setup a customer with an address
        customer = new Customer("C001", "John Doe", "555-1234", null, 
        		new Address("123 Main St", "Apt 4B", "Springfield", "IL", "62701"));

        // Setup a parking lot
        parkingLot = new ParkingLot("P001", "Lot A", address);
        
        // Setup a car for the customer
        car = new Car(CarType.COMPACT, "ABC123", customer);
        
        // Register the customer
        parkingOffice.register(customer);
        
        // Register the parking lot
        parkingOffice.register(parkingLot);
        
        // Register the car and get parking permit
        parkingPermit = new ParkingPermit("Permit001", car, new Date());
    }

    @Test
    public void testRegisterCustomer() {
        // Given
        ParkingOffice office = new ParkingOffice("Main Office", 
        		new Address("123 Main St", "Apt 4B", "City", "State", "12345"));
        Customer newCustomer = new Customer("C001", "John","Doe", "123-456-7890", 
        		new Address("123 Main St", "apt1", "Springfield", "IL", "62701"));
        
        // Register the customer
        String registrationMessage = office.register(newCustomer);

        // Assert that the registration message contains the correct customer ID and success message
        assertTrue(registrationMessage.contains("Customer registered successfully"), 
                   "Customer registration failed or ID mismatch.");

        // Verify that the customer was added to the list
        boolean customerRegistered = office.getListOfCustomers().stream()
                                           .anyMatch(c -> c.getId().equals(newCustomer.getId()));
        assertTrue(customerRegistered, "Customer should be registered and added to ParkingOffice.");
    }


    @Test
    public void testRegisterParkingLot() {
        parkingOffice.register(parkingLot);

        // Check if the parking lot was added to the list of parking lots
        assertTrue(parkingOffice.getListOfParkingLots().contains(parkingLot), 
        		"Parking lot should be added to ParkingOffice.");
    }

    @Test
    public void testRegisterCarAndGeneratePermit() {
        // Register the customer first
        Customer customer = new Customer("John Doe", "123-456-7890", null, null, 
        		new Address("123 Main St", null, "Springfield", "IL", "62701"));
        String customerId = parkingOffice.register(customer);  // Registering the customer

        // Create a car with the registered customer
        Car car = new Car(CarType.COMPACT, customerId, customer);  // Car constructor takes a Customer object

        // Register the car and generate the permit
        String permitId = parkingOffice.register(car);

        assertNotNull(permitId);  // Verify that a permit ID is returned
    }

    @Test
    public void testPark() {
        // Assuming you have a valid parking permit and parking lot
        ParkingTransaction transaction = parkingOffice.park(new Date(), 
        		parkingPermit, parkingLot);

        assertNotNull(transaction, "Parking transaction should be successful.");
        assertTrue(transaction.getParkingLot().equals(parkingLot), "Parking transaction should be associated with the correct parking lot.");
    }

    @Test
    public void testGetParkingChargesForPermit() {
        // Assuming there are charges for parking, we can test getting the charges for a permit
        Money charges = parkingOffice.getParkingCharges(parkingPermit);

        assertNotNull(charges, "Parking charges should be returned for the permit.");
    }

    @Test
    public void testGetParkingChargesForCustomer() {
        // Assuming there are charges for parking, test getting the charges for a customer
        Money charges = parkingOffice.getParkingCharges(customer);

        assertNotNull(charges, "Parking charges should be returned for the customer.");
    }

    @Test
    public void testInvalidCustomerRegistrationForCar() {
        ParkingOffice parkingOffice = new ParkingOffice("Main Office", new Address("123 Main St", "Apt 4B", "City", "State", "12345"));
        Customer invalidCustomer = new Customer("John Doe", "123-456-7890", null, null, 
        		new Address("123 Main St", null, "Springfield", "IL", "62701"));
        Car invalidCar = new Car(CarType.SUV, "ABC123", invalidCustomer);  // This customer is not registered in the parking office

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            parkingOffice.register(invalidCar);  // Trying to register a car with an unregistered customer
        }, "Expected IllegalArgumentException to be thrown when registering car with unregistered customer.");
    }

    @Test
    public void testInvalidParkingLotInPark() {
        // Try parking in an unregistered parking lot
        ParkingLot unregisteredLot = new ParkingLot("P002", "Lot B", address);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parkingOffice.park(new Date(), parkingPermit, unregisteredLot);
        });

        assertEquals("Parking lot not registered", exception.getMessage(), 
        		"Exception should be thrown for unregistered parking lot.");
    }

    @Test
    public void testInvalidCarForParking_NoOwner() {
        // Attempt to create a car with null owner, should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Car(CarType.COMPACT, "ABC123", null); // null owner triggers the exception
        });

        // Assert the correct exception message is thrown
        assertEquals("Car properties cannot be null", exception.getMessage());
    }
}
