package org.soen387.domain.player.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.user.User;
import org.soen387.domain.player.tdg.PlayerTDG;

public class PlayerMapper {
	
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
			player = new Player(idn, version, firstName, lastName, email);
		}
		
		return player;
	}

	
}