/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Properties;

public class RegisterCustomerCommand implements Command{
	
    private final ParkingOffice office;
	
    /**
     * Constructor
     */	
	public RegisterCustomerCommand(ParkingOffice office) {
		
		this.office = office;
	}

	public void checkParameters(Properties params) {
        if (params.getProperty("firstName") == null ||
            params.getProperty("lastName") == null ||
            params.getProperty("phoneNumber") == null ||
            params.getProperty("street1") == null ||
            params.getProperty("city") == null ||
            params.getProperty("state") == null ||
            params.getProperty("zip") == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }
    }
	
    @Override
    public String execute(Properties props) {
        Address addr = new Address(
            props.getProperty("street"),
            props.getProperty("unit"),
            props.getProperty("city"),
            props.getProperty("state"),
            props.getProperty("zip")
        );

        Customer customer = new Customer(
            null,
            props.getProperty("firstName"),
            props.getProperty("lastName"),
            props.getProperty("phoneNumber"),
            addr
        );

        return office.register(customer);
    }
	
    @Override
    public String getCommandName() {
        return "REGISTER_CUSTOMER";
    }
	
    @Override
	public String getDisplayName() {
		return "Register Customer";
	}
}
