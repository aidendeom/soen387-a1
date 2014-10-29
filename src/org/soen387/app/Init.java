package org.soen387.app;

import java.sql.SQLException;

import org.dsrg.soenea.service.MySQLConnectionFactory;
import org.dsrg.soenea.service.registry.Registry;
import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.soen387.domain.challenge.tdg.ChallengeTDG;
import org.soen387.domain.checkerboard.tdg.CheckerBoardTDG;
import org.soen387.domain.player.tdg.PlayerTDG;
import org.soen387.domain.user.tdg.UserTDG;

/**
 * Initializes the database
 *
 */
public class Init {
	
	public static void prepareDbRegistry() {
		MySQLConnectionFactory f = new MySQLConnectionFactory(null, null, null, null);
		try {
			f.defaultInitialization();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		DbRegistry.setConFactory(f);
		String tablePrefix;
		try {
			tablePrefix = Registry.getProperty("mySqlTablePrefix");
		} catch (Exception e1) {
			e1.printStackTrace();
			tablePrefix = "";
		}
		if(tablePrefix == null) {
			tablePrefix = "";
		}
		DbRegistry.setTablePrefix(tablePrefix);
	}
	
	public static void prepareTables(){
		try {
			UserTDG.dropTable();
			UserTDG.createTable();
			System.out.println("Created User Table");
		} catch (SQLException e) {
			System.out.println("Error creating user table");
			e.printStackTrace();
		}
		
		try {
			PlayerTDG.dropTable();
			PlayerTDG.createTable();
			System.out.println("Created Player Table");
		} catch (SQLException e) {
			System.out.println("Error creating player table");
			e.printStackTrace();
		}
		
		try {
			CheckerBoardTDG.dropTable();
			CheckerBoardTDG.createTable();
			System.out.println("Created CheckerBoard Table");
		} catch (SQLException e) {
			System.out.println("Error creating checkerboard table");
			e.printStackTrace();
			
		}
		
		try {
			ChallengeTDG.dropTable();
			ChallengeTDG.createTable();
			System.out.println("Created Challenge Table");
		} catch (SQLException e) {
			System.out.println("Error creating challenge Table");
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		
		System.out.println("Connecting to MySQL");
		prepareDbRegistry();
		
		System.out.println("Creating all tables");
		System.out.println("--------------------");
		prepareTables();
		System.out.println("--------------------");
		
		System.out.println("Populate Data");
		
		
	}
}
