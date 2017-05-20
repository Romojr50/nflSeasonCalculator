package nfl.season.playoffs;

import nfl.season.league.Team;

public class NFLPlayoffTeam {

	private Team team;
	
	private int conferenceSeed;
	
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
	
}
