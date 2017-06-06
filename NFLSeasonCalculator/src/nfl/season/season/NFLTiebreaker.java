package nfl.season.season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nfl.season.league.League;
import nfl.season.league.Team;

public class NFLTiebreaker {

	NFLSeason season;
	
	public NFLTiebreaker(NFLSeason season) {
		this.season = season;
	}
	
	public NFLSeasonTeam tiebreakTeams(NFLSeasonTeam team1,
			NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		double team1WinPercent = calculateWinPercentFromWinsLossesAndTies(
				team1.getNumberOfWins(), team1.getNumberOfLosses(), team1.getNumberOfTies());
		double team2WinPercent = calculateWinPercentFromWinsLossesAndTies(
				team2.getNumberOfWins(), team2.getNumberOfLosses(), team2.getNumberOfTies());
		
		if (team1WinPercent > team2WinPercent) {
			tieWinner = team1;
		} else if (team2WinPercent > team1WinPercent) {
			tieWinner = team2;
		} else {
			tieWinner = resolveHeadToHeadTiebreak(team1, team2);
			
			if (tieWinner == null) {
				League league = season.getLeague();
				Team leagueTeam1 = team1.getTeam();
				Team leagueTeam2 = team2.getTeam();
				
				if (league.areInSameDivision(leagueTeam1, leagueTeam2)) {
					tieWinner = resolveDivisionalTeamTiebreakers(team1, team2);
				} else {
					tieWinner = resolveConferenceTeamTiebreakers(team1, team2);
				}
			}
		}
		
		return tieWinner;
	}
	

	public NFLSeasonTeam tiebreakManyTeams(NFLSeasonTeam... teams) {
		NFLSeasonTeam tieWinner = null;
		
		List<NFLSeasonTeam> remainingTeams = resolveManyTeamWinPercentTieBreak(teams);
		
		if (remainingTeams.size() > 2) {
			remainingTeams = resolveManyTeamHeadToHeadTieBreak(remainingTeams);
		}
		if (remainingTeams.size() > 2) {
			remainingTeams = resolveManyTeamDivisionWinPercentBreak(remainingTeams);
		}
		if (remainingTeams.size() > 2) {
			remainingTeams = resolveManyTeamCommonGamesWinPercentTieBreak(remainingTeams);
		}
		
		tieWinner = resolveTieBreakOfOneOrTwoTeamsFromMany(tieWinner,
				remainingTeams);
		
		return tieWinner;
	}
	
	private double calculateWinPercentFromWinsLossesAndTies(int wins, 
			int losses, int ties) {
		double returnWinPercent = 0.0;
		
		if (wins + losses + ties > 0) {
			returnWinPercent = (wins + (ties / 2.0)) / (wins + losses + ties);
		}
		
		return returnWinPercent;
	}
	

	private NFLSeasonTeam resolveHeadToHeadTiebreak(NFLSeasonTeam team1,
			NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		Team leagueTeam1 = team1.getTeam();
		Team leagueTeam2 = team2.getTeam();
		String team1Name = leagueTeam1.getName();
		String team2Name = leagueTeam2.getName();
		
		List<String> team1Wins = team1.getWinsAgainst();
		List<String> team1Losses = team1.getLossesAgainst();
		List<String> team1Ties = team1.getTiesAgainst();
		double team1HeadToHeadWinPercent = getHeadToHeadWinPercent(team2Name,
				team1Wins, team1Losses, team1Ties);
		
		List<String> team2Wins = team2.getWinsAgainst();
		List<String> team2Losses = team2.getLossesAgainst();
		List<String> team2Ties = team2.getTiesAgainst();
		double team2HeadToHeadWinPercent = getHeadToHeadWinPercent(team1Name,
				team2Wins, team2Losses, team2Ties);
		
		if (team1HeadToHeadWinPercent > team2HeadToHeadWinPercent) {
			tieWinner = team1;
		} else if (team2HeadToHeadWinPercent > team1HeadToHeadWinPercent) {
			tieWinner = team2;
		}
		
		return tieWinner;
	}
	
	private NFLSeasonTeam resolveDivisionalTeamTiebreakers(NFLSeasonTeam team1,
			NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		tieWinner = resolveDivisionWinPercentTieBreak(team1, team2);
		
		if (tieWinner == null) {
			tieWinner = resolveCommonGameWinPercentTieBreak(team1, team2);
		}
		if (tieWinner == null) {
			tieWinner = resolveConferenceWinPercentTieBreak(team1, team2);
		}
		if (tieWinner == null) {
			tieWinner = resolveStrengthOfVictoryTieBreak(team1, team2);
		}
		if (tieWinner == null) {
			tieWinner = resolveStrengthOfScheduleTieBreak(team1, team2);
		}
		
		return tieWinner;
	}
	
