package nfl.season.calculator;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.menu.MainMenu;
import nfl.season.menu.MatchupMenu;
import nfl.season.menu.SingleTeamMenu;
import nfl.season.menu.TeamsMenu;
import nfl.season.menu.MainMenu.MainMenuOptions;
import nfl.season.menu.TeamsMenu.TeamsMenuOptions;

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
		
		SingleTeamMenu singleTeamMenu = new SingleTeamMenu(input, nfl);
		MatchupMenu matchupMenu = new MatchupMenu();
		singleTeamMenu.setSubMenu(matchupMenu, 1);
		
		teamsMenu.setSubMenu(singleTeamMenu, TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		return teamsMenu;
	}
	
}
