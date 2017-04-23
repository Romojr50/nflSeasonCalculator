package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
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
	
	private League league;
	
	public SingleTeamMenu(NFLSeasonInput input, League league) {
		this.input = input;
		this.league = league;
	}
	
	@Override
	public void launchSubMenu() {
		String singleTeamMenuMessage = MenuOptionsUtil.createMenuMessage(
				SingleTeamMenuOptions.class);
		
		int selectedOption = -1;
		while (selectedOption != SingleTeamMenuOptions.EXIT.optionNumber) {
			selectedOption = input.askForInt(singleTeamMenuMessage);
				
			if (SingleTeamMenuOptions.SET_POWER_RANKING.optionNumber == selectedOption) {
				launchSetPowerRankingMenu();
			}
		}
	}
	
	public void setTeam(Team team) {
		this.selectedTeam = team;
	}

	private void launchSetPowerRankingMenu() {
		int newPowerRanking = Team.CLEAR_RANKING;
		while (newPowerRanking < 1 || 
			newPowerRanking > NFLTeamEnum.values().length) {
			newPowerRanking = input.askForInt(POWER_RANKING_MESSAGE);
			
			Team teamWithThatRanking = league.getTeamWithPowerRanking(newPowerRanking);
			
			if (teamWithThatRanking == null) {
				selectedTeam.setPowerRanking(newPowerRanking);
			} else {
				newPowerRanking = launchRankingOverwriteMenu(newPowerRanking, 
						teamWithThatRanking);
			}
		}
	}

	private int launchRankingOverwriteMenu(int newPowerRanking,
			Team teamWithThatRanking) {
		String overwriteAnswer = "";
		
		String teamWithThatRankingName = teamWithThatRanking.getName();
		String overwriteMessage = "The " + teamWithThatRankingName + 
				" already are #" + newPowerRanking + ". Clear the " + 
				teamWithThatRankingName + " ranking and assign #" + 
				newPowerRanking + " to " + selectedTeam.getName() + 
				"? (Y/N)";
		
		while (isNotYesOrNoIndicator(overwriteAnswer)) {
			overwriteAnswer = input.askForString(overwriteMessage);
			if ("Y".equalsIgnoreCase(overwriteAnswer)) {
				selectedTeam.setPowerRanking(newPowerRanking);
				teamWithThatRanking.setPowerRanking(Team.CLEAR_RANKING);
			} else if ("N".equalsIgnoreCase(overwriteAnswer)){
				newPowerRanking = Team.CLEAR_RANKING;
			}
		}
		
		return newPowerRanking;
	}

}
