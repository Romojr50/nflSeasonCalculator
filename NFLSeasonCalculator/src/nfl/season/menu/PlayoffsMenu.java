package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;
import nfl.season.menu.TeamsMenu.TeamsMenuOptions;
import nfl.season.playoffs.NFLPlayoffs;

public class PlayoffsMenu extends SubMenu {

	public enum PlayoffsMenuOptions implements MenuOptions {
		SELECT_TEAM(1, "Select Teams for Playoffs"), 
		EXIT(2, "Back to Main Menu");
		
		private int optionNumber;
		private String optionDescription;
		
		private PlayoffsMenuOptions(int optionNumber, String optionDescription) {
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
	
	private NFLPlayoffs playoffs;
	
	public PlayoffsMenu(NFLSeasonInput input, NFLPlayoffs playoffs) {
		this.input = input;
		this.playoffs = playoffs;
	}

	@Override
	public void launchSubMenu() {
		String playoffsMenuMessage = MenuOptionsUtil.createMenuMessage(PlayoffsMenuOptions.class);
		
		int selectedOption = -1;
		
		while (selectedOption != PlayoffsMenuOptions.EXIT.optionNumber) {
			selectedOption = input.askForInt(playoffsMenuMessage);
		}
	}
	
}
