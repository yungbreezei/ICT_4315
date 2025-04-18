package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.Address;
import ict4315_assignment_1.Car;
import ict4315_assignment_1.CarType;
import ict4315_assignment_1.Customer;
import ict4315_assignment_1.ParkingPermit;

class ParkingPermitTest {

    private ParkingPermit permit;
    private Car car;
    private LocalDateTime expiration;

    @BeforeEach
    public void setUp() {
        Address address = new Address(null, null, null, null, null);
        address.setStreetAddress1("123 Main St");
        address.setCity("Denver");
        address.setState("CO");
        address.setZip("80203");

        // Initialize the car and expiration fields
        Customer customer = new Customer("C100", "Bria", "Wright", "555-1234", address);
        car = new Car(CarType.SUV, "SUV-4321", customer);
        
        // Set expiration to 30 days from the current time
        expiration = LocalDateTime.now().plusDays(30); // Expiry date 30 days from now

        // Create the permit with the initialized car and expiration
        permit = new ParkingPermit("P100", car, expiration);
    }

    @Test
    public void testConstructorAndGetters() {
        // Test constructor and getters
        assertEquals("P100", permit.getId());
        assertEquals(car, permit.getCar());
        assertEquals(expiration, permit.getExpiration());
    }

    @Test
    public void testSetters() {
        // Create a new car
        Car newCar = new Car(CarType.COMPACT, "CMP-2024", car.getOwner());

        // Add 1 day to the current time using LocalDateTime's plusDays() method
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);

        // Set values on the permit
        permit.setId("P200");
        permit.setCar(newCar);
        permit.setExpiration(newDate);

        // Assert the setters worked correctly
        assertEquals("P200", permit.getId());
        assertEquals(newCar, permit.getCar());
        assertEquals(newDate, permit.getExpiration());
    }

    @Test
    public void testEqualsAndHashCode() {
        ParkingPermit samePermit = new ParkingPermit("P100", car,  LocalDateTime.now());
        assertEquals(permit, samePermit);
        assertEquals(permit.hashCode(), samePermit.hashCode());

        ParkingPermit differentPermit = new ParkingPermit("P999", car,  LocalDateTime.now());
        assertNotEquals(permit, differentPermit);
    }

    @Test
    public void testToStringIncludesImportantInfo() {
        String result = permit.toString();
        assertTrue(result.contains("P100"));
        assertTrue(result.contains("SUV-4321"));
    }
}
