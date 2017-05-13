package nfl.season.menu;

import java.util.List;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.league.Matchup;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;

public class SingleTeamMenu extends SubMenu {

	public enum SingleTeamMenuOptions implements MenuOptions {
		SET_POWER_RANKING(1, "Set Power Ranking"), 
		SET_ELO_RATING(2, "Set Elo Rating"),
		SET_HOME_FIELD_ADVANTAGE(3, "Set Home Field Advantage"),
		SET_DEFAULT_POWER_RANKING(4, "Set Power Ranking to Default"),
		SET_DEFAULT_ELO_RATING(5, "Set Elo Rating to Default"),
		SET_DEFAULT_HOME_FIELD_ADVANTAGE(6, "Set Home Field Advantage to Default"),
		SET_ALL_DEFAULTS(7, "Revert All Team Values to Defaults"),
		CHOOSE_MATCHUP(8, "Edit Matchup Settings"),
		EXIT(9, "Back to Teams Menu");
		
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
	
	private static final String POWER_RANKING_MESSAGE_SUFFIX = "\nPlease enter in " +
			"a number between 1-32 to set the team to that ranking\nor -1 to clear " +
			"this team's ranking:";
	
	private static final String PLEASE_ENTER_NATURAL_NUMBER = "\nPlease enter in an integer above 0";
	
	private static final int NON_POWER_RANKING = 0;
	
	private Team selectedTeam;
	
	private NFLSeasonInput input;
	
	private League league;
	
	public SingleTeamMenu(NFLSeasonInput input, League league) {
		this.input = input;
		this.league = league;
		subMenus = new SubMenu[1];
	}
	
	@Override
	public void launchSubMenu() {
		String singleTeamMenuMessageSuffix = MenuOptionsUtil.createMenuMessage(
				SingleTeamMenuOptions.class);
		String singleTeamMenuMessage = singleTeamMenuMessageSuffix;
		
		int selectedOption = -1;
		while (selectedOption != SingleTeamMenuOptions.EXIT.optionNumber) {
			singleTeamMenuMessage = selectedTeam.getName() + "\nPower Ranking: " + 
					selectedTeam.getPowerRanking() + "\nElo Rating: " + 
					selectedTeam.getEloRating() + "\nHome Field Advantage: " + 
					selectedTeam.getHomeFieldAdvantage() + "\n" + singleTeamMenuMessageSuffix;
			singleTeamMenuMessage = singleTeamMenuMessage.replace("" + Team.CLEAR_RANKING, 
					Team.UNSET_RANKING_DISPLAY); 
			
			selectedOption = input.askForInt(singleTeamMenuMessage);
				
			executeSelectedOption(selectedOption);
		}
	}

	public void setTeam(Team team) {
		this.selectedTeam = team;
	}
	
	public MatchupMenu getMatchupMenu() {
		SubMenu subMenu = subMenus[0];
		MatchupMenu matchupMenu = null;
		if (subMenu != null && subMenu instanceof MatchupMenu) {
			matchupMenu = (MatchupMenu) subMenu;
			matchupMenu.setSelectedTeamName(selectedTeam.getName());
		}
		return matchupMenu;
	}
	
	private void executeSelectedOption(int selectedOption) {
		if (SingleTeamMenuOptions.SET_POWER_RANKING.optionNumber == selectedOption) {
			launchSetPowerRankingMenu();
		} else if (SingleTeamMenuOptions.SET_ELO_RATING.optionNumber == selectedOption) {
			launchSetEloRatingMenu();
		} else if (SingleTeamMenuOptions.SET_HOME_FIELD_ADVANTAGE.optionNumber == 
				selectedOption) {
			launchSetHomeFieldAdvantageMenu();
		} else if (SingleTeamMenuOptions.SET_DEFAULT_POWER_RANKING.optionNumber == 
				selectedOption) {
			launchSetDefaultPowerRankingMenu();
		} else if (SingleTeamMenuOptions.SET_DEFAULT_ELO_RATING.optionNumber == 
				selectedOption) {
			selectedTeam.setEloRating(selectedTeam.getDefaultEloRating());
		} else if (SingleTeamMenuOptions.SET_DEFAULT_HOME_FIELD_ADVANTAGE.optionNumber == 
				selectedOption) {
			selectedTeam.setHomeFieldAdvantage(
					selectedTeam.getDefaultHomeFieldAdvantage());
		} else if (SingleTeamMenuOptions.SET_ALL_DEFAULTS.optionNumber == selectedOption) {
			selectedTeam.resetToDefaults();
		} else if (SingleTeamMenuOptions.CHOOSE_MATCHUP.optionNumber == selectedOption) {
			launchSelectMatchupMenu();
		}
	}

