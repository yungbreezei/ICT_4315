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
import ict4315_assignment_1.Customer;
import ict4315_assignment_1.ParkingOffice;
import ict4315_assignment_1.RegisterCarCommand;

class RegisterCarCommandTest {

	private ParkingOffice office;
    private RegisterCarCommand registerCarCommand;

    @BeforeEach
    public void setup() {
        // Setup ParkingOffice and RegisterCarCommand for testing
        office = new ParkingOffice("Main Office", new Address("123 Street", "Apt 1", "City", "State", "12345"));
        registerCarCommand = new RegisterCarCommand(office);
        
        // Register a sample customer
        Customer customer = new Customer("CUST001", "John", "Doe", "870-555-1234", new Address("789 Wonderland Blvd", "Lot 2", "City", "State", "12345"));
        office.register(customer);
    }

    @Test
    public void testExecute_RegisterCar_Success() {
        // Prepare properties for car registration
        Properties props = new Properties();
        props.setProperty("carType", "SUV");
        props.setProperty("licensePlate", "ABC1234");
        props.setProperty("customerId", "CUST001");

        // Execute car registration
        String result = registerCarCommand.execute(props);

        // Assert the success message
        assertEquals("Car registered successfully", result);
    }

    @Test
    public void testExecute_RegisterCar_CustomerNotFound() {
        // Prepare properties for car registration with an invalid customerId
        Properties props = new Properties();
        props.setProperty("customerId", "INVALID_ID");
        props.setProperty("licensePlate", "XYZ5678");
        props.setProperty("carType", "SUV");

        // Try executing car registration with invalid customer ID
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            registerCarCommand.execute(props);
        });

        // Assert the exception message
        assertEquals("Customer not found", thrown.getMessage());
    }
}