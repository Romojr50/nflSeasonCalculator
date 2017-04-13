package nfl.season.calculator;

import java.util.InputMismatchException;

import nfl.season.calculator.MainMenu.MainMenuOptions;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.NFLTeamEnum;

public class TeamsMenu extends SubMenu {

	NFLSeasonInput input;
	
	public enum TeamsMenuOptions implements MenuOptions {
		SELECT_TEAM(1, "Select Team"), 
		SET_ALL_RANKINGS(2, "Set all Team Power Rankings"), 
		EXIT(3, "Back to Main Menu");
		
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
	
	public TeamsMenu(NFLSeasonInput input) {
		this.input = input;
	}
	
	@Override
	public void launchSubMenu() {
		String teamsMenuMessage = MenuOptionsUtil.createMenuMessage(TeamsMenuOptions.class);
		
		int selectedOption = -1;
		while (selectedOption != TeamsMenuOptions.EXIT.optionNumber) {
			try {
				selectedOption = input.askForInt(teamsMenuMessage);
				
				if (TeamsMenuOptions.SELECT_TEAM.optionNumber == selectedOption) {
					String teamListMessage = createTeamListMessage();
					input.askForInt(teamListMessage);
				}
			} catch (InputMismatchException ime) {
				selectedOption = -1;
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
		}
		teamListMessage.append((NFLTeamEnum.values().length + 1) + ". Back to Team Menu");
		return teamListMessage.toString();
	}

}
