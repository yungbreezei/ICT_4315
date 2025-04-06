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

import java.util.Properties;

import ict4315_assignment_1.Address;
import ict4315_assignment_1.Customer;

class CustomerTest {

	    private Customer customer;
	    private Address address;

	    @BeforeEach
	    public void setUp() {
	        address = new Address(null, null, null, null, null);
	        address.setStreetAddress1("456 Elm St");
	        address.setStreetAddress2("");
	        address.setCity("Aurora");
	        address.setState("CO");
	        address.setZip("80014");

	        customer = new Customer("C001", "Bria", "Wright", "555-1234", address);
	    }

	    @Test
	    public void testConstructorAndGetters() {
	        assertEquals("C001", customer.getId());
	        assertEquals("Bria Wright", customer.getCustomerName());
	        assertEquals("555-1234", customer.getPhoneNumber());
	        assertEquals(address, customer.getAddress());
	    }

	    @Test
	    public void testSetters() {
	        Address newAddress = new Address(null, null, null, null, null);
	        newAddress.setStreetAddress1("789 Oak St");
	        newAddress.setCity("Denver");
	        newAddress.setState("CO");
	        newAddress.setZip("80202");

	        customer.setId("C002");
	        customer.setFirstName("Ava");
	        customer.setLastName("Jones");
	        customer.setPhoneNumber("555-678-2354");
	        customer.setAddress(newAddress);

	        assertEquals("C002", customer.getId());
	        assertEquals("Ava Jones", customer.getCustomerName());
	        assertEquals("555-678-2354", customer.getPhoneNumber());
	        assertEquals(newAddress, customer.getAddress());
	    }

	    @Test
	    public void testEqualsAndHashCode() {
	        Customer sameCustomer = new Customer("C001", "Bria", "Wright", "555-1234", address);
	        assertEquals(customer, sameCustomer);
	        assertEquals(customer.hashCode(), sameCustomer.hashCode());
	    }

	    @Test
	    public void testNotEquals() {
	        Customer different = new Customer("C999", "Sam", "Doe", "555-0000", address);
	        assertNotEquals(customer, different);
	    }

	    @Test
	    public void testToString() {
	        String str = customer.toString();
	        assertTrue(str.contains("C001"));
	        assertTrue(str.contains("Bria"));
	        assertTrue(str.contains("Wright"));
	    }

	    @Test
	    public void testCreateFromProperties() {
	        Properties props = new Properties();
	        props.setProperty("id", "C010");
	        props.setProperty("firstName", "Taylor");
	        props.setProperty("lastName", "Smith");
	        props.setProperty("phoneNumber", "555-9999");
	        props.setProperty("streetAddress1", "123 Maple St");
	        props.setProperty("streetAddress2", "Unit B");
	        props.setProperty("city", "Boulder");
	        props.setProperty("state", "CO");
	        props.setProperty("zip", "80301");

	        Customer created = Customer.create(props);
	        assertEquals("C010", created.getId());
	        assertEquals("Taylor Smith", created.getCustomerName());
	        assertEquals("555-9999", created.getPhoneNumber());

	        Address addr = created.getAddress();
	        assertEquals("123 Maple St", addr.getStreetAddress1());
	        assertEquals("Boulder", addr.getCity());
	    }
}
