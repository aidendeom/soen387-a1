package org.soen387.app;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.DispatcherServlet;
import org.dsrg.soenea.application.servlet.Servlet;
import org.dsrg.soenea.service.MySQLConnectionFactory;
import org.dsrg.soenea.service.registry.Registry;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

/**
 * Servlet implementation class PageController
 */
@WebServlet("/PageController")
public abstract class AbstractPageController extends Servlet {
	private static final long serialVersionUID = 1L;
    private static boolean DBSetup = false;
    /**
     * @see DispatcherServlet#DispatcherServlet()
     */
    public AbstractPageController() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	AbstractPageController.setupDb();
    };

    public static synchronized void setupDb() {
    	if(!DBSetup) {
    		prepareDbRegistry();
    	}
    }
    
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
			tablePrefix = "";
		}
		if(tablePrefix == null) {
			tablePrefix = "";
		}
		DbRegistry.setTablePrefix(tablePrefix);
	}

}