	private NFLSeasonTeam resolveConferenceTeamTiebreakers(NFLSeasonTeam team1,
			NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		tieWinner = resolveConferenceWinPercentTieBreak(team1, team2);
		
		if (tieWinner == null) {
			tieWinner = resolveCommonGameWinPercentTieBreak(team1, team2);
		}
		if (tieWinner == null) {
			tieWinner = resolveStrengthOfVictoryTieBreak(team1, team2);
		}
		if (tieWinner == null) {
			tieWinner = resolveStrengthOfScheduleTieBreak(team1, team2);
		}
		
		return tieWinner;
	}
	
	private List<NFLSeasonTeam> resolveManyTeamWinPercentTieBreak(NFLSeasonTeam... teams) {
		List<NFLSeasonTeam> remainingTeams = new ArrayList<NFLSeasonTeam>();
		remainingTeams.addAll(Arrays.asList(teams));
		
		List<Double> teamWinPercents = new ArrayList<Double>();
		for (NFLSeasonTeam team : teams) {
			double teamWinPercent = calculateWinPercentFromWinsLossesAndTies(
					team.getNumberOfWins(), team.getNumberOfLosses(), team.getNumberOfTies());
			teamWinPercents.add(teamWinPercent);
		}
		
		double highestWinPercent = getHighestWinPercentFromList(teamWinPercents);
		
		for (NFLSeasonTeam team : teams) {
			double teamWinPercent = calculateWinPercentFromWinsLossesAndTies(
					team.getNumberOfWins(), team.getNumberOfLosses(), team.getNumberOfTies());
			if (teamWinPercent < highestWinPercent) {
				remainingTeams.remove(team);
			}
		}
		
		return remainingTeams;
	}
	
	private List<NFLSeasonTeam> resolveManyTeamHeadToHeadTieBreak(
			List<NFLSeasonTeam> remainingTeams) {
		List<NFLSeasonTeam> nextRemainingTeams = new ArrayList<NFLSeasonTeam>();
		nextRemainingTeams.addAll(remainingTeams);
		
		List<String> teamNames = new ArrayList<String>();
		for (NFLSeasonTeam team : remainingTeams) {
			Team leagueTeam = team.getTeam();
			teamNames.add(leagueTeam.getName());
		}
		
		List<Double> headToHeadWinPercents = new ArrayList<Double>();
		for (NFLSeasonTeam team : remainingTeams) {
			double headToHeadWinPercent = getTeamManyHeadToHeadWinPercent(
					teamNames, headToHeadWinPercents, team);
			headToHeadWinPercents.add(headToHeadWinPercent);
		}
		
		double highestWinPercent = getHighestWinPercentFromList(headToHeadWinPercents);
		for (NFLSeasonTeam team : remainingTeams) {
			double headToHeadWinPercent = getTeamManyHeadToHeadWinPercent(
					teamNames, headToHeadWinPercents, team);
			if (headToHeadWinPercent < highestWinPercent) {
				nextRemainingTeams.remove(team);
			}
		}
		
		return nextRemainingTeams;
	}
	
	private List<NFLSeasonTeam> resolveManyTeamDivisionWinPercentBreak(
			List<NFLSeasonTeam> remainingTeams) {
		List<NFLSeasonTeam> nextRemainingTeams = new ArrayList<NFLSeasonTeam>();
		
		List<Double> divisionWinPercents = new ArrayList<Double>();
		for (NFLSeasonTeam team : remainingTeams) {
			double teamDivisionWinPercent = calculateWinPercentFromWinsLossesAndTies(
					team.getNumberOfDivisionWins(), team.getNumberOfDivisionLosses(), 
					team.getNumberOfDivisionTies());
			divisionWinPercents.add(teamDivisionWinPercent);
		}
		
		double highestWinPercent = getHighestWinPercentFromList(divisionWinPercents);
		for (NFLSeasonTeam team : remainingTeams) {
			double teamDivisionWinPercent = calculateWinPercentFromWinsLossesAndTies(
					team.getNumberOfDivisionWins(), team.getNumberOfDivisionLosses(), 
					team.getNumberOfDivisionTies());
			if (teamDivisionWinPercent >= highestWinPercent) {
				nextRemainingTeams.add(team);
			}
		}
		
		return nextRemainingTeams;
	}
	
