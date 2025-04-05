/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Properties;

public class RegisterCustomerCommand implements Command{
	
	private ParkingOffice office;
	
    /**
     * Constructor
     */	
	public RegisterCustomerCommand(ParkingOffice office) {
		
		this.office = office;
	}
	
	private void checkParameters(Properties properties) {
		// Parameter validation logic
	}
	
    @Override
    public String execute(Properties properties) {
        checkParameters(properties);
        Customer customer = Customer.create(properties);
        return office.register(customer);
    }
	
	public String getCommandName() {
		return "CUSTOMER";
	}
	
	public String getDisplayName() {
		return "Register Customer";
	}
}
