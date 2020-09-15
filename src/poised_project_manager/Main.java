package poised_project_manager;

//Imports
import java.util.*;

/**
* 
* @author Iain Jenkins
*
*/

public class Main {
	
public static void main(String[] args) {
		
		// Project Variables
		int choice = 0;
		Scanner input = new Scanner(System.in);
		while(true) {
			
			// Variables
			int projectSelection = 0;
			boolean projectSelected = false;
			
			// Prints Main Menu
			Menu.printMainMenu();
			
			// Handles Menu selection
			choice = Menu.selectedOption();
			
			// Picking a Project
			if(choice == 1) {
				try {
					System.out.println("Pick a Project from the list using it's Number");
					Menu.printAllProjectsNames();
					projectSelection = Menu.selectedOption() - 1;
					if(projectSelection < Menu.projects.size() && projectSelection >= 0) {
						projectSelected = true;
						Menu.printMenu();
					} else {
						System.out.print("There is no project at that number\n");
					}
				} catch(Exception e) {
					System.out.println("No projects exist yet");
				}
			}
			
			// Creating Project
			if(choice == 2) {
				
				// Creating Project Object
				Project project = Menu.createProject();
				Menu.projects.add(project);
				
				// Updating Database
				SQL_Database.addProjectsToDatabase(project);
			}
			
			// Displays all Projects
			if(choice == 3) {
				try {
					Menu.printAllProjects();
				} catch(Exception e) {
					System.out.println("No projects exist yet");
				}
			}
			
			// Displays Projects that are Unfinished
			if(choice == 4) {
				try {
					Menu.printUnfinishedProjects();
				} catch(Exception e) {
					System.out.println("No projects exist yet");
				}
			}
			
			// Displays Projects which are overdue
			if(choice == 5) {
				try {
					Menu.printOverdueProjects();
				} catch(Exception e) {
					System.out.println("No projects exist yet");
				}
			}
			
			// Exits Program
			if(choice == 0) {
				input.close();
				break;
			}
			while(projectSelected) {
				choice = Menu.selectedOption();
				
				if(choice == 1) {
					try {
						Menu.printSelectedProject(projectSelection);
					} catch(Exception e) {
						System.out.println("No Projects exist yet");
					}
				}
				
				// Changes a Projects Deadline
				if(choice == 2) {
					try {
						Menu.projects.get(projectSelection).changeDate();
						
						// Updating Project to Database
						SQL_Database.updateDatabase("Deadline",(projectSelection + 1));;
					} catch(Exception e) {
						System.out.println("No projects exist yet");
					}
				}
				
				// Change Total Paid to Date
				if(choice == 3) {
					try {
						Menu.projects.get(projectSelection).changeAmountPaid();
						
						// Updating Project to Database
						SQL_Database.updateDatabase("AmountPaid", (projectSelection + 1));;
					} catch(Exception e) {
						System.out.println("No projects exist yet");
					}
				}
				
				// Updates Contractor Details
				if(choice == 4) {
					try {
						Menu.projects.get(projectSelection).updateContractor();
						// Updating Project to Database
						SQL_Database.updateDatabase("Contractor", (projectSelection + 1));;
					} catch(Exception e) {
						System.out.println("No projects exist yet");
					}
				}
				// Finalise Project
				if(choice == 5) {
					try {
						
						// Creates Invoice
						String [] invoice = Menu.projects.get(projectSelection).finaliseProject(
							Menu.projects.get(projectSelection).totalFee, Menu.projects.get(projectSelection).totalPaidDate);
						
						// Updating Finalised Status in Projects List
						if(invoice[3] == "Finalised") {
							Menu.projects.get(projectSelection).finalised = "Yes";
						}
						
						// Outputs Invoice
						for(int i = 0;i < 5;i++) {
							System.out.println(invoice[i]);
						}
						
						
						// Updating Project to Database
						SQL_Database.updateDatabase("Finalised", (projectSelection + 1));
					} catch(Exception e) {
						e.printStackTrace();
						System.out.println("No projects exist yet");
					}
				}
				if(choice == 0) {
					break;
				}
				Menu.printMenu();
			}	
		}
	}
}