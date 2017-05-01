package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.Matchup;

public class MatchupMenu extends SubMenu {

	public enum MatchupMenuOptions implements MenuOptions {
		SET_TEAM_1_WIN_CHANCE(1, "Set <Team1> win chance"), 
		SET_TEAM_2_WIN_CHANCE(2, "Set <Team2> win chance"),
		CALCULATE_BASED_OFF_POWER_RANKINGS(3, "Calculate and set win chances based " +
				"off teams' Power Rankings"),
		BACK_TO_SINGLE_TEAM_MENU(4, "Back to <Team1> Menu");
		
		private int optionNumber;
		private String optionDescription;
		
		private MatchupMenuOptions(int optionNumber, String optionDescription) {
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
	
	private Matchup matchup;
	
	private String selectedTeamName;
	
	private NFLSeasonInput input;
	
	public MatchupMenu(NFLSeasonInput input) {
		this.input = input;
	}

	public void setMatchup(Matchup matchup) {
		this.matchup = matchup;
	}

	public String getSelectedTeamName() {
		return this.selectedTeamName;
	}

	public void setSelectedTeamName(String selectedTeamName) {
		this.selectedTeamName = selectedTeamName;
	}
	
	public void launchSubMenu() {
		String team1Name = selectedTeamName;
		String team2Name = matchup.getOpponentName(team1Name);
		
		
		int selectedOption = -1;
		while (selectedOption != MatchupMenuOptions.BACK_TO_SINGLE_TEAM_MENU.optionNumber) {
			String matchupMenuMessage = buildMatchupMenuMessage(team1Name,
					team2Name);
			selectedOption = input.askForInt(matchupMenuMessage);
			
			if (selectedOption == MatchupMenuOptions.SET_TEAM_1_WIN_CHANCE.optionNumber) {
				launchSetTeamWinChanceMenu(team1Name);
			} else if (selectedOption == MatchupMenuOptions.SET_TEAM_2_WIN_CHANCE.optionNumber) {
				launchSetTeamWinChanceMenu(team2Name);
			} else if (selectedOption == 
					MatchupMenuOptions.CALCULATE_BASED_OFF_POWER_RANKINGS.optionNumber) {
				matchup.calculateTeamWinChancesFromPowerRankings();
			}
		}
	}

	private String buildMatchupMenuMessage(String team1Name, String team2Name) {
		StringBuilder matchupMenuMessageBuilder = new StringBuilder();
		matchupMenuMessageBuilder.append("Matchup: " + team1Name + " vs. " + 
				team2Name + "\n");
		matchupMenuMessageBuilder.append("Current win chances:\n");
		matchupMenuMessageBuilder.append(team1Name + ": " + 
				matchup.getTeamWinChance(team1Name) + "\n");
		matchupMenuMessageBuilder.append(team2Name + ": " + 
				matchup.getTeamWinChance(team2Name) + "\n");
		
		matchupMenuMessageBuilder.append("Current win chance determiner: ");
		matchupMenuMessageBuilder.append(matchup.getWinChanceMode().winChanceModeDescription);
		matchupMenuMessageBuilder.append("\n");
		
		matchupMenuMessageBuilder.append(MenuOptionsUtil.createMenuMessage
				(MatchupMenuOptions.class));
		
		String matchupMenuMessage = matchupMenuMessageBuilder.toString();
		matchupMenuMessage = matchupMenuMessage.replace("<Team1>", team1Name);
		matchupMenuMessage = matchupMenuMessage.replace("<Team2>", team2Name);
		return matchupMenuMessage;
	}

	private void launchSetTeamWinChanceMenu(String teamName) {
		String setTeamWinChanceMessage = "Current " + teamName + " win chance: " + 
				matchup.getTeamWinChance(teamName) + 
				"\nPlease enter in a number between 1 and 99";
		int newTeamWinChance = -1;
		while (newTeamWinChance < 1 || newTeamWinChance > 99) {
			newTeamWinChance = input.askForInt(setTeamWinChanceMessage);
			if (newTeamWinChance >= 1 && newTeamWinChance <= 99) {
				matchup.setTeamWinChance(teamName, newTeamWinChance);
			}
		}
	}

}
