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
		
		String matchupLine = "/" + opponentName + "/" + neutralWinChance + "," + 
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
			NFLTeamSettingsFileWriterFactory fileWriterFactory) throws IOException {
		FileOutputStream fileWriter = fileWriterFactory.createNFLTeamSettingsWriter();
		String teamSettingsFileString = createTeamSettingsFileString(league);
		fileWriter.write(teamSettingsFileString.getBytes());
		fileWriter.close();
	}

	public void setTeamSettingsFromTeamLine(Team team, String teamLine) {
		String[] teamLineTokens = teamLine.split(",");
		String newPowerRankingString = teamLineTokens[0];
		String newEloRatingString = teamLineTokens[1];
		String newHomeFieldAdvantageString = teamLineTokens[2];
		
		int newPowerRanking = Integer.parseInt(newPowerRankingString);
		int newEloRating = Integer.parseInt(newEloRatingString);
		int newHomeFieldAdvantage = Integer.parseInt(newHomeFieldAdvantageString);
		
		team.setPowerRanking(newPowerRanking);
		team.setEloRating(newEloRating);
		team.setHomeFieldAdvantage(newHomeFieldAdvantage);
	}

	public void setMatchupSettingsFromMatchupLine(Team team, String matchupLine) {
		String teamName = team.getName();
		
		int firstSlashIndex = matchupLine.indexOf('/');
		int secondSlashIndex = matchupLine.indexOf('/', firstSlashIndex + 1);
		String opponentName = matchupLine.substring(firstSlashIndex + 1, secondSlashIndex);
		
		Matchup matchup = team.getMatchup(opponentName);
		
		String[] matchupTokens = matchupLine.split(",");
		String homeWinChanceString = matchupTokens[2];
		String homeModeCode = matchupTokens[3];
		String awayWinChanceString = matchupTokens[4];
		String awayModeCode = matchupTokens[5];
		
		setNeutralWinChanceFromMatchupLine(teamName, matchup, matchupLine);
		
		setMatchupHomeWinChanceFromMatchupLine(teamName, matchup, homeModeCode, 
				homeWinChanceString);
		setMatchupHomeWinChanceFromMatchupLine(opponentName, matchup, awayModeCode, 
				awayWinChanceString);
	}
	
	public void setAllTeamSettingsFromTeamSection(League league, String teamSection) {
		int indexOfEquals = teamSection.indexOf('=');
		int secondIndexOfEquals = teamSection.lastIndexOf('=');
		String teamName = teamSection.substring(indexOfEquals + 1, secondIndexOfEquals);
		Team team = league.getTeam(teamName);
		
		String[] teamSectionLines = teamSection.split("\n");
		String teamLine = teamSectionLines[1];
		setTeamSettingsFromTeamLine(team, teamLine);
		
		for (int i = 2; i < teamSectionLines.length; i++) {
			String matchupLine = teamSectionLines[i];
			setMatchupSettingsFromMatchupLine(team, matchupLine);
		}
	}
	
	public void setTeamsSettingsFromTeamSettingsFileString(League league,
			String teamSettingsFileString) {
		String[] teamSections = teamSettingsFileString.split("\n=");
		for (String teamSection : teamSections) {
			if (teamSection.charAt(0) != '=') {
				teamSection = "=" + teamSection;
			}
			setAllTeamSettingsFromTeamSection(league, teamSection);
		}
	}

	private void setNeutralWinChanceFromMatchupLine(String teamName,
			Matchup matchup, String matchupLine) {
		String[] matchupTokens = matchupLine.split(",");
		String neutralModeCode = matchupTokens[1];
		
		if (WinChanceModeEnum.POWER_RANKINGS.winChanceModeDescription.charAt(0) == 
				neutralModeCode.charAt(0)) {
			matchup.calculateTeamWinChancesFromPowerRankings();
		} else if (WinChanceModeEnum.ELO_RATINGS.winChanceModeDescription.charAt(0) == 
				neutralModeCode.charAt(0)) {
			matchup.calculateTeamWinChancesFromEloRatings();
		} else if (WinChanceModeEnum.CUSTOM_SETTING.winChanceModeDescription.charAt(0) == 
				neutralModeCode.charAt(0)) {
			int secondSlashIndex = matchupTokens[0].lastIndexOf("/");
			String neutralWinChanceString = matchupTokens[0].substring(secondSlashIndex + 1);
			int neutralWinChance = Integer.parseInt(neutralWinChanceString);
			matchup.setTeamNeutralWinChance(teamName, neutralWinChance);
		}
	}
	
	private void setMatchupHomeWinChanceFromMatchupLine(String homeTeam, Matchup matchup, 
			String homeModeCode, String homeWinChanceString) {
		if (HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE.winChanceModeDescription.charAt(0) == 
				homeModeCode.charAt(0)) {
			matchup.calculateHomeWinChanceFromHomeFieldAdvantage(homeTeam);
		} else if (HomeAwayWinChanceModeEnum.CUSTOM_SETTING.winChanceModeDescription.charAt(0) == 
				homeModeCode.charAt(0)) {
			int homeWinChance = Integer.parseInt(homeWinChanceString);
			matchup.setTeamHomeWinChance(homeTeam, homeWinChance);
		}
	}

}