	private void launchSetPowerRankingMenu() {
		int newPowerRanking = NON_POWER_RANKING;
		while ((newPowerRanking < 1 || 
				newPowerRanking > NFLTeamEnum.values().length) && 
				newPowerRanking != Team.CLEAR_RANKING) {
			String powerRankingMessage = "Currently #" + selectedTeam.getPowerRanking() + 
					POWER_RANKING_MESSAGE_SUFFIX;
			newPowerRanking = input.askForInt(powerRankingMessage);
			
			newPowerRanking = handleOverwritePowerRankings(newPowerRanking);
		}
	}
	
	private void launchSetDefaultPowerRankingMenu() {
		int defaultPowerRanking = selectedTeam.getDefaultPowerRanking();
		handleOverwritePowerRankings(defaultPowerRanking);
	}
	
	private void launchSetEloRatingMenu() {
		int newEloRating = -1;
		
		while (newEloRating <= 0) {
			String eloRatingMessage = "Current Elo Rating: " + selectedTeam.getEloRating() + 
					PLEASE_ENTER_NATURAL_NUMBER;
			newEloRating = input.askForInt(eloRatingMessage);
			
			if (newEloRating > 0) {
				selectedTeam.setEloRating(newEloRating);
			}
		}
	}
	
	private void launchSetHomeFieldAdvantageMenu() {
		int newHomeFieldAdvantage = -1;
		
		String homeFieldMessage = "Current Home Field Advantage: " + 
				selectedTeam.getHomeFieldAdvantage() + PLEASE_ENTER_NATURAL_NUMBER;
		newHomeFieldAdvantage = input.askForInt(homeFieldMessage);
			
		selectedTeam.setHomeFieldAdvantage(newHomeFieldAdvantage);
	}
	
	private void launchSelectMatchupMenu() {
		String matchupMenuMessage = getMatchupMenuMessage();
		
		List<Matchup> teamMatchups = selectedTeam.getMatchups();
		int exitMatchup = teamMatchups.size() + 1;
		int matchupSelection = -1;
		while (matchupSelection != exitMatchup) {
			matchupSelection = input.askForInt(matchupMenuMessage);
			if (matchupSelection < exitMatchup && matchupSelection > 0) {
				Matchup selectedMatchup = teamMatchups.get(matchupSelection - 1);
				MatchupMenu matchupMenu = getMatchupMenu();
				matchupMenu.setMatchup(selectedMatchup);
				matchupMenu.launchSubMenu();
			}
		}
	}
	
	private int handleOverwritePowerRankings(int newPowerRanking) {
		Team teamWithThatRanking = null;
		if (newPowerRanking != Team.CLEAR_RANKING) {
			teamWithThatRanking = league.getTeamWithPowerRanking(newPowerRanking);
		}
		
		if (teamWithThatRanking == null) {
			selectedTeam.setPowerRanking(newPowerRanking);
		} else {
			newPowerRanking = launchRankingOverwriteMenu(newPowerRanking, 
					teamWithThatRanking);
		}
		return newPowerRanking;
	}
	
	private String getMatchupMenuMessage() {
		StringBuilder matchupMenuMessageBuilder = new StringBuilder();
		matchupMenuMessageBuilder.append(MenuOptionsUtil.MENU_INTRO);
		int matchupIndex = 1;
		List<Matchup> teamMatchups = selectedTeam.getMatchups();
		for (Matchup matchup : teamMatchups) {
			matchupMenuMessageBuilder.append(matchupIndex + ". ");
			String opponentName = matchup.getOpponentName(selectedTeam.getName());
			matchupMenuMessageBuilder.append(opponentName + "\n");
			
			matchupIndex++;
		}
		int exitMatchup = matchupIndex;
		matchupMenuMessageBuilder.append(exitMatchup + ". Back to Team Menu");
		
		String matchupMenuMessage = matchupMenuMessageBuilder.toString();
		return matchupMenuMessage;
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
				newPowerRanking = NON_POWER_RANKING;
			}
		}
		
		return newPowerRanking;
	}
	
}
