package nfl.season.playoffs;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;

public class NFLPlayoffConference {

	private Conference conference;
	
	private List<NFLPlayoffDivision> divisions;
	
	private List<NFLPlayoffTeam> teams;
	
	public NFLPlayoffConference(Conference conference) {
		this.conference = conference;
		divisions = new ArrayList<NFLPlayoffDivision>();
		teams = new ArrayList<NFLPlayoffTeam>();
	}

	public Conference getConference() {
		return conference;
	}

	public List<NFLPlayoffDivision> getDivisions() {
		List<NFLPlayoffDivision> returnDivisions = new ArrayList<NFLPlayoffDivision>();
		returnDivisions.addAll(divisions);
		return returnDivisions;
	}

	public void addDivision(NFLPlayoffDivision playoffDivision) {
		divisions.add(playoffDivision);
	}

	public NFLPlayoffTeam getDivisionWinner(String divisionName) {
		NFLPlayoffTeam divisionWinner = null;
		
		NFLPlayoffDivision playoffDivision = getDivision(divisionName);
		if (playoffDivision != null) {
			divisionWinner = playoffDivision.getDivisionWinner();
		}
		
		return divisionWinner;
	}

	public int getOpenDivisionWinnerSeed() {
		int openSeed = -1;
		for (int i = 1; i <= 4; i++) {
			NFLPlayoffTeam teamWithSeed = getTeamWithSeed(i);
			if (teamWithSeed == null) {
				openSeed = i;
				break;
			}
		}
		return openSeed;
	}

	public NFLPlayoffTeam getTeamWithSeed(int conferenceSeed) {
		NFLPlayoffTeam returnTeam = null;
		
		for (NFLPlayoffTeam playoffTeam : teams) {
			if (playoffTeam.getConferenceSeed() == conferenceSeed) {
				returnTeam = playoffTeam;
			}
		}
		
		return returnTeam;
	}

	public void addTeam(NFLPlayoffTeam playoffTeam) {
		teams.add(playoffTeam);
	}

	public NFLPlayoffDivision getDivision(String divisionName) {
		NFLPlayoffDivision returnDivision = null;
		
		for (NFLPlayoffDivision playoffDivision : divisions) {
			Division leagueDivision = playoffDivision.getDivision();
			String leagueDivisionName = leagueDivision.getName();
			if (leagueDivisionName.equalsIgnoreCase(divisionName)) {
				returnDivision = playoffDivision;
				break;
			}
		}
		
		return returnDivision;
	}
	
}
