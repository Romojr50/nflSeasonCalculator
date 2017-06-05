package nfl.season.season;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Team;

public class NFLTiebreaker {

	public static NFLSeasonTeam tiebreakTeams(NFLSeasonTeam team1,
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
			Team leagueTeam1 = team1.getTeam();
			Team leagueTeam2 = team2.getTeam();
			String team1Name = leagueTeam1.getName();
			String team2Name = leagueTeam2.getName();
			
			tieWinner = resolveHeadToHeadTiebreak(team1, team2);
			
			if (tieWinner == null) {
				tieWinner = resolveDivisionWinPercentTieBreak(team1, team2);
				
				if (tieWinner == null) {
					tieWinner = resolveCommonGameWinPercentTieBreak(team1, team2);
				}
			}
		}
		
		return tieWinner;
	}
	
	private static double calculateWinPercentFromWinsLossesAndTies(int wins, 
			int losses, int ties) {
		return (wins + (ties / 2.0)) / (wins + losses + ties);
	}
	

	private static NFLSeasonTeam resolveHeadToHeadTiebreak(NFLSeasonTeam team1,
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

	private static NFLSeasonTeam resolveDivisionWinPercentTieBreak(
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
	
	private static NFLSeasonTeam resolveCommonGameWinPercentTieBreak(
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
		
		if (team1CommonWinPercent > team2CommonWinPercent) {
			tieWinner = team1;
		} else if (team2CommonWinPercent > team1CommonWinPercent) {
			tieWinner = team2;
		}
		
		return tieWinner;
	}

	private static double getHeadToHeadWinPercent(String team2Name,
			List<String> team1Wins, List<String> team1Losses,
			List<String> team1Ties) {
		int team1HeadToHeadWins = numberOfTimesInList(team1Wins, team2Name);
		int team1HeadToHeadLosses = numberOfTimesInList(team1Losses, team2Name);
		int team1HeadToHeadTies = numberOfTimesInList(team1Ties, team2Name);
		double team1HeadToHeadWinPercent = calculateWinPercentFromWinsLossesAndTies(
				team1HeadToHeadWins, team1HeadToHeadLosses, team1HeadToHeadTies);
		return team1HeadToHeadWinPercent;
	}

	private static List<String> getListOfCommonGames(NFLSeasonTeam team1,
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
	
	private static boolean teamHasPlayedAgainstOpponent(NFLSeasonTeam team, 
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
	
	private static int numberOfTimesInList(List<String> list,
			String element) {
		int numberOfTimesInList = 0;
		
		for (String listItem : list) {
			if (listItem != null && listItem.equalsIgnoreCase(element)) {
				numberOfTimesInList++;
			}
		}
		
		return numberOfTimesInList;
	}

}
