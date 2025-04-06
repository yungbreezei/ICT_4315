package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.Address;
import ict4315_assignment_1.Car;
import ict4315_assignment_1.CarType;
import ict4315_assignment_1.Customer;
import ict4315_assignment_1.Money;
import ict4315_assignment_1.ParkingLot;
import ict4315_assignment_1.ParkingOffice;
import ict4315_assignment_1.ParkingPermit;
import ict4315_assignment_1.ParkingTransaction;

class ParkingOfficeTest {

    private ParkingOffice office;
    private Address officeAddress;
    private Address customerAddress;
    private Customer customer;
    private String customerId;
    
    @BeforeEach
    void setUp() {
        officeAddress = new Address("123 Main St", "", "Anytown", "CA", "12345");
        office = new ParkingOffice("Test Office", officeAddress);
        customerAddress = new Address("456 Oak Ave", "Apt 2", "Othertown", "NY", "67890");
        
        // Create and register a customer
        Customer customer = new Customer(null, "Bob", "Johnson", "555-9012", customerAddress);
        String customerId = office.register(customer);
    }

    @Test
    void testRegisterCustomer() {
        assertNotNull(customerId);
        assertFalse(customerId.isEmpty());
        assertEquals(1, office.getListOfCustomers().size());
    }

    @Test
    void testRegisterCar() {
        Car car = new Car(CarType.COMPACT, "XYZ789", customer);
        String permitId = office.register(car);
        
        assertNotNull(permitId);
        assertFalse(permitId.isEmpty());
    }

    @Test
    void testParkAndGetCharges() {
        // Register a car first
    	Car car = new Car(CarType.COMPACT, "XYZ789", customer);
        String permitId = office.register(car);
        ParkingPermit permit = new ParkingPermit(permitId, car, new Date());
        
        // Register parking lot in the office
        ParkingLot lot = new ParkingLot("L1", "Downtown Lot", officeAddress);
        office.getListOfParkingLots().add(lot);
        
        // Park the car
        ParkingTransaction transaction = office.park(new Date(), permit, lot);
        assertEquals(10.00, transaction.getChargedAmount().getAmount());
        
        // Check charges
        Money charges = office.getParkingCharges(permit);
        assertEquals(10.00, charges.getAmount());
        
        Money customerCharges = office.getParkingCharges(customer);
        assertEquals(10.00, customerCharges.getAmount());
    }
    
    @Test
    void testRegisterCarForUnregisteredCustomer() {
        Customer unregisteredCustomer = new Customer(null, "Unregistered", "User", "555-0000", customerAddress);
        Car car = new Car(CarType.SUV, "UNREG1", unregisteredCustomer);
        
        assertThrows(IllegalArgumentException.class, () -> office.register(car));
    }
}
