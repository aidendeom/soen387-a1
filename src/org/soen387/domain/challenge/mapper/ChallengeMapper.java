package org.soen387.domain.challenge.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.soen387.domain.challenge.tdg.ChallengeTDG;
import org.soen387.domain.model.challenge.Challenge;
import org.soen387.domain.model.challenge.ChallengeStatus;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.player.PlayerProxy;

public class ChallengeMapper
{
    public static Challenge find(long id) throws SQLException
    {
        ResultSet rs = ChallengeTDG.find(id);
        
        Challenge c = null;
        
        if (rs.next())
        {
            long challengerID = rs.getLong("challengerId");
            long challengeeID = rs.getLong("challengeeId");
            
            IPlayer challenger = new PlayerProxy(challengerID);
            IPlayer challengee = new PlayerProxy(challengeeID);
            
            c = new Challenge(id, challenger, challengee, ChallengeStatus.Open);
        }
        
        rs.close();
        
        return c;
    }
}
