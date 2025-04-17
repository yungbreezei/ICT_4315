/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ParkingService {

	private ParkingOffice office;
	private Map<String, Command> commands = new HashMap<>();
	
    /*
     * Constructors: 
     */
	public ParkingService(ParkingOffice office){
		
		this.office = office;
	}
    /**
     * Getters: 
     */
	public ParkingOffice getOffice() {
		return office;
	}
	
    /**
     * Setters: 
     */
	public void setOffice(ParkingOffice office) {
		this.office = office;
	}
	
    /*
     * Methods
     */	 
	
    /**
     * Registers a command with the ParkingService. Allows the service to
     * execute commands based on their command name.
     * 
     * @param command The command to be registered with the service.
     */
    public void register(Command command) {
        commands.put(command.getCommandName(), command);
    }

    /**
     * Executes a command based on the command type and arguments provided. If the command
     * is found, it is executed with the given arguments.
     * 
     * @param commandType The name of the command to be executed.
     * @param args The arguments to be passed to the command.
     * @return The result of executing the command, or "Invalid Command" if the command is not found.
     */
    public String performCommand(String commandType, String[] args) {
        // Retrieve the command from the map based on the command type
        Command command = commands.get(commandType);
        
        // If the command is found, execute it with the provided arguments
        if (command != null) {
            Properties props = new Properties();
            // Split the arguments into key-value pairs and populate the Properties object
            for (String arg : args) {
                String[] parts = arg.split("=", 2);
                if (parts.length == 2) {
                    props.setProperty(parts[0].trim(), parts[1].trim());
                }
            }
            
            // Check for required parameters for each command type
            if (commandType.equals("ParkCar")) {
                if (props.getProperty("permitId") == null || 
                    props.getProperty("parkingLotId") == null) {
                    return "Missing parking lot or permit ID";
                }
            }
            
            // Execute the command with the properties and return the result
            return command.execute(props);
        }
        // If the command is not found, return an error message
        return "Invalid Command";
    }
}