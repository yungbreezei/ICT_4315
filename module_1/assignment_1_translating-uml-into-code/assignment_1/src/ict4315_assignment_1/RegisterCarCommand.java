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

    public void checkParameters(Properties params) {
        if (params.getProperty("customerId") == null ||
            params.getProperty("licensePlate") == null ||
            params.getProperty("carType") == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }
    }
	
    @Override
    public String execute(Properties props) {
        String customerId = props.getProperty("customerId");
        Customer customer = office.getListOfCustomers().stream()
            .filter(c -> c.getId().equals(customerId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Car car = new Car(
            CarType.valueOf(props.getProperty("carType")), // COMPACT or SUV
            props.getProperty("licensePlate"),
            customer
        );

        return office.register(car);
    }

    @Override
    public String getCommandName() {
        return "REGISTER_CAR";
    }

    @Override
    public String getDisplayName() {
        return "Register Car";
    }

}
