package org.soen387.domain.model.challenge;

import org.dsrg.soenea.domain.MapperException;
import org.soen387.domain.challenge.mapper.ChallengeMapper;
import org.soen387.domain.model.player.IPlayer;

public class Challenge {
	
	long id;
	int version;
	IPlayer challenger;
	IPlayer challengee;
	ChallengeStatus status;

	public Challenge(long id, int version, IPlayer challenger, IPlayer challengee, ChallengeStatus status){
		super();
		this.id = id;
		this.version = version;
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
	}
	
	public Challenge(IPlayer challenger, IPlayer challengee) throws MapperException
	{
        super();
        this.id = ChallengeMapper.getnextID();
        this.version = 1;
        this.challenger = challenger;
        this.challengee = challengee;
        this.status = ChallengeStatus.Open;
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

	public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public ChallengeStatus getStatus() {
		return status;
	}

	public void setStatus(ChallengeStatus status) {
		this.status = status;
	}
	
	
}