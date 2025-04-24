package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.PermitManager;
import ict4315_assignment_1.ParkingPermit;
import ict4315_assignment_1.Address;
import ict4315_assignment_1.Car;
import ict4315_assignment_1.Customer;
import ict4315_assignment_1.CarType;

public class PermitManagerTest {

    private PermitManager permitManager;
    private Customer customer;
    private Car car;

    @BeforeEach
    public void setUp() {
        permitManager = new PermitManager();
        customer = new Customer("C001", "Joe", "Doe", "870-555-1234", new Address("123 Main St", "Apt 1A", 
        		"Springfield", "IL", "62701"));
        
        car = new Car(CarType.COMPACT, "XYZ123", customer);
    }

    @Test
    public void testRegisterPermit() {
        ParkingPermit permit = permitManager.register(car);

        assertNotNull(permit);
        assertNotNull(permit.getId());
        assertEquals(car.getLicensePlate(), permit.getCar().getLicensePlate());
        assertEquals(customer.getId(), permit.getCar().getOwner().getId());

        // Compare expiration date (allowing 1-second margin)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expectedExpiration = now.plusYears(1);
        LocalDateTime actualExpiration = permit.getExpiration();

        // Allow up to 2 seconds of difference due to execution time
        long secondsDifference = Math.abs(java.time.Duration.between(expectedExpiration, actualExpiration).getSeconds());
        assertTrue(secondsDifference <= 2, "Expiration should be approximately one year from now.");
    }


    @Test
    public void testRegisterCarWithoutOwner() {
        // Expecting IllegalArgumentException to be thrown due to null owner
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(CarType.SUV, "ABC123", null); // Passing null as the owner
        }, "Expected IllegalArgumentException when trying to register a car with no owner.");
    }



    @Test
    public void testGetPermitById() {
        // Register the car and get the permit
        ParkingPermit permit = permitManager.register(car);

        // Retrieve the permit using the permit ID
        ParkingPermit retrievedPermit = permitManager.getPermits().stream()
            .filter(p -> p.getId().equals(permit.getId()))
            .findFirst()
            .orElse(null);

        assertNotNull(retrievedPermit, "Permit should be found by ID.");
        assertEquals(permit.getId(), retrievedPermit.getId(), "The retrieved permit should have the same ID.");
    }

    @Test
    public void testGetInvalidPermitById() {
        // Try retrieving a permit that does not exist
        ParkingPermit invalidPermit = permitManager.getPermits().stream()
            .filter(p -> p.getId().equals("nonexistent-id"))
            .findFirst()
            .orElse(null);

        assertNull(invalidPermit, "Expected null when retrieving a permit that does not exist.");
    }

    @Test
    public void testRegisterDuplicateCar() {
        // Register the car twice
        permitManager.register(car);
        assertThrows(IllegalArgumentException.class, () -> permitManager.register(car),
            "Expected IllegalArgumentException when trying to register the same car again.");
    }
}
