package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.Matchup;

public class MatchupMenu extends SubMenu {

	public enum MatchupMenuOptions implements MenuOptions {
		SET_TEAM_1_WIN_CHANCE(1, "Set <Team1> neutral win chance"), 
		SET_TEAM_2_WIN_CHANCE(2, "Set <Team2> neutral win chance"),
		CALCULATE_BASED_OFF_POWER_RANKINGS(3, "Calculate and set win chances based " +
				"off teams' Power Rankings: #<Team1Rank> vs. #<Team2Rank>"),
		CALCULATE_BASED_OFF_ELO_RATINGS(4, "Calculate and set win chances based " + 
				"off teams' Elo Ratings: <Team1Rating> vs. <Team2Rating>"),
		SET_FIRST_TEAM_HOME_CHANCE(5, "Set <Team1> home win chance"),
		SET_FIRST_TEAM_AWAY_CHANCE(6, "Set <Team1> away win chance"),
		SET_SECOND_TEAM_HOME_CHANCE(7, "Set <Team2> home win chance"),
		SET_SECOND_TEAM_AWAY_CHANCE(8, "Set <Team2> away win chance"),
		CALCULATE_TEAM_1_HOME_WIN_CHANCE(9, "Calculate <Team1> Home " +
				"Win Chance by set Home Field Advantage: <Team1Advantage>"),
		CALCULATE_TEAM_2_HOME_WIN_CHANCE(10, "Calculate <Team2> Home " +
				"Win Chance by set Home Field Advantage: <Team2Advantage>"),
		BACK_TO_SINGLE_TEAM_MENU(11, "Back to <Team1> Matchup List");
		
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
		boolean calculationSuccess = true;
		int lastCalculationDone = -1;
		
		int selectedOption = -1;
		while (selectedOption != MatchupMenuOptions.BACK_TO_SINGLE_TEAM_MENU.optionNumber) {
			String matchupMenuMessage = buildMatchupMenuMessage(team1Name,
					team2Name, calculationSuccess, lastCalculationDone);
			selectedOption = input.askForInt(matchupMenuMessage);
			
			calculationSuccess = true;
			if (selectedOption == MatchupMenuOptions.SET_TEAM_1_WIN_CHANCE.optionNumber) {
				launchSetTeamNeutralWinChanceMenu(team1Name);
			} else if (selectedOption == MatchupMenuOptions.SET_TEAM_2_WIN_CHANCE.optionNumber) {
				launchSetTeamNeutralWinChanceMenu(team2Name);
			} else if (selectedOption == 
					MatchupMenuOptions.CALCULATE_BASED_OFF_POWER_RANKINGS.optionNumber) {
				calculationSuccess = matchup.calculateTeamWinChancesFromPowerRankings();
				lastCalculationDone = selectedOption;
			} else if (selectedOption == 
					MatchupMenuOptions.CALCULATE_BASED_OFF_ELO_RATINGS.optionNumber) {
				calculationSuccess = matchup.calculateTeamWinChancesFromEloRatings();
				lastCalculationDone = selectedOption;
			} else if (selectedOption == MatchupMenuOptions.SET_FIRST_TEAM_HOME_CHANCE.optionNumber) {
				launchSetTeamHomeWinChanceMenu(team1Name);
			} else if (selectedOption == MatchupMenuOptions.SET_FIRST_TEAM_AWAY_CHANCE.optionNumber) {
				launchSetTeamAwayWinChanceMenu(team1Name);
			} else if (selectedOption == MatchupMenuOptions.SET_SECOND_TEAM_HOME_CHANCE.optionNumber) {
				launchSetTeamHomeWinChanceMenu(team2Name);
			} else if (selectedOption == MatchupMenuOptions.SET_SECOND_TEAM_AWAY_CHANCE.optionNumber) {
				launchSetTeamAwayWinChanceMenu(team2Name);
			} else if (selectedOption == MatchupMenuOptions.CALCULATE_TEAM_1_HOME_WIN_CHANCE.optionNumber) {
				matchup.calculateHomeWinChanceFromHomeFieldAdvantage(team1Name);
			} else if (selectedOption == MatchupMenuOptions.CALCULATE_TEAM_2_HOME_WIN_CHANCE.optionNumber) {
				matchup.calculateHomeWinChanceFromHomeFieldAdvantage(team2Name);
			}
		}
	}

	private String buildMatchupMenuMessage(String team1Name, String team2Name,
			boolean calculationSuccess, int lastCalculationDone) {
		StringBuilder matchupMenuMessageBuilder = new StringBuilder();
		
		addCalculationUnsuccessfulMessage(calculationSuccess,
				lastCalculationDone, matchupMenuMessageBuilder);
		
		matchupMenuMessageBuilder.append("Matchup: " + team1Name + " vs. " + 
				team2Name + "\n");
		matchupMenuMessageBuilder.append("Current win chances:\n");
		matchupMenuMessageBuilder.append(getTeamWinChancesMessage(team1Name));
		matchupMenuMessageBuilder.append(getTeamWinChancesMessage(team2Name));
		
		matchupMenuMessageBuilder.append("Current win chance determiners: Neutral - ");
		matchupMenuMessageBuilder.append(matchup.getWinChanceMode().winChanceModeDescription);
		matchupMenuMessageBuilder.append(", " + team1Name + " Home - " + 
				matchup.getHomeAwayWinChanceMode(team1Name).winChanceModeDescription);
		matchupMenuMessageBuilder.append(", " + team2Name + " Home - " + 
				matchup.getHomeAwayWinChanceMode(team2Name).winChanceModeDescription);
		matchupMenuMessageBuilder.append("\n");
		
		matchupMenuMessageBuilder.append(MenuOptionsUtil.createMenuMessage
				(MatchupMenuOptions.class));
		
		String matchupMenuMessage = matchupMenuMessageBuilder.toString();
		
		matchupMenuMessage = replacePlaceholdersInMatchupMenuMessage(team1Name,
				team2Name, matchupMenuMessage);
		return matchupMenuMessage;
	}

