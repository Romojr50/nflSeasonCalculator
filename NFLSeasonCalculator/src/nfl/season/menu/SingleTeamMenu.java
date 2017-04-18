package nfl.season.menu;

import java.util.InputMismatchException;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;

public class SingleTeamMenu extends SubMenu {

	public enum SingleTeamMenuOptions implements MenuOptions {
		SET_POWER_RANKING(1, "Set Power Ranking"), 
		EXIT(2, "Back to Teams Menu");
		
		private int optionNumber;
		private String optionDescription;
		
		private SingleTeamMenuOptions(int optionNumber, String optionDescription) {
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
	
	private static final String POWER_RANKING_MESSAGE = 
			"Please enter in a number between 1-32:";
	
	private Team selectedTeam;
	
	private NFLSeasonInput input;
	
	public SingleTeamMenu(NFLSeasonInput input) {
		this.input = input;
	}
	
	@Override
	public void launchSubMenu() {
		String singleTeamMenuMessage = MenuOptionsUtil.createMenuMessage(
				SingleTeamMenuOptions.class);
		
		int selectedOption = -1;
		while (selectedOption != SingleTeamMenuOptions.EXIT.optionNumber) {
			try {
				selectedOption = input.askForInt(singleTeamMenuMessage);
				
				if (SingleTeamMenuOptions.SET_POWER_RANKING.optionNumber == selectedOption) {
					int newPowerRanking = -1;
					while (newPowerRanking < 1 || 
							newPowerRanking > NFLTeamEnum.values().length) {
						try {
							newPowerRanking = input.askForInt(POWER_RANKING_MESSAGE);
							selectedTeam.setPowerRanking(newPowerRanking);
						} catch (InputMismatchException ime) {
							newPowerRanking = -1;
						}
					}
				}
			} catch (InputMismatchException ime) {
				selectedOption = -1;
			}
		}
	}

	public void setTeam(Team team) {
		this.selectedTeam = team;
	}

}
