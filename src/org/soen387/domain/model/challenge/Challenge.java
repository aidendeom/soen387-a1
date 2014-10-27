package org.soen387.domain.model.challenge;

import org.soen387.domain.model.checkerboard.GameStatus;
import org.soen387.domain.model.player.Player;

public class Challenge {
	
	long id;
	Player challenger;
	Player challengee;
	ChallengeStatus status;

	public Challenge(long id, Player challenger, Player challengee, ChallengeStatus status){
		super();
		this.id = id;
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
	}

	public Player getChallenger() {
		return challenger;
	}

	public void setChallenger(Player challenger) {
		this.challenger = challenger;
	}

	public Player getChallengee() {
		return challengee;
	}

	public void setChallengee(Player challengee) {
		this.challengee = challengee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ChallengeStatus getStatus() {
		return status;
	}

	public void setStatus(ChallengeStatus status) {
		this.status = status;
	}
	
	
}