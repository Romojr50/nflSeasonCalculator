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
			
			if (playoffDivision != null) {
				NFLPlayoffTeam oldWinner = playoffConference.getDivisionWinner(divisionName);
				if (oldWinner == null) {
					int openSeed = playoffConference.getOpenDivisionWinnerSeed();
					divisionWinner.setConferenceSeed(openSeed);
				} else {
					divisionWinner.setConferenceSeed(oldWinner.getConferenceSeed());
					playoffConference.removeTeam(oldWinner);
				}
				playoffConference.addTeam(divisionWinner);
				playoffDivision.setDivisionWinner(divisionWinner);
			}
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
	
	public void addWildcardTeam(String conferenceName, NFLPlayoffTeam playoffTeam) {
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		
		if (playoffConference != null) {
			int openWildcardSeed = playoffConference.getOpenWildcardSeed();
			
			if (openWildcardSeed == NFLPlayoffConference.CLEAR_SEED) {
				NFLPlayoffTeam sixSeed = playoffConference.getTeamWithSeed(6);
				int oldSeed = playoffTeam.getConferenceSeed();
				sixSeed.setConferenceSeed(oldSeed);
				playoffConference.removeTeam(sixSeed);
				playoffTeam.setConferenceSeed(6);
				playoffConference.addTeam(playoffTeam);
			} else {
				playoffTeam.setConferenceSeed(openWildcardSeed);
				playoffConference.addTeam(playoffTeam);
			}
		}
	}

	public NFLPlayoffTeam getTeamByConferenceSeed(String conferenceName, int conferenceSeed) {
		NFLPlayoffTeam returnTeam = null;
		
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		if (playoffConference != null) {
			returnTeam = playoffConference.getTeamWithSeed(conferenceSeed);
		}
		
		return returnTeam;
	}
	
	public void setTeamConferenceSeed(NFLPlayoffTeam playoffTeam, int conferenceSeed) {
		NFLPlayoffConference playoffConference = getConference(playoffTeam);
		
		if (playoffConference != null) {
			int oldConferenceSeed = playoffTeam.getConferenceSeed();
			playoffTeam.setConferenceSeed(conferenceSeed);
			
			NFLPlayoffTeam teamThatUsedToHaveSeed = 
					playoffConference.getTeamWithSeed(conferenceSeed);
			teamThatUsedToHaveSeed.setConferenceSeed(oldConferenceSeed);
		}
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
	
	public NFLPlayoffConference getConference(NFLPlayoffTeam playoffTeam) {
		NFLPlayoffConference returnConference = null;
		
		for (NFLPlayoffConference playoffConference : conferences) {
			List<NFLPlayoffTeam> playoffTeams = playoffConference.getTeams();
			if (playoffTeams.contains(playoffTeam)) {
				returnConference = playoffConference;
				break;
			}
		}
		
		return returnConference;
	}
	
}
