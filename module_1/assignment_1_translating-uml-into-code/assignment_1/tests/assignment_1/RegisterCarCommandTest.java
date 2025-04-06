package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.Address;
import ict4315_assignment_1.Customer;
import ict4315_assignment_1.ParkingOffice;
import ict4315_assignment_1.RegisterCarCommand;

class RegisterCarCommandTest {

	private ParkingOffice office;
    private RegisterCarCommand command;
    private String customerId;
    
    @BeforeEach
    void setUp() {
        Address officeAddress = new Address("123 Main St", "", "Anytown", "CA", "12345");
        office = new ParkingOffice("Test Office", officeAddress);
        command = new RegisterCarCommand(office);
        
        // Register a customer first
        Address customerAddress = new Address("456 Oak Ave", "Apt 2", "Othertown", "NY", "67890");
        Customer customer = new Customer(null, "John", "Doe", "555-1234", customerAddress);
        customerId = office.register(customer);
    }

    @Test
    void testCommandInfo() {
        assertEquals("REGISTER_CAR", command.getCommandName());
        assertEquals("Register Car", command.getDisplayName());
    }

    @Test
    void testExecuteSuccess() {
        Properties params = new Properties();
        params.setProperty("customerId", customerId);
        params.setProperty("licensePlate", "NEW123");
        params.setProperty("carType", "SUV");
        
        String permitId = command.execute(params);
        assertNotNull(permitId);
        assertFalse(permitId.isEmpty());
    }

    @Test
    void testExecuteMissingParameters() {
        Properties params = new Properties();
        params.setProperty("customerId", customerId);
        // Missing licensePlate and carType
        
        assertThrows(NullPointerException.class, () -> command.execute(params));
    }

    @Test
    void testExecuteInvalidCustomer() {
        Properties params = new Properties();
        params.setProperty("customerId", "INVALID_ID");
        params.setProperty("licensePlate", "NEW123");
        params.setProperty("carType", "SUV");
        
        assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }
}