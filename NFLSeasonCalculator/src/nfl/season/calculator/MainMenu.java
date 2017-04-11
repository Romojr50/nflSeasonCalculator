package nfl.season.calculator;

import nfl.season.input.NFLSeasonInput;

public class MainMenu {

private static final String MAIN_MENU = "Select tab:";
	
	public enum MainMenuOptions {
		TEAMS(1, "Edit Team Settings"), EXIT(2, "Exit");
		private int optionNumber;
		private String optionDescription;
		
		private MainMenuOptions(int optionNumber, String optionDescription) {
			this.optionNumber = optionNumber;
			this.optionDescription = optionDescription;
		}
	}
	
	private NFLSeasonInput input;
	
	public MainMenu(NFLSeasonInput input) {
		this.input = input;
	}

	public void launchMainMenu() {
		String mainMenuMessage = createMainMenuMessage();
		
		int selectedOption = -1;
		while (selectedOption != MainMenuOptions.EXIT.optionNumber) {
			selectedOption = input.askForInt(mainMenuMessage);
			
			if (MainMenuOptions.TEAMS.optionNumber == selectedOption) {
				
			}
		}
		
	}

	private String createMainMenuMessage() {
		StringBuilder mainMenuMessage = new StringBuilder();
		for (MainMenuOptions option : MainMenuOptions.values()) {
			mainMenuMessage.append(option.optionNumber + ". " 
					+ option.optionDescription + "\n");
		}
		mainMenuMessage.setLength(mainMenuMessage.length() - 1);
		return mainMenuMessage.toString();
	}
	
}
