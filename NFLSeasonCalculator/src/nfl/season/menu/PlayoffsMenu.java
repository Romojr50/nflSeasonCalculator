package nfl.season.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nfl.season.input.NFLFileReaderFactory;
import nfl.season.input.NFLFileWriterFactory;
import nfl.season.input.NFLPlayoffSettings;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffConference;
import nfl.season.playoffs.NFLPlayoffDivision;
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;

public class PlayoffsMenu extends SubMenu {
	
	public enum PlayoffsMenuOptions implements MenuOptions {
		SELECT_TEAMS(1, "Select Teams for Playoffs"), 
		SELECT_TEAMS_ON_POWER_RANKINGS(2, "Select Playoff Teams Based on Power Rankings"),
		SELECT_TEAMS_ON_ELO_RATINGS(3, "Select Playoff Teams Based on Elo Ratings"),
		RESEED_TEAMS(4, "Reseed Current Playoff Teams"),
		CALCULATE_TEAM_CHANCES_BY_ROUND(5, "Calculate and Print Team Chances By Playoff Round"),
		LOAD_PLAYOFFS(6, "Load Saved Playoff Teams"),
		SAVE_PLAYOFFS(7, "Save Playoff Teams"),
		EXIT(8, "Back to Main Menu");
		
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
	
	private static final int NUMBER_OF_WILDCARDS_IN_CONFERENCE = 2;
	
	private static final String SAVE_FILE_SUCCEEDED = "Playoffs Saved Successfully\n";
	
	private static final String SAVE_FILE_FAILED = "Playoffs Save FAILED\n";

	private static final String LOAD_FILE_SUCCEEDED = "Playoffs Loaded Successfully\n";

	private static final String LOAD_FILE_FAILED = "Playoffs Load FAILED\n";
	
	private NFLSeasonInput input;
	
	private NFLPlayoffs playoffs;
	
	private NFLPlayoffSettings playoffSettings;
	
	private NFLFileWriterFactory fileWriterFactory;
	
	private NFLFileReaderFactory fileReaderFactory;
	
