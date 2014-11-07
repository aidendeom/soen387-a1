package org.soen387.domain.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.service.threadLocal.ThreadLocalTracker;
import org.soen387.domain.model.user.User;
import org.soen387.domain.user.tdg.UserTDG;

public class UserMapper {

    public static ThreadLocal<HashMap<Long, User>> identityMap = new ThreadLocal<HashMap<Long, User>>()
    {
        @Override
        protected HashMap<Long, User> initialValue()
        {
            return new HashMap<Long, User>();
        }
    };
    
  //auto purge the identityMap
    static {
    	ThreadLocalTracker.registerThreadLocal(identityMap);
    }
    
	public static User find(long id) throws MapperException
	{
        try
        {
            User user = identityMap.get().get(id);
            
            if (user == null)
            {
                ResultSet rs = UserTDG.find(id);
                
                if (rs.next())
                    user = createUser(rs);
        		
        		rs.close();
            }
            
            return user;
        }
        catch (SQLException e)
        {
            throw new MapperException(e);
        }
	}
	
	public static User findByUsername(String username) throws MapperException
	{
	    try
	    {	        
            ResultSet rs = UserTDG.findByUsername(username);
            
            User user = null;
            
            if (rs.next())
            {
                long id = rs.getLong("id");
                
                user = identityMap.get().get(id);
                
                if (user == null)
                    user = createUser(rs);
            }
    		
    		rs.close();
    		
    		return user;
	    }
	    catch (SQLException e)
	    {
	        throw new MapperException(e);
	    }
	}
	
	public static long getNextId() throws MapperException
	{
	    try
	    {
	        return UserTDG.getNextId();
	    }
	    catch (SQLException e)
	    {
	        throw new MapperException(e);
	    }
	}

    public static int insert(User user) throws MapperException
    {
        try
        {
            identityMap.get().putIfAbsent(user.getId(), user);
            return UserTDG.insert(user.getId(), user.getVersion(), user.getUsername(), user.getPassword());
        }
        catch (SQLException e)
        {
            throw new MapperException(e);
        }
    }
    
    private static User createUser(ResultSet rs) throws SQLException
    {
		User user = null;
		
		long id = rs.getLong("id");
		int version = rs.getInt("version");
		String usern = rs.getString("username");
		String pass = rs.getString("password");
		user = new User(id, version, usern, pass);
		
		identityMap.get().put(id, user);
		
		return user;
    }
}
