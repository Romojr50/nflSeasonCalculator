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
		SET_TEAM_LEVEL(2, "Set Team Level"),
		CHOOSE_MATCHUP(3, "Edit Matchup Settings"),
		EXIT(4, "Back to Teams Menu");
		
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
					selectedTeam.getPowerRanking() + "\nTeam Level: " + 
					selectedTeam.getTeamLevel() + "\n" + singleTeamMenuMessageSuffix;
			singleTeamMenuMessage = singleTeamMenuMessage.replace("" + Team.CLEAR_RANKING, 
					Team.UNSET_RANKING_DISPLAY); 
			
			selectedOption = input.askForInt(singleTeamMenuMessage);
				
			if (SingleTeamMenuOptions.SET_POWER_RANKING.optionNumber == selectedOption) {
				launchSetPowerRankingMenu();
			} else if (SingleTeamMenuOptions.SET_TEAM_LEVEL.optionNumber == selectedOption) {
				launchSetTeamLevelMenu();
			} else if (SingleTeamMenuOptions.CHOOSE_MATCHUP.optionNumber == selectedOption) {
				launchSelectMatchupMenu();
			}
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

	private void launchSetPowerRankingMenu() {
		int newPowerRanking = NON_POWER_RANKING;
		while ((newPowerRanking < 1 || 
				newPowerRanking > NFLTeamEnum.values().length) && 
				newPowerRanking != Team.CLEAR_RANKING) {
			String powerRankingMessage = "Currently #" + selectedTeam.getPowerRanking() + 
					POWER_RANKING_MESSAGE_SUFFIX;
			newPowerRanking = input.askForInt(powerRankingMessage);
			
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
		}
	}
	
	private void launchSetTeamLevelMenu() {
		int newTeamLevel = -1;
		
		while (newTeamLevel <= 0) {
			String teamLevelMessage = "Current Team Level: " + selectedTeam.getTeamLevel() + 
					"\nPlease enter in an integer above 0";
			newTeamLevel = input.askForInt(teamLevelMessage);
			
			if (newTeamLevel > 0) {
				selectedTeam.setTeamLevel(newTeamLevel);
			}
		}
		
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
	
}
