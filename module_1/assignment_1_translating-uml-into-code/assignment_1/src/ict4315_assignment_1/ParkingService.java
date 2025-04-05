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
	private Map<String, Command> commands;
	
    /**
     * Constructors: 
     */
	public ParkingService(ParkingOffice office){
		
		this.office = office;
		this.commands = new HashMap<>();
	}
	
    /*
     * Methods
     */	 
    public void register(Command command) {
        commands.put(command.getCommandName(), command);
    }

    public String performCommand(String commandType, Properties args) {
        Command command = commands.get(commandType);
        if (command != null) {
            return command.execute(args);
        }
        return "Invalid Command";
    }
}