package org.soen387.domain.player.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayerTDG {
	public static final String TABLE_NAME = "players";
	public static final String TRUNCATE_TABLE = "TRUNCATE TABLE  " + TABLE_NAME + ";";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	public static final String FIND_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?;";
	public static final String FIND_BY_USER_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE userid = ?;";
	public static final String GET_NEXT_ID = "SELECT max(id) AS id FROM " + TABLE_NAME + ";";
	public static final String INSERT = "INSERT INTO " + TABLE_NAME + " (id, version, firstName, lastName, email, userid) VALUES(?,?,?,?,?,?);";
	public static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";
	public static final String CREATE_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
			+ "id BIGINT, "
			+ "version int, "
			+ "firstName VARCHAR(80), "
			+ "lastName VARCHAR(80), "
			+ "email VARCHAR(80), "
			+ "userid BIGINT"
			+ ");";
	public static final String EMAIL_EXISTS = "SELECT email FROM " + TABLE_NAME +
	        " WHERE email = ?;";
	
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
		PreparedStatement ps = con.prepareStatement(FIND_BY_ID);
		ps.setLong(1, id);
		return ps.executeQuery();
	}
	
	public static ResultSet findByUserID(long id) throws SQLException
	{
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND_BY_USER_ID);
		ps.setLong(1, id);
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
                              String firstName,
                              String lastName,
                              String email,
                              long userID) throws SQLException
    {
        Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(INSERT);
        
        ps.setLong(1, id);
        ps.setInt(2, version);
        ps.setString(3, firstName);
        ps.setString(4, lastName);
        ps.setString(5, email);
        ps.setLong(6, userID);
        
        return ps.executeUpdate();
    }

	public static ResultSet findAll() throws SQLException
	{
		Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(FIND_ALL);
        return ps.executeQuery();
	}

    public static ResultSet emailExists(String email) throws SQLException
    {
        Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(EMAIL_EXISTS);
        ps.setString(1, email);
        return ps.executeQuery();
    }

}
