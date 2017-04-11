package nfl.season.calculator;

import nfl.season.input.NFLSeasonInput;

public class NFLSeasonCalculator {

	public static void main(String[] args) {
		NFLSeasonInput input = new NFLSeasonInput(System.in, System.out);
		MainMenu mainMenu = new MainMenu(input);
		
		mainMenu.launchMainMenu();
	}

}
