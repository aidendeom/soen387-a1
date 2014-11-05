package org.soen387.domain.model.checkerboard;

import java.awt.Point;
import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.soen387.domain.checkerboard.mapper.CheckerBoardDataMapper;
import org.soen387.domain.model.player.IPlayer;

public class CheckerBoard {

	public CheckerBoard(long id, int version, GameStatus status,
			char[][] pieces, IPlayer firstPlayer, IPlayer secondPlayer,
			IPlayer currentPlayer) {
		super();
		this.id = id;
		this.version = version;
		this.status = status;
		this.pieces = pieces;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.currentPlayer = currentPlayer;
		this.stringPieces = this.getStringPieces();
	}
	
	public CheckerBoard(IPlayer firstPlayer, IPlayer secondPlayer) throws MapperException{
		this(CheckerBoardDataMapper.getNextId(), 1, GameStatus.Ongoing, createDefaultPieces(), firstPlayer, secondPlayer, firstPlayer);
	}
	
	private static char[][] createDefaultPieces(){
		char[][] pieces = new char[8][8];
        for(int i=0; i < 8; i++)
        {
            for(int j=0; j < 8; j+=2)
            {
            	if(i < 3) {
            		pieces[j][i] = 'r';
                } else if(i > 4) {
                    pieces[j][i] = 'b';
                } else {
                	pieces[j][i] = ' ';
                }
            }
        }
        return pieces;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public char[][] getPieces() {
		return pieces;
	}

	public void setPieces(char[][] pieces) {
		this.pieces = pieces;
	}

	public IPlayer getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(IPlayer firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public IPlayer getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(IPlayer secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public IPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(IPlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public long getId() {
		return id;
	}

	long id;
	int version;
	GameStatus status;
	char[][] pieces;
	IPlayer firstPlayer;
	IPlayer secondPlayer;
	IPlayer currentPlayer;
	String stringPieces;


	public void move(Point source, Point target) {
		
	}
	
	public void jump(Point source, Point... targets) {
		
	}
	
	public String getStringPieces(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				sb.append(pieces[j][i]);
			}
		}
		
		return sb.toString();
	}
}
