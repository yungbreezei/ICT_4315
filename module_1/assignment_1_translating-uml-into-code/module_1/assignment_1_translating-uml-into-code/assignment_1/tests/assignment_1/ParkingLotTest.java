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

import ict4315_assignment_1.Address;
import ict4315_assignment_1.CarType;
import ict4315_assignment_1.Money;
import ict4315_assignment_1.ParkingLot;

class ParkingLotTest {

    private ParkingLot parkingLot;
    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address(null, null, null, null, null);
        address.setStreetAddress1("123 Main St");
        address.setStreetAddress2("Suite 100");
        address.setCity("Denver");
        address.setState("CO");
        address.setZip("80202");

        parkingLot = new ParkingLot("PL001", "Downtown Lot", address);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("PL001", parkingLot.getId());
        assertEquals("Downtown Lot", parkingLot.getName());
        assertEquals(address, parkingLot.getAddress());
    }

    @Test
    public void testSetters() {
        Address newAddress = new Address(null, null, null, null, null);
        newAddress.setStreetAddress1("456 Elm St");
        newAddress.setCity("Aurora");
        newAddress.setState("CO");
        newAddress.setZip("80014");

        parkingLot.setId("PL002");
        parkingLot.setName("East Side Lot");
        parkingLot.setAddress(newAddress);

        assertEquals("PL002", parkingLot.getId());
        assertEquals("East Side Lot", parkingLot.getName());
        assertEquals(newAddress, parkingLot.getAddress());
    }

    @Test
    public void testGetDailyRateCompact() {
        Money rate = parkingLot.getDailyRate(CarType.COMPACT);
        assertEquals(10.00, rate.getAmount(), 0.001);
        assertEquals("USD", rate.getCurrency());
    }

    @Test
    public void testGetDailyRateSUV() {
        Money rate = parkingLot.getDailyRate(CarType.SUV);
        assertEquals(15.00, rate.getAmount(), 0.001);
        assertEquals("USD", rate.getCurrency());
    }

    @Test
    public void testEqualsAndHashCode() {
        ParkingLot sameLot = new ParkingLot("PL001", "Downtown Lot", address);
        assertEquals(parkingLot, sameLot);
        assertEquals(parkingLot.hashCode(), sameLot.hashCode());
    }

    @Test
    public void testNotEqualsDifferentId() {
        ParkingLot differentLot = new ParkingLot("PL999", "Another Lot", address);
        assertNotEquals(parkingLot, differentLot);
    }

    @Test
    public void testToString() {
        String str = parkingLot.toString();
        assertTrue(str.contains("PL001"));
        assertTrue(str.contains("Downtown Lot"));
        assertTrue(str.contains("Parking Lot")); 
    }
}
