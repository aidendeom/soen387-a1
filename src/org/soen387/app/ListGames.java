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

/**
 * Servlet implementation class ListGames
 */
@WebServlet("/ListGames")
public class ListGames extends AbstractPageController implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public ListGames() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//We're inheriting from SoenEA's Servlet, so we get the DB getting taken care of mostly for us
		//We just need to make sure MyResources.properties has all our stuff, then it opens
		//and closes db connections for us and we can get at them by asking for
		//DbRegistry.getConnection()
		//
		//But I don't start a transaction or deal with commit/rollback automatically... You gotta do that as
		//appropriate!
		
		try {
			List<CheckerBoard> games = CheckerBoardDataMapper.findAll();
			request.setAttribute("games", games);
			request.getRequestDispatcher("/WEB-INF/jsp/xml/listgames.jsp").forward(request, response);
		} catch (MapperException e) {

			e.printStackTrace();
		}
		
	}


}
