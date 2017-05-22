package nfl.season.playoffs;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.League;
import nfl.season.league.Matchup;
import nfl.season.league.Team;

public class NFLPlayoffs {

	private League nfl;
	
	private List<NFLPlayoffConference> conferences;
	
	private List<NFLPlayoffTeam> wildcardWinners;
	
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
			if (teamThatUsedToHaveSeed != null) {
				teamThatUsedToHaveSeed.setConferenceSeed(oldConferenceSeed);
			}
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

	public NFLPlayoffTeam createPlayoffTeam(Team leagueTeam) {
		NFLPlayoffTeam playoffTeam = new NFLPlayoffTeam(leagueTeam);
		return playoffTeam;
	}

	public void clearPlayoffTeams() {
		for (NFLPlayoffConference playoffConference : conferences) {
			playoffConference.clearPlayoffTeams();
		}
	}

	public List<Matchup> getWildcardMatchups(String conferenceName) {
		List<Matchup> wildcardMatchups = new ArrayList<Matchup>();
		
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		if (playoffConference != null) {
			NFLPlayoffTeam threeSeed = playoffConference.getTeamWithSeed(3);
			NFLPlayoffTeam fourSeed = playoffConference.getTeamWithSeed(4);
			NFLPlayoffTeam fiveSeed = playoffConference.getTeamWithSeed(5);
			NFLPlayoffTeam sixSeed = playoffConference.getTeamWithSeed(6);
			
			if (threeSeed != null && fourSeed != null && fiveSeed != null &&
					sixSeed != null) {
				Team team3 = threeSeed.getTeam();
				Team team4 = fourSeed.getTeam();
				Team team5 = fiveSeed.getTeam();
				Team team6 = sixSeed.getTeam();
				
				Matchup threeSixMatchup = team3.getMatchup(team6.getName());
				Matchup fourFiveMatchup = team4.getMatchup(team5.getName());
				
				wildcardMatchups.add(threeSixMatchup);
				wildcardMatchups.add(fourFiveMatchup);
			}
		}
		
		return wildcardMatchups;
	}

	public void setWildcardWinners(NFLPlayoffTeam wildcardWinner1,
			NFLPlayoffTeam wildcardWinner2) {
		wildcardWinners = new ArrayList<NFLPlayoffTeam>();
		wildcardWinners.add(wildcardWinner1);
		wildcardWinners.add(wildcardWinner2);
	}

	public List<Matchup> getDivisionalMatchups(String conferenceName) {
		List<Matchup> divisionalMatchups = new ArrayList<Matchup>();
		
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		if (playoffConference != null && wildcardWinners != null && wildcardWinners.size() == 2) {
			NFLPlayoffTeam firstWildcardWinner = wildcardWinners.get(0);
			int firstWildcardSeed = firstWildcardWinner.getConferenceSeed();
			NFLPlayoffTeam secondWildcardWinner = wildcardWinners.get(1);
			int secondWildcardSeed = secondWildcardWinner.getConferenceSeed();
			
			NFLPlayoffTeam topWildcardWinner = firstWildcardWinner;
			NFLPlayoffTeam bottomWildcardWinner = secondWildcardWinner;
			if (firstWildcardSeed > secondWildcardSeed) {
				topWildcardWinner = secondWildcardWinner;
				bottomWildcardWinner = firstWildcardWinner;
			}
			
			NFLPlayoffTeam oneSeed = playoffConference.getTeamWithSeed(1);
			NFLPlayoffTeam twoSeed = playoffConference.getTeamWithSeed(2);
			
			if (oneSeed != null && twoSeed != null && topWildcardWinner != null && bottomWildcardWinner != null) {
				Team teamWildcardTop = topWildcardWinner.getTeam();
				Team teamWildcardBottom = bottomWildcardWinner.getTeam();
				
				Team team1 = oneSeed.getTeam();
				Matchup oneSeedMatchup = team1.getMatchup(teamWildcardBottom.getName());
				
				Team team2 = twoSeed.getTeam();
				Matchup twoSeedMatchup = team2.getMatchup(teamWildcardTop.getName());
				
				divisionalMatchups.add(oneSeedMatchup);
				divisionalMatchups.add(twoSeedMatchup);
			}
		}
		
		return divisionalMatchups;
	}
	
}
