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
    private RegisterCustomerCommand command;
    
    @BeforeEach
    void setUp() {
        Address officeAddress = new Address("123 Main St", "", "Anytown", "CA", "12345");
        office = new ParkingOffice("Test Office", officeAddress);
        command = new RegisterCustomerCommand(office);
    }

    @Test
    void testCommandInfo() {
        assertEquals("REGISTER_CUSTOMER", command.getCommandName());
        assertEquals("Register Customer", command.getDisplayName());
    }

    @Test
    void testExecuteSuccess() {
        Properties params = new Properties();
        params.setProperty("firstName", "Alice");
        params.setProperty("lastName", "Wonderland");
        params.setProperty("phoneNumber", "555-1212");
        params.setProperty("street1", "100 Fantasy Ln");
        params.setProperty("city", "Wonderland");
        params.setProperty("state", "WL");
        params.setProperty("zip", "99999");
        
        String customerId = command.execute(params);
        assertNotNull(customerId);
        assertFalse(customerId.isEmpty());
    }

    @Test
    void testExecuteMissingParameters() {
        Properties params = new Properties();
        params.setProperty("firstName", "Alice");
        // Missing other required fields
        
        assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }
}