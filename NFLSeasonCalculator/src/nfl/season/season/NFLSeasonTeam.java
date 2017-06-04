package nfl.season.season;

import nfl.season.league.Matchup;
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

	public String getScheduleString() {
		String scheduleString = "Team's schedule is empty\n";
		
		if (!seasonGamesAreEmpty()) {
			scheduleString = createScheduleString();
		}
		
		return scheduleString;
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
			} else {
				scheduleBuilder.append("Bye");
			}
			scheduleBuilder.append("\n");
			weekNumber++;
		}
		
		return scheduleBuilder.toString();
	}

}
