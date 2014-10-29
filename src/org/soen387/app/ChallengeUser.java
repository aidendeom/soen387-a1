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

@WebServlet("ChallengeUser")
public class ChallengeUser extends AbstractPageController implements Servlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public ChallengeUser() {
        super();
        // TODO Auto-generated constructor stub
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
                
                // TODO: This should should return whether or not there are any open challenges between two users
                // (there should only ever be on)
                // Something similar for games (CheckerBoards?)
                if (!ChallengeMapper.challengeExists(thisPlayer, otherPlayer)
                        && !CheckerBoardDataMapper.gameExists(thisPlayer, otherPlayer))
                {
                    Challenge c = new Challenge(thisPlayer, otherPlayer);
                }
            }
            request.getRequestDispatcher("/WEB-INF/jsp/xml/challengeuser.jsp").forward(request, response);
        } catch (MapperException e) {

            e.printStackTrace();
        }
        
    }


}
