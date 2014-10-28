package org.soen387.domain.challenge.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
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
            c = createChallenge(rs);
        }
        
        rs.close();
        
        return c;
    }

    public static List<Challenge> findAll() throws MapperException
    {
        try
        {
            ResultSet rs = ChallengeTDG.findAll();
            
            List<Challenge> challenges = new ArrayList<Challenge>();
            
            while (rs.next())
            {
                challenges.add(createChallenge(rs));
            }
            
            rs.close();
            
            return challenges;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new MapperException("ChallengeMapper failed!");
        }
    }
    
    private static Challenge createChallenge(ResultSet rs) throws SQLException
    {
        long id = rs.getLong("id");
        
        long challengerID = rs.getLong("challengerId");
        long challengeeID = rs.getLong("challengeeId");
        
        IPlayer challenger = new PlayerProxy(challengerID);
        IPlayer challengee = new PlayerProxy(challengeeID);
        
        return new Challenge(id, challenger, challengee, ChallengeStatus.Open);
    }
}
