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
import org.soen387.domain.challenge.mapper.ChallengeMapper;
import org.soen387.domain.checkerboard.mapper.CheckerBoardDataMapper;
import org.soen387.domain.model.challenge.Challenge;
import org.soen387.domain.model.challenge.ChallengeStatus;
import org.soen387.domain.model.checkerboard.CheckerBoard;
import org.soen387.domain.model.checkerboard.GameStatus;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.player.mapper.PlayerMapper;
import org.soen387.domain.user.mapper.UserMapper;

@WebServlet("/RespondToChallenge")
public class RespondToChallenge extends AbstractPageController implements Servlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public RespondToChallenge() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try
        {
        	HttpSession session = request.getSession();
        	if (Utils.isLoggedIn(session)){
        		long challengeId = Long.parseLong(request.getParameter("id"));
        		boolean accept = Boolean.parseBoolean(request.getParameter("accept"));
        		Challenge c = ChallengeMapper.find(challengeId);
        		//TODO: do we want to know if c actually exists? (check for null)?
        		if (accept){
        			c.setStatus(ChallengeStatus.Accepted);
        			c.setVersion(c.getVersion()+1);
        			ChallengeMapper.update(c);
        			
        			Player thisPlayer = PlayerMapper.find(c.getChallenger().getId());
                    Player otherPlayer = PlayerMapper.find(c.getChallengee().getId());
                    
                    CheckerBoard game = null;
        			try {
						game = new CheckerBoard(thisPlayer, otherPlayer);
						CheckerBoardDataMapper.insert(game);
					} catch (MapperException e) {
						e.printStackTrace();
					}			
        		} else {
        			c.setStatus(ChallengeStatus.Refused);
        			
        		}
        		
        		request.setAttribute("challenge", c);
        		//TODO: setup a JSP for succesful?
        		request.getRequestDispatcher("/WEB-INF/jsp/xml/challengesuccesful.jsp").forward(request, response);
        	
        		
        	} else {
        		request.setAttribute("reason", "User not logged in!");
            	request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request, response);
        	}
        	
        } catch (MapperException e) {

            e.printStackTrace();
        }
        
    }


}
