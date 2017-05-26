package nfl.season.menu;

import java.util.ArrayList;
import java.util.List;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffConference;
import nfl.season.playoffs.NFLPlayoffDivision;
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;

public class PlayoffsMenu extends SubMenu {

	private static final int NUMBER_OF_WILDCARDS_IN_CONFERENCE = 2;
	
	public enum PlayoffsMenuOptions implements MenuOptions {
		SELECT_TEAMS(1, "Select Teams for Playoffs"), 
		SELECT_TEAMS_ON_POWER_RANKINGS(2, "Select Playoff Teams Based on Power Rankings"),
		SELECT_TEAMS_ON_ELO_RATINGS(3, "Select Playoff Teams Based on Elo Ratings"),
		CALCULATE_TEAM_CHANCES_BY_ROUND(4, "Calculate and Print Team Chances By Playoff Round"),
		EXIT(5, "Back to Main Menu");
		
		private int optionNumber;
		private String optionDescription;
		
		private PlayoffsMenuOptions(int optionNumber, String optionDescription) {
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
	
	private NFLPlayoffs playoffs;
	
	public PlayoffsMenu(NFLSeasonInput input, NFLPlayoffs playoffs) {
		this.input = input;
		this.playoffs = playoffs;
	}

	@Override
	public void launchSubMenu() {
		
		int selectedOption = -1;
		
		String playoffsPrefixMessage = "";
		while (selectedOption != PlayoffsMenuOptions.EXIT.optionNumber) {
			String playoffsMenuMessage = getPlayoffsMenuMessage(); 
			
			selectedOption = input.askForInt(playoffsPrefixMessage + playoffsMenuMessage);
			
			if (PlayoffsMenuOptions.SELECT_TEAMS.optionNumber == selectedOption) {
				launchSelectPlayoffTeamsMenu();
			} else if (PlayoffsMenuOptions.SELECT_TEAMS_ON_POWER_RANKINGS.optionNumber == 
					selectedOption) {
				boolean success = playoffs.populateTeamsByPowerRankings();
				if (success) {
					playoffsPrefixMessage = "Teams set based on Power Rankings\n";
				} else {
					playoffsPrefixMessage = "Need to set Power Rankings on all teams first\n";
				}
			} else if (PlayoffsMenuOptions.SELECT_TEAMS_ON_ELO_RATINGS.optionNumber == 
					selectedOption) {
				playoffs.populateTeamsByEloRatings();
				playoffsPrefixMessage = "Teams set based on Elo Ratings\n";
			} else if (PlayoffsMenuOptions.CALCULATE_TEAM_CHANCES_BY_ROUND.optionNumber == 
					selectedOption) {
				playoffsPrefixMessage = calculatePlayoffRoundChancesAndReturnResultPrintout();
			}
		}
	}

	private String getPlayoffsMenuMessage() {
		StringBuilder playoffsMenuBuilder = new StringBuilder();
		playoffsMenuBuilder.append("Current Playoff Teams\n");
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			playoffsMenuBuilder.append(conferenceName + "\n");
			
			for (int i = 1; i <= 6; i++) {
				NFLPlayoffTeam playoffTeam = playoffConference.getTeamWithSeed(i);
				if (playoffTeam == null) {
					playoffsMenuBuilder.append(i + ". Unset\n");
				} else {
					Team leagueTeam = playoffTeam.getTeam();
					String teamName = leagueTeam.getName();
					
					playoffsMenuBuilder.append(i + ". " + teamName + "\n");
				}
			}
			playoffsMenuBuilder.append("\n");
		}
		
		String playoffsMenuMessage = playoffsMenuBuilder.toString() + 
				MenuOptionsUtil.createMenuMessage(PlayoffsMenuOptions.class);
		
		return playoffsMenuMessage;
	}

	private void launchSelectPlayoffTeamsMenu() {
		playoffs.clearPlayoffTeams();
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			List<NFLPlayoffDivision> playoffDivisions = 
					playoffConference.getDivisions();
			
			getDivisionWinners(conferenceName, playoffDivisions);
			
			List<String> divisionWinnerNames = getDivisionWinnerNames(playoffConference);
			
			getWildcardTeams(leagueConference, divisionWinnerNames);
		}
	}

	private String calculatePlayoffRoundChancesAndReturnResultPrintout() {
		playoffs.calculateChancesByRoundForAllPlayoffTeams();
		
		StringBuilder playoffRoundChancesBuilder = new StringBuilder();
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			playoffRoundChancesBuilder.append(conferenceName + "\n");
			
			for (int i = 1; i <= 6; i++) {
				NFLPlayoffTeam playoffTeam = playoffConference.getTeamWithSeed(i);
				Team leagueTeam = playoffTeam.getTeam();
				String teamName = leagueTeam.getName();
					
				playoffRoundChancesBuilder.append(i + ". " + teamName + "\n");
				playoffRoundChancesBuilder.append("Make Divisional Round: " + 
						playoffTeam.getChanceOfMakingDivisionalRound() + "%\n");
				playoffRoundChancesBuilder.append("Make Conference Round: " + 
						playoffTeam.getChanceOfMakingConferenceRound() + "%\n");
				playoffRoundChancesBuilder.append("Win Conference: " + 
						playoffTeam.getChanceOfMakingSuperBowl() + "%\n");
				playoffRoundChancesBuilder.append("Win Super Bowl: " + 
						playoffTeam.getChanceOfWinningSuperBowl() + "%\n");
				playoffRoundChancesBuilder.append("\n");
			}
			playoffRoundChancesBuilder.append("\n");
		}
		
