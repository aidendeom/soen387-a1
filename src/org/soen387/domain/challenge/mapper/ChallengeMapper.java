package org.soen387.domain.challenge.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.service.threadLocal.ThreadLocalTracker;
import org.soen387.domain.challenge.tdg.ChallengeTDG;
import org.soen387.domain.model.challenge.Challenge;
import org.soen387.domain.model.challenge.ChallengeStatus;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.player.PlayerProxy;

public class ChallengeMapper
{
    public static ThreadLocal<HashMap<Long, Challenge>> identityMap = new ThreadLocal<HashMap<Long, Challenge>>()
    {
        @Override
        protected HashMap<Long, Challenge> initialValue()
        {
            return new HashMap<Long, Challenge>();
        }
    };
    
    static {
    	ThreadLocalTracker.registerThreadLocal(identityMap);
    }
    
    public static Challenge find(long id) throws MapperException
    {
        try
        {
            Challenge c = identityMap.get().get(id);
            
            if (c == null)
            {
                ResultSet rs = ChallengeTDG.find(id);
                
                if (rs.next())
                {
                    c = createChallenge(rs);
                }
                
                rs.close();
            }
            
            return c;
        }
        catch (SQLException e)
        {
            throw new MapperException(e);
        }
    }
    
    public static boolean challengeExists(IPlayer p1, IPlayer p2) throws MapperException
    {
        try
        {
            return ChallengeTDG.challengeExists(p1.getId(), p2.getId());
        }
        catch (SQLException e)
        {
            throw new MapperException(e);
        }
    }

    public static List<Challenge> findAll() throws MapperException
    {
        try
        {
            ResultSet rs = ChallengeTDG.findAll();
            
            List<Challenge> challenges = new ArrayList<Challenge>();
            
            while (rs.next())
            {
                Challenge c = identityMap.get().get(rs.getLong("id"));
                if (c == null)
                {
                    c = createChallenge(rs);
                    challenges.add(c);
                }
            }
            
            rs.close();
            
            return challenges;
        }
        catch (SQLException e)
        {
            throw new MapperException(e);
        }
    }
    
    public static int insert(Challenge c) throws MapperException
    {
        try
        {
            identityMap.get().put(c.getId(), c);
            return ChallengeTDG.insert(c.getId(),
                    c.getVersion(),
                    c.getChallenger().getId(),
                    c.getChallengee().getId(),
                    c.getStatus().getId());
        }
        catch (SQLException e)
        {
            throw new MapperException(e);
        }
    }
    
    public static long getnextID() throws MapperException
    {
        try
        {
            return ChallengeTDG.getNextId();
        }
        catch (SQLException e)
        {
            throw new MapperException(e);
        }
    }
    
    private static Challenge createChallenge(ResultSet rs) throws SQLException
    {
        long id = rs.getLong("id");
        
        long challengerID = rs.getLong("challengerId");
        long challengeeID = rs.getLong("challengeeId");
        
        IPlayer challenger = new PlayerProxy(challengerID);
        IPlayer challengee = new PlayerProxy(challengeeID);
        
        Challenge c = new Challenge(id, rs.getInt("version"), challenger, challengee, ChallengeStatus.values()[rs.getInt("status")]);
        
        identityMap.get().put(c.getId(), c);
        
        return c;
    }

	public static int update(Challenge c) throws MapperException  {
		try {
			return ChallengeTDG.update(c.getId(), c.getVersion(), c.getChallenger().getId(),
					c.getChallengee().getId(), c.getStatus().getId());
		} catch (SQLException e) {
			throw new MapperException(e);
		}
		
	}
}
