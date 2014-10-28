package org.soen387.app;

import javax.servlet.http.HttpSession;

public class Utils
{
    public static boolean isLoggedIn(HttpSession session)
    {
        Object userID = session.getAttribute("userid");
        Object playerID = session.getAttribute("playerid");

        return userID != null && playerID != null;
    }
    
    public static boolean isNullOrEmpty(String s)
    {
        if (s == null)
            return true;
        return s.isEmpty();
    }
}
