package org.soen387.domain.model.challenge;

import org.soen387.domain.model.checkerboard.GameStatus;
import org.soen387.domain.model.player.Iplayer;

public class Challenge {
	
	long id;
	Iplayer challenger;
	Iplayer challengee;
	ChallengeStatus status;

	public Challenge(long id, Iplayer challenger, Iplayer challengee, ChallengeStatus status){
		super();
		this.id = id;
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
	}

	public Iplayer getChallenger() {
		return challenger;
	}

	public void setChallenger(Iplayer challenger) {
		this.challenger = challenger;
	}

	public Iplayer getChallengee() {
		return challengee;
	}

	public void setChallengee(Iplayer challengee) {
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