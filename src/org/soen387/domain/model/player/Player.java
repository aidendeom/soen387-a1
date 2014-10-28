package org.soen387.domain.model.player;

import org.soen387.domain.model.user.User;

/**
 * 
 *This is a complete stub. 
 */
public class Player implements Iplayer {
	long id;
	int version;
	String firstName;
	String lastName;
	String email;
	User user;

	public Player(long id, int version, String firstName, String lastName, String email, User user) {
		super();
		this.id = id;
		this.version = version;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.user = user;
	}
	
	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#getId()
	 */
	@Override
	public long getId() {
		return id;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#getVersion()
	 */
	@Override
	public int getVersion() {
		return version;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#setVersion(int)
	 */
	@Override
	public void setVersion(int version) {
		this.version = version;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#getLastName()
	 */
	@Override
	public String getLastName() {
		return lastName;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}


	/* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#setId(long)
	 */
	@Override
	public void setId(long id) {
		this.id = id;
	}


    /* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#getUser()
	 */
    @Override
	public User getUser()
    {
        return user;
    }


    /* (non-Javadoc)
	 * @see org.soen387.domain.model.player.Iplayer#setUser(org.soen387.domain.model.user.User)
	 */
    @Override
	public void setUser(User user)
    {
        this.user = user;
    }	
}
