package nfl.season.calculator;

import java.util.InputMismatchException;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;

public class TeamsMenu extends SubMenu {
	
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
	
	private NFLSeasonInput input;
	
	private League nfl;
	
	private SingleTeamMenu singleTeamMenu;
	
	public TeamsMenu(NFLSeasonInput input, League nfl) {
		this.input = input;
		this.nfl = nfl;
		subMenus = new SubMenu[TeamsMenuOptions.values().length - 1];
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
					int selectedTeamNumber = -1;
					
					while (selectedTeamNumber <= NFLTeamEnum.values().length) {
						selectedTeamNumber = input.askForInt(teamListMessage);
						
						Team selectedTeam = nfl.getTeam(selectedTeamNumber);
						
						if (selectedTeam != null) {
							SingleTeamMenu singleTeamMenu = getSingleTeamMenu();
							singleTeamMenu.setTeam(selectedTeam);
							singleTeamMenu.launchSubMenu();
						}
					}
				}
			} catch (InputMismatchException ime) {
				selectedOption = -1;
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
