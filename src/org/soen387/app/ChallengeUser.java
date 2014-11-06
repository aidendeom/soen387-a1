package org.soen387.app;

import java.io.IOException;
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
import org.soen387.domain.model.checkerboard.GameStatus;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.player.mapper.PlayerMapper;
import org.soen387.domain.user.mapper.UserMapper;

@WebServlet("/ChallengeUser")
public class ChallengeUser extends AbstractPageController implements Servlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public ChallengeUser() {
        super();
    }

    @Override
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try
        {
            HttpSession session = request.getSession();
            if (Utils.isLoggedIn(session))
            {
                long id = (long) session.getAttribute("playerid");
                long id2 = Long.parseLong(request.getParameter("id"));
                
                Player thisPlayer = PlayerMapper.find(id);
                Player otherPlayer = PlayerMapper.find(id2);
                
                if(thisPlayer == null || otherPlayer == null){
                	request.setAttribute("reason", "User doesn't exist");
                	request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request, response);
                }
                
                if (!ChallengeMapper.challengeExists(thisPlayer, otherPlayer)
                        && !CheckerBoardDataMapper.gameExists(thisPlayer, otherPlayer))
                {
                    Challenge c = new Challenge(thisPlayer, otherPlayer);
                    ChallengeMapper.insert(c);
                    request.setAttribute("challenge", c);
                    request.getRequestDispatcher("/WEB-INF/jsp/xml/challengeuser.jsp").forward(request, response);
                } else {
                	request.setAttribute("reason", "Open Challenge or Game exists");
                	request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request, response);
                }
            } else {
            	request.setAttribute("reason", "User not logged in!");
            	request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request, response);
            }
        } catch (MapperException e) {

            e.printStackTrace();
        }
        
    }


}
