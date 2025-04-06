/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

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
        // Register the car and get the permit
        ParkingPermit permit = permitManager.register(car);

        assertNotNull(permit, "Permit should not be null after registration.");
        assertNotNull(permit.getId(), "Permit ID should be generated.");
        assertEquals(car.getLicensePlate(), permit.getCar().getLicensePlate(), "The permit should match the car.");
        assertEquals(customer.getId(), permit.getCar().getOwner().getId(), "The permit should be associated with the correct customer.");

        // Check if the expiration date is set correctly (1 year from now)
        Date currentDate = new Date();
        long expirationTime = permit.getExpiration().getTime();
        long oneYearInMillis = 365L * 24 * 60 * 60 * 1000;
        assertTrue(expirationTime - currentDate.getTime() >= oneYearInMillis - 1000 && expirationTime - currentDate.getTime() <= oneYearInMillis + 1000,
                "Expiration time should be approximately one year from now.");
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
