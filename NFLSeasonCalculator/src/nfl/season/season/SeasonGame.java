package nfl.season.season;

import nfl.season.league.Game;
import nfl.season.league.Team;

public class SeasonGame extends Game {

	private boolean isDivisionGame;
	
	private boolean isConferenceGame;
	
	private boolean alreadyHappened = false;
	
	private Team winner;
	
	private boolean wasATie = false;
	
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

}