	private List<NFLSeasonTeam> resolveManyTeamCommonGamesWinPercentTieBreak(
			List<NFLSeasonTeam> remainingTeams) {
		List<NFLSeasonTeam> nextRemainingTeams = new ArrayList<NFLSeasonTeam>();
		
		List<String> commonGames = getListOfCommonGames(remainingTeams);

		List<Double> commonWinPercents = new ArrayList<Double>();
		for (NFLSeasonTeam team : remainingTeams) {
			double commonWinPercent = getWinPercentOfTeamAgainstOpponentList(
					commonGames, team);
			commonWinPercents.add(commonWinPercent);
		}
		
		double highestWinPercent = getHighestWinPercentFromList(commonWinPercents);
		for (NFLSeasonTeam team : remainingTeams) {
			double commonWinPercent = getWinPercentOfTeamAgainstOpponentList(
					commonGames, team);
			if (commonWinPercent >= highestWinPercent) {
				nextRemainingTeams.add(team);
			}
		}
		
		return nextRemainingTeams;
	}

	private NFLSeasonTeam resolveDivisionWinPercentTieBreak(
			NFLSeasonTeam team1, NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		double team1DivisionWinPercent = calculateWinPercentFromWinsLossesAndTies(
				team1.getNumberOfDivisionWins(), team1.getNumberOfDivisionLosses(), 
				team1.getNumberOfDivisionTies());
		double team2DivisionWinPercent = calculateWinPercentFromWinsLossesAndTies(
				team2.getNumberOfDivisionWins(), team2.getNumberOfDivisionLosses(), 
				team2.getNumberOfDivisionTies());
		
		if (team1DivisionWinPercent > team2DivisionWinPercent) {
			tieWinner = team1;
		} else if (team2DivisionWinPercent > team1DivisionWinPercent) {
			tieWinner = team2;
		}
		return tieWinner;
	}
	
	private NFLSeasonTeam resolveCommonGameWinPercentTieBreak(
			NFLSeasonTeam team1, NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		List<String> team1WinsAgainst = team1.getWinsAgainst();
		List<String> team1LossesAgainst = team1.getLossesAgainst();
		List<String> team1TiesAgainst = team1.getTiesAgainst();
		
		List<String> team2WinsAgainst = team2.getWinsAgainst();
		List<String> team2LossesAgainst = team2.getLossesAgainst();
		List<String> team2TiesAgainst = team2.getTiesAgainst();
		
		List<String> commonGames = getListOfCommonGames(team1, team2);
		int team1CommonWins = 0;
		int team1CommonLosses = 0;
		int team1CommonTies = 0;
		int team2CommonWins = 0;
		int team2CommonLosses = 0;
		int team2CommonTies = 0;
		
		for (String commonGame : commonGames) {
			team1CommonWins += numberOfTimesInList(team1WinsAgainst, commonGame);
			team1CommonLosses += numberOfTimesInList(team1LossesAgainst, commonGame);
			team1CommonTies += numberOfTimesInList(team1TiesAgainst, commonGame);
			team2CommonWins += numberOfTimesInList(team2WinsAgainst, commonGame);
			team2CommonLosses += numberOfTimesInList(team2LossesAgainst, commonGame);
			team2CommonTies += numberOfTimesInList(team2TiesAgainst, commonGame);
		}
			
		double team1CommonWinPercent = calculateWinPercentFromWinsLossesAndTies(
				team1CommonWins, team1CommonLosses, team1CommonTies);
		double team2CommonWinPercent = calculateWinPercentFromWinsLossesAndTies(
				team2CommonWins, team2CommonLosses, team2CommonTies);
		
		if ((team1CommonWins + team1CommonLosses + team1CommonTies) >= 4 &&
				(team2CommonWins + team2CommonLosses + team2CommonTies >= 4)) {
			if (team1CommonWinPercent > team2CommonWinPercent) {
				tieWinner = team1;
			} else if (team2CommonWinPercent > team1CommonWinPercent) {
				tieWinner = team2;
			}
		}
		
		return tieWinner;
	}
	
	private NFLSeasonTeam resolveConferenceWinPercentTieBreak(
			NFLSeasonTeam team1, NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		double team1ConferenceWinPercent = calculateWinPercentFromWinsLossesAndTies(
				team1.getNumberOfConferenceWins(), team1.getNumberOfConferenceLosses(), 
				team1.getNumberOfConferenceTies());
		double team2ConferenceWinPercent = calculateWinPercentFromWinsLossesAndTies(
				team2.getNumberOfConferenceWins(), team2.getNumberOfConferenceLosses(), 
				team2.getNumberOfConferenceTies());
		
		if (team1ConferenceWinPercent > team2ConferenceWinPercent) {
			tieWinner = team1;
		} else if (team2ConferenceWinPercent > team1ConferenceWinPercent) {
			tieWinner = team2;
		}
		return tieWinner;
	}
	
