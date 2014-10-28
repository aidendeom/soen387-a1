package org.soen387.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.soen387.domain.model.player.Player;
import org.soen387.domain.player.mapper.PlayerMapper;

@WebServlet("/ListPlayers")
public class ListPlayers extends AbstractPageController implements Servlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public ListPlayers()
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
        	List<Player> players = PlayerMapper.findAll();
        	request.setAttribute("players", players);
        	request.getRequestDispatcher("/WEB-INF/jsp/xml/listplayers.jsp").forward(request,  response);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
