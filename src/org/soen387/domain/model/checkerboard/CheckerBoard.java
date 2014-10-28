package org.soen387.domain.model.checkerboard;

import java.awt.Point;

import org.soen387.domain.model.player.Iplayer;

public class CheckerBoard {

	public CheckerBoard(long id, int version, GameStatus status,
			char[][] pieces, Iplayer firstPlayer, Iplayer secondPlayer,
			Iplayer currentPlayer) {
		super();
		this.id = id;
		this.version = version;
		this.status = status;
		this.pieces = pieces;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.currentPlayer = currentPlayer;
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

	public Iplayer getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Iplayer firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Iplayer getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(Iplayer secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public Iplayer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Iplayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public long getId() {
		return id;
	}

	long id;
	int version;
	GameStatus status;
	char[][] pieces;
	Iplayer firstPlayer;
	Iplayer secondPlayer;
	Iplayer currentPlayer;


	public void move(Point source, Point target) {
		
	}
	
	public void jump(Point source, Point... targets) {
		
	}
}
