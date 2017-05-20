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
		EXIT(2, "Back to Main Menu");
		
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
		String playoffsMenuMessage = MenuOptionsUtil.createMenuMessage(PlayoffsMenuOptions.class);
		
		int selectedOption = -1;
		
		while (selectedOption != PlayoffsMenuOptions.EXIT.optionNumber) {
			selectedOption = input.askForInt(playoffsMenuMessage);
			
			if (PlayoffsMenuOptions.SELECT_TEAMS.optionNumber == selectedOption) {
				launchSelectPlayoffTeamsMenu();
			}
		}
	}

	private void launchSelectPlayoffTeamsMenu() {
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			List<NFLPlayoffDivision> playoffDivisions = 
					playoffConference.getDivisions();
			
			getDivisionWinners(conferenceName, playoffDivisions);
			
			List<String> divisionWinnerNames = getDivisionWinnerNames(playoffConference);
			
			getWildcardTeams(leagueConference, conferenceName,
					divisionWinnerNames);
		}
	}

	private void getDivisionWinners(String conferenceName,
			List<NFLPlayoffDivision> playoffDivisions) {
		for (NFLPlayoffDivision playoffDivision : playoffDivisions) {
			Division leagueDivision = playoffDivision.getDivision();
			
			String selectTeamsMessage = getSelectDivisionWinnerMessage(
					conferenceName, leagueDivision);
			
			int divisionWinnerIndex = input.askForInt(selectTeamsMessage);
			List<Team> divisionTeams = leagueDivision.getTeams();
			Team divisionWinner = divisionTeams.get(divisionWinnerIndex - 1);
			NFLPlayoffTeam playoffDivisionWinner = playoffs.createPlayoffTeam(
					divisionWinner);
			playoffDivision.setDivisionWinner(playoffDivisionWinner);
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
			String conferenceName, List<String> divisionWinnerNames) {
		List<Team> conferenceTeams = leagueConference.getTeams();
		for (int i = 0; i < NUMBER_OF_WILDCARDS_IN_CONFERENCE; i++) {
			StringBuilder chooseWildcardBuilder = new StringBuilder();
			chooseWildcardBuilder.append(conferenceName + " Wildcard\n");
			chooseWildcardBuilder.append(MenuOptionsUtil.MENU_INTRO);
			
			int teamIndex = 1;
			for (Team conferenceTeam : conferenceTeams) {
				String conferenceTeamName = conferenceTeam.getName();
				if (!divisionWinnerNames.contains(conferenceTeamName)) {
					chooseWildcardBuilder.append(teamIndex + ". " + 
							conferenceTeamName + "\n");
					teamIndex++;
				}
			}
			input.askForInt(chooseWildcardBuilder.toString());
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
		
		String selectTeamsMessage = selectTeamsMessageBuilder.toString();
		return selectTeamsMessage;
	}
	
}
