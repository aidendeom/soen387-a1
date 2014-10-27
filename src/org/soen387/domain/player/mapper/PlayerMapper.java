package org.soen387.domain.player.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        {
            long idn = rs.getLong("id");
            int version = rs.getInt("version");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String email = rs.getString("email");
            User user = UserMapper.find(idn);
            
            player = new Player(idn, version, firstName, lastName, email, user);
        }

        rs.close();
        
        return player;
    }

    public static int insert(Player player) throws SQLException
    {
        return PlayerTDG.insert(player.getId(),
                player.getVersion(),
                player.getFirstName(),
                player.getLastName(),
                player.getEmail());

    }

}