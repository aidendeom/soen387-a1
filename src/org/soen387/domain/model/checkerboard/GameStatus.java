package org.soen387.domain.model.checkerboard;

public enum GameStatus {
	Ongoing,
	Won,
	Tied;

	//Let's make it play nice with Javabeans
	public int getId() { return this.ordinal(); }
}
