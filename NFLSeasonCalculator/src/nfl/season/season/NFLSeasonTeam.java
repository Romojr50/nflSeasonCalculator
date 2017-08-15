package nfl.season.season;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Matchup;
import nfl.season.league.Team;

public class NFLSeasonTeam implements Serializable {

	private static final long serialVersionUID = 4325864372064659710L;

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
	
	private int gotOneSeed;
	
	private int gotRoundOneBye;
	
	private int wonDivision;
	
	private int madePlayoffs;
	
	private int hadWinningSeason;
	
	private int wasInDivisionCellar;
	
	private int wasBottomTeam;
	
	private int chanceToWinSuperBowl;
	
	private int chanceToWinConference;
	
	private int chanceToMakeConferenceRound;
	
	private int chanceToMakeDivisionalRound;
	
	private int simulatedWins;
	
	private int simulatedLosses;
	
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
		clearSimulatedGames();
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
	
	public void addGotOneSeed() {
		gotOneSeed++;
	}
	
	public int getGotOneSeed() {
		return gotOneSeed;
	}

	public void addGotRoundOneBye() {
		gotRoundOneBye++;
	}
	
	public int getGotRoundOneBye() {
		return gotRoundOneBye;
	}

	public void addWonDivision() {
		wonDivision++;
	}
	
	public int getWonDivision() {
		return wonDivision;
	}

	public void addMadePlayoffs() {
		madePlayoffs++;
	}
	
	public int getMadePlayoffs() {
		return madePlayoffs;
	}

	public void addHadWinningSeason() {
		hadWinningSeason++;
	}
	
	public int getHadWinningSeason() {
		return hadWinningSeason;
	}
	
	public void addWasInDivisionCellar() {
		wasInDivisionCellar++;
	}
	
	public int getWasInDivisionCellar() {
		return wasInDivisionCellar;
	}

	public void addWasBottomTeam() {
		wasBottomTeam++;
	}
	
	public int getWasBottomTeam() {
		return wasBottomTeam;
	}
	
	public void addToChanceToWinSuperBowl(int chanceToWinSuperBowl) {
		this.chanceToWinSuperBowl += chanceToWinSuperBowl;
	}
	
	public int getChanceToWinSuperBowl() {
		return chanceToWinSuperBowl;
	}

	public void addToChanceToWinConference(int chanceToWinConference) {
		this.chanceToWinConference += chanceToWinConference;
	}
	
	public int getChanceToWinConference() {
		return chanceToWinConference;
	}

	public void addToChanceToMakeConferenceRound(int chanceToMakeConferenceRound) {
		this.chanceToMakeConferenceRound += chanceToMakeConferenceRound;
	}
	
	public int getChanceToMakeConferenceRound() {
		return chanceToMakeConferenceRound;
	}

	public void addToChanceToMakeDivisionalRound(int chanceToMakeDivisionalRound) {
		this.chanceToMakeDivisionalRound += chanceToMakeDivisionalRound;
	}
	
	public int getChanceToMakeDivisionalRound() {
		return chanceToMakeDivisionalRound;
	}

	public void addSimulatedWins(int simulatedWins) {
		this.simulatedWins += simulatedWins;
	}

	public int getSimulatedWins() {
		return simulatedWins;
	}

	public void addSimulatedLosses(int simulatedLosses) {
		this.simulatedLosses += simulatedLosses;
	}
	
	public int getSimulatedLosses() {
		return simulatedLosses;
	}

	public void clearSimulatedResults() {
		gotOneSeed = 0;
		gotRoundOneBye = 0;
		wonDivision = 0;
		madePlayoffs = 0;
		hadWinningSeason = 0;
		wasInDivisionCellar = 0;
		wasBottomTeam = 0;
		chanceToWinSuperBowl = 0;
		chanceToWinConference = 0;
		chanceToMakeConferenceRound = 0;
		chanceToMakeDivisionalRound = 0;
		simulatedWins = 0;
		simulatedLosses = 0;
	}

