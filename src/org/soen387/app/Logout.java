package org.soen387.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dsrg.soenea.domain.MapperException;
import org.soen387.domain.checkerboard.mapper.CheckerBoardDataMapper;
import org.soen387.domain.model.checkerboard.CheckerBoard;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.user.User;
import org.soen387.domain.player.mapper.PlayerMapper;
import org.soen387.domain.user.mapper.UserMapper;

/**
 * Servlet implementation class ListGames
 */
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
        
        System.out.println(session.isNew());
        
        Object userID = session.getAttribute("userID");
        Object playerID = session.getAttribute("playerID");
        
        session.removeAttribute("userID");
        session.removeAttribute("playerID");
        
        if (userID != null && playerID != null)
        {        
            request.setAttribute("status", "success");
            request.getRequestDispatcher("/WEB-INF/jsp/xml/logout.jsp").forward(request, response);
        }
        else
        {
            request.setAttribute("status", "failed");
            request.getRequestDispatcher("/WEB-INF/jsp/xml/logout.jsp").forward(request, response);
        }
    }
}