	public PlayoffsMenu(NFLSeasonInput input, NFLPlayoffs playoffs, 
			NFLPlayoffSettings playoffSettings, 
			NFLFileWriterFactory fileWriterFactory, 
			NFLFileReaderFactory fileReaderFactory) {
		this.input = input;
		this.playoffs = playoffs;
		this.playoffSettings = playoffSettings;
		this.fileWriterFactory = fileWriterFactory;
		this.fileReaderFactory = fileReaderFactory;
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
			} else if (PlayoffsMenuOptions.RESEED_TEAMS.optionNumber == selectedOption) {
				playoffsPrefixMessage = launchReseedPlayoffTeamsMenu();
			} else if (PlayoffsMenuOptions.CALCULATE_TEAM_CHANCES_BY_ROUND.optionNumber == 
					selectedOption) {
				playoffsPrefixMessage = calculatePlayoffRoundChancesAndReturnResultPrintout();
			} else if (PlayoffsMenuOptions.LOAD_PLAYOFFS.optionNumber == selectedOption) {
				playoffsPrefixMessage = loadSettingsFile();
			} else if (PlayoffsMenuOptions.SAVE_PLAYOFFS.optionNumber == selectedOption) {
				playoffsPrefixMessage = saveToSettingsFile();
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
	
	private String launchReseedPlayoffTeamsMenu() {
		String errorMessage = "";
		
		boolean allPlayoffTeamsSet = playoffs.allPlayoffTeamsSet();
		if (allPlayoffTeamsSet) {
			List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
			for (NFLPlayoffConference playoffConference : playoffConferences) {
				Conference leagueConference = playoffConference.getConference();
				String conferenceName = leagueConference.getName();
				
				int conferenceSeed = 1;
				List<NFLPlayoffTeam> playoffDivisionWinners = playoffConference.getDivisionWinners();
				
				for (conferenceSeed = 1; conferenceSeed < 4; conferenceSeed++) {
					reseedNextDivisionWinner(conferenceName, conferenceSeed,
							playoffDivisionWinners);
				}
				NFLPlayoffTeam remainingDivisionWinner = playoffDivisionWinners.remove(0);
				playoffs.setTeamConferenceSeed(remainingDivisionWinner, conferenceSeed);
				
				conferenceSeed++;
				
				reseedWildcards(playoffConference, conferenceName, conferenceSeed);
			}
		} else {
			errorMessage = "Please Fill Out All Playoff Teams First\n";
		}
		
		return errorMessage;
	}

	private String calculatePlayoffRoundChancesAndReturnResultPrintout() {
		boolean success = playoffs.calculateChancesByRoundForAllPlayoffTeams();
		
		StringBuilder playoffRoundChancesBuilder = new StringBuilder();
		
		if (success) {
			playoffRoundChancesBuilder.append(getPlayoffRoundChancesPrintout());
		} else {
			playoffRoundChancesBuilder.append("Calculation Failed; Please set all 12 Playoff Teams\n");
		}
		
		return playoffRoundChancesBuilder.toString();
	}
	
	private String saveToSettingsFile() {
		String saveFilePrefix;
		try {
			boolean saveSuccess = playoffSettings.saveToSettingsFile(playoffs, 
					fileWriterFactory);
			if (saveSuccess) {
				saveFilePrefix = SAVE_FILE_SUCCEEDED;
			} else {
				saveFilePrefix = SAVE_FILE_FAILED;
			}
		} catch (IOException e) {
			saveFilePrefix = SAVE_FILE_FAILED;
		}
		return saveFilePrefix;
	}

	private String loadSettingsFile() {
		String saveLoadFilePrefix;
		String playoffFileString = null;
		try {
			playoffFileString = playoffSettings.loadSettingsFile(fileReaderFactory);
			
			if (playoffFileString != null && !"".equals(playoffFileString)) {
				playoffSettings.loadPlayoffSettingsString(playoffs, 
						playoffs.getLeague(), playoffFileString);
				saveLoadFilePrefix = LOAD_FILE_SUCCEEDED;
			} else {
				saveLoadFilePrefix = LOAD_FILE_FAILED;
			}
		} catch (IOException e) {
			saveLoadFilePrefix = LOAD_FILE_FAILED;
		}
		return saveLoadFilePrefix;
	}
	
	private String getPlayoffRoundChancesPrintout() {
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

	private void reseedNextDivisionWinner(String conferenceName, int conferenceSeed,
			List<NFLPlayoffTeam> playoffDivisionWinners) {
		StringBuilder reseedDivisionWinnersBuilder = new StringBuilder();
		reseedDivisionWinnersBuilder.append(conferenceName + " Seed " + 
				conferenceSeed + "\n");
		
		int divisionWinnerIndex = 1;
		for (NFLPlayoffTeam playoffDivisionWinner : playoffDivisionWinners) {
			Team leagueDivisionWinner = playoffDivisionWinner.getTeam();
			String divisionWinnerName = leagueDivisionWinner.getName();
			reseedDivisionWinnersBuilder.append(divisionWinnerIndex + ". " + 
					divisionWinnerName + "\n");
			
			divisionWinnerIndex++;
		}
		reseedDivisionWinnersBuilder.deleteCharAt(reseedDivisionWinnersBuilder.length() - 1);
		String reseedDivisionWinnerMessage = reseedDivisionWinnersBuilder.toString();
		
		int reseededTeamIndex = -1;
		while (reseededTeamIndex < 1 || reseededTeamIndex > playoffDivisionWinners.size()) {
			reseededTeamIndex = input.askForInt(reseedDivisionWinnerMessage);
		}
		
		NFLPlayoffTeam reseededTeam = playoffDivisionWinners.remove(reseededTeamIndex - 1);
		playoffs.setTeamConferenceSeed(reseededTeam, conferenceSeed);
	}

	private void reseedWildcards(NFLPlayoffConference playoffConference,
			String conferenceName, int conferenceSeed) {
		StringBuilder reseedWildcardBuilder = new StringBuilder();
		reseedWildcardBuilder.append(conferenceName + " Seed 5\n");
		List<NFLPlayoffTeam> playoffWildcards = new ArrayList<NFLPlayoffTeam>();
		playoffWildcards.add(playoffConference.getTeamWithSeed(5));
		playoffWildcards.add(playoffConference.getTeamWithSeed(6));
		
		int playoffWildcardIndex = 1;
		for (NFLPlayoffTeam playoffWildcard : playoffWildcards) {
			Team leagueWildcard = playoffWildcard.getTeam();
			String wildcardName = leagueWildcard.getName();
			reseedWildcardBuilder.append(playoffWildcardIndex + ". " + wildcardName + "\n");
			playoffWildcardIndex++;
		}
		reseedWildcardBuilder.deleteCharAt(reseedWildcardBuilder.length() - 1);
		
		String reseedWildcardMessage = reseedWildcardBuilder.toString();
		
		int reseededTeamIndex = -1;
		while (reseededTeamIndex < 1 || reseededTeamIndex > playoffWildcards.size()) {
			reseededTeamIndex = input.askForInt(reseedWildcardMessage);
		}
		
		NFLPlayoffTeam reseededTeam = playoffWildcards.remove(reseededTeamIndex - 1);
		playoffs.setTeamConferenceSeed(reseededTeam, conferenceSeed);
		
		conferenceSeed++;
		NFLPlayoffTeam remainingWildcard = playoffWildcards.remove(0);
		playoffs.setTeamConferenceSeed(remainingWildcard, conferenceSeed);
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
