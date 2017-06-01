package nfl.season.calculator;

import java.io.IOException;

import nfl.season.input.NFLPlayoffSettings;
import nfl.season.input.NFLSeasonInput;
import nfl.season.input.NFLTeamSettings;
import nfl.season.input.NFLFileReaderFactory;
import nfl.season.input.NFLFileWriterFactory;
import nfl.season.league.League;
import nfl.season.menu.MainMenu;
import nfl.season.menu.MainMenu.MainMenuOptions;
import nfl.season.menu.MatchupMenu;
import nfl.season.menu.PlayoffsMenu;
import nfl.season.menu.SingleTeamMenu;
import nfl.season.menu.TeamsMenu;
import nfl.season.menu.TeamsMenu.TeamsMenuOptions;
import nfl.season.playoffs.NFLPlayoffs;

public class NFLSeasonCalculator {

	public static void main(String[] args) {
		NFLSeasonInput input = new NFLSeasonInput(System.in, System.out);
		League nfl = new League("NFL");
		nfl.initializeNFL();
		
		MainMenu mainMenu = createMainMenu(input, nfl);
		
		loadTeamSettings(nfl);
		
		mainMenu.launchMainMenu();
	}

	public static MainMenu createMainMenu(NFLSeasonInput input, League nfl) {
		MainMenu mainMenu = new MainMenu(input);
		
		TeamsMenu teamsMenu = createTeamsMenu(input, nfl);
		mainMenu.setSubMenu(teamsMenu, MainMenuOptions.TEAMS.getOptionNumber());
		
		PlayoffsMenu playoffsMenu = createPlayoffsMenu(input, nfl);
		mainMenu.setSubMenu(playoffsMenu, MainMenuOptions.PLAYOFFS.getOptionNumber());
		
		return mainMenu;
	}

	public static TeamsMenu createTeamsMenu(NFLSeasonInput input, League nfl) {
		NFLTeamSettings nflTeamSettings = new NFLTeamSettings();
		NFLFileWriterFactory teamSettingsFileWriterFactory = 
				new NFLFileWriterFactory();
		NFLFileReaderFactory teamSettingsFileReaderFactory = 
				new NFLFileReaderFactory();
		TeamsMenu teamsMenu = new TeamsMenu(input, nfl, nflTeamSettings, 
				teamSettingsFileWriterFactory, teamSettingsFileReaderFactory);
		
		SingleTeamMenu singleTeamMenu = new SingleTeamMenu(input, nfl);
		MatchupMenu matchupMenu = new MatchupMenu(input);
		singleTeamMenu.setSubMenu(matchupMenu, 1);
		
		teamsMenu.setSubMenu(singleTeamMenu, TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		
		return teamsMenu;
	}
	
	public static PlayoffsMenu createPlayoffsMenu(NFLSeasonInput input,
			League nfl) {
		NFLPlayoffSettings playoffSettings = new NFLPlayoffSettings();
		NFLFileWriterFactory fileWriterFactory = 
				new NFLFileWriterFactory();
		NFLFileReaderFactory fileReaderFactory = 
				new NFLFileReaderFactory();
		NFLPlayoffs playoffs = new NFLPlayoffs(nfl);
		playoffs.initializeNFLPlayoffs();
		loadPlayoffSettings(playoffs);
		PlayoffsMenu playoffsMenu = new PlayoffsMenu(input, playoffs, playoffSettings, 
				fileWriterFactory, fileReaderFactory);
		
		return playoffsMenu;
	}
	
	public static void loadTeamSettings(League nfl) {
		NFLTeamSettings nflTeamSettings = new NFLTeamSettings();
		NFLFileReaderFactory teamSettingsFileReaderFactory = 
				new NFLFileReaderFactory();
		
		try {
			String loadSettingsFileString = nflTeamSettings.loadSettingsFile(
					teamSettingsFileReaderFactory);
			if (loadSettingsFileString != null && !"".equals(loadSettingsFileString)) {
				nflTeamSettings.setTeamsSettingsFromTeamSettingsFileString(nfl, 
						loadSettingsFileString);
			}
		} catch (IOException e) {
		}
	}
	
	public static void loadPlayoffSettings(NFLPlayoffs playoffs) {
		NFLPlayoffSettings playoffSettings = new NFLPlayoffSettings();
		NFLFileReaderFactory fileReaderFactory = new NFLFileReaderFactory();
		
		League nfl = playoffs.getLeague();
		try {
			String playoffSettingsFileString = playoffSettings.loadSettingsFile(
					fileReaderFactory);
			if (playoffSettingsFileString != null && !"".equals(playoffSettingsFileString)) {
				playoffSettings.loadPlayoffSettingsString(playoffs, nfl, 
						playoffSettingsFileString);
			}
		} catch (IOException e) {
		}
	}
	
}
