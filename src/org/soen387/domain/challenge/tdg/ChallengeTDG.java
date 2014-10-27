package org.soen387.domain.challenge.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class ChallengeTDG {

	public static final String TABLE_NAME = "challenges";
	public static final String TRUNCATE_TABLE = "TRUNCATE TABLE  " + TABLE_NAME + ";";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	public static final String FIND_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?;";
	public static final String GET_NEXT_ID = "SELECT max(id) AS id FROM " + TABLE_NAME + ";";
	public static final String INSERT = "INSERT INTO " + TABLE_NAME + " (id, challengerId, challengeeId, statusId) VALUES(?,?,?,?);";
	public static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME + ";";
	public static final String CREATE_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
			+ "id BIGINT, "
			+ "challengerId BIGINT, "
			+ "challengeeId BIGINT, "
			+ "status int"
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
		PreparedStatement ps = con.prepareStatement(FIND_BY_ID);
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
                              long challengerId,
                              long challengeeId,
                              int status) throws SQLException
    {
        Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(INSERT);
        
        ps.setLong(1, id);
        ps.setLong(2, challengerId);
        ps.setLong(3, challengeeId);
        ps.setInt(4, status);
        
        return ps.executeUpdate();
    }

	public static ResultSet findAll() throws SQLException
	{
		Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(FIND_ALL);
        return ps.executeQuery();
	}
}
