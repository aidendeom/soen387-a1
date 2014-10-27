package org.soen387.domain.user.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class UserTDG
{
	public static final String TABLE_NAME = "users";
	public static final String TRUNCATE_TABLE = "TRUNCATE TABLE  " + TABLE_NAME + ";";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	public static final String FIND = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
	public static final String FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?;";
	public static final String GET_NEXT_ID = "SELECT max(id) AS id FROM users;";
	public static final String INSERT = "INSERT INTO " + TABLE_NAME + " ("
			+ "id, version, username, password) " 
			+ "VALUES(?,?,?,?);";
	public static final String CREATE_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
			+ "id BIGINT, "
			+ "version int, "
			+ "username VARCHAR(80), "
			+ "password VARCHAR(80)"
			+ ");";
	
	private static long nextID = -1L;

	public static void createTable() throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		Statement update = con.createStatement();
		update.execute(CREATE_TABLE);
	}
	
	public static void dropTable() throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		Statement update = con.createStatement();
		//commented out the truncate table as it seems unnecessary
		//update.execute(TRUNCATE_TABLE);
		//update = con.createStatement();
		update.execute(DROP_TABLE);
	}
	
	public static ResultSet find(long id) throws SQLException
	{
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND);
		ps.setLong(1, id);
		return ps.executeQuery();
	}
	
	public static ResultSet findByUsername(String username) throws SQLException
	{
    	Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND_BY_USERNAME);
		ps.setString(1, username);
		return ps.executeQuery();
	}
	
	
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
                              String username,
                              String password) throws SQLException
    {
        Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(INSERT);
        
        ps.setLong(1, id);
        ps.setInt(2, version);
        ps.setString(3, username);
        ps.setString(4, password);
                
        return ps.executeUpdate();
    }
}
