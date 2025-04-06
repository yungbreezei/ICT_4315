package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.Address;
import ict4315_assignment_1.ParkingOffice;
import ict4315_assignment_1.ParkingService;
import ict4315_assignment_1.RegisterCarCommand;
import ict4315_assignment_1.RegisterCustomerCommand;

class ParkingServiceTest {

	private ParkingService service;
    private String customerId;
    
    @BeforeEach
    void setUp() {
        Address officeAddress = new Address("123 Main St", "", "Anytown", "CA", "12345");
        ParkingOffice office = new ParkingOffice("Test Office", officeAddress);
        service = new ParkingService(office);
        
        // Register commands
        service.register(new RegisterCustomerCommand(office));
        service.register(new RegisterCarCommand(office));
        
        // Register a customer for car registration tests
        String[] customerParams = {
            "firstName", "Jane",
            "lastName", "Doe",
            "phoneNumber", "555-1234",
            "street1", "100 Main St",
            "city", "Anytown",
            "state", "CA",
            "zip", "12345"
        };
        customerId = service.performCommand("REGISTER_CUSTOMER", customerParams);
    }

    @Test
    void testRegisterCustomer() {
        String[] params = {
            "firstName", "John",
            "lastName", "Smith",
            "phoneNumber", "555-5678",
            "street1", "200 Oak Ave",
            "city", "Othertown",
            "state", "NY",
            "zip", "67890"
        };
        String result = service.performCommand("REGISTER_CUSTOMER", params);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testRegisterCar() {
        String[] params = {
            "customerId", customerId,
            "licensePlate", "ABC123",
            "carType", "COMPACT"
        };
        String result = service.performCommand("REGISTER_CAR", params);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
    
    @Test
    void testRegisterCarInvalidCustomer() {
        String[] params = {
            "customerId", "INVALID_ID",
            "licensePlate", "ABC123",
            "carType", "COMPACT"
        };
        assertThrows(IllegalArgumentException.class, () -> 
            service.performCommand("REGISTER_CAR", params));
    }

    @Test
    void testUnknownCommand() {
        assertThrows(IllegalArgumentException.class, () -> 
            service.performCommand("UNKNOWN_COMMAND", new String[]{}));
    }
}
