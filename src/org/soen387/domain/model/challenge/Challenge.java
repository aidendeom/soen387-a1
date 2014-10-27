package org.soen387.domain.model.challenge;

import org.soen387.domain.model.checkerboard.GameStatus;
import org.soen387.domain.model.player.Player;

public class Challenge {
	
	long id;
	long challengerId;
	long challengeeId;
	ChallengeStatus status;

	public Challenge(long id, long challengerID, long challengeeID, ChallengeStatus status){
		super();
		this.id = id;
		this.challengerId = challengerID;
		this.challengeeId = challengeeID;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChallengerId() {
		return challengerId;
	}

	public void setChallengerId(long challengerId) {
		this.challengerId = challengerId;
	}

	public long getChallengeeId() {
		return challengeeId;
	}

	public void setChallengeeId(long challengeeId) {
		this.challengeeId = challengeeId;
	}

	public ChallengeStatus getStatus() {
		return status;
	}

	public void setStatus(ChallengeStatus status) {
		this.status = status;
	}
	
	
}