package org.soen387.app;

import java.io.IOException;
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
import org.soen387.domain.player.mapper.PlayerMapper;

/**
 * Servlet implementation class ListGames
 */
@WebServlet("/ViewGame")
public class ViewGame extends AbstractPageController implements Servlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public ViewGame()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) 
                                          throws ServletException,
                                          IOException
    {
        try
        {
            long id = Long.parseLong(request.getParameter("id"));
            CheckerBoard g = CheckerBoardDataMapper.find(id);
           
            request.setAttribute("game", g);
            
            request.getRequestDispatcher("/WEB-INF/jsp/xml/viewgame.jsp").forward(request, response);
        }
        catch (MapperException e)
        {
            e.printStackTrace();
        }

    }

}
