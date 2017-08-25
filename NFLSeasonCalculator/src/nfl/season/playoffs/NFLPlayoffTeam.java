package nfl.season.playoffs;

import java.io.Serializable;

import nfl.season.league.Team;

public class NFLPlayoffTeam implements Serializable {

	private static final long serialVersionUID = 7432171945055348167L;

	private Team team;
	
	private int conferenceSeed;
	
	private int chanceOfMakingDivisionalRound;
	
	private int chanceOfMakingConferenceRound;
	
	private int chanceOfMakingSuperBowl;
	
	private int chanceOfWinningSuperBowl;
	
	public NFLPlayoffTeam(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
	}

	public int getConferenceSeed() {
		return conferenceSeed;
	}

	public void setConferenceSeed(int conferenceSeed) {
		this.conferenceSeed = conferenceSeed;
	}

	public int getChanceOfMakingDivisionalRound() {
		return chanceOfMakingDivisionalRound;
	}
	
	public void setChanceOfMakingDivisionalRound(int chanceOfMakingDivisionalRound) {
		this.chanceOfMakingDivisionalRound = chanceOfMakingDivisionalRound;
	}

	public int getChanceOfMakingConferenceRound() {
		return chanceOfMakingConferenceRound;
	}
	
	public void setChanceOfMakingConferenceRound(int chanceOfMakingConferenceRound) {
		this.chanceOfMakingConferenceRound = chanceOfMakingConferenceRound;
	}

	public int getChanceOfMakingSuperBowl() {
		return chanceOfMakingSuperBowl;
	}
	
	public void setChanceOfMakingSuperBowl(int chanceOfMakingSuperBowl) {
		this.chanceOfMakingSuperBowl = chanceOfMakingSuperBowl;
	}

	public int getChanceOfWinningSuperBowl() {
		return chanceOfWinningSuperBowl;
	}
	
	public void setChanceOfWinningSuperBowl(int chanceOfWinningSuperBowl) {
		this.chanceOfWinningSuperBowl = chanceOfWinningSuperBowl;
	}
	
}
