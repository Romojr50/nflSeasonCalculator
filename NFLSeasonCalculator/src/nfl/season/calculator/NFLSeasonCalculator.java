package nfl.season.calculator;

import nfl.season.calculator.MainMenu.MainMenuOptions;
import nfl.season.input.NFLSeasonInput;

public class NFLSeasonCalculator {

	public static void main(String[] args) {
		
		MainMenu mainMenu = createMainMenu();
		
		mainMenu.launchMainMenu();
	}

	public static MainMenu createMainMenu() {
		NFLSeasonInput input = new NFLSeasonInput(System.in, System.out);
		
		MainMenu mainMenu = new MainMenu(input);
		TeamsMenu teamsMenu = new TeamsMenu();
		mainMenu.setSubMenu(teamsMenu, MainMenuOptions.TEAMS.getOptionNumber());
		
		return mainMenu;
	}
	
}
