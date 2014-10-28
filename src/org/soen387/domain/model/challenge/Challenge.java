package org.soen387.domain.model.challenge;

import org.soen387.domain.model.player.IPlayer;

public class Challenge {
	
	long id;
	IPlayer challenger;
	IPlayer challengee;
	ChallengeStatus status;

	public Challenge(long id, IPlayer challenger, IPlayer challengee, ChallengeStatus status){
		super();
		this.id = id;
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
	}

	public IPlayer getChallenger() {
		return challenger;
	}

	public void setChallenger(IPlayer challenger) {
		this.challenger = challenger;
	}

	public IPlayer getChallengee() {
		return challengee;
	}

	public void setChallengee(IPlayer challengee) {
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