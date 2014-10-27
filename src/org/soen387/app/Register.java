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
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.user.User;
import org.soen387.domain.player.mapper.PlayerMapper;
import org.soen387.domain.user.mapper.UserMapper;

@WebServlet("/Register")
public class Register extends AbstractPageController implements Servlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public Register()
    {
        super();
    }

    @Override
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException,
                                                                       IOException
    {
        try
        {
            long id = UserMapper.getNextId();
            
            String username = request.getParameter("user");
            String password = request.getParameter("pass");
            
            User user = new User(id, 1, username, password);
            
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String email = request.getParameter("email");
            
            Player player = new Player(id, 1, firstName, lastName, email, user);
            
            UserMapper.insert(user);
            PlayerMapper.insert(player);
            
            request.setAttribute("user", user);
            request.setAttribute("player", player);
            
            request.getRequestDispatcher("/WEB-INF/jsp/xml/login.jsp").forward(request,  response);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
