package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;

public class MainMenu {

	public enum MainMenuOptions implements MenuOptions {
		TEAMS(1, "Edit Team Settings"),
		SEASON(2, "Go to Season Menu"),
		PLAYOFFS(3, "Go to Playoffs Menu"),
		EXIT(4, "Exit");
		private int optionNumber;
		private String optionDescription;
		
		private MainMenuOptions(int optionNumber, String optionDescription) {
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
	
	private SubMenu[] subMenus;
	
	public MainMenu(NFLSeasonInput input) {
		this.input = input;
		subMenus = new SubMenu[MainMenuOptions.values().length - 1];
	}

	public void launchMainMenu() {
		String mainMenuMessage = MenuOptionsUtil.createMenuMessage(MainMenuOptions.class);
		
		int selectedOption = -1;
		while (selectedOption != MainMenuOptions.EXIT.optionNumber) {
			selectedOption = input.askForInt(mainMenuMessage);
			
			if (MainMenuOptions.EXIT.optionNumber > selectedOption) {
				SubMenu selectedSubMenu = subMenus[selectedOption - 1];
				selectedSubMenu.launchSubMenu();
			}
		}
		
	}
	
	public Object getSubMenu(int optionNumber) {
		return subMenus[optionNumber - 1];
	}
	
	public void setSubMenu(SubMenu subMenu, int optionNumber) {
		subMenus[optionNumber - 1] = subMenu;
	}

}