	private NFLSeasonTeam resolveStrengthOfVictoryTieBreak(
			NFLSeasonTeam team1, NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		List<String> team1WinsAgainst = team1.getWinsAgainst();
		double team1StrengthOfVictory = getWinPercentOfTeamsInList(team1WinsAgainst);
		
		List<String> team2WinsAgainst = team2.getWinsAgainst();
		double team2StrengthOfVictory = getWinPercentOfTeamsInList(team2WinsAgainst);
		
		if (team1StrengthOfVictory > team2StrengthOfVictory) {
			tieWinner = team1;
		} else if (team2StrengthOfVictory > team1StrengthOfVictory) {
			tieWinner = team2;
		}
		
		return tieWinner;
	}
	
	private NFLSeasonTeam resolveStrengthOfScheduleTieBreak(
			NFLSeasonTeam team1, NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		double team1StrengthOfSchedule = 0.0;
		double team2StrengthOfSchedule = 0.0;
		
		team1StrengthOfSchedule += getWinPercentOfTeamsInList(team1.getWinsAgainst());
		team1StrengthOfSchedule += getWinPercentOfTeamsInList(team1.getLossesAgainst());
		team1StrengthOfSchedule += getWinPercentOfTeamsInList(team1.getTiesAgainst());
		
		team2StrengthOfSchedule += getWinPercentOfTeamsInList(team2.getWinsAgainst());
		team2StrengthOfSchedule += getWinPercentOfTeamsInList(team2.getLossesAgainst());
		team2StrengthOfSchedule += getWinPercentOfTeamsInList(team2.getTiesAgainst());
		
		if (team1StrengthOfSchedule > team2StrengthOfSchedule) {
			tieWinner = team1;
		} else if (team2StrengthOfSchedule > team1StrengthOfSchedule) {
			tieWinner = team2;
		}
		
		return tieWinner;
	}

	private double getHeadToHeadWinPercent(String team2Name,
			List<String> team1Wins, List<String> team1Losses,
			List<String> team1Ties) {
		int team1HeadToHeadWins = numberOfTimesInList(team1Wins, team2Name);
		int team1HeadToHeadLosses = numberOfTimesInList(team1Losses, team2Name);
		int team1HeadToHeadTies = numberOfTimesInList(team1Ties, team2Name);
		double team1HeadToHeadWinPercent = calculateWinPercentFromWinsLossesAndTies(
				team1HeadToHeadWins, team1HeadToHeadLosses, team1HeadToHeadTies);
		return team1HeadToHeadWinPercent;
	}

	private double getHighestWinPercentFromList(List<Double> teamWinPercents) {
		double highestWinPercent = 0.0;
		for (double teamWinPercent : teamWinPercents) {
			if (teamWinPercent > highestWinPercent) {
				highestWinPercent = teamWinPercent;
			}
		}
		return highestWinPercent;
	}

	private NFLSeasonTeam resolveTieBreakOfOneOrTwoTeamsFromMany(
			NFLSeasonTeam tieWinner, List<NFLSeasonTeam> remainingTeams) {
		if (remainingTeams.size() == 1) {
			tieWinner = remainingTeams.get(0);
		} else if (remainingTeams.size() == 2) {
			tieWinner = tiebreakTeams(remainingTeams.get(0), remainingTeams.get(1));
		}
		return tieWinner;
	}
	
	private double getTeamManyHeadToHeadWinPercent(List<String> teamNames,
			List<Double> headToHeadWinPercents, NFLSeasonTeam team) {
		double headToHeadWinPercent = 0.0;
		
		List<String> teamWins = team.getWinsAgainst();
		List<String> teamLosses = team.getLossesAgainst();
		List<String> teamTies = team.getTiesAgainst();
		
		Team leagueTeam = team.getTeam();
		String teamName = leagueTeam.getName();
		for (String opponent : teamNames) {
			if (!teamName.equalsIgnoreCase(opponent)) {
				headToHeadWinPercent += getHeadToHeadWinPercent(opponent, 
						teamWins, teamLosses, teamTies);
			}
		}
		
		return headToHeadWinPercent;
	}

