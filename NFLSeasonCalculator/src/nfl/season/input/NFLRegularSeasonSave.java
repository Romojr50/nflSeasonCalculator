package nfl.season.input;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import nfl.season.league.League;
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
		
		weekBuilder.append(week.getWeekNumber());
		weekBuilder.append(",");
		
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

	public SeasonGame createGameFromGameString(String gameString, League league) {
		SeasonGame seasonGame = null;
		
		if (gameString != null) {
			gameString = gameString.replace(",", "");
			
			String[] gameTokens = gameString.split(" ");
			String homeTeamName = gameTokens[1];
			String awayTeamName = gameTokens[0];
			
			String winnerName = null;
			if (gameTokens.length > 2) {
				winnerName = gameTokens[2];
			}
			
			Team homeTeam = league.getTeam(homeTeamName);
			Team awayTeam = league.getTeam(awayTeamName);
			Team winner = league.getTeam(winnerName);
			
			seasonGame = new SeasonGame(homeTeam, awayTeam);
			if (winner != null) {
				seasonGame.setWinner(winner);
			}
		}
		
		return seasonGame;
	}

	public SeasonWeek createWeekFromWeekString(String weekString,
			League league) {
		SeasonWeek week = null;
		
		if (weekString != null) {
			weekString = weekString.replace("\n", "");
			
			String[] gameStrings = weekString.split(",");
			week = new SeasonWeek(Integer.parseInt(gameStrings[0]));
			for (int i = 1; i < gameStrings.length; i++) {
				String gameString = gameStrings[i];
				SeasonGame game = createGameFromGameString(gameString, league);
				week.addSeasonGame(game);
			}
		}
		
		return week;
	}

	public void populateSeasonFromSeasonString(String seasonString,
			NFLSeason season) {
		League league = season.getLeague();
		String[] weekStrings = seasonString.split("\n");
		for (String weekString : weekStrings) {
			SeasonWeek week = createWeekFromWeekString(weekString, league);
			season.addWeek(week);
		}
	}

	public String loadSeasonSave(NFLFileReaderFactory fileReaderFactory) throws IOException {
		BufferedReader fileReader = fileReaderFactory.createNFLSeasonSaveReader();
		
		StringBuilder nflTeamSettingsBuilder = new StringBuilder();
		
		String line;
		try {
			line = fileReader.readLine();
			while (line != null) {
				nflTeamSettingsBuilder.append(line);
				nflTeamSettingsBuilder.append("\n");
				line = fileReader.readLine();
			}
		} finally {
			fileReader.close();
		}
		
		
		return nflTeamSettingsBuilder.toString();
	}

}