	public String getSimulatedResults(int numberOfSimulatedSeasons) {
		String simulatedResults = "";
		
		if (gotOneSeed > 0 || gotRoundOneBye > 0 || wonDivision > 0 || madePlayoffs > 0 || hadWinningSeason > 0 || wasInDivisionCellar > 0 || wasBottomTeam > 0) {
			double numberOfSimulatedSeasonsDouble = numberOfSimulatedSeasons * 1.0;
			
			int averageWins = (int) Math.round(simulatedWins / numberOfSimulatedSeasonsDouble);
			int averageLosses = (int) Math.round(simulatedLosses / numberOfSimulatedSeasonsDouble);
			int chanceToGetOneSeed = (int) Math.round(gotOneSeed / numberOfSimulatedSeasonsDouble * 100.0);
			int chanceToGetRoundOneBye = (int) Math.round(gotRoundOneBye / numberOfSimulatedSeasonsDouble * 100.0);
			int chanceToWinDivision = (int) Math.round(wonDivision / numberOfSimulatedSeasonsDouble * 100.0);
			int chanceToMakePlayoffs = (int) Math.round(madePlayoffs / numberOfSimulatedSeasonsDouble * 100.0);
			int chanceToHaveWinningSeason = (int) Math.round(hadWinningSeason / numberOfSimulatedSeasonsDouble * 100.0);
			int chanceToBeInCellar = (int) Math.round(wasInDivisionCellar / numberOfSimulatedSeasonsDouble * 100.0);
			int chanceToBeBottomTeam = (int) Math.round(wasBottomTeam / numberOfSimulatedSeasonsDouble * 100.0);
			int chanceToWinSuperBowlPercent = (int) Math.round(chanceToWinSuperBowl / numberOfSimulatedSeasonsDouble);
			int chanceToWinConferencePercent = (int) Math.round(chanceToWinConference / numberOfSimulatedSeasonsDouble);
			int chanceToMakeConferenceRoundPercent = (int) Math.round(chanceToMakeConferenceRound / numberOfSimulatedSeasonsDouble);
			int chanceToMakeDivisionalRoundPercent = (int) Math.round(chanceToMakeDivisionalRound / numberOfSimulatedSeasonsDouble);
			
			StringBuilder resultsBuilder = new StringBuilder();
			resultsBuilder.append("Average Wins: ");
			resultsBuilder.append(averageWins + "\n");
			resultsBuilder.append("Average Losses: ");
			resultsBuilder.append(averageLosses + "\n");
			resultsBuilder.append("Percent Chance to...\n");
			resultsBuilder.append("Win Super Bowl: " + chanceToWinSuperBowlPercent + "\n");
			resultsBuilder.append("Win Conference: " + chanceToWinConferencePercent + "\n");
			resultsBuilder.append("Get to Conference Round: " + chanceToMakeConferenceRoundPercent + "\n");
			resultsBuilder.append("Get to Divisional Round: " + chanceToMakeDivisionalRoundPercent + "\n");
			resultsBuilder.append("Win One Seed: " + chanceToGetOneSeed + "\n");
			resultsBuilder.append("Get Round One Bye: " + chanceToGetRoundOneBye + "\n");
			resultsBuilder.append("Win Division: " + chanceToWinDivision + "\n");
			resultsBuilder.append("Make Playoffs: " + chanceToMakePlayoffs + "\n");
			resultsBuilder.append("Have Winning Season: " + chanceToHaveWinningSeason + "\n");
			resultsBuilder.append("Be in Division Cellar: " + chanceToBeInCellar + "\n");
			resultsBuilder.append("Be in League Bottom 5: " + chanceToBeBottomTeam + "\n");
			
			simulatedResults = resultsBuilder.toString();
			simulatedResults = simulatedResults.replace(" 0", " < 1");
		} else {
			simulatedResults = "No simulations done yet; please run the Many Seasons Simulation\n";
		}
		
		return simulatedResults;
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
