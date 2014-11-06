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
    }

    @Override
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try
        {
        	HttpSession session = request.getSession();
        	String mode = request.getParameter("mode");
        	
        	if (Utils.isLoggedIn(session)){
        		long challengeId = Long.parseLong(request.getParameter("id"));
        		boolean accept = Boolean.parseBoolean(request.getParameter("accept"));
        		Challenge c = ChallengeMapper.find(challengeId);
        		
        		if (c == null){
        			responseFailed(request, response, "This challenge does not exist");
        		}
        		
        		//is the challenge already accepted?
        		if (c.getStatus()==ChallengeStatus.Accepted){
        			responseFailed(request, response, "This challenge has already been accepted");
        		}
        		
        		if (accept){
        			c.setStatus(ChallengeStatus.Accepted);
        			c.setVersion(Integer.parseInt(request.getParameter("version")+1));
        			//c.setVersion(c.getVersion()+1);
        			ChallengeMapper.update(c);
        			
        			Player thisPlayer = PlayerMapper.find(c.getChallenger().getId());
                    Player otherPlayer = PlayerMapper.find(c.getChallengee().getId());
                    
                    //only challengee should accept the challenge
                    if ((Long)session.getAttribute("playerid") == thisPlayer.getId()){
                    	responseFailed(request, response, "You can't accept your own issued challenge");
                    }
                    
                    CheckerBoard game = null;
        			try {
						game = new CheckerBoard(otherPlayer, thisPlayer);
						CheckerBoardDataMapper.insert(game);
					} catch (MapperException e) {
						e.printStackTrace();
					}			
        		} else {
        			c.setStatus(ChallengeStatus.Refused);
        			c.setVersion(Integer.parseInt(request.getParameter("version"))+1);
        			ChallengeMapper.update(c);        			
        		}
        		
        		request.setAttribute("challenge", c);
        		 if (mode.equals("xml")){
        			 request.getRequestDispatcher("/WEB-INF/jsp/xml/challengesuccesful.jsp").forward(request, response);
        		 } else { //we don't have an alternative yet
        			 request.getRequestDispatcher("/WEB-INF/jsp/xml/challengesuccesful.jsp").forward(request, response);
        		 }
        		
        	
        		
        	} else {
        		responseFailed(request, response, "User not logged in!");
        	}
        	
        } catch (MapperException e) {

            e.printStackTrace();
        }
        
    }
    
    private static void responseFailed(HttpServletRequest request,
            HttpServletResponse response,
            String reason) throws ServletException,
                          IOException
    {
    	request.setAttribute("reason", reason);
    	if(request.getParameter("mode").equals("xml")){
    		request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp")
        	.forward(request, response);
    	} else {
    		request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp")
        	.forward(request, response);
    	}
    	
    }

}
