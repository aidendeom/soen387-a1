package org.soen387.domain.model.user;

import java.sql.SQLException;

import org.soen387.domain.user.tdg.UserTDG;

public class User {
	
	private long id;
	private int version;
	private String username;
	private String password;
	
	public User(long id, int version, String username, String password) {
		super();
		this.id = id;
		this.version = version;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
