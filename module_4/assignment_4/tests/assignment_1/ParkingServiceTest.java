package assignment_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315.parking.charges.factory.DefaultParkingChargeStrategyFactory;
import ict4315.parking.charges.factory.ParkingChargeStrategyFactory;
import ict4315_assignment_1.Address;
import ict4315_assignment_1.Command;
import ict4315_assignment_1.ParkingLot;
import ict4315_assignment_1.ParkingOffice;
import ict4315_assignment_1.ParkingService;
import ict4315_assignment_1.RegisterCarCommand;
import ict4315_assignment_1.RegisterCustomerCommand;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingServiceTest {

    private ParkingOffice office;
    private ParkingService parkingService;
    private ParkingChargeStrategyFactory strategyFactory;
    private Address address;

    private Command registerCustomerCommand;
    private Command registerCarCommand;
    
    
    @BeforeEach
    public void setUp() {
        
    	// Initialize strategy factory
    	strategyFactory = new DefaultParkingChargeStrategyFactory();
    	
        // Setup a basic parking office with address
    	Address address = new Address("100 Office St", "Suite 200", "MetroCity", "IL", "60007");

        // Setup the ParkingOffice instance and initialize ParkingService
        office = new ParkingOffice("Main Office", address, strategyFactory);
        
        // Initialize ParkingService with the ParkingOffice
        parkingService = new ParkingService(office);
        
        // Create mock commands to test
        registerCustomerCommand = new RegisterCustomerCommand(office);
        registerCarCommand = new RegisterCarCommand(office);
        
        // Register the commands with the service
        parkingService.register(registerCustomerCommand);
        parkingService.register(registerCarCommand);  // Make sure this is registered

    }

    /**
     * Test case to ensure a valid command is executed properly.
     */
    @Test
    public void testPerformCommand_ValidCommand() {
        String[] args = {
            "id=CUST001",
            "firstName=John",
            "lastName=Doe",
            "phoneNumber=870-555-1234",
            "address=123 Main St, apt 3, Springfield, IL, 62704"
        };
        
        String result = parkingService.performCommand("RegisterCustomer", args);
        
        assertEquals("Customer registered successfully", result, "The customer registration should succeed.");
    }
    
    /**
     * Test case to check for invalid command execution.
     */
    @Test
    public void testPerformCommand_InvalidCommand() {
        String[] args = {
            "id=CUST002",
            "firstName=Jane",
            "lastName=Smith",
            "phoneNumber=870-555-1234",
            "address=456 Oak St, lot 3, Denver, CO, 80014"
        };
        
        String result = parkingService.performCommand("InvalidCommand", args);
        
        assertEquals("Invalid Command", result, "An invalid command should return 'Invalid Command'.");
    }

    /**
     * Test case to ensure that registering a car works properly.
     */
    @Test
    public void testPerformCommand_RegisterCar() {
        // First register a customer
        String[] customerArgs = {
            "id=CUST001",
            "firstName=John",
            "lastName=Doe",
            "phoneNumber=870-555-1234",
            "address=456 Oak St, lot 3, Denver, CO, 80014"
        };
        
        String result = parkingService.performCommand("RegisterCustomer", customerArgs);
        
        assertEquals("Customer registered successfully", result, "The customer registration should succeed.");
        
        // Then register a car for the customer
        String[] carArgs = {
            "customerId=CUST001",
            "licensePlate=ABC123",
            "carType=SUV"
        };
        
        result = parkingService.performCommand("RegisterCar", carArgs);
        
        assertEquals("Car registered successfully", result, "The car registration should succeed.");
    }
}
