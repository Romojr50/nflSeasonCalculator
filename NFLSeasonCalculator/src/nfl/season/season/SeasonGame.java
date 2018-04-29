package nfl.season.season;

import java.io.Serializable;
import java.util.Random;

import nfl.season.league.Game;
import nfl.season.league.Matchup;
import nfl.season.league.Team;

public class SeasonGame extends Game implements Serializable {

	private static final long serialVersionUID = 5731511507544303733L;

	private boolean isDivisionGame;
	
	private boolean isConferenceGame;
	
	private boolean alreadyHappened = false;
	
	private Team winner;
	
	private boolean wasATie = false;
	
	private int homeScore = 0;
	
	private int awayScore = 0;
	
	private Team simulatedWinner;
	
	public SeasonGame(Team homeTeam, Team awayTeam) {
		super(homeTeam, awayTeam);
		winner = null;
	}

	public boolean isDivisionGame() {
		return isDivisionGame;
	}
	
	public void setIsDivisionGame(boolean isDivisionGame) {
		this.isDivisionGame = isDivisionGame;
	}
	
	public boolean isConferenceGame() {
		return isConferenceGame;
	}
	
	public void setIsConferenceGame(boolean isConferenceGame) {
		this.isConferenceGame = isConferenceGame;
	}

	public boolean alreadyHappened() {
		return alreadyHappened;
	}

	public Team getWinner() {
		return winner;
	}

	public boolean wonTheGame(Team team) {
		boolean wonTheGame = false;
		
		if (winner != null && winner.equals(team)) {
			wonTheGame = true;
		}
		
		return wonTheGame;
	}

	public void setWinner(Team winner) {
		this.winner = winner;
		alreadyHappened = true;
		wasATie = false;
	}

	public boolean wasATie() {
		return wasATie;
	}

	public void setToTie() {
		alreadyHappened = true;
		this.winner = null;
		wasATie = true;
	}
	
	public int getHomeScore() {
		return homeScore;
	}
	
	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getAwayScore() {
		return awayScore;
	}
	
	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}

	public int getWinningScore() {
		int winningScore = homeScore;
		if (awayScore > homeScore) {
			winningScore = awayScore;
		}
		return winningScore;
	}

	public int getLosingScore() {
		int losingScore = homeScore;
		if (awayScore < homeScore) {
			losingScore = awayScore;
		}
		return losingScore;
	}

	public void simulateGame() {
		if (!alreadyHappened) {
			Matchup matchup = getMatchup();
			Team homeTeam = getHomeTeam();
			Team awayTeam = getAwayTeam();
			int awayTeamWinChance = matchup.getTeamAwayWinChance(awayTeam.getName());
			
			Random random = new Random();
			int randomInt = random.nextInt(100) + 1;
			if (randomInt <= awayTeamWinChance) {
				simulatedWinner = awayTeam;
			} else {
				simulatedWinner = homeTeam;
			}
		}
	}

	public Team getSimulatedWinner() {
		return simulatedWinner;
	}

	public void clearSimulatedResult() {
		simulatedWinner = null;
	}


}
