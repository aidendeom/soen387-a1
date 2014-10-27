package org.soen387.test;

import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.soen387.app.AbstractPageController;
import org.soen387.domain.checkerboard.tdg.CheckerBoardTDG;
import org.soen387.domain.model.checkerboard.GameStatus;

public class Setup {

	public static void main(String[] args) throws InterruptedException {
		AbstractPageController.setupDb();
		try {
			CheckerBoardTDG.createTable();
			
			CheckerBoardTDG.insert(1, 1, GameStatus.Ongoing.getId(), 
					"b b b b  b b b bb b b b                  r r r rr r r r  r r r r", 
					1, 2, 1);
			//Ample time to look at your db for changes if we're not doing transactions
			Thread.sleep(10000);
			CheckerBoardTDG.dropTable();
			
			DbRegistry.getDbConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