		return playoffRoundChancesBuilder.toString();
	}

	private void getDivisionWinners(String conferenceName,
			List<NFLPlayoffDivision> playoffDivisions) {
		for (NFLPlayoffDivision playoffDivision : playoffDivisions) {
			Division leagueDivision = playoffDivision.getDivision();
			
			String selectTeamsMessage = getSelectDivisionWinnerMessage(
					conferenceName, leagueDivision);
			
			int divisionWinnerIndex = -1;
			List<Team> divisionTeams = leagueDivision.getTeams();
			while (divisionWinnerIndex < 0 || divisionWinnerIndex > divisionTeams.size()) {
				divisionWinnerIndex = input.askForInt(selectTeamsMessage);
			}
			Team divisionWinner = divisionTeams.get(divisionWinnerIndex - 1);
			NFLPlayoffTeam playoffDivisionWinner = playoffs.createPlayoffTeam(
					divisionWinner);
			playoffs.setDivisionWinner(conferenceName, leagueDivision.getName(), 
					playoffDivisionWinner);;
		}
	}
	
	private List<String> getDivisionWinnerNames(
			NFLPlayoffConference playoffConference) {
		List<NFLPlayoffTeam> divisionWinners = playoffConference.getDivisionWinners();
		List<String> divisionWinnerNames = new ArrayList<String>();
		for (NFLPlayoffTeam divisionWinner : divisionWinners) {
			Team leagueDivisionWinner = divisionWinner.getTeam();
			divisionWinnerNames.add(leagueDivisionWinner.getName());
		}
		return divisionWinnerNames;
	}
	
	private void getWildcardTeams(Conference leagueConference,
			List<String> divisionWinnerNames) {
		String conferenceName = leagueConference.getName();
		
		List<Team> conferenceTeams = removeDivisionWinnersFromConferenceList(
				leagueConference, divisionWinnerNames);
		
		for (int i = 0; i < NUMBER_OF_WILDCARDS_IN_CONFERENCE; i++) {
			StringBuilder chooseWildcardBuilder = new StringBuilder();
			chooseWildcardBuilder.append(conferenceName + " Wildcard\n");
			chooseWildcardBuilder.append(MenuOptionsUtil.MENU_INTRO);
			
			int teamIndex = 1;
			for (Team conferenceTeam : conferenceTeams) {
				String conferenceTeamName = conferenceTeam.getName();
				chooseWildcardBuilder.append(teamIndex + ". " + 
						conferenceTeamName + "\n");
				teamIndex++;
			}
			chooseWildcardBuilder.deleteCharAt(chooseWildcardBuilder.lastIndexOf("\n"));
			
			int newWildcardIndex = -1;
			while (newWildcardIndex < 0 || newWildcardIndex > conferenceTeams.size()) {
				newWildcardIndex = input.askForInt(chooseWildcardBuilder.toString());
			}
			
			Team leagueNewWildcard = conferenceTeams.get(newWildcardIndex - 1);
			NFLPlayoffTeam newWildcardTeam = playoffs.createPlayoffTeam(leagueNewWildcard);
			playoffs.addWildcardTeam(conferenceName, newWildcardTeam);
			conferenceTeams.remove(leagueNewWildcard);
		}
	}
	
	private String getSelectDivisionWinnerMessage(String conferenceName,
			Division leagueDivision) {
		String divisionName = leagueDivision.getName();
		
		StringBuilder selectTeamsMessageBuilder = new StringBuilder();
		selectTeamsMessageBuilder.append(conferenceName + " " + 
				divisionName + " Champion\n");
		selectTeamsMessageBuilder.append(MenuOptionsUtil.MENU_INTRO);
		
		List<Team> leagueTeams = leagueDivision.getTeams();
		int teamIndex = 1;
		for (Team leagueTeam : leagueTeams) {
			selectTeamsMessageBuilder.append(teamIndex + ". " + 
					leagueTeam.getName() + "\n");
			teamIndex++;
		}
		selectTeamsMessageBuilder.deleteCharAt(
				selectTeamsMessageBuilder.lastIndexOf("\n"));
		
		String selectTeamsMessage = selectTeamsMessageBuilder.toString();
		return selectTeamsMessage;
	}

	private List<Team> removeDivisionWinnersFromConferenceList(
			Conference leagueConference, List<String> divisionWinnerNames) {
		List<Team> conferenceTeams = leagueConference.getTeams();
		for (int i = 0; i < conferenceTeams.size(); i++) {
			Team conferenceTeam = conferenceTeams.get(i);
			String conferenceTeamName = conferenceTeam.getName();
			if (divisionWinnerNames.contains(conferenceTeamName)) {
				conferenceTeams.remove(conferenceTeam);
				i--;
			}
		}
		return conferenceTeams;
	}
	
}
