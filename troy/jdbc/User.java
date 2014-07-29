package troy.jdbc;

public class User {
	
	private int id = 0;
	private String login = "";
	private String password = "";
	private String name = "undefined";
	private String lastName = "undefined";
	private String email = "";
	
	
	public User(String login, String password, String email){
		this.login=login;
		this.password = password;
		this.email=email;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", name=" + name + ", lastName=" + lastName + ", email="
				+ email + "]";
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	

}
