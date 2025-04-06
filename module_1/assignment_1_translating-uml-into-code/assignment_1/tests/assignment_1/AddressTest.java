package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.Address;

class AddressTest {

    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address("123 Main St", "Apt 4B", "Springfield", "IL", "62701");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("123 Main St", address.getStreetAddress1());
        assertEquals("Apt 4B", address.getStreetAddress2());
        assertEquals("Springfield", address.getCity());
        assertEquals("IL", address.getState());
        assertEquals("62701", address.getZip());
    }

    @Test
    public void testSetters() {
        address.setStreetAddress1("456 Oak St");
        address.setStreetAddress2("Suite 101");
        address.setCity("Chicago");
        address.setState("IL");
        address.setZip("60601");

        assertEquals("456 Oak St", address.getStreetAddress1());
        assertEquals("Suite 101", address.getStreetAddress2());
        assertEquals("Chicago", address.getCity());
        assertEquals("IL", address.getState());
        assertEquals("60601", address.getZip());
    }

    @Test
    public void testEqualsAndHashCode() {
        Address address1 = new Address("123 Main St", "Apt 4B", "Springfield", "IL", "62701");
        Address address2 = new Address("123 Main St", "Apt 4B", "Springfield", "IL", "62701");
        Address address3 = new Address("456 Oak St", "Suite 101", "Chicago", "IL", "60601");

        assertTrue(address1.equals(address2)); // Same values, should be equal
        assertEquals(address1.hashCode(), address2.hashCode()); // Same values, should have the same hash code
        
        assertFalse(address1.equals(address3)); // Different values, should not be equal
        assertNotEquals(address1.hashCode(), address3.hashCode()); // Different values, should have different hash codes
    }

    @Test
    public void testToString() {
        String expected = "123 Main St, Apt 4B, Springfield, IL 62701";
        assertEquals(expected, address.toString());
    }

    @Test
    public void testGetAddressInfo() {
        String expected = "123 Main St Apt 4B Springfield IL 62701";
        assertEquals(expected, address.getAddressInfo());
    }

    @Test
    public void testNullStreetAddress2() {
        Address address = new Address("123 Main St", null, "Springfield", "IL", "62701");

        String expected = "123 Main St, Springfield, IL 62701"; // Null streetAddress2 should not break the format
        assertEquals(expected, address.toString());
    }
}
