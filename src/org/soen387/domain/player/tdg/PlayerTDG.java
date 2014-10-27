package org.soen387.domain.player.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayerTDG {

	public static final String FIND_BY_ID = "SELECT * FROM players WHERE id = ?;";
	public static final String GET_NEXT_ID = "SELECT max(id) AS id FROM players;";
	public static final String INSERT = "INSERT INTO players(id, version, firstName, lastName, email) VALUES(?,?,?,?,?);";
	public static final String FIND_ALL = "SELECT * FROM players;";
	
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
			PreparedStatement ps = con.prepareStatement(GET_NEXT_ID);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				nextID = rs.getLong("id");
			}
		}
		
		return ++nextID;
	}

    public static int insert(long id,
                              int version,
                              String firstName,
                              String lastName,
                              String email) throws SQLException
    {
        Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(INSERT);
        
        ps.setLong(1, id);
        ps.setInt(2, version);
        ps.setString(3, firstName);
        ps.setString(4, lastName);
        ps.setString(5, email);
        
        return ps.executeUpdate();
    }

	public static ResultSet findAll() throws SQLException
	{
		Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(FIND_ALL);
        return ps.executeQuery();
	}

}
