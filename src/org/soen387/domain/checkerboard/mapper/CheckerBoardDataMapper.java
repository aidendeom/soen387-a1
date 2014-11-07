package org.soen387.domain.checkerboard.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.dsrg.soenea.service.threadLocal.ThreadLocalTracker;
import org.soen387.domain.challenge.tdg.ChallengeTDG;
import org.soen387.domain.checkerboard.tdg.CheckerBoardTDG;
import org.soen387.domain.model.challenge.Challenge;
import org.soen387.domain.model.challenge.ChallengeStatus;
import org.soen387.domain.model.checkerboard.CheckerBoard;
import org.soen387.domain.model.checkerboard.GameStatus;
import org.soen387.domain.model.player.IPlayer;
import org.soen387.domain.model.player.Player;
import org.soen387.domain.model.player.PlayerProxy;

public class CheckerBoardDataMapper {
	
    public static ThreadLocal<HashMap<Long, CheckerBoard>> identityMap = new ThreadLocal<HashMap<Long, CheckerBoard>>()
    {
        @Override
        protected HashMap<Long, CheckerBoard> initialValue()
        {
            return new HashMap<Long, CheckerBoard>();
        }
    };
    
    static {
    	ThreadLocalTracker.registerThreadLocal(identityMap);
    }
    
	public static List<CheckerBoard> buildCollection(ResultSet rs)
		    throws SQLException {
		    ArrayList<CheckerBoard> l = new ArrayList<CheckerBoard>();
		    while(rs.next())
		    {
		        CheckerBoard b = identityMap.get().get(rs.getLong("id"));
		    	if (b == null)
		    	    b = createBoard(rs);
		    	
		        l.add(b);
		    }
		    return l;
		}
	
	private static CheckerBoard createBoard(ResultSet rs) throws SQLException
	{
            String piecesString = rs.getString("pieces");
            System.out.println("From DM: " + piecesString);
            char[][] pieces = new char[8][8];
            for(int i=0; i < 8; i++)
            {
                for(int j=0; j < 8; j++)
                {
                    pieces[i][j] = piecesString.charAt(i*8+j);
                }
            }
            
            CheckerBoard b = new CheckerBoard(rs.getLong("id"),
                    rs.getInt("version"),
                    GameStatus.values()[rs.getInt("status")],
                    pieces,
                    new PlayerProxy(rs.getLong("first_player")),
                    new PlayerProxy(rs.getLong("second_player")),
                    new PlayerProxy(rs.getLong("current_player")));
            
            identityMap.get().put(b.getId(), b);
            
            return b;
	}

	public static List<CheckerBoard> findAll() throws MapperException {
        try {
            ResultSet rs = CheckerBoardTDG.findAll();
            return buildCollection(rs);
        } catch (SQLException e) {
            throw new MapperException(e);
        }
	}
	
	public static List<CheckerBoard> findForPlayer(IPlayer p) throws MapperException
	{
	    try
	    {
    	    ResultSet rs = CheckerBoardTDG.findForPlayer(p.getId());
    	    return buildCollection(rs);
        }
	    catch (SQLException e) {
            throw new MapperException(e);
        }
	}
	
    public static boolean gameExists(IPlayer p1, IPlayer p2) throws MapperException
    {
        try
        {
            return CheckerBoardTDG.gameExists(p1.getId(), p2.getId());
        }
        catch (SQLException e)
        {
            throw new MapperException(e);
        }
    }

	public static long getNextId() throws MapperException {
		try {
			return CheckerBoardTDG.getNextId();
		} catch (SQLException e) {
			throw new MapperException(e);
		}
	}

	public static int insert(CheckerBoard g) throws MapperException {
		try {
			identityMap.get().put(g.getId(), g);
			return CheckerBoardTDG.insert(g.getId(), g.getVersion(), 
					g.getStatus().getId(), g.getStringPieces(), g.getFirstPlayer().getId(), 
					g.getSecondPlayer().getId(), g.getCurrentPlayer().getId());
			
		} catch (SQLException e) {
			throw new MapperException(e);
		}
		
	}
	
	public static CheckerBoard find(long id) throws MapperException
    {
        try
        {
            CheckerBoard c = identityMap.get().get(id);
            
            if (c == null)
            {
                ResultSet rs = CheckerBoardTDG.find(id);
                
                if (rs.next())
                {
                    c = createBoard(rs);
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
}
