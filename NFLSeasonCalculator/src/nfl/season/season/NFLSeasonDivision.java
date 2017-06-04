package nfl.season.season;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Division;

public class NFLSeasonDivision {

	private Division leagueDivision;
	
	private List<NFLSeasonTeam> teams;
	
	public NFLSeasonDivision(Division leagueDivision) {
		this.leagueDivision = leagueDivision;
		teams = new ArrayList<NFLSeasonTeam>();
	}
	
	public Division getDivision() {
		return leagueDivision;
	}

	public List<NFLSeasonTeam> getTeams() {
		return teams;
	}

	public void addTeam(NFLSeasonTeam seasonTeam) {
		teams.add(seasonTeam);
	}

}
