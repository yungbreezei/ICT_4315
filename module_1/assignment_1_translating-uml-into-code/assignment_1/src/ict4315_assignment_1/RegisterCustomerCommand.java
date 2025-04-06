/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Properties;

public class RegisterCustomerCommand implements Command {
    
    private ParkingOffice office;
    
    /**
     * Constructor
     * @param office the ParkingOffice to register the customer with
     */
    public RegisterCustomerCommand(ParkingOffice office) {
        this.office = office;
    }
    
    /*
     * Getters 
     */
	public ParkingOffice getOffice() {
		return office;
	}
	
    /*
     * Setters 
     */
	public void setOffice(ParkingOffice office) {
		this.office = office;
	}

    /**
     * Validates that all required parameters are provided
     * @param params the properties to check for required parameters
     */
    public void checkParameters(Properties params) {
        if (params.getProperty("id") == null ||
            params.getProperty("firstName") == null ||
            params.getProperty("lastName") == null ||
            params.getProperty("phoneNumber") == null ||
            params.getProperty("address") == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }
    }
    
    @Override
    public String execute(Properties props) {
        String customerId = props.getProperty("id");
        String firstName = props.getProperty("firstName");
        String lastName = props.getProperty("lastName");
        String phoneNumber = props.getProperty("phoneNumber");
        String addressStr = props.getProperty("address");

        // Validate parameters
        if (customerId == null || firstName == null || lastName == null ||
            phoneNumber == null || addressStr == null || phoneNumber.isEmpty()) {
            return "Missing required parameters";
        }

        // Check if customer already exists by ID
        for (Customer c : office.getListOfCustomers()) {
            if (c.getId().equals(customerId)) {
                return "Customer already exists";
            }
        }
        
        // Validate customer ID (shouldn't be empty)
        if (customerId.trim().isEmpty()) {
            return "Invalid customer ID";
        }

        // Parse address (assuming address is a string like "789 Wonderland Blvd, Lot 2, City, State, 12345")
        String[] addressParts = addressStr.split(",");
        if (addressParts.length != 5) {
            return "Invalid address format";
        }

        String streetAddress1 = addressParts[0].trim();
        String streetAddress2 = addressParts[1].trim();
        String city = addressParts[2].trim();
        String state = addressParts[3].trim();
        String zip = addressParts[4].trim();

        // Create an Address object
        Address address = new Address(streetAddress1, streetAddress2, city, state, zip);

        // Create and register the customer
        Customer newCustomer = new Customer(customerId, firstName, lastName, phoneNumber, address);
        office.register(newCustomer);

        return "Customer registered successfully";
    }
    
    @Override
    public String getCommandName() {
        return "RegisterCustomer";
    }
    
    @Override
    public String getDisplayName() {
        return "Register Customer";
    }
}
