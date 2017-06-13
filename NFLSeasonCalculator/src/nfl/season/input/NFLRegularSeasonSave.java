package nfl.season.input;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import nfl.season.league.Team;
import nfl.season.season.NFLSeason;
import nfl.season.season.SeasonGame;
import nfl.season.season.SeasonWeek;

public class NFLRegularSeasonSave {

	public String getGameString(SeasonGame seasonGame) {
		String gameString = "";
		
		Team homeTeam = seasonGame.getHomeTeam();
		Team awayTeam = seasonGame.getAwayTeam();
		String homeTeamName = homeTeam.getName();
		String awayTeamName = awayTeam.getName();
		
		gameString = awayTeamName + " " + homeTeamName;
		
		Team winner = seasonGame.getWinner();
		if (winner != null) {
			String winnerName = winner.getName();
			gameString = gameString + " " + winnerName;
		}
		
		gameString = gameString + ",";
		
		return gameString;
	}

	public String getWeekString(SeasonWeek week) {
		StringBuilder weekBuilder = new StringBuilder();
		
		List<SeasonGame> seasonGames = week.getSeasonGames();
		for (SeasonGame seasonGame : seasonGames) {
			String gameString = getGameString(seasonGame);
			weekBuilder.append(gameString);
		}
		weekBuilder.append("\n");
		
		return weekBuilder.toString();
	}

	public String getSeasonString(NFLSeason season) {
		StringBuilder seasonBuilder = new StringBuilder();
		
		SeasonWeek[] weeks = season.getWeeks();
		for (SeasonWeek week : weeks) {
			String weekString = getWeekString(week);
			seasonBuilder.append(weekString);
		}
		
		return seasonBuilder.toString();
	}

	public boolean saveToSeasonFile(NFLSeason season,
			NFLFileWriterFactory fileWriterFactory) throws IOException {
		boolean success = true;
		
		FileOutputStream fileWriter = null;
		try {
			fileWriter = fileWriterFactory.createNFLSeasonSaveWriter();
			String seasonSaveString = getSeasonString(season);
			fileWriter.write(seasonSaveString.getBytes());
		} catch (IOException e) {
			success = false;
		} finally {
			fileWriter.close();
		}
		
		return success;
	}

}
