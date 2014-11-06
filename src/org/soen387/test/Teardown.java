package org.soen387.test;

import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;
//import org.soen387.app.AbstractPageController;
import org.soen387.domain.checkerboard.tdg.CheckerBoardTDG;
//import org.soen387.domain.model.checkerboard.GameStatus;

public class Teardown {

	public static void main(String[] args) throws InterruptedException {
		//AbstractPageController.setupDb();
		try {
			CheckerBoardTDG.dropTable();
			
			DbRegistry.getDbConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
