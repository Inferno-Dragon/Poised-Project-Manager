package poised_project_manager;

/**
 * 
 * @author Iain Jenkins
 *
 */

public class Person{
	// Variables
	String name = "";
	String phoneNum = "";
	String email = "";
	String address = "";
	String role = "";
	
	/**
	 * Constructor for A Person Object
	 * @param role
	 * @param name
	 * @param phoneNum
	 * @param email
	 * @param address
	 */
	public Person(String role, String name, String phoneNum, String email,
			String address) {
		this.role = role;
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
		this.address = address;
	}
	
	// Methods
	
	/** Prints a Person Objects details */
	public void printPerson() {
		String personDetails = "\n" + role;
		personDetails += "\nName: " + name;
		personDetails += "\nPhone Number: " + phoneNum;
		personDetails += "\nEmail Address: " + email;
		personDetails += "\nPhysical Address: " + address;
		
		System.out.println(personDetails);
	}
}