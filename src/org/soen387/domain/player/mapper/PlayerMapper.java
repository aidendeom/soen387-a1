package org.soen387.domain.player.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.user.User;
import org.soen387.domain.player.tdg.PlayerTDG;
import org.soen387.domain.user.mapper.UserMapper;

public class PlayerMapper
{
    public static ThreadLocal<HashMap<Long, Player>> identityMap = new ThreadLocal<HashMap<Long,Player>>()
    {
        @Override
        protected HashMap<Long, Player> initialValue()
        {
            return new HashMap<Long, Player>();
        }
    };
    
    public static Player find(long id) throws SQLException
    {
        
        Player player = identityMap.get().get(id);
        
        if (player == null)
        {
            ResultSet rs = PlayerTDG.find(id);
            
            if (rs.next())
            	player = createPlayer(rs);
    
            rs.close();
        }
        
        return player;
    }

    public static List<Player> findAll() throws SQLException
    {
    	ResultSet rs = PlayerTDG.findAll();
    	
    	List<Player> players = new ArrayList<Player>();
    	
    	while (rs.next())
    	{
    	    Player p = identityMap.get().get(rs.getLong("id"));
    	    
    	    if (p == null)
    	        p = createPlayer(rs);
    	    
    	    players.add(p);
    	}
    	
    	rs.close();
    	
    	return players;
    }
    
    public static int insert(IPlayer player) throws SQLException
    {
        return PlayerTDG.insert(player.getId(),
                player.getVersion(),
                player.getFirstName(),
                player.getLastName(),
                player.getEmail(),
                player.getUser().getId());

    }
    
    private static Player createPlayer(ResultSet rs) throws SQLException
    {        
        long idn = rs.getLong("id");
        int version = rs.getInt("version");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String email = rs.getString("email");
        User user = UserMapper.find(idn);
        
        Player p = new Player(idn, version, firstName, lastName, email, user);
        
        identityMap.get().put(p.getId(), p);
        
        return p;
    }

}