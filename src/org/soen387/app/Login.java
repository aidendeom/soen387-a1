package org.soen387.app;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dsrg.soenea.domain.MapperException;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.user.User;
import org.soen387.domain.player.mapper.PlayerMapper;
import org.soen387.domain.user.mapper.UserMapper;

@WebServlet("/Login")
public class Login extends AbstractPageController implements Servlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see AbstractPageController#AbstractPageController()
     */
    public Login()
    {
        super();
    }

    @Override
    protected void
            processRequest(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException,
                                                        IOException
    {
        HttpSession session = request.getSession();
        String mode = request.getParameter("mode");
        
        if (!Utils.isLoggedIn(session))
        {
            try
            {
                String username = request.getParameter("user");
                String password = request.getParameter("pass");
    
                User user = UserMapper.findByUsername(username);
                if (user != null && password.equals(user.getPassword()))
                {
                    IPlayer player = PlayerMapper.find(user.getId());
    
                    request.setAttribute("user", user);
                    request.setAttribute("player", player);
                    
                    System.out.println(session.isNew());
    
                    session.setAttribute("userid", user.getId());
                    session.setAttribute("playerid", player.getId());
                    
                    if (mode.equals("xml")){
                    	request.getRequestDispatcher("/WEB-INF/jsp/xml/login.jsp")
                        .forward(request, response);
                    } else {
                    	//this would be for html view, but we dont' have
                    	request.getRequestDispatcher("/WEB-INF/jsp/xml/login.jsp")
                        .forward(request, response);

                    }
                    
                }
                else if (user == null)
                {
                    loginFailed(request, response, "incorrect username");
                }
                else
                {
                    loginFailed(request, response, "incorrect password");
                }
    
            }
            catch (MapperException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            loginFailed(request, response, "Already logged in");
        }
    }

    private static void loginFailed(HttpServletRequest request,
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
