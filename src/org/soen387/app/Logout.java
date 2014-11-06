package org.soen387.app;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends AbstractPageController implements Servlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public Logout() {
        super();
    }

    @Override
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
    	HttpSession session = request.getSession();
    	String mode = request.getParameter("mode");
    	
        System.out.println(session.isNew());
        
        Object userID = session.getAttribute("userid");
        Object playerID = session.getAttribute("playerid");
        
        session.removeAttribute("userid");
        session.removeAttribute("playerid");
        
        session.invalidate();
        
        if (userID != null && playerID != null)
        {        
            request.setAttribute("status", "success");
        }
        else
        {
            request.setAttribute("status", "failed");
        }
        
        if (mode != null && mode.equals("xml")){
        	request.getRequestDispatcher("/WEB-INF/jsp/xml/logout.jsp").forward(request, response);
        } else {
            //this would be for html view, but we dont' have
        	request.getRequestDispatcher("/WEB-INF/jsp/xml/logout.jsp").forward(request, response);
        }
    }
}
