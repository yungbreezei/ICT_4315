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
	
    /**
     * Constructors: 
     */
	public ParkingService(ParkingOffice office){
		
		this.office = office;
	}
	
    /*
     * Methods
     */	 
    public void register(Command command) {
        commands.put(command.getCommandName(), command);
    }

    public String performCommand(String commandType, String[] args) {
        Command command = commands.get(commandType);
        if (command != null) {
            Properties props = new Properties();
            for (String arg : args) {
                String[] parts = arg.split("=", 2);
                if (parts.length == 2) {
                    props.setProperty(parts[0].trim(), parts[1].trim());
                }
            }
            return command.execute(props);
        }
        return "Invalid Command";
    }
}