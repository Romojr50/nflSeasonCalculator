package nfl.season.season;

import nfl.season.league.Team;

public class NFLSeasonTeam {

	private Team leagueTeam;
	
	private SeasonGame[] seasonGames;
	
	public NFLSeasonTeam(Team leagueTeam) {
		this.leagueTeam = leagueTeam;
		seasonGames = new SeasonGame[NFLSeason.NUMBER_OF_WEEKS_IN_SEASON];
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
		}
	}

}
