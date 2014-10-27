package org.soen387.domain.model.challenge;

public enum ChallengeStatus {
	
	Open,
	Accepted,
	Refused;
	
	//playing nice with JavaBeans?  (BEANNNNSS!!)
	public int getId() {return this.ordinal();}
	

}