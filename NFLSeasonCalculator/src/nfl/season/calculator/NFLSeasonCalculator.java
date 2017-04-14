package nfl.season.calculator;

import nfl.season.calculator.MainMenu.MainMenuOptions;
import nfl.season.calculator.TeamsMenu.TeamsMenuOptions;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;

public class NFLSeasonCalculator {

	public static void main(String[] args) {
		NFLSeasonInput input = new NFLSeasonInput(System.in, System.out);
		League nfl = new League("NFL");
		nfl.initializeNFL();
		
		MainMenu mainMenu = createMainMenu(input, nfl);
		
		mainMenu.launchMainMenu();
	}

	public static MainMenu createMainMenu(NFLSeasonInput input, League nfl) {
		MainMenu mainMenu = new MainMenu(input);
		TeamsMenu teamsMenu = createTeamsMenu(input, nfl);
		mainMenu.setSubMenu(teamsMenu, MainMenuOptions.TEAMS.getOptionNumber());
		
		return mainMenu;
	}

	public static TeamsMenu createTeamsMenu(NFLSeasonInput input, League nfl) {
		TeamsMenu teamsMenu = new TeamsMenu(input, nfl);
		SingleTeamMenu singleTeamMenu = new SingleTeamMenu();
		teamsMenu.setSubMenu(singleTeamMenu, TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		return teamsMenu;
	}
	
}
