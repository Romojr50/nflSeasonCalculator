package nfl.season.playoffs;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.Game;
import nfl.season.league.League;
import nfl.season.league.Matchup;
import nfl.season.league.Team;

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

	public List<Game> getWildcardGames(String conferenceName) {
		List<Game> wildcardGames = new ArrayList<Game>();
		
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		if (playoffConference != null) {
			NFLPlayoffTeam threeSeed = playoffConference.getTeamWithSeed(3);
			NFLPlayoffTeam fourSeed = playoffConference.getTeamWithSeed(4);
			NFLPlayoffTeam fiveSeed = playoffConference.getTeamWithSeed(5);
			NFLPlayoffTeam sixSeed = playoffConference.getTeamWithSeed(6);
			
			if (threeSeed != null && fourSeed != null && fiveSeed != null &&
					sixSeed != null) {
				Game threeSixGame = createGameWithTeams(threeSeed, sixSeed);
				wildcardGames.add(threeSixGame);
				
				Game fourFiveGame = createGameWithTeams(fourSeed, fiveSeed);
				wildcardGames.add(fourFiveGame);
			}
		}
		
		return wildcardGames;
	}
	
	public void setWildcardWinners(String conferenceName,
			NFLPlayoffTeam wildcardWinner1, NFLPlayoffTeam wildcardWinner2) {
		NFLPlayoffConference conference = getConference(conferenceName);
		
		if (conference != null) {
			conference.setWildcardWinners(wildcardWinner1, wildcardWinner2);
		}
	}

	public List<Game> getDivisionalGames(String conferenceName) {
		List<Game> divisionalGame = new ArrayList<Game>();
		
		NFLPlayoffConference playoffConference = getConference(conferenceName);
		if (playoffConference != null) {
			List<NFLPlayoffTeam> wildcardWinners = playoffConference.getWildcardWinners();
			
			if (wildcardWinners != null && wildcardWinners.size() == 2) {
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
					Game oneSeedGame = createGameWithTeams(oneSeed, bottomWildcardWinner);
					Game twoSeedGame = createGameWithTeams(twoSeed, topWildcardWinner);
					divisionalGame.add(oneSeedGame);
					divisionalGame.add(twoSeedGame);
				}
			}
		}
		
		return divisionalGame;
	}
	
	public void setDivisionalRoundWinners(String conferenceName,
			NFLPlayoffTeam divisionalRoundWinner1, NFLPlayoffTeam divisionalRoundWinner2) {
		NFLPlayoffConference conference = getConference(conferenceName);
		
		if (conference != null) {
			conference.setDivisionalRoundWinners(divisionalRoundWinner1, divisionalRoundWinner2);
		}
	}

	public Game getConferenceGame(String conferenceName) {
		Game conferenceGame = null;
		
		NFLPlayoffConference conference = getConference(conferenceName);
		if (conference != null) {
			List<NFLPlayoffTeam> divisionalRoundWinners = 
					conference.getDivisionalRoundWinners();
			if (divisionalRoundWinners != null && divisionalRoundWinners.size() == 2) {
				NFLPlayoffTeam divisionalRoundWinner1 = divisionalRoundWinners.get(0);
				NFLPlayoffTeam divisionalRoundWinner2 = divisionalRoundWinners.get(1);
				
				int divisionalRoundWinner1Seed = divisionalRoundWinner1.getConferenceSeed();
				int divisionalRoundWinner2Seed = divisionalRoundWinner2.getConferenceSeed();
				if (divisionalRoundWinner1Seed < divisionalRoundWinner2Seed) {
					conferenceGame = createGameWithTeams(divisionalRoundWinner1, divisionalRoundWinner2);
				} else {
					conferenceGame = createGameWithTeams(divisionalRoundWinner2, divisionalRoundWinner1);
				}
			}
		}
		
		return conferenceGame;
	}

	public void setConferenceWinner(String conferenceName,
			NFLPlayoffTeam conferenceWinner) {
		NFLPlayoffConference conference = getConference(conferenceName);
		
		if (conference != null) {
			conference.setConferenceWinner(conferenceWinner);
		}
	}

	public Game getSuperBowlGame() {
		Game superBowlGame = null;
		
		NFLPlayoffConference conference1 = conferences.get(0);
		NFLPlayoffConference conference2 = conferences.get(1);
		
		NFLPlayoffTeam conference1Winner = conference1.getConferenceWinner();
		NFLPlayoffTeam conference2Winner = conference2.getConferenceWinner();

		if (conference1Winner != null && conference2Winner != null) {
			Team leagueConference1Winner = conference1Winner.getTeam();
			Team leagueConference2Winner = conference2Winner.getTeam();
			String leagueConference2WinnerName = leagueConference2Winner.getName();
			
			Matchup superBowlMatchup = leagueConference1Winner.getMatchup(
					leagueConference2WinnerName);
			superBowlGame = new Game(superBowlMatchup);
		}
		
		return superBowlGame;
	}
	
	private Game createGameWithTeams(NFLPlayoffTeam homeTeam,
			NFLPlayoffTeam awayTeam) {
		Team leagueHomeTeam = homeTeam.getTeam();
		Team leagueAwayTeam = awayTeam.getTeam();
		Game game = new Game(leagueHomeTeam, leagueAwayTeam);
		return game;
	}

	public void populateTeamsByPowerRankings() {
		
	}
}
