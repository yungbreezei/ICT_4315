package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.Address;
import ict4315_assignment_1.ParkingOffice;
import ict4315_assignment_1.RegisterCustomerCommand;

class RegisterCustomerCommandTest {

    private ParkingOffice office;
    private RegisterCustomerCommand registerCustomerCommand;

    @BeforeEach
    public void setUp() {
        office = new ParkingOffice("Main Office", new Address("123 Main St", "Apt 1", "City", "State", "12345"));  // Create the ParkingOffice instance with valid Address
        registerCustomerCommand = new RegisterCustomerCommand(office);  // Initialize the RegisterCustomerCommand
    }

    @Test
    public void testExecute_RegisterCustomer_Success() {
        // Prepare test data for customer registration
        Properties props = new Properties();
        props.setProperty("id", "CUST001");
        props.setProperty("firstName", "John");
        props.setProperty("lastName", "Doe");
        props.setProperty("phoneNumber", "870-555-1234");
        props.setProperty("address", "123 Main St, Apt 2, City, State, Zip");

        // Execute the register customer command
        String result = registerCustomerCommand.execute(props);

        // Assert that the result indicates successful registration
        assertEquals("Customer registered successfully", 
        		result, "The customer should be registered successfully.");
    }

    @Test
    public void testExecute_RegisterCustomer_MissingRequiredParameters() {
        // Prepare test data with missing parameters (e.g., missing phone number)
        Properties props = new Properties();
        props.setProperty("id", "CUST002");
        props.setProperty("firstName", "Jane");
        props.setProperty("lastName", "Smith");
        props.setProperty("phoneNumber", ""); // Missing phone number
        props.setProperty("address", "456 Another St, City, State, 12345");

        // Execute the register customer command
        String result = registerCustomerCommand.execute(props);

        // Assert that the result indicates missing required parameters
        assertEquals("Missing required parameters", result, "Missing information should cause an error.");
    }

    @Test
    public void testExecute_RegisterCustomer_InvalidCustomerId() {
        // Prepare test data with an invalid customer ID (e.g., empty ID)
        Properties props = new Properties();
        props.setProperty("id", "");  // Invalid empty customer ID
        props.setProperty("firstName", "Alice");
        props.setProperty("lastName", "Wonderland");
        props.setProperty("phoneNumber", "870-555-5678");
        props.setProperty("address", "789 Wonderland Blvd, City, State, 12345");

        // Execute the register customer command
        String result = registerCustomerCommand.execute(props);

        // Assert that the result indicates an invalid customer ID
        assertEquals("Invalid customer ID", result, "Empty customer ID should cause an error.");
    }

    @Test
    public void testExecute_RegisterCustomer_CustomerAlreadyExists() {
        // Prepare test data for a customer that already exists in the office
        Properties props = new Properties();
        props.setProperty("id", "CUST001");
        props.setProperty("firstName", "John");
        props.setProperty("lastName", "Doe");
        props.setProperty("phoneNumber", "870-555-1234");
        props.setProperty("address", "789 Wonderland Blvd, Lot 2, City, State, 12345");

        // First registration
        registerCustomerCommand.execute(props);

        // Try registering the same customer again
        String result = registerCustomerCommand.execute(props);
        
        // Assert that the result indicates the customer already exists
        assertEquals("Customer already exists", result, "The customer should not be registered twice.");
    }
}