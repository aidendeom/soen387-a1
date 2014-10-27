package org.soen387.domain.player.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.user.User;
import org.soen387.domain.player.tdg.PlayerTDG;
import org.soen387.domain.user.mapper.UserMapper;

public class PlayerMapper
{

    public static Player find(long id) throws SQLException
    {
        ResultSet rs = PlayerTDG.find(id);

        Player player = null;
        
        if (rs.next())
        	player = createPlayer(rs);

        rs.close();
        
        return player;
    }

    public static List<Player> findAll() throws SQLException
    {
    	ResultSet rs = PlayerTDG.findAll();
    	
    	List<Player> players = new ArrayList<Player>();
    	
    	while (rs.next())
    	{
    		players.add(createPlayer(rs));
    	}
    	
    	rs.close();
    	
    	return players;
    }
    
    public static int insert(Player player) throws SQLException
    {
        return PlayerTDG.insert(player.getId(),
                player.getVersion(),
                player.getFirstName(),
                player.getLastName(),
                player.getEmail());

    }
    
    private static Player createPlayer(ResultSet rs) throws SQLException
    {        
        long idn = rs.getLong("id");
        int version = rs.getInt("version");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String email = rs.getString("email");
        User user = UserMapper.find(idn);
        
        return new Player(idn, version, firstName, lastName, email, user);
    }

}