package org.soen387.domain.player.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayerTDG {

	public static final String FIND_BY_ID = "SELECT * FROM players WHERE id = ?;";
	public static final String GET_NEXT_ID = "SELECT max(id) AS id FROM players;";
	
	public static ResultSet find(long id) throws SQLException
	{
    	Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND_BY_ID);
		ps.setLong(1, id);
		return ps.executeQuery();
	}
	
	private static long nextID = -1L;
	
	public static long getNextId() throws SQLException
	{
		if (nextID == -1L)
		{
			Connection con = DbRegistry.getDbConnection();
			PreparedStatement ps = con.prepareStatement(FIND_BY_ID);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				nextID = rs.getLong("id");
			}
		}
		
		return ++nextID;
	}

}
