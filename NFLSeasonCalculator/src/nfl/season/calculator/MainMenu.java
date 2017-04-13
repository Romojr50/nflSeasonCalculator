package nfl.season.calculator;

import java.util.InputMismatchException;

import nfl.season.input.NFLSeasonInput;

public class MainMenu {

	public enum MainMenuOptions implements MenuOptions {
		TEAMS(1, "Edit Team Settings"), EXIT(2, "Exit");
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
			try {
				selectedOption = input.askForInt(mainMenuMessage);
				
				if (MainMenuOptions.EXIT.optionNumber > selectedOption) {
					SubMenu selectedSubMenu = subMenus[selectedOption - 1];
					selectedSubMenu.launchSubMenu();
				}
			} catch (InputMismatchException ime) {
				selectedOption = -1;
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
