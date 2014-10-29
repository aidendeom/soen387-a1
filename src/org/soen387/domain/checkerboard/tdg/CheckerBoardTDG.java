package org.soen387.domain.checkerboard.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class CheckerBoardTDG {
	public static final String TABLE_NAME = "CheckerBoard";
	public static final String GET_NEXT_ID = "SELECT max(id) AS id FROM " + TABLE_NAME + ";";
	public static final String COLUMNS = "id, version, status, pieces, first_player, second_player, current_player ";
	public static final String TRUNCATE_TABLE = "TRUNCATE TABLE  " + TABLE_NAME + ";";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	public static final String CREATE_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
			+ "id BIGINT, "
			+ "version int, "
			+ "status int, "
			+ "first_player BIGINT, "
			+ "second_player BIGINT, "
			+ "current_player BIGINT, "
			+ "pieces CHAR(64)"
			+ ");";

	public static final String UPDATE = "UPDATE " + TABLE_NAME + " "
			+ "SET version=version+1, "
			+ "status=? "
			+ "pieces=? "
			+ "first_player=? "
			+ "second_player=? "
			+ "current_player=? "
			+ "WHERE id=? AND version=?;";
	public static final String DELETE = "DELETE FROM " + TABLE_NAME + " "
			+ "WHERE id=? AND version=?;";

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
	
	
	public static final String INSERT = "INSERT INTO " + TABLE_NAME + " (" + COLUMNS + ") "
			+ "VALUES (?,?,?,?,?,?,?);";
	public static int insert(long id, int version, int status, String pieces, long first, long second, long current) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT);
		ps.setLong(1,id);
		ps.setInt(2,version);
		ps.setInt(3,status);
		ps.setString(4,pieces);
		ps.setLong(5, first);
		ps.setLong(6, second);
		ps.setLong(7, current);
		System.out.println(ps.toString());
		return ps.executeUpdate();
	}
	
	public static final String FIND_ALL = "SELECT " + COLUMNS + " FROM " + TABLE_NAME + ";";
	public static ResultSet findAll() throws SQLException {
    	Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(FIND_ALL);
		return ps.executeQuery();
	}
	
	public static final String FIND_FOR_PLAYER = "SELECT " + COLUMNS + " FROM " + TABLE_NAME
	        + " WHERE first_player = ? OR second_player = ?;";
	public static ResultSet findForPlayer(long id) throws SQLException
	{
	    Connection con = DbRegistry.getDbConnection();
	    PreparedStatement ps = con.prepareStatement(FIND_FOR_PLAYER);
	    ps.setLong(1, id);
	    ps.setLong(2, id);
	    return ps.executeQuery();
	}

	public static final String GAME_EXISTS = "SELECT * FROM " + TABLE_NAME
	        + " WHERE (first_player = ? AND second_player = ?)"
	        + " OR (first_player = ? AND second_player = ?) " 
	        + " AND status = 0;";
	
    public static boolean gameExists(long id1, long id2) throws SQLException
    {
        Connection con = DbRegistry.getDbConnection();
        PreparedStatement ps = con.prepareStatement(GAME_EXISTS);
        ps.setLong(1, id1);
        ps.setLong(2, id2);
        ps.setLong(3, id2);
        ps.setLong(4, id1);
        
        ResultSet rs = ps.executeQuery();
        
        boolean result = rs.next();
        
        rs.close();
        
        return result;
    }

	public static long getNextId() throws SQLException {
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
	
}
