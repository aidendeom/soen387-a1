package org.soen387.app;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.MapperException;
import org.soen387.domain.challenge.mapper.ChallengeMapper;
import org.soen387.domain.model.challenge.Challenge;

@WebServlet("/ListChallenges")
public class ListChallenges extends AbstractPageController implements Servlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public ListChallenges() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
        	String mode = request.getParameter("mode");
            List<Challenge> challenges = ChallengeMapper.findAll();
            request.setAttribute("challenges", challenges);
            if (mode != null && mode.equals("xml")){
            	request.getRequestDispatcher("/WEB-INF/jsp/xml/listchallenges.jsp").forward(request, response);
            } else {
                //this would be for html view, but we dont' have
            	request.getRequestDispatcher("/WEB-INF/jsp/xml/listchallenges.jsp").forward(request, response);
            }
        } catch (MapperException e) {

            e.printStackTrace();
        }
        
    }


}
