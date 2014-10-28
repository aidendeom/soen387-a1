package org.soen387.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.MapperException;
import org.soen387.domain.checkerboard.mapper.CheckerBoardDataMapper;
import org.soen387.domain.model.checkerboard.CheckerBoard;
import org.soen387.domain.model.player.Iplayer;
import org.soen387.domain.model.user.User;
import org.soen387.domain.player.mapper.PlayerMapper;
import org.soen387.domain.user.mapper.UserMapper;

/**
 * Servlet implementation class ListGames
 */
@WebServlet("/Login")
public class Login extends AbstractPageController implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public Login() {
        super();
    }

	@Override
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String username = request.getParameter("user");
			String password = request.getParameter("pass");
			
			User user = UserMapper.findByUsername(username);
			if (user != null && password.equals(user.getPassword()))
			{
				Iplayer player = PlayerMapper.find(user.getId());
				
				request.setAttribute("user", user);
				request.setAttribute("player", player);
				
				request.getRequestDispatcher("/WEB-INF/jsp/xml/login.jsp").forward(request,  response);
			}
			else if (user == null)
			{
				request.setAttribute("reason", "username");
				request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("reason", "password");
				request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request, response);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
