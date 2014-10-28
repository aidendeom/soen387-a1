package org.soen387.domain.model.player;

import java.sql.SQLException;

import org.soen387.domain.model.user.User;
import org.soen387.domain.player.mapper.PlayerMapper;

public class PlayerProxy implements IPlayer
{
    long id;
    Player innerObject;
    
    public PlayerProxy(long id)
    {
        this.id = id;
    }
    
    private Player getInnerObject()
    {
            if (innerObject == null)
            {
                try
                {
                    innerObject = PlayerMapper.find(id);
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
            
        return innerObject;
    }

    @Override
    public long getId()
    {
        return getInnerObject().getId();
    }
    
    @Override
    public void setId(long id)
    {
        getInnerObject().setId(id);   
    }

    @Override
    public int getVersion()
    {
        return getInnerObject().getVersion();
    }

    @Override
    public void setVersion(int version)
    {
        getInnerObject().setVersion(version);
    }

    @Override
    public String getFirstName()
    {
        return getInnerObject().getFirstName();
    }

    @Override
    public void setFirstName(String firstName)
    {
        getInnerObject().setFirstName(firstName);
    }

    @Override
    public String getLastName()
    {
        return getInnerObject().getLastName();
    }

    @Override
    public void setLastName(String lastName)
    {
        getInnerObject().setLastName(lastName);
    }

    @Override
    public String getEmail()
    {
        return getInnerObject().getEmail();
    }

    @Override
    public void setEmail(String email)
    {
        getInnerObject().setEmail(email);
    }

    @Override
    public User getUser()
    {
        return getInnerObject().getUser();
    }

    @Override
    public void setUser(User user)
    {
        getInnerObject().setUser(user);
    }
}
