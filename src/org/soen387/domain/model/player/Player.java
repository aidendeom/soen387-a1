package org.soen387.domain.model.player;

/**
 * 
 *This is a complete stub. 
 */
public class Player {
	long id;
	int version;
	String firstName;
	String lastName;
	String email;

	public Player(long id, int version, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.version = version;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	
	public long getId() {
		return id;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setId(long id) {
		this.id = id;
	}

	
	
}
