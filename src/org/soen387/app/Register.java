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
        HttpSession session = request.getSession();
        String mode = request.getParameter("mode");
        
        if (!Utils.isLoggedIn(session))
        {
            try
            {
                long uid = UserMapper.getNextId();
                String username = request.getParameter("user");
                String password = request.getParameter("pass");
                String firstName = request.getParameter("firstname");
                String lastName = request.getParameter("lastname");
                String email = request.getParameter("email");
                
                if (!Utils.isNullOrEmpty(username)
                        && !Utils.isNullOrEmpty(password)
                        && !Utils.isNullOrEmpty(firstName)
                        && !Utils.isNullOrEmpty(lastName)
                        && !Utils.isNullOrEmpty(email))
                {
                    User user = UserMapper.findByUsername(username); 
                    
                    if (user == null)
                    {                
                        user = new User(uid, 1, username, password);
                        
                        if (!PlayerMapper.emailExists(email))
                        {
                            long pid = PlayerMapper.getNextID();
                            
                            IPlayer player = new Player(pid, 1, firstName, lastName, email, user);
                            
                            UserMapper.insert(user);
                            PlayerMapper.insert(player);
                            
                            
                            //request.setAttribute("user", user.getUsername());
                            //request.setAttribute("pass", user.getPassword());
                            //response.sendRedirect(String.format("/soen387-a1/Login?user=%s&pass=%s", user.getUsername(), user.getPassword()));
                            request.setAttribute("user", user);
                            request.setAttribute("player", player);
                            if (mode != null && mode.equals("xml")){
                            	request.getRequestDispatcher("/WEB-INF/jsp/xml/register.jsp").forward(request,  response);
                            } else {
                                //this would be for html view, but we dont' have
                            	request.getRequestDispatcher("/WEB-INF/jsp/xml/register.jsp").forward(request,  response);
                            }
                        }
                        else
                        {
                        	loginFailed(request, response, "Email already exists");
                        }
                    }
                    else
                    {
                    	loginFailed(request, response, "Username already exists");
                    }
                }
                else
                {
                	loginFailed(request, response, "Incorrect number of parameters");
                }
            }
            catch (MapperException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            request.setAttribute("reason", "Already logged in");
            request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request,  response);
        }
    }
    
    private static void loginFailed(HttpServletRequest request,
    		HttpServletResponse response,
    		String reason) throws ServletException,
    		IOException
    {
    	request.setAttribute("reason", reason);
    	if(request.getParameter("mode") != null && request.getParameter("mode").equals("xml")){
    		request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request, response);
    	} else {
    		request.getRequestDispatcher("/WEB-INF/jsp/xml/loginfailed.jsp").forward(request, response);
    	}
    }
}
