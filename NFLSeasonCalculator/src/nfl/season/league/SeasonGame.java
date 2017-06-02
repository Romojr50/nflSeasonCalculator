package nfl.season.league;

public class SeasonGame extends Game {

	private boolean isDivisionGame;
	
	private boolean isConferenceGame;
	
	public SeasonGame(Team homeTeam, Team awayTeam) {
		super(homeTeam, awayTeam);
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

}