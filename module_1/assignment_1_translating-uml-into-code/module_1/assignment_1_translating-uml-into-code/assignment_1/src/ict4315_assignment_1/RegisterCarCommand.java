/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Properties;

public class RegisterCarCommand implements Command{
	
	private ParkingOffice office;

    /**
     * Constructor 
     */
	public RegisterCarCommand(ParkingOffice office) {
		
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
	

    public void checkParameters(Properties params) {
        if (params.getProperty("customerId") == null ||
            params.getProperty("licensePlate") == null ||
            params.getProperty("carType") == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }
    }
	
    @Override
    public String execute(Properties props) {
        checkParameters(props);  // Call the validation method

        String customerId = props.getProperty("customerId");
        String licensePlate = props.getProperty("licensePlate");
        String carType = props.getProperty("carType");

        // Check if the customer exists
        Customer owner = office.getListOfCustomers().stream()
            .filter(c -> c.getId().equals(customerId))
            .findFirst()
            .orElse(null);

        if (owner == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        // Create a new Car object
        Car car = new Car(CarType.COMPACT, licensePlate, owner);

        // Register the car
        String result = office.register(car);

        return result;  // Return success message or registration result
    }

    @Override
    public String getCommandName() {
        return "RegisterCar";
    }

    @Override
    public String getDisplayName() {
        return "Register Car";
    }

}
