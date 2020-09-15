package poised_project_manager;

//Imports
import java.util.*;
import java.sql.*;

/**
 * 
 * @author Iain Jenkins
 *
 */

public class SQL_Database {
	
	// Methods
	
	/** Connects to the Database
	 * 
	 * @return Statement Object of the Database
	 */
	public static Statement getDatabaseStatement() {
		try {
			Connection conn = 
				DriverManager.getConnection("jdbc:mysql://localhost:3306/poisepms?useSSL=false&allowPublicKeyRetrieval=true",
						"myuser", "Heyo77");
			Statement stmnt = conn.createStatement();
			return stmnt;
		} catch (SQLException e) {
			System.out.println("Could not establish connection to the Database.");
			return null;
		}
	}
	
	/** Reads Projects from Database into an ArrayList
	 * 
	 * @return ArrayList of Project objects
	 */
	public static ArrayList<Project> getProjects() {
		// Create ArrayList of projects
		ArrayList<Project> projects = new ArrayList<Project>();
		
		try {
			Statement stmnt = getDatabaseStatement();
			// String used to select all fields needed from tables in Database
			String select = "select * from project"
					+ " inner join contractor on project.CONTRA_ID = contractor.ID"
					+ " inner join customer on project.CUST_ID = customer.ID"
					+ " inner join architect on project.ARCH_ID = architect.ID";
			
			ResultSet results = stmnt.executeQuery(select);
			
			while(results.next()) {
				// Creating Project Object
				Project project = new Project();
				
				project.projName = results.getString("project.NAME");
				project.projNum = results.getString("NUM");
				project.buildingType = results.getString("BUILD_TYPE");
				project.buildingAddress = results.getString("BUILD_ADDRESS");
				project.erfNum = results.getString("ERF_NUM");
				project.deadline = results.getString("DEADLINE");
				project.totalFee = results.getDouble("TOTAL_FEE");
				project.totalPaidDate = results.getDouble("AMOUNT_PAID");
				project.finalised = results.getString("FINALISED");
				
				// Creating and Assigning Project People
				Person architect = new Person("Architect", results.getString("architect.NAME"), results.getString("architect.PHONE_NUM"),
						results.getString("architect.EMAIL"),results.getString("architect.ADDRESS"));
				project.architect = architect;
				
				Person customer = new Person("Customer", results.getString("customer.NAME"), results.getString("customer.PHONE_NUM"),
						results.getString("customer.EMAIL"),results.getString("customer.ADDRESS"));
				project.customer = customer;
				
				Person contractor = new Person("Contractor", results.getString("contractor.NAME"), results.getString("contractor.PHONE_NUM"),
						results.getString("contractor.EMAIL"),results.getString("contractor.ADDRESS"));
				project.contractor = contractor;
				
				// Adding Project record to Project ArrayList
				projects.add(project);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		// Returns Project Object ArrayList
		return projects;
	}
	
	/** Adds Project Object ArrayList info to Database */
	public static void addProjectsToDatabase(Project project) {
		Statement statement = getDatabaseStatement();
		int count = 0;
		
		try {
			ResultSet num = statement.executeQuery("select NUM from project");
			
			while(num.next()) {
				count ++;
			}
			
			// String used to insert new Projects fields into the Database
			String insertProjectString = "insert into project values ('" + project.projName + "', '" + project.projNum + "', '" + project.buildingType
					+ "', '" + project.buildingAddress + "', '" + project.erfNum + "', '" + project.deadline + "', " + project.totalFee + ", "
					+ project.totalPaidDate + ", '" + project.finalised + "', " + (count +1) + ", " + (count +1) + ", " + (count +1) + ")";
			
			int num1 = statement.executeUpdate(insertProjectString);
			
			String insertContractorString = "insert into contractor values (" + (count +1) + ", '" + project.contractor.name + "', '" + project.contractor.phoneNum
					+ "', '" + project.contractor.email + "', '" + project.contractor.address + "')";
			
			int num2 = statement.executeUpdate(insertContractorString);
			
			String insertCustomerString = "insert into customer values (" + (count +1) + ", '" + project.customer.name + "', '" + project.customer.phoneNum
					+ "', '" + project.customer.email + "', '" + project.customer.address + "')";
			
			int num3 = statement.executeUpdate(insertCustomerString);
			
			String insertArchitectString = "insert into architect values (" + (count +1) + ", '" + project.architect.name + "', '" + project.architect.phoneNum
					+ "', '" + project.architect.email + "', '" + project.architect.address + "')";
			
			int num4 = statement.executeUpdate(insertArchitectString);
			
 		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** Updates a Projects Record in the Database 
	 * 
	 * @param field Which field to update for that projects record
	 * @param project Which Project's record to update
	 * */
	public static void updateDatabase(String field, int project) {
		Statement statement = getDatabaseStatement();
		
		// Strings for varies Update queries
		String updateDeadline = "update project set DEADLINE = '" + Menu.projects.get(project - 1 ).deadline + "' where CUST_ID = " + project;
		String updateAmountPaid = "update project set AMOUNT_PAID = " + Menu.projects.get(project - 1).totalPaidDate + " where CUST_ID = " + project;
		String updateContractor  = "update contractor set PHONE_NUM = '" + Menu.projects.get(project -1).contractor.phoneNum + "', EMAIL = '" +
				Menu.projects.get(project - 1).contractor.email + "' where ID = " + project;
		String updateFinalised = "update project set FINALISED = '" + Menu.projects.get(project - 1).finalised + "' where CUST_ID = " + project;
		
		try {
			// If statements to decide which update to make
			if(field.equals("Deadline")) {
				int countDeadline = statement.executeUpdate(updateDeadline);
			}
			
			if(field.equals("AmountPaid")) {
				int countAmountPaid = statement.executeUpdate(updateAmountPaid);
			}
			
			if(field.equals("Contractor")) {
				int countContractor = statement.executeUpdate(updateContractor);
			}
			
			if(field.equals("Finalised")) {
				int countFinalised = statement.executeUpdate(updateFinalised);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}