package nfl.season.season;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Matchup;
import nfl.season.league.Team;

public class NFLSeasonTeam {

	private Team leagueTeam;
	
	private SeasonGame[] seasonGames;
	
	private int numberOfWins;
	
	private int numberOfLosses;
	
	private int numberOfTies;
	
	private List<String> winsAgainst;
	
	private List<String> lossesAgainst;
	
	private List<String> tiesAgainst;
	
	private int numberOfConferenceWins;
	
	private int numberOfConferenceLosses;
	
	private int numberOfConferenceTies;
	
	private int numberOfDivisionWins;
	
	private int numberOfDivisionLosses;
	
	private int numberOfDivisionTies;
	
	public NFLSeasonTeam(Team leagueTeam) {
		this.leagueTeam = leagueTeam;
		seasonGames = new SeasonGame[NFLSeason.NUMBER_OF_WEEKS_IN_SEASON];
		winsAgainst = new ArrayList<String>();
		lossesAgainst = new ArrayList<String>();
		tiesAgainst = new ArrayList<String>();
	}
	
	public Team getTeam() {
		return leagueTeam;
	}

	public SeasonGame[] getSeasonGames() {
		return seasonGames;
	}

	public SeasonGame getSeasonGame(int weekNumber) {
		SeasonGame returnGame = null;
		
		if (weekNumber >= 1 && weekNumber <= NFLSeason.NUMBER_OF_WEEKS_IN_SEASON) {
			returnGame = seasonGames[weekNumber - 1];
		}
		
		return returnGame;
	}

	public void addSeasonGame(int weekNumber, SeasonGame seasonGame) {
		if (weekNumber >= 1 && weekNumber <= NFLSeason.NUMBER_OF_WEEKS_IN_SEASON) {
			seasonGames[weekNumber - 1] = seasonGame;
			
			if (seasonGame.alreadyHappened()) {
				Team winner = seasonGame.getWinner();
				Matchup matchup = seasonGame.getMatchup();
				String opponentName = matchup.getOpponentName(leagueTeam.getName());
				
				if (winner != null) {
					if (winner.equals(leagueTeam)) {
						tallyWin(seasonGame, opponentName);
					} else {
						tallyLoss(seasonGame, opponentName);
					}
				} else {
					tallyTie(seasonGame, opponentName);
				}
			}
		}
	}

	public String getScheduleString() {
		String scheduleString = "Team's schedule is empty\n";
		
		if (!seasonGamesAreEmpty()) {
			scheduleString = createScheduleString();
		}
		
		return scheduleString;
	}
	
	public int getNumberOfWins() {
		return numberOfWins;
	}
	
	public int getNumberOfLosses() {
		return numberOfLosses;
	}
	
	public int getNumberOfTies() {
		return numberOfTies;
	}
	
	public List<String> getWinsAgainst() {
		return winsAgainst;
	}
	
	public List<String> getLossesAgainst() {
		return lossesAgainst;
	}
	
	public List<String> getTiesAgainst() {
		return tiesAgainst;
	}
	
	public int getNumberOfConferenceWins() {
		return numberOfConferenceWins;
	}
	
	public int getNumberOfConferenceLosses() {
		return numberOfConferenceLosses;
	}
	
	public int getNumberOfConferenceTies() {
		return numberOfConferenceTies;
	}
	
	public int getNumberOfDivisionWins() {
		return numberOfDivisionWins;
	}
	
	public int getNumberOfDivisionLosses() {
		return numberOfDivisionLosses;
	}
	
	public int getNumberOfDivisionTies() {
		return numberOfDivisionTies;
	}
	
	public void simulateSeason() {
		for (SeasonGame seasonGame : seasonGames) {
			if (seasonGame != null) {
				Team simulatedWinner = seasonGame.getSimulatedWinner();
				if (simulatedWinner == null) {
					seasonGame.simulateGame();
				}
				
				simulatedWinner = seasonGame.getSimulatedWinner();
				
				String teamName = leagueTeam.getName();
				Matchup matchup = seasonGame.getMatchup();
				String opponentName = matchup.getOpponentName(teamName);
				if (leagueTeam.equals(simulatedWinner)) {
					tallyWin(seasonGame, opponentName);
				} else if (simulatedWinner != null) {
					tallyLoss(seasonGame, opponentName);
				}
			}
		}
	}

