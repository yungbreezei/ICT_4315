/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Properties;

public class RegisterCarCommand {
	
	private ParkingOffice office;

    /**
     * Constructor 
     */
	public RegisterCarCommand(ParkingOffice office) {
		
		this.office = office;
	}

    private void checkParameters(Properties properties) {
        // Parameter validation logic
    }
	
    public String execute(Properties properties) {
        checkParameters(properties);
        Car car = Car.create(properties);
        return office.register(car);
    }

    public String getCommandName() {
        return "CAR";
    }

    public String getDisplayName() {
        return "Register Car";
    }

}
