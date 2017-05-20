package nfl.season.playoffs;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.League;

public class NFLPlayoffs {

	private League nfl;
	
	private List<NFLPlayoffConference> conferences;
	
	public NFLPlayoffs(League nfl) {
		this.nfl = nfl;
		conferences = new ArrayList<NFLPlayoffConference>();
	}

	public void initializeNFLPlayoffs() {
		List<Conference> leagueConferences = nfl.getConferences();
		for (Conference leagueConference : leagueConferences) {
			NFLPlayoffConference playoffConference = 
					new NFLPlayoffConference(leagueConference);
			conferences.add(playoffConference);
			
			List<Division> leagueDivisions = leagueConference.getDivisions();
			for (Division leagueDivision : leagueDivisions) {
				NFLPlayoffDivision playoffDivision = 
						new NFLPlayoffDivision(leagueDivision);
				playoffConference.addDivision(playoffDivision);
			}
		}
	}

	public List<NFLPlayoffConference> getConferences() {
		List<NFLPlayoffConference> returnConferences = new ArrayList<NFLPlayoffConference>();
		returnConferences.addAll(conferences);
		return returnConferences;
	}

	public void setDivisionWinner(String conferenceName, String divisionName,
			NFLPlayoffTeam divisionWinner) {
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		
		if (playoffConference != null) {
			NFLPlayoffDivision playoffDivision = playoffConference.getDivision(divisionName);
			
			NFLPlayoffTeam oldWinner = playoffConference.getDivisionWinner(divisionName);
			if (oldWinner == null) {
				int openSeed = playoffConference.getOpenDivisionWinnerSeed();
				divisionWinner.setConferenceSeed(openSeed);
			} else {
				divisionWinner.setConferenceSeed(oldWinner.getConferenceSeed());
			}
			playoffConference.addTeam(divisionWinner);
			playoffDivision.setDivisionWinner(divisionWinner);
		}
	}

	public NFLPlayoffTeam getDivisionWinner(String conferenceName, String divisionName) {
		NFLPlayoffTeam divisionWinner = null;
		
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		if (playoffConference != null) {
			divisionWinner = playoffConference.getDivisionWinner(divisionName);
		}
		
		return divisionWinner;
	}

	public NFLPlayoffTeam getTeamByConferenceSeed(String conferenceName, int conferenceSeed) {
		NFLPlayoffTeam returnTeam = null;
		
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		if (playoffConference != null) {
			returnTeam = playoffConference.getTeamWithSeed(conferenceSeed);
		}
		
		return returnTeam;
	}
	
	public NFLPlayoffConference getConference(String conferenceName) {
		NFLPlayoffConference returnConference = null;
		
		for (NFLPlayoffConference playoffConference : conferences) {
			Conference leagueConference = playoffConference.getConference();
			String leagueConferenceName = leagueConference.getName();
			if (leagueConferenceName.equalsIgnoreCase(conferenceName)) {
				returnConference = playoffConference;
				break;
			}
		}
		
		return returnConference;
	}
	
}
