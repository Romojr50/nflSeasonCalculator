package nfl.season.input;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import nfl.season.league.League;
import nfl.season.league.Matchup;
import nfl.season.league.Matchup.HomeAwayWinChanceModeEnum;
import nfl.season.league.Matchup.WinChanceModeEnum;
import nfl.season.league.Team;

public class NFLTeamSettings {

	public String createTeamLine(Team team) {
		int powerRanking = team.getPowerRanking();
		int eloRating = team.getEloRating();
		int homeFieldAdvantage = team.getHomeFieldAdvantage();
		
		String teamLine = powerRanking + "," + eloRating + "," + homeFieldAdvantage;
		return teamLine;
	}

	public String createMatchupLine(String teamName, Matchup matchup) {
		int neutralWinChance = matchup.getTeamNeutralWinChance(teamName);
		WinChanceModeEnum neutralWinChanceMode = matchup.getWinChanceMode();
		int homeWinChance = matchup.getTeamHomeWinChance(teamName);
		HomeAwayWinChanceModeEnum homeWinChanceMode = 
				matchup.getHomeAwayWinChanceMode(teamName);
		int awayWinChance = matchup.getTeamAwayWinChance(teamName);
		
		String opponentName = matchup.getOpponentName(teamName);
		HomeAwayWinChanceModeEnum awayWinChanceMode = 
				matchup.getHomeAwayWinChanceMode(opponentName);
		
		String matchupLine = "-" + opponentName + "-" + neutralWinChance + "," + 
				neutralWinChanceMode.winChanceModeDescription.charAt(0) + "," + 
				homeWinChance + "," + homeWinChanceMode.winChanceModeDescription.charAt(0) + 
				"," + awayWinChance + "," + 
				awayWinChanceMode.winChanceModeDescription.charAt(0);
		return matchupLine;
	}

	public String createTeamSection(Team team) {
		String teamName = team.getName();
		
		String teamSection = "=" + teamName + "=\n" + createTeamLine(team) + "\n";
		
		List<Matchup> matchups = team.getMatchups();
		for (Matchup matchup : matchups) {
			teamSection = teamSection + createMatchupLine(teamName, matchup) + "\n";
		}
		
		return teamSection;
	}

	public String createTeamSettingsFileString(League league) {
		StringBuilder teamSettingsFileBuilder = new StringBuilder();
		List<Team> teamList = league.getTeams();
		for (Team team : teamList) {
			teamSettingsFileBuilder.append(createTeamSection(team));
		}
		return teamSettingsFileBuilder.toString();
	}

	public void saveToSettingsFile(League league, 
			NFLTeamSettingsFileWriterFactory fileWriterFactory) {
		try {
			FileOutputStream fileWriter = fileWriterFactory.createNFLTeamSettingsWriter();
			String teamSettingsFileString = createTeamSettingsFileString(league);
			fileWriter.write(teamSettingsFileString.getBytes());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
