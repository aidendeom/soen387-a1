package org.soen387.domain.user.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class UserTDG
{
	public static final String FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?;";
	public static final String GET_NEXT_ID = "SELECT max(id) AS id FROM users;";
	
	public static ResultSet findByUsername(String username) throws SQLException
	{
    	Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND_BY_USERNAME);
		ps.setString(1, username);
		return ps.executeQuery();
	}
	
	private static long nextID = -1L;
	
	public static long getNextId() throws SQLException
	{
		if (nextID == -1L)
		{
			Connection con = DbRegistry.getDbConnection();
			PreparedStatement ps = con.prepareStatement(FIND_BY_USERNAME);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				nextID = rs.getLong("id");
			}
		}
		
		return ++nextID;
	}
}
