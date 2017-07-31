package nfl.season.playoffs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.Game;
import nfl.season.league.League;
import nfl.season.league.Matchup;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;
import nfl.season.season.NFLSeasonTeam;

public class NFLPlayoffs implements Serializable {

	private static final long serialVersionUID = 3772284569816305782L;

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

	public League getLeague() {
		return nfl;
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
	
	public NFLPlayoffTeam getPlayoffVersionOfSeasonTeam(NFLSeasonTeam seasonTeam) {
		NFLPlayoffTeam returnTeam = null;;
		
		Team leagueTeam = seasonTeam.getTeam();
		String teamName = leagueTeam.getName();
		
		if (teamName != null) {
			for (NFLPlayoffConference playoffConference : conferences) {
				List<NFLPlayoffTeam> playoffTeams = playoffConference.getTeams();
				for (NFLPlayoffTeam playoffTeam : playoffTeams) {
					Team leagueTeamOfPlayoff = playoffTeam.getTeam();
					String nameOfPlayoffTeam = leagueTeamOfPlayoff.getName();
					
					if (teamName.equals(nameOfPlayoffTeam)) {
						returnTeam = playoffTeam;
						break;
					}
				}
			}
		}
		
		return returnTeam;
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
			NFLPlayoffTeam teamThatUsedToHaveSeed = 
					playoffConference.getTeamWithSeed(conferenceSeed);
			
			playoffTeam.setConferenceSeed(conferenceSeed);
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

	public boolean allPlayoffTeamsSet() {
		boolean allTeamsSet = true;
		
		for (NFLPlayoffConference playoffConference : conferences) {
			List<NFLPlayoffTeam> playoffTeams = playoffConference.getTeams();
			if (playoffTeams.size() < 6) {
				allTeamsSet = false;
				break;
			}
		}
		
		return allTeamsSet;
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
	
	public boolean populateTeamsByPowerRankings() {
		boolean success = allTeamPowerRankingsAreSet();
		
		if (success) {
			clearPlayoffTeams();
			for (NFLPlayoffConference conference : conferences) {
				Conference leagueConference = conference.getConference();
				String conferenceName = leagueConference.getName();
				
				List<Team> divisionWinners = new ArrayList<Team>();
				List<NFLPlayoffDivision> divisions = conference.getDivisions();
				addDivisionWinnersByPowerRanking(conferenceName, divisionWinners,
						divisions);
				
				List<Team> wildcards = new ArrayList<Team>();
				addNextWildcardByPowerRanking(leagueConference,
						divisionWinners, wildcards);
				addNextWildcardByPowerRanking(leagueConference, divisionWinners, 
						wildcards);
				
				setDivisionWinnerSeedsByPowerRankings(conference);
			}
		}
		
		return success;
	}
	
	public void populateTeamsByEloRatings() {
		clearPlayoffTeams();
		for (NFLPlayoffConference conference : conferences) {
			Conference leagueConference = conference.getConference();
			String conferenceName = leagueConference.getName();
			
			List<Team> divisionWinners = new ArrayList<Team>();
			List<NFLPlayoffDivision> divisions = conference.getDivisions();
			addDivisionWinnersByEloRating(conferenceName, divisionWinners,
					divisions);
			
			List<Team> wildcards = new ArrayList<Team>();
			addNextWildcardByEloRating(leagueConference,
					divisionWinners, wildcards);
			addNextWildcardByEloRating(leagueConference, divisionWinners, 
					wildcards);
			
			setDivisionWinnerSeedsByEloRatings(conference);
		}
	}
	
	public boolean calculateChancesByRoundForAllPlayoffTeams() {
		return NFLPlayoffRoundsUtil.calculateChancesByRoundForAllPlayoffTeams(this);
	}
	
	private Game createGameWithTeams(NFLPlayoffTeam homeTeam,
			NFLPlayoffTeam awayTeam) {
		Team leagueHomeTeam = homeTeam.getTeam();
		Team leagueAwayTeam = awayTeam.getTeam();
		Game game = new Game(leagueHomeTeam, leagueAwayTeam);
		return game;
	}
	
	private boolean allTeamPowerRankingsAreSet() {
		boolean success = true;
		
		for (NFLPlayoffConference conference : conferences) {
			Conference leagueConference = conference.getConference();
			List<Team> teams = leagueConference.getTeams();
			for (Team team : teams) {
				if (team.getPowerRanking() == Team.CLEAR_RANKING) {
					success = false;
					break;
				}
			}
		}
		
		return success;
	}

	private void addDivisionWinnersByPowerRanking(String conferenceName,
			List<Team> divisionWinners, List<NFLPlayoffDivision> divisions) {
		for (NFLPlayoffDivision division : divisions) {
			Division leagueDivision = division.getDivision();
			String divisionName = leagueDivision.getName();
			
			int bestPowerRanking = NFLTeamEnum.values().length + 1;
			List<Team> teams = leagueDivision.getTeams();
			Team divisionWinner = null;
			for (Team team : teams) {
				int teamPowerRanking = team.getPowerRanking();
				if (teamPowerRanking != Team.CLEAR_RANKING && 
						teamPowerRanking < bestPowerRanking) {
					bestPowerRanking = teamPowerRanking;
					divisionWinner = team;
				}
			}
			
			NFLPlayoffTeam playoffDivisionWinner = createPlayoffTeam(divisionWinner);
			setDivisionWinner(conferenceName, divisionName, playoffDivisionWinner);
			divisionWinners.add(divisionWinner);
		}
	}

	private void addNextWildcardByPowerRanking(Conference leagueConference,
			List<Team> divisionWinners, List<Team> wildcards) {
		String conferenceName = leagueConference.getName();
		
		int bestPowerRanking = NFLTeamEnum.values().length + 1;
		Team wildcard = null;
		List<Team> conferenceTeams = leagueConference.getTeams();
		for (Team conferenceTeam : conferenceTeams) {
			if (!divisionWinners.contains(conferenceTeam) && 
					!wildcards.contains(conferenceTeam)) {
				int teamPowerRanking = conferenceTeam.getPowerRanking();
				if (teamPowerRanking != Team.CLEAR_RANKING && teamPowerRanking < bestPowerRanking) {
					bestPowerRanking = teamPowerRanking;
					wildcard = conferenceTeam;
				}
			}
		}
		
		NFLPlayoffTeam playoffWildcard = createPlayoffTeam(wildcard);
		addWildcardTeam(conferenceName, playoffWildcard);
		wildcards.add(wildcard);
	}
	
	private void addDivisionWinnersByEloRating(String conferenceName,
			List<Team> divisionWinners, List<NFLPlayoffDivision> divisions) {
		for (NFLPlayoffDivision division : divisions) {
			Division leagueDivision = division.getDivision();
			String divisionName = leagueDivision.getName();
			
			int bestEloRating = 0;
			List<Team> teams = leagueDivision.getTeams();
			Team divisionWinner = null;
			for (Team team : teams) {
				int teamEloRating = team.getEloRating();
				if (teamEloRating > bestEloRating) {
					bestEloRating = teamEloRating;
					divisionWinner = team;
				}
			}
			
			NFLPlayoffTeam playoffDivisionWinner = createPlayoffTeam(divisionWinner);
			setDivisionWinner(conferenceName, divisionName, playoffDivisionWinner);
			divisionWinners.add(divisionWinner);
		}
	}

	private void addNextWildcardByEloRating(Conference leagueConference,
			List<Team> divisionWinners, List<Team> wildcards) {
		String conferenceName = leagueConference.getName();
		
		int bestEloRating = 0;
		Team wildcard = null;
		List<Team> conferenceTeams = leagueConference.getTeams();
		for (Team conferenceTeam : conferenceTeams) {
			if (!divisionWinners.contains(conferenceTeam) && 
					!wildcards.contains(conferenceTeam)) {
				int teamEloRating = conferenceTeam.getEloRating();
				if (teamEloRating > bestEloRating) {
					bestEloRating = teamEloRating;
					wildcard = conferenceTeam;
				}
			}
		}
		
		NFLPlayoffTeam playoffWildcard = createPlayoffTeam(wildcard);
		addWildcardTeam(conferenceName, playoffWildcard);
		wildcards.add(wildcard);
	}
	
	private void setDivisionWinnerSeedsByPowerRankings(
			NFLPlayoffConference conference) {
		List<NFLPlayoffTeam> playoffDivisionWinners = conference.getDivisionWinners();
		playoffDivisionWinners.sort(new Comparator<NFLPlayoffTeam>() {
			@Override
			public int compare(NFLPlayoffTeam team1, NFLPlayoffTeam team2) {
				Team leagueTeam1 = team1.getTeam();
				Team leagueTeam2 = team2.getTeam();
				int returnCompare = 0;
				if (leagueTeam1.getPowerRanking() < leagueTeam2.getPowerRanking()) {
					returnCompare = -1;
				} else if (leagueTeam2.getPowerRanking() < leagueTeam1.getPowerRanking()) {
					returnCompare = 1;
				}
				return returnCompare;
			}
		});
		
		int playoffDivisionWinnerIndex = 1;
		for (NFLPlayoffTeam playoffDivisionWinner : playoffDivisionWinners) {
			setTeamConferenceSeed(playoffDivisionWinner, playoffDivisionWinnerIndex);
			playoffDivisionWinnerIndex++;
		}
	}
	
	private void setDivisionWinnerSeedsByEloRatings(
			NFLPlayoffConference conference) {
		List<NFLPlayoffTeam> playoffDivisionWinners = conference.getDivisionWinners();
		playoffDivisionWinners.sort(new Comparator<NFLPlayoffTeam>() {
			@Override
			public int compare(NFLPlayoffTeam team1, NFLPlayoffTeam team2) {
				Team leagueTeam1 = team1.getTeam();
				Team leagueTeam2 = team2.getTeam();
				int returnCompare = 0;
				if (leagueTeam1.getEloRating() > leagueTeam2.getEloRating()) {
					returnCompare = -1;
				} else if (leagueTeam2.getEloRating() > leagueTeam1.getEloRating()) {
					returnCompare = 1;
				}
				return returnCompare;
			}
		});
		
		int playoffDivisionWinnerIndex = 1;
		for (NFLPlayoffTeam playoffDivisionWinner : playoffDivisionWinners) {
			setTeamConferenceSeed(playoffDivisionWinner, playoffDivisionWinnerIndex);
			playoffDivisionWinnerIndex++;
		}
	}
	
}