	private double getWinPercentOfTeamAgainstOpponentList(
			List<String> commonGames, NFLSeasonTeam team) {
		List<String> winsAgainst = team.getWinsAgainst();
		List<String> lossesAgainst = team.getLossesAgainst();
		List<String> tiesAgainst = team.getTiesAgainst();
		
		int commonWins = 0;
		int commonLosses = 0;
		int commonTies = 0;
		
		for (String commonGame : commonGames) {
			commonWins += numberOfTimesInList(winsAgainst, commonGame);
			commonLosses += numberOfTimesInList(lossesAgainst, commonGame);
			commonTies += numberOfTimesInList(tiesAgainst, commonGame);
		}
		
		double commonWinPercent = calculateWinPercentFromWinsLossesAndTies(
				commonWins, commonLosses, commonTies);
		return commonWinPercent;
	}
	
	private List<String> getListOfCommonGames(NFLSeasonTeam team1,
			NFLSeasonTeam team2) {
		List<String> commonGames = new ArrayList<String>();
		
		List<String> team1WinsAgainst = team1.getWinsAgainst();
		List<String> team1LossesAgainst = team1.getLossesAgainst();
		List<String> team1TiesAgainst = team1.getTiesAgainst();
		
		for (String team1WinAgainst : team1WinsAgainst) {
			if (!commonGames.contains(team1WinAgainst) && teamHasPlayedAgainstOpponent(team2, team1WinAgainst)) {
				commonGames.add(team1WinAgainst);
			}
		}
		for (String team1LossAgainst : team1LossesAgainst) {
			if (!commonGames.contains(team1LossAgainst) && teamHasPlayedAgainstOpponent(team2, team1LossAgainst)) {
				commonGames.add(team1LossAgainst);
			}
		}
		for (String team1TieAgainst : team1TiesAgainst) {
			if (!commonGames.contains(team1TieAgainst) && teamHasPlayedAgainstOpponent(team2, team1TieAgainst)) {
				commonGames.add(team1TieAgainst);
			}
		}
		
		return commonGames;
	}
	
	private List<String> getListOfCommonGames(List<NFLSeasonTeam> teams) {
		List<String> commonGames = new ArrayList<String>();
		
		for (NFLSeasonTeam team : teams) {
			List<String> allGames = getAllOpponentsOfTeamWithoutDuplicates(team);
			
			for (String game : allGames) {
				boolean playedAgainstOtherTeams = true;
				for (NFLSeasonTeam otherTeam : teams) {
					if (!teamHasPlayedAgainstOpponent(otherTeam, game)) {
						playedAgainstOtherTeams = false;
						break;
					}
				}
				
				if (playedAgainstOtherTeams && !commonGames.contains(game)) {
					commonGames.add(game);
				}
			}
		}
		
		return commonGames;
	}
	
	private boolean teamHasPlayedAgainstOpponent(NFLSeasonTeam team, 
			String opponentName) {
		boolean teamHasPlayedOpponent = false;
		
		List<String> teamWins = team.getWinsAgainst();
		List<String> teamLosses = team.getLossesAgainst();
		List<String> teamTies = team.getTiesAgainst();
		
		if (teamWins.contains(opponentName) || teamLosses.contains(opponentName)
				|| teamTies.contains(opponentName)) {
			teamHasPlayedOpponent = true;
		}
		
		return teamHasPlayedOpponent;
	}
	
	private int numberOfTimesInList(List<String> list,
			String element) {
		int numberOfTimesInList = 0;
		
		for (String listItem : list) {
			if (listItem != null && listItem.equalsIgnoreCase(element)) {
				numberOfTimesInList++;
			}
		}
		
		return numberOfTimesInList;
	}
	
	private double getWinPercentOfTeamsInList(List<String> teamList) {
		double winPercent = 0.0;
		
		for (String teamName : teamList) {
			NFLSeasonTeam seasonTeam = season.getTeam(teamName);
			winPercent += calculateWinPercentFromWinsLossesAndTies(
					seasonTeam.getNumberOfWins(), seasonTeam.getNumberOfLosses(), 
					seasonTeam.getNumberOfTies());
		}
		
		return winPercent;
	}

	private List<String> getAllOpponentsOfTeamWithoutDuplicates(
			NFLSeasonTeam team) {
		List<String> allGames = new ArrayList<String>();
		List<String> allGamesWithDuplicates = new ArrayList<String>();
		allGamesWithDuplicates.addAll(team.getWinsAgainst());
		allGamesWithDuplicates.addAll(team.getLossesAgainst());
		allGamesWithDuplicates.addAll(team.getTiesAgainst());
		
		for (String game : allGamesWithDuplicates) {
			if (!allGames.contains(game)) {
				allGames.add(game);
			}
		}
		return allGames;
	}

}