	public void clearSimulatedGames() {
		List<SeasonGame> seasonGamesCopy = new ArrayList<SeasonGame>();
		for (SeasonGame seasonGame : seasonGames) {
			if (seasonGame != null) {
				if (seasonGame.getSimulatedWinner() != null) {
					seasonGame.clearSimulatedResult();
				}
			}
			seasonGamesCopy.add(seasonGame);
		}
		
		resetSeasonGames();
		
		for (int week = 1; week <= seasonGamesCopy.size(); week++) {
			SeasonGame seasonGame = seasonGamesCopy.get(week - 1);
			if (seasonGame != null) {
				addSeasonGame(week, seasonGame);
			}
		}
	}
	
	private void tallyWin(SeasonGame seasonGame, String opponentName) {
		numberOfWins++;
		winsAgainst.add(opponentName);
		
		if (seasonGame.isConferenceGame()) {
			numberOfConferenceWins++;
		}
		if (seasonGame.isDivisionGame()) {
			numberOfDivisionWins++;
		}
	}

	private void tallyLoss(SeasonGame seasonGame, String opponentName) {
		numberOfLosses++;
		lossesAgainst.add(opponentName);
		
		if (seasonGame.isConferenceGame()) {
			numberOfConferenceLosses++;
		}
		if (seasonGame.isDivisionGame()) {
			numberOfDivisionLosses++;
		}
	}

	private void tallyTie(SeasonGame seasonGame, String opponentName) {
		numberOfTies++;
		tiesAgainst.add(opponentName);
		
		if (seasonGame.isConferenceGame()) {
			numberOfConferenceTies++;
		}
		if (seasonGame.isDivisionGame()) {
			numberOfDivisionTies++;
		}
	}

	private boolean seasonGamesAreEmpty() {
		boolean areEmpty = true;
		
		for (SeasonGame seasonGame : seasonGames) {
			if (seasonGame != null) {
				areEmpty = false;
				break;
			}
		}
		
		return areEmpty;
	}

	private String createScheduleString() {
		StringBuilder scheduleBuilder = new StringBuilder();
		
		int weekNumber = 1;
		for (SeasonGame seasonGame : seasonGames) {
			scheduleBuilder.append("Week " + weekNumber + " ");
			if (seasonGame != null) {
				Matchup matchup = seasonGame.getMatchup();
				String opponentName = matchup.getOpponentName(leagueTeam.getName());
				
				Team homeTeam = seasonGame.getHomeTeam();
				if (homeTeam.equals(leagueTeam)) {
					scheduleBuilder.append("vs. ");
				} else {
					scheduleBuilder.append("at ");
				}
				
				scheduleBuilder.append(opponentName);
				
				NFLSeason.appendGameResultToStringBuilder(scheduleBuilder, seasonGame);
			} else {
				scheduleBuilder.append("Bye");
			}
			scheduleBuilder.append("\n");
			weekNumber++;
		}
		
		return scheduleBuilder.toString();
	}
	
	private void resetSeasonGames() {
		seasonGames = new SeasonGame[NFLSeason.NUMBER_OF_WEEKS_IN_SEASON];
		numberOfWins = 0;
		numberOfLosses = 0;
		numberOfTies = 0;
		numberOfConferenceWins = 0;
		numberOfConferenceLosses = 0;
		numberOfConferenceTies = 0;
		numberOfDivisionWins = 0;
		numberOfDivisionLosses = 0;
		numberOfDivisionTies = 0;
		winsAgainst = new ArrayList<String>();
		lossesAgainst = new ArrayList<String>();
		tiesAgainst = new ArrayList<String>();
	}
	
}
