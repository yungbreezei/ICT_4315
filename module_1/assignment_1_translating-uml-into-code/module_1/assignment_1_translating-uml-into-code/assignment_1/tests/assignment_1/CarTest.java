/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.Address;
import ict4315_assignment_1.Car;
import ict4315_assignment_1.CarType;
import ict4315_assignment_1.Customer;

class CarTest {

    private Car car;
    private Customer owner;

    @BeforeEach
    public void setUp() {
        Address address = new Address(null, null, null, null, null);
        address.setStreetAddress1("456 Oak St");
        address.setCity("Denver");
        address.setState("CO");
        address.setZip("80210");

        owner = new Customer("C002", "Jordan", "Lee", "555-2020", address);
        car = new Car(CarType.COMPACT, "XYZ-123", owner);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(CarType.COMPACT, car.getType());
        assertEquals("XYZ-123", car.getLicensePlate());
        assertEquals(owner, car.getOwner());
    }

    @Test
    public void testSetters() {
        Customer newOwner = new Customer("C003", "Taylor", "Kim", "555-3333", owner.getAddress());
        car.setLicensePlate("ABC-987");
        car.setType(CarType.SUV);
        car.setOwner(newOwner);

        assertEquals("ABC-987", car.getLicensePlate());
        assertEquals(CarType.SUV, car.getType());
        assertEquals(newOwner, car.getOwner());
    }

    @Test
    public void testCreateFromProperties() {
        Properties props = new Properties();
        props.setProperty("type", "SUV");
        props.setProperty("licensePlate", "LMN-456");

        Address address = new Address(null, null, null, null, null);
        address.setStreetAddress1("789 Birch Ln");
        address.setCity("Boulder");
        address.setState("CO");
        address.setZip("80301");

        Customer testOwner = new Customer("C004", "Morgan", "Reed", "555-8888", address);

        Car createdCar = Car.create(props);
        createdCar.setOwner(testOwner);  // Assuming create() doesn't handle owner

        assertEquals(CarType.SUV, createdCar.getType());
        assertEquals("LMN-456", createdCar.getLicensePlate());
        assertEquals(testOwner, createdCar.getOwner());
    }

    @Test
    public void testToString() {
        String str = car.toString();
        assertTrue(str.contains("XYZ-123"));
        assertTrue(str.contains("COMPACT") || str.contains("Compact")); // Allow for formatting
    }
}
