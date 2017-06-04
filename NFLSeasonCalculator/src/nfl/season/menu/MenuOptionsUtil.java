package nfl.season.menu;

import nfl.season.league.NFLTeamEnum;

public class MenuOptionsUtil {

	public static final String MENU_INTRO = 
			"Please enter in an integer corresponding to one of the following:\n";
	
	public static final int EXIT_FROM_TEAM_SELECT = NFLTeamEnum.values().length + 1;
	
	public static <T extends MenuOptions> String createMenuMessage(Class<T> optionsEnum) {
		StringBuilder menuMessage = new StringBuilder();
		menuMessage.append(MENU_INTRO);
		for (T option : optionsEnum.getEnumConstants()) {
			menuMessage.append(option.getOptionNumber() + ". " 
					+ option.getOptionDescription() + "\n");
		}
			menuMessage.setLength(menuMessage.length() - 1);
		return menuMessage.toString();
	}
	
	public static String buildTeamListMessage() {
		StringBuilder teamListMessage = new StringBuilder();
		teamListMessage.append(MenuOptionsUtil.MENU_INTRO);
		int teamIndex = 1;
		for (NFLTeamEnum nflTeam : NFLTeamEnum.values()) {
			teamListMessage.append(teamIndex + ". ");
			teamListMessage.append(nflTeam.getTeamName());
			teamListMessage.append("\n");
			teamIndex++;
		}
		teamListMessage.append(EXIT_FROM_TEAM_SELECT + ". Back to Team Menu");
		return teamListMessage.toString();
	}
	
}
