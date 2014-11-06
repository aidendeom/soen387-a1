package org.soen387.domain.model.checkerboard;

import java.awt.Point;
import java.nio.charset.Charset;

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
	}
	
	public CheckerBoard(IPlayer firstPlayer, IPlayer secondPlayer) throws MapperException{
		this(CheckerBoardDataMapper.getNextId(), 1, GameStatus.Ongoing, createDefaultPieces(), firstPlayer, secondPlayer, firstPlayer);
	}
	
	private static char[][] createDefaultPieces(){
		
		//b _ b _ r _ _ b
		//when row is even, its' p_p_p_p_
		//when row is odd, it's _p_p_p_p
		//when row is less then < 3, its all white
		//when row is greater than >4 its all black
		char[][] pieces = new char[8][8];
		for (int i=0; i < 8; i++) //row
			   for (int j=0; j < 8; j++)//column
				   if (i < 3){ //initialize the black pieces
					   if(i % 2 == 0 && j%2 ==0 ){ //even row & column
						   pieces[i][j] = 'b';
					   } else if(i%2!=0 && j%2!=0){//odd row, odd column
						   pieces[i][j] = 'b';
					   } else { //e-r, o-c and o-r, e-c 
						   pieces[i][j] = ' ';
					   }
				   } else if (i > 4){ //initialize the red pieces
					   if(i%2==0 && j%2==0) { //even row and column
						   pieces[i][j] = 'r';
					   } else if (i%2!=0 && j%2!=0){ //odd row, odd column
						   pieces[i][j] = 'r';
					   } else { //e-r,o-c and o-r, e-c
						   pieces[i][j] = ' ';
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

	public void move(Point source, Point target) {
		
	}
	
	public void jump(Point source, Point... targets) {
		
	}
	
	public String getStringPieces(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				if(pieces[i][j] == 0X0 || pieces[i][j] == ' '){
					sb.append(" ");
				} else {
					sb.append(pieces[i][j]);
				}
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
}
