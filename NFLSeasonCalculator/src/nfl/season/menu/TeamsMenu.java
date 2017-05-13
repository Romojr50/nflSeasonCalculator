package nfl.season.menu;

import java.io.IOException;
import java.util.List;

import nfl.season.input.NFLSeasonInput;
import nfl.season.input.NFLTeamSettings;
import nfl.season.input.NFLTeamSettingsFileWriterFactory;
import nfl.season.league.League;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;

public class TeamsMenu extends SubMenu {
	
	private static final int EXIT_FROM_TEAM_SELECT = NFLTeamEnum.values().length + 1;

	public enum TeamsMenuOptions implements MenuOptions {
		SELECT_TEAM(1, "Select Team"), 
		SET_ALL_RANKINGS(2, "Set all Team Power Rankings"),
		RESET_TO_DEFAULTS(3, "Revert All Teams and Matchups to Default Settings"),
		SAVE_CURRENT_TEAM_SETTINGS(4, "Save Current Team and Matchup Settings"),
		EXIT(5, "Back to Main Menu");
		
		private int optionNumber;
		private String optionDescription;
		
		private TeamsMenuOptions(int optionNumber, String optionDescription) {
			this.optionNumber = optionNumber;
			this.optionDescription = optionDescription;
		}

		@Override
		public int getOptionNumber() {
			return optionNumber;
		}

		@Override
		public String getOptionDescription() {
			return optionDescription;
		}
	}
	
	private NFLSeasonInput input;
	
	private League nfl;
	
	private NFLTeamSettings nflTeamSettings;
	
	private NFLTeamSettingsFileWriterFactory fileWriterFactory;
	
	private SingleTeamMenu singleTeamMenu;
	
	public TeamsMenu(NFLSeasonInput input, League nfl, NFLTeamSettings nflTeamSettings, 
			NFLTeamSettingsFileWriterFactory fileWriterFactory) {
		this.input = input;
		this.nfl = nfl;
		this.nflTeamSettings = nflTeamSettings;
		this.fileWriterFactory = fileWriterFactory;
		subMenus = new SubMenu[TeamsMenuOptions.values().length - 1];
	}
	
	@Override
	public void launchSubMenu() {
		String teamsMenuMessage = MenuOptionsUtil.createMenuMessage(TeamsMenuOptions.class);
		String saveFilePrefix = "";
		
		int selectedOption = -1;
		while (selectedOption != TeamsMenuOptions.EXIT.optionNumber) {
			teamsMenuMessage = saveFilePrefix + teamsMenuMessage;
			selectedOption = input.askForInt(teamsMenuMessage);
			saveFilePrefix = "";
				
			if (TeamsMenuOptions.SELECT_TEAM.optionNumber == selectedOption) {
				launchTeamSelect();
			} else if (TeamsMenuOptions.SET_ALL_RANKINGS.optionNumber == selectedOption) {
				launchSetAllRankings();
			} else if (TeamsMenuOptions.RESET_TO_DEFAULTS.optionNumber == selectedOption) {
				List<Team> allTeams = nfl.getTeams();
				for (Team team : allTeams) {
					team.resetToDefaults();
				}
			} else if (TeamsMenuOptions.SAVE_CURRENT_TEAM_SETTINGS.optionNumber == 
					selectedOption) {
				try {
					nflTeamSettings.saveToSettingsFile(nfl, fileWriterFactory);
					saveFilePrefix = "Team Settings Saved Successfully\n";
				} catch (IOException e) {
					saveFilePrefix = "Team Settings Save FAILED\n";
				}
			}
		}
	}
	
	public SingleTeamMenu getSingleTeamMenu() {
		return singleTeamMenu;
	}
	
	@Override
	public void setSubMenu(SubMenu subMenu, int optionNumber) {
		super.setSubMenu(subMenu, optionNumber);
		
		if (subMenu instanceof SingleTeamMenu) {
			singleTeamMenu = (SingleTeamMenu) subMenu;
		}
	}
	
	private void launchTeamSelect() {
		String teamListMessage = createTeamListMessage();
		int selectedTeamNumber = -1;
		
		while (selectedTeamNumber != EXIT_FROM_TEAM_SELECT) {
			selectedTeamNumber = input.askForInt(teamListMessage);
				
			Team selectedTeam = nfl.getTeam(selectedTeamNumber);
				
			if (selectedTeam != null) {
				SingleTeamMenu singleTeamMenu = getSingleTeamMenu();
				singleTeamMenu.setTeam(selectedTeam);
				singleTeamMenu.launchSubMenu();
			}
		}
	}
	
	private void launchSetAllRankings() {
		String confirmationMessage = "All rankings will be cleared. Proceed? (Y/N)";
		
		String confirmationAnswer = "";
		
		while (isNotYesOrNoIndicator(confirmationAnswer)) {
			confirmationAnswer = input.askForString(confirmationMessage);
		}
		
		if ("Y".equalsIgnoreCase(confirmationAnswer)) {
			int selectedTeamNumber = -1;
			
			int currentRanking = 1;
			List<Team> remainingTeams = nfl.getTeams();
			
			while (selectedTeamNumber != EXIT_FROM_TEAM_SELECT && remainingTeams.size() > 0) {
				String setAllRankingsPrefix = "Set #" + currentRanking + "\n";
				String teamListMessage = setAllRankingsPrefix + createTeamListMessageFromList(remainingTeams);
				selectedTeamNumber = input.askForInt(teamListMessage);
				
				if (selectedTeamNumber <= remainingTeams.size()) {
					if (currentRanking == 1) {
						for (Team team : remainingTeams) {
							team.setPowerRanking(Team.CLEAR_RANKING);
						}
					}
					Team selectedTeam = remainingTeams.remove(selectedTeamNumber - 1);
					selectedTeam.setPowerRanking(currentRanking);
					currentRanking++;
				}
			}
		}
		
	}

	private String createTeamListMessage() {
		StringBuilder teamListMessage = new StringBuilder();
		teamListMessage.append(MenuOptionsUtil.MENU_INTRO);
		int teamIndex = 1;
		for (NFLTeamEnum nflTeam : NFLTeamEnum.values()) {
			teamListMessage.append(teamIndex + ". ");
			teamListMessage.append(nflTeam.getTeamName());
			teamListMessage.append("\n");
			teamIndex++;
		}
		teamListMessage.append(EXIT_FROM_TEAM_SELECT + ". Back to Team Menu");
		return teamListMessage.toString();
	}
	
	private String createTeamListMessageFromList(List<Team> teams) {
		StringBuilder teamListMessage = new StringBuilder();
		teamListMessage.append(MenuOptionsUtil.MENU_INTRO);
		int teamIndex = 1;
		for (Team team : teams) {
			teamListMessage.append(teamIndex + ". ");
			teamListMessage.append(team.getName());
			teamListMessage.append("\n");
			teamIndex++;
		}
		teamListMessage.append(EXIT_FROM_TEAM_SELECT + ". Back to Team Menu");
		return teamListMessage.toString();
	}

}
