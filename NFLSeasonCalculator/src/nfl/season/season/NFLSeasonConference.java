package nfl.season.season;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;

public class NFLSeasonConference {

	private Conference leagueConference;
	
	private List<NFLSeasonDivision> divisions;
	
	public NFLSeasonConference(Conference leagueConference) {
		this.leagueConference = leagueConference;
		divisions = new ArrayList<NFLSeasonDivision>();
	}
	
	public Conference getConference() {
		return leagueConference;
	}

	public List<NFLSeasonDivision> getDivisions() {
		return divisions;
	}

	public void addDivision(NFLSeasonDivision seasonDivision) {
		divisions.add(seasonDivision);
	}

}