	private void launchSetTeamNeutralWinChanceMenu(String teamName) {
		String setTeamWinChanceMessage = "Current " + teamName + " neutral win chance: " + 
				matchup.getTeamNeutralWinChance(teamName) + 
				"\nPlease enter in a number between 1 and 99";
		int newTeamWinChance = -1;
		while (newTeamWinChance < 1 || newTeamWinChance > 99) {
			newTeamWinChance = input.askForInt(setTeamWinChanceMessage);
			if (newTeamWinChance >= 1 && newTeamWinChance <= 99) {
				matchup.setTeamNeutralWinChance(teamName, newTeamWinChance);
			}
		}
	}
	
	private void launchSetTeamHomeWinChanceMenu(String teamName) {
		String setTeamWinChanceMessage = "Current " + teamName + " home win chance: " + 
				matchup.getTeamHomeWinChance(teamName) + 
				"\nPlease enter in a number between 1 and 99";
		int newTeamWinChance = -1;
		while (newTeamWinChance < 1 || newTeamWinChance > 99) {
			newTeamWinChance = input.askForInt(setTeamWinChanceMessage);
			if (newTeamWinChance >= 1 && newTeamWinChance <= 99) {
				matchup.setTeamHomeWinChance(teamName, newTeamWinChance);
			}
		}
	}
	
	private void launchSetTeamAwayWinChanceMenu(String teamName) {
		String setTeamWinChanceMessage = "Current " + teamName + " away win chance: " + 
				matchup.getTeamAwayWinChance(teamName) + 
				"\nPlease enter in a number between 1 and 99";
		int newTeamWinChance = -1;
		while (newTeamWinChance < 1 || newTeamWinChance > 99) {
			newTeamWinChance = input.askForInt(setTeamWinChanceMessage);
			if (newTeamWinChance >= 1 && newTeamWinChance <= 99) {
				matchup.setTeamAwayWinChance(teamName, newTeamWinChance);
			}
		}
	}

	private void addCalculationUnsuccessfulMessage(boolean calculationSuccess,
			int lastCalculationDone, StringBuilder matchupMenuMessageBuilder) {
		if (!calculationSuccess) {
			if (lastCalculationDone == 
					MatchupMenuOptions.CALCULATE_BASED_OFF_POWER_RANKINGS.optionNumber) {
				matchupMenuMessageBuilder.append("Could not calculate; set Power " +
						"Rankings on both teams.\n");
			} else if (lastCalculationDone == 
					MatchupMenuOptions.CALCULATE_BASED_OFF_ELO_RATINGS.optionNumber) {
				matchupMenuMessageBuilder.append("Could not calculate; set Elo " +
						"Ratings on both teams to be above 0.\n");
			}
		}
	}
	
	private String getTeamWinChancesMessage(String team1Name) {
		return team1Name + ": Neutral - " + 
				matchup.getTeamNeutralWinChance(team1Name) + ", Home - " +
				matchup.getTeamHomeWinChance(team1Name) + ", Away - " +
				matchup.getTeamAwayWinChance(team1Name) + "\n";
	}
	
	private String replacePlaceholdersInMatchupMenuMessage(String team1Name,
			String team2Name, String matchupMenuMessage) {
		matchupMenuMessage = matchupMenuMessage.replace("<Team1>", team1Name);
		matchupMenuMessage = matchupMenuMessage.replace("<Team2>", team2Name);
		
		matchupMenuMessage = matchupMenuMessage.replace("<Team1Rank>", "" + 
				matchup.getTeamPowerRanking(team1Name));
		matchupMenuMessage = matchupMenuMessage.replace("<Team2Rank>", "" + 
				matchup.getTeamPowerRanking(team2Name));
		
		matchupMenuMessage = matchupMenuMessage.replace("<Team1Rating>", "" + matchup.getTeamEloRating(team1Name));
		matchupMenuMessage = matchupMenuMessage.replace("<Team2Rating>", "" + matchup.getTeamEloRating(team2Name));
		
		matchupMenuMessage = matchupMenuMessage.replace("<Team1Advantage>", 
				"" + matchup.getTeamHomeFieldAdvantage(team1Name));
		matchupMenuMessage = matchupMenuMessage.replace("<Team2Advantage>", 
				"" + matchup.getTeamHomeFieldAdvantage(team2Name));
		
		return matchupMenuMessage;
	}

}
