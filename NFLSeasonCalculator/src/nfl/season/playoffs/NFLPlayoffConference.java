package nfl.season.playoffs;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;

public class NFLPlayoffConference {

	public static final int CLEAR_SEED = -1;

	private Conference conference;
	
	private List<NFLPlayoffDivision> divisions;
	
	private List<NFLPlayoffTeam> teams;
	
	private List<NFLPlayoffTeam> wildcardWinners;
	
	private List<NFLPlayoffTeam> divisionalRoundWinners;
	
	private NFLPlayoffTeam conferenceWinner;
	
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
	
	public List<NFLPlayoffTeam> getDivisionWinners() {
		List<NFLPlayoffTeam> divisionWinners = new ArrayList<NFLPlayoffTeam>();
		
		for (NFLPlayoffDivision playoffDivision : divisions) {
			NFLPlayoffTeam divisionWinner = playoffDivision.getDivisionWinner();
			if (divisionWinner != null) {
				divisionWinners.add(divisionWinner);
			}
		}
		
		return divisionWinners;
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
		int openSeed = CLEAR_SEED;
		for (int i = 1; i <= 4; i++) {
			NFLPlayoffTeam teamWithSeed = getTeamWithSeed(i);
			if (teamWithSeed == null) {
				openSeed = i;
				break;
			}
		}
		return openSeed;
	}
	
	public int getOpenWildcardSeed() {
		int openSeed = CLEAR_SEED;
		
		for (int i = 5; i <= 6; i++) {
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
	
	public List<NFLPlayoffTeam> getTeams() {
		return teams;
	}
	
	public List<NFLPlayoffTeam> getTeamsInSeedOrder() {
		List<NFLPlayoffTeam> playoffTeams = new ArrayList<NFLPlayoffTeam>();
		playoffTeams.add(getTeamWithSeed(1));
		playoffTeams.add(getTeamWithSeed(2));
		playoffTeams.add(getTeamWithSeed(3));
		playoffTeams.add(getTeamWithSeed(4));
		playoffTeams.add(getTeamWithSeed(5));
		playoffTeams.add(getTeamWithSeed(6));
		return playoffTeams;
	}

	public void addTeam(NFLPlayoffTeam playoffTeam) {
		teams.add(playoffTeam);
	}
	
	public void removeTeam(NFLPlayoffTeam playoffTeam) {
		teams.remove(playoffTeam);
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
	
	public List<NFLPlayoffTeam> getWildcardWinners() {
		return wildcardWinners;
	}
	
	public void setWildcardWinners(NFLPlayoffTeam wildcardWinner1,
			NFLPlayoffTeam wildcardWinner2) {
		wildcardWinners = new ArrayList<NFLPlayoffTeam>();
		wildcardWinners.add(wildcardWinner1);
		wildcardWinners.add(wildcardWinner2);
	}
	
	public List<NFLPlayoffTeam> getDivisionalRoundWinners() {
		return divisionalRoundWinners;
	}
	
	public void setDivisionalRoundWinners(NFLPlayoffTeam divisionalRoundWinner1,
			NFLPlayoffTeam divisionalRoundWinner2) {
		divisionalRoundWinners = new ArrayList<NFLPlayoffTeam>();
		divisionalRoundWinners.add(divisionalRoundWinner1);
		divisionalRoundWinners.add(divisionalRoundWinner2);
	}

	public void clearPlayoffTeams() {
		teams = new ArrayList<NFLPlayoffTeam>();
		for (NFLPlayoffDivision playoffDivision : divisions) {
			playoffDivision.setDivisionWinner(null);
		}
	}

	public NFLPlayoffTeam getConferenceWinner() {
		return conferenceWinner;
	}

	public void setConferenceWinner(NFLPlayoffTeam conferenceWinner) {
		this.conferenceWinner = conferenceWinner;
	}
	
}
