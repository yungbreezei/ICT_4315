package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

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
    private Date expiration;

    @BeforeEach
    public void setUp() {
        Address address = new Address(null, null, null, null, null);
        address.setStreetAddress1("123 Main St");
        address.setCity("Denver");
        address.setState("CO");
        address.setZip("80203");

        Customer customer = new Customer("C100", "Bria", "Wright", "555-1234", address);
        car = new Car(CarType.SUV, "SUV-4321", customer);
        expiration = new Date(); // Current date

        permit = new ParkingPermit("P100", car, expiration);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("P100", permit.getId());
        assertEquals(car, permit.getCar());
        assertEquals(expiration, permit.getExpiration());
    }

    @Test
    public void testSetters() {
        Car newCar = new Car(CarType.COMPACT, "CMP-2024", car.getOwner());
        Date newDate = new Date(System.currentTimeMillis() + 86400000); // +1 day

        permit.setId("P200");
        permit.setCar(newCar);
        permit.setExpiration(newDate);

        assertEquals("P200", permit.getId());
        assertEquals(newCar, permit.getCar());
        assertEquals(newDate, permit.getExpiration());
    }

    @Test
    public void testEqualsAndHashCode() {
        ParkingPermit samePermit = new ParkingPermit("P100", car, expiration);
        assertEquals(permit, samePermit);
        assertEquals(permit.hashCode(), samePermit.hashCode());

        ParkingPermit differentPermit = new ParkingPermit("P999", car, expiration);
        assertNotEquals(permit, differentPermit);
    }

    @Test
    public void testToStringIncludesImportantInfo() {
        String result = permit.toString();
        assertTrue(result.contains("P100"));
        assertTrue(result.contains("SUV-4321"));
    }
}
