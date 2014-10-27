package org.soen387.domain.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.soen387.domain.model.user.User;
import org.soen387.domain.user.tdg.UserTDG;

public class UserMapper {

	public static User findByUsername(String username) throws SQLException
	{
		ResultSet rs = UserTDG.findByUsername(username);
		
		User user = null;
		
		if (rs.next())
		{
			long id = rs.getLong("id");
			int version = rs.getInt("version");
			String usern = rs.getString("username");
			String pass = rs.getString("password");
			user = new User(id, version, usern, pass);
		}
		
		return user;
	}
	
	public static long getNextId() throws SQLException
	{
	    return UserTDG.getNextId();
	}

    public static int insert(User user) throws SQLException
    {
        return UserTDG.insert(user.getId(), user.getVersion(), user.getUsername(), user.getPassword());
    }
}
