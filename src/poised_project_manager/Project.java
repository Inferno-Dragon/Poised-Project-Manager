package poised_project_manager;

//Imports
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.*;

/**
* 
* @author Iain Jenkins
*
*/

public class Project{
	
	// Variables
	String projName;
	String projNum;
	String buildingType;
	String buildingAddress;
	String erfNum;
	String deadline;
	double totalFee;
	double totalPaidDate;
	
	// People
	Person architect;
	Person customer;
	Person contractor;
	
	String finalised;
	// Constructors
	public Project() {	
	}
	/**
	 * Constructor for A Project object
	 * @param projName
	 * @param projNum
	 * @param buildingType
	 * @param buildAddress
	 * @param erfNum
	 * @param deadline
	 * @param totalFee
	 * @param totalPaidDate
	 * @param architect
	 * @param customer
	 * @param contractor
	 * @param finalised
	 */
	
	public Project(String projName, String projNum, String buildingType, String buildAddress,
				String erfNum, String deadline, double totalFee, double totalPaidDate,Person architect,
				Person customer, Person contractor, String finalised) {
		
		this.projName = projName;
		this.projNum = projNum;
		this.buildingType = buildingType;
		this.buildingAddress = buildAddress;
		this.erfNum = erfNum;
		this.deadline = deadline;
		this.totalFee = totalFee;
		this.totalPaidDate = totalPaidDate;
		this.architect = architect;
		this.customer = customer;
		this.contractor = contractor;
		this.finalised = finalised;
	}
	
	// Methods
	Scanner input = new Scanner(System.in);
	
	/** Print out Projects Details Method */
	
	public void printProject() {
		// Joining into single string
		String projectDetails = "\nProject Name: " + projName;
		projectDetails += "\nProject Number: " + projNum;
		projectDetails += "\nBuilding Type: " + buildingType;
		projectDetails += "\nBuilding Address: " + buildingAddress;
		projectDetails += "\nERF Number: " + erfNum;
		projectDetails += "\nProject Deadline: " + deadline;
		projectDetails += "\nProject Total Fee: " + totalFee;
		projectDetails += "\nAmount Paid to Date: " + totalPaidDate;
		projectDetails += "\nFinalised: " + finalised;
		// Output
		System.out.println(projectDetails);

		customer.printPerson();
		architect.printPerson();
		contractor.printPerson();
	}
	
	/** Changes Due Date of a Project */
	public void changeDate() {
		while(true) {
			try {
				System.out.print("New Deadline: ");
				String temp = input.nextLine();
				Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(temp);
				this.deadline = new SimpleDateFormat("yyyy-MM-dd").format(newDate);
				break;
			}
			catch(Exception e) {
				System.out.println("Please enter in valid date format! eg (2020-05-27)");
			}
		}
		System.out.println("Deadline Updated!");
	}
	
	/** Change Total Paid to Date */
	public void changeAmountPaid() {
		System.out.print("New Total Amount: ");
		input.nextLine();
		double newAmount = input.nextDouble();
		this.totalPaidDate = newAmount;
		System.out.println("Total Paid to Date Updated!");
	}
	
	/** Updates Contractors Details */
	public void updateContractor() {
		// Check if user wants to update phone number
		System.out.println("Update Contractors Phone Number? Enter yes or no");
		String choice = input.next();
		// Update it with user input
		if(choice.equalsIgnoreCase("Yes")) {
			System.out.print("Contractors Phone Number: ");
			String newPhoneNum = input.next();
			this.contractor.phoneNum = newPhoneNum;
			System.out.println("Contractors Phone Number has been Updated!");
		}
		// Check if user wants to update email
		System.out.println("Update Contractors Email Address? Enter yes or no");
		choice = input.next();
		// Update it with user input
		if(choice.equalsIgnoreCase("Yes")) {
			System.out.print("Contractors Email Address: ");
			String newEmail = input.next();
			this.contractor.email = newEmail;
			System.out.println("Contractors Email Address has been Updated!");
		}
	}
	
	/** Finalise Project by Creating invoice
	 * 
	 * @param total
	 * @param totalPaid
	 * @return Invoice String array
	 */
	public String[] finaliseProject(double total, double totalPaid) {
		String[] invoice = new String[5] ;
		double amountToPay = total - totalPaid;
		// Creating  invoice
		
		if(amountToPay > 0) {
			invoice[0] = "Customer Phone Number: " + this.customer.phoneNum;
			invoice[1] = "Customer Email Address: " + this.customer.email;
			invoice[2] = "Amount owed: " + amountToPay;
			invoice[3] = "Finalised";
			invoice[4] = "Date Finalised: " + LocalDateTime.now();
			this.finalised = "Yes";
		}
		
		return invoice;
	}
}
