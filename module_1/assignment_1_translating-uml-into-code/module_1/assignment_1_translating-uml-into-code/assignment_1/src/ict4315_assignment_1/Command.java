/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package ict4315_assignment_1;

import java.util.Properties;

public interface Command {

		public String getCommandName();
		public String getDisplayName();
		public String execute(Properties params);
	
}
