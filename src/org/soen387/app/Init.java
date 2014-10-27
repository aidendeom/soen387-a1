package org.soen387.app;

import java.sql.SQLException;

import org.soen387.domain.checkerboard.tdg.CheckerBoardTDG;
import org.soen387.domain.player.tdg.PlayerTDG;
import org.soen387.domain.user.tdg.UserTDG;

/**
 * Initializes the database
 *
 */
public class Init {

	public static void main(String[] args) {
		
		System.out.println("Creating all tables");
		System.out.println("--------------------");
		try {
			UserTDG.createTable();
			System.out.println("Created User Table");
		} catch (SQLException e) {
			System.out.println("Error creating user table");
			e.printStackTrace();
		}
		
		try {
			PlayerTDG.createTable();
			System.out.println("Created Player Table");
		} catch (SQLException e) {
			System.out.println("Error creating player table");
			e.printStackTrace();
		}
		
		try {
			CheckerBoardTDG.createTable();
			System.out.println("Created CheckerBoard Table");
		} catch (SQLException e) {
			System.out.println("Error creating checkerboard table");
			e.printStackTrace();
			
		}
	}
}
