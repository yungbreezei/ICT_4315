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
import ict4315_assignment_1.ParkingPermit;
import ict4315_assignment_1.ParkingTransaction;

class ParkingTransactionTest {

    private ParkingTransaction transaction;
    private ParkingPermit permit;
    private ParkingLot parkingLot;
    private Money chargedAmount;
    private Date date;

    @BeforeEach
    public void setUp() {
        // Setup Address
        Address address = new Address(null, null, null, null, null);
        address.setStreetAddress1("456 Elm St");
        address.setCity("Denver");
        address.setState("CO");
        address.setZip("80205");

        // Setup Car and Permit
        Customer customer = new Customer("C101", "Bria", "Wright", "555-9876", address);
        Car car = new Car(CarType.COMPACT, "CMP-1234", customer);
        permit = new ParkingPermit("PERMIT-1", car, new Date(System.currentTimeMillis() + 86400000)); // +1 day

        // Setup ParkingLot and Money
        parkingLot = new ParkingLot("LOT-1", "Downtown Garage", address);
        chargedAmount = new Money(10.00, "USD");
        date = new Date();

        transaction = new ParkingTransaction(date, permit, parkingLot, chargedAmount);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(date, transaction.getDate());
        assertEquals(permit, transaction.getPermit());
        assertEquals(parkingLot, transaction.getParkingLot());
        assertEquals(chargedAmount, transaction.getChargedAmount());
    }

    @Test
    public void testSetters() {
        Date newDate = new Date(System.currentTimeMillis() + 172800000); // +2 days
        ParkingLot newLot = new ParkingLot("LOT-2", "Airport Lot", parkingLot.getAddress());
        Money newAmount = new Money(15.00, "USD");

        transaction.setDate(newDate);
        transaction.setParkingLot(newLot);
        transaction.setChargedAmount(newAmount);

        assertEquals(newDate, transaction.getDate());
        assertEquals(newLot, transaction.getParkingLot());
        assertEquals(newAmount, transaction.getChargedAmount());
    }

    @Test
    public void testEqualsAndHashCode() {
        ParkingTransaction duplicateTransaction = new ParkingTransaction(
                new Date(), permit, parkingLot, new Money(20.00, "USD"));

        assertEquals(transaction, duplicateTransaction);
        assertEquals(transaction.hashCode(), duplicateTransaction.hashCode());
    }

    @Test
    public void testToString() {
        String str = transaction.toString();
        assertTrue(str.contains("Permit='"));
        assertTrue(str.contains("Date='"));
        assertTrue(str.contains("Parking Lot='"));
        assertTrue(str.contains("Charged Amount='"));
    }
}
