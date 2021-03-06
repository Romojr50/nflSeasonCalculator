package nfl.season.menu;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nfl.season.input.NFLFileReaderFactory;
import nfl.season.input.NFLFileWriterFactory;
import nfl.season.input.NFLPlayoffSettings;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.League;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffConference;
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;
import nfl.season.playoffs.TestWithMockPlayoffObjects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayoffsMenuTest extends TestWithMockPlayoffObjects {

	private static final int SELECT_TEAMS_FOR_PLAYOFFS = 1;
	
	private static final int SELECT_TEAMS_BY_POWER_RANKINGS = 2;
	
	private static final int SELECT_TEAMS_BY_ELO_RATINGS = 3;
	
	private static final int RESEED_TEAMS = 4;
	
	private static final int CALCULATE_TEAM_CHANCES_BY_ROUND = 5;
	
	private static final int LOAD_PLAYOFF_SETTINGS = 6;
	
	private static final int SAVE_PLAYOFF_SETTINGS = 7;
	
	private static final int BACK_TO_MAIN_MENU = 8;
	
	private String expectedMenuMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private League league;
	
	@Mock
	private NFLPlayoffs playoffs;
	
	@Mock
	private NFLPlayoffSettings playoffSettings;
	
	@Mock
	private NFLFileWriterFactory fileWriterFactory;
	
	@Mock
	private NFLFileReaderFactory fileReaderFactory;
	
	private String loadedPlayoffsFileString = "Load Playoffs File";
	
	private PlayoffsMenu playoffsMenu;
	
	@Before
	public void setUp() {
		playoffsMenu = new PlayoffsMenu(input, playoffs, playoffSettings, 
				fileWriterFactory, fileReaderFactory);
		
		super.setUpMockObjects();
		super.setUpMockPlayoffsWithTeamsAndConferences(playoffs);
		setExpectedMenuMessage();
		when(playoffs.getLeague()).thenReturn(league);
	}

	@Test
	public void invalidInputGivenOnMenuIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(999, -3, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void selectTeamsForPlayoffsHasUserPutInTeamsForPlayoffs() {
		setUpDivisionWinners();
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_FOR_PLAYOFFS, 
				2, 3, 1, 1, 3, 1, 2, 1, 1, 1, 1, 2, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verifyChoosePlayoffTeamsMessages();
		
		verify(playoffs).clearPlayoffTeams();
		verify(playoffs).setDivisionWinner(leagueConference1.getName(), leagueDivision1_1.getName(), playoffTeam1_1_2);
		verify(playoffs).setDivisionWinner(leagueConference1.getName(), leagueDivision1_2.getName(), playoffTeam1_2_3);
		verify(playoffs).setDivisionWinner(leagueConference2.getName(), leagueDivision2_1.getName(), playoffTeam2_1_2);
		verify(playoffs).setDivisionWinner(leagueConference2.getName(), leagueDivision2_2.getName(), playoffTeam2_2_1);
		verify(playoffs).addWildcardTeam(leagueConference1.getName(), playoffTeam1_2_1);
		verify(playoffs).addWildcardTeam(leagueConference1.getName(), playoffTeam1_1_1);
		verify(playoffs).addWildcardTeam(leagueConference2.getName(), playoffTeam2_1_1);
		verify(playoffs).addWildcardTeam(leagueConference2.getName(), playoffTeam2_2_2);
	}
	
	@Test
	public void selectTeamsForPlayoffsUserPutsInInvalidInputWhichIsIgnored() {
		setUpDivisionWinners();
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_FOR_PLAYOFFS, 
				5, -1, 2, 3, 1, 1, 5, -1, 3, 1, 2, 1, 1, 1, 1, 2, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		
		when(leagueConference1.getTeams()).thenReturn(conference1Teams);
		when(leagueConference2.getTeams()).thenReturn(conference2Teams);
		verify(input, times(3)).askForInt(getChooseDivisionWinnerMessage(leagueConference1, 
				leagueDivision1_1));
		verify(input, times(3)).askForInt(getChooseWildcardMessage(leagueConference1, 
				leagueTeam1_1_2.getName(), leagueTeam1_2_3.getName(), 
				leagueTeam1_3_1.getName(), leagueTeam1_4_1.getName(), ""));
	}
	
	@Test
	public void choosePlayoffTeamsBasedOnPowerRankingsCallsPlayoffsToGetTeamsByPowerRankings() {
		when(playoffs.populateTeamsByPowerRankings()).thenReturn(true);
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_BY_POWER_RANKINGS, 
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt("Teams set based on Power Rankings\n" + expectedMenuMessage);
		verify(playoffs, times(1)).populateTeamsByPowerRankings();
	}
	
	@Test
	public void choosePlayoffTeamsOnPowerRankingsButNotAllRankingsSetSoPlayoffTeamsNotSet() {
		when(playoffs.populateTeamsByPowerRankings()).thenReturn(false);
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_BY_POWER_RANKINGS, 
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt("Need to set Power Rankings on all teams first\n" + 
				expectedMenuMessage);
		verify(playoffs, times(1)).populateTeamsByPowerRankings();
	}
	
	@Test
	public void choosePlayoffTeamsOnEloRatingsHasPlayoffsPopulateTeamsByEloRatings() {
		when(playoffs.populateTeamsByPowerRankings()).thenReturn(true);
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_BY_ELO_RATINGS, 
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt("Teams set based on Elo Ratings\n" + expectedMenuMessage);
		verify(playoffs, times(1)).populateTeamsByEloRatings();
	}
	
	@Test
	public void reseedPlayoffTeamsSoTeamsAreReseeded() {
		when(playoffs.allPlayoffTeamsSet()).thenReturn(true);
		
		setUpDivisionWinners();
		when(playoffConference1.getTeamWithSeed(5)).thenReturn(playoffTeam1_1_1);
		when(playoffConference1.getTeamWithSeed(6)).thenReturn(playoffTeam1_2_2);
		when(playoffConference2.getTeamWithSeed(5)).thenReturn(playoffTeam2_1_3);
		when(playoffConference2.getTeamWithSeed(6)).thenReturn(playoffTeam2_2_2);
		
		when(input.askForInt(anyString())).thenReturn(RESEED_TEAMS, 5, -1, 
				2, 4, 3, 1, 2, 4, 3, 2, 3, -1, 1, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		setUpDivisionWinners();
		verifyChooseDivisionWinnersMessages();
		verifyReseedWildcardMessages();
		
		verify(playoffs).setTeamConferenceSeed(playoffTeam1_2_3, 1);
		verify(playoffs).setTeamConferenceSeed(playoffTeam1_4_1, 2);
		verify(playoffs).setTeamConferenceSeed(playoffTeam1_1_2, 3);
		verify(playoffs).setTeamConferenceSeed(playoffTeam1_3_1, 4);
		verify(playoffs).setTeamConferenceSeed(playoffTeam1_2_2, 5);
		verify(playoffs).setTeamConferenceSeed(playoffTeam1_1_1, 6);
		
		verify(playoffs).setTeamConferenceSeed(playoffTeam2_4_1, 1);
		verify(playoffs).setTeamConferenceSeed(playoffTeam2_3_1, 2);
		verify(playoffs).setTeamConferenceSeed(playoffTeam2_2_1, 3);
		verify(playoffs).setTeamConferenceSeed(playoffTeam2_1_2, 4);
		verify(playoffs).setTeamConferenceSeed(playoffTeam2_1_3, 5);
		verify(playoffs).setTeamConferenceSeed(playoffTeam2_2_2, 6);
	}
	
	@Test
	public void reseedPlayoffTeamsButNotAllTeamsSetSoReseedingDoesNotHappen() {
		when(playoffs.allPlayoffTeamsSet()).thenReturn(false);
		
		when(input.askForInt(anyString())).thenReturn(RESEED_TEAMS, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		expectedMenuMessage = "Please Fill Out All Playoff Teams First\n" + 
				expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMenuMessage);
		
		verify(playoffs, never()).setTeamConferenceSeed(any(NFLPlayoffTeam.class), anyInt());
	}

	@Test
	public void calculateTeamChancesByRoundOutputsAllTeamsAndRoundsChances() {
		when(playoffConference1.getTeamWithSeed(1)).thenReturn(playoffTeam1_1_1);
		when(playoffConference1.getTeamWithSeed(2)).thenReturn(playoffTeam1_2_1);
		when(playoffConference1.getTeamWithSeed(3)).thenReturn(playoffTeam1_3_1);
		when(playoffConference1.getTeamWithSeed(4)).thenReturn(playoffTeam1_4_1);
		when(playoffConference1.getTeamWithSeed(5)).thenReturn(playoffTeam1_1_2);
		when(playoffConference1.getTeamWithSeed(6)).thenReturn(playoffTeam1_2_2);
		
		when(playoffConference2.getTeamWithSeed(1)).thenReturn(playoffTeam2_1_1);
		when(playoffConference2.getTeamWithSeed(2)).thenReturn(playoffTeam2_2_1);
		when(playoffConference2.getTeamWithSeed(3)).thenReturn(playoffTeam2_3_1);
		when(playoffConference2.getTeamWithSeed(4)).thenReturn(playoffTeam2_4_1);
		when(playoffConference2.getTeamWithSeed(5)).thenReturn(playoffTeam2_1_2);
		when(playoffConference2.getTeamWithSeed(6)).thenReturn(playoffTeam2_2_2);
		
		when(playoffs.calculateChancesByRoundForAllPlayoffTeams()).thenReturn(true);
		
		when(input.askForInt(anyString())).thenReturn(CALCULATE_TEAM_CHANCES_BY_ROUND,
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		setExpectedMenuMessage();
		verify(input, times(1)).askForInt(expectedMenuMessage);
		String allTeamAndRoundChancesMessage = getAllTeamAndRoundChancesMessage();
		verify(input, times(1)).askForInt(allTeamAndRoundChancesMessage + expectedMenuMessage);
		
		verify(playoffs, times(1)).calculateChancesByRoundForAllPlayoffTeams();
	}
	
	@Test
	public void calculateTeamChancesByRoundFailsSoDisplayFailMessage() {
		when(playoffs.calculateChancesByRoundForAllPlayoffTeams()).thenReturn(false);
		
		when(input.askForInt(anyString())).thenReturn(CALCULATE_TEAM_CHANCES_BY_ROUND,
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		setExpectedMenuMessage();
		verify(input, times(1)).askForInt(expectedMenuMessage);
		String calculationFailedMessage = "Calculation Failed; Please set all 12 Playoff Teams\n";
		verify(input, times(1)).askForInt(calculationFailedMessage + expectedMenuMessage);
		
		verify(playoffs, times(1)).calculateChancesByRoundForAllPlayoffTeams();
	}
	
	@Test
	public void savePlayoffsSavesPlayoffsToFile() throws IOException {
		when(playoffSettings.saveToSettingsFile(playoffs, fileWriterFactory)).thenReturn(true);
		
		when(input.askForInt(anyString())).thenReturn(SAVE_PLAYOFF_SETTINGS, 
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		String saveMessage = "Playoffs Saved Successfully\n";
		verify(input, times(1)).askForInt(saveMessage + expectedMenuMessage);
		verify(playoffSettings).saveToSettingsFile(playoffs, fileWriterFactory);
	}
	
	@Test
	public void saveTeamSettingsFailsSoUserIsNotified() throws IOException {
		when(input.askForInt(anyString())).thenReturn(SAVE_PLAYOFF_SETTINGS, 
				BACK_TO_MAIN_MENU);
		doThrow(new IOException()).when(playoffSettings).saveToSettingsFile(playoffs, 
				fileWriterFactory);
		
		playoffsMenu.launchSubMenu();
		
		verifySaveSettingsFailureOccurs();
	}
	
	@Test
	public void saveTeamSettingsFailsWithFalseBooleanSoUserIsNotified() throws IOException {
		when(input.askForInt(anyString())).thenReturn(SAVE_PLAYOFF_SETTINGS,
				BACK_TO_MAIN_MENU);
		when(playoffSettings.saveToSettingsFile(playoffs, fileWriterFactory)).thenReturn(false);
		
		playoffsMenu.launchSubMenu();
		
		verifySaveSettingsFailureOccurs();
	}
	
	@Test
	public void loadTeamSettingsPullsInSavedSettings() throws IOException {
		when(input.askForInt(anyString())).thenReturn(LOAD_PLAYOFF_SETTINGS, 
				BACK_TO_MAIN_MENU);
		when(playoffSettings.loadSettingsFile(fileReaderFactory)).thenReturn(
				loadedPlayoffsFileString);
		
		playoffsMenu.launchSubMenu();
		
		verify(playoffSettings).loadSettingsFile(fileReaderFactory);
		verify(playoffSettings).loadPlayoffSettingsString(playoffs, league,
				loadedPlayoffsFileString);
		
		String expectedMessageWithSuccessfulLoad = "Playoffs Loaded Successfully\n" +
				expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMessageWithSuccessfulLoad);
	}
	
	@Test
	public void loadTeamSettingsFailsWithExceptionSoFailMessageIsDisplayed() throws IOException {
		when(input.askForInt(anyString())).thenReturn(LOAD_PLAYOFF_SETTINGS, 
				BACK_TO_MAIN_MENU);
		when(playoffSettings.loadSettingsFile(fileReaderFactory)).thenThrow(new IOException());
		
		playoffsMenu.launchSubMenu();
		
		verifyLoadSettingsFailureOccurs();
	}
	
	@Test
	public void loadTeamSettingsFailsWithEmptyResponseSoFailMessageIsDisplayed() throws IOException {
		when(input.askForInt(anyString())).thenReturn(LOAD_PLAYOFF_SETTINGS, 
				BACK_TO_MAIN_MENU);
		when(playoffSettings.loadSettingsFile(fileReaderFactory)).thenReturn("");
		
		playoffsMenu.launchSubMenu();
		
		verifyLoadSettingsFailureOccurs();
	}

	private void setExpectedMenuMessage() {
		StringBuilder expectedMenuBuilder = new StringBuilder();
		
		expectedMenuBuilder.append("Current Playoff Teams\n");
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			expectedMenuBuilder.append(conferenceName + "\n");
			
			for (int i = 1; i <= 6; i++) {
				NFLPlayoffTeam playoffTeam = playoffConference.getTeamWithSeed(i);
				if (playoffTeam == null) {
					expectedMenuBuilder.append(i + ". Unset\n");
				} else {
					Team leagueTeam = playoffTeam.getTeam();
					String teamName = leagueTeam.getName();
					
					expectedMenuBuilder.append(i + ". " + teamName + "\n");
				}
			}
			expectedMenuBuilder.append("\n");
		}
		
		expectedMenuBuilder.append("Please enter in an integer corresponding to one of the following:\n");
		expectedMenuBuilder.append("1. Select Teams for Playoffs\n");
		expectedMenuBuilder.append("2. Select Playoff Teams Based on Power Rankings\n");
		expectedMenuBuilder.append("3. Select Playoff Teams Based on Elo Ratings\n");
		expectedMenuBuilder.append("4. Reseed Current Playoff Teams\n");
		expectedMenuBuilder.append("5. Calculate and Print Team Chances By Playoff Round\n");
		expectedMenuBuilder.append("6. Load Saved Playoff Teams\n");
		expectedMenuBuilder.append("7. Save Playoff Teams\n");
		expectedMenuBuilder.append("8. Back to Main Menu");
		
		expectedMenuMessage = expectedMenuBuilder.toString();
	}
	
	private void setUpDivisionWinners() {
		List<NFLPlayoffTeam> conference1DivisionWinners = new ArrayList<NFLPlayoffTeam>();
		conference1DivisionWinners.add(playoffTeam1_1_2);
		conference1DivisionWinners.add(playoffTeam1_2_3);
		conference1DivisionWinners.add(playoffTeam1_3_1);
		conference1DivisionWinners.add(playoffTeam1_4_1);
		when(playoffConference1.getDivisionWinners()).thenReturn(conference1DivisionWinners);
		
		List<NFLPlayoffTeam> conference2DivisionWinners = new ArrayList<NFLPlayoffTeam>();
		conference2DivisionWinners.add(playoffTeam2_1_2);
		conference2DivisionWinners.add(playoffTeam2_2_1);
		conference2DivisionWinners.add(playoffTeam2_3_1);
		conference2DivisionWinners.add(playoffTeam2_4_1);
		when(playoffConference2.getDivisionWinners()).thenReturn(conference2DivisionWinners);
	}
	
	private void verifyChoosePlayoffTeamsMessages() {
		when(leagueConference1.getTeams()).thenReturn(conference1Teams);
		when(leagueConference2.getTeams()).thenReturn(conference2Teams);
		
		verify(input, times(1)).askForInt(getChooseDivisionWinnerMessage(leagueConference1, 
				leagueDivision1_1));
		verify(input, times(1)).askForInt(getChooseDivisionWinnerMessage(leagueConference1, 
				leagueDivision1_2));
		verify(input, times(1)).askForInt(getChooseDivisionWinnerMessage(leagueConference2, 
				leagueDivision2_1));
		verify(input, times(1)).askForInt(getChooseDivisionWinnerMessage(leagueConference2, 
				leagueDivision2_2));
		verify(input, times(1)).askForInt(getChooseWildcardMessage(leagueConference1, 
				leagueTeam1_1_2.getName(), leagueTeam1_2_3.getName(), 
				leagueTeam1_3_1.getName(), leagueTeam1_4_1.getName(), ""));
		verify(input, times(1)).askForInt(getChooseWildcardMessage(leagueConference1, 
				leagueTeam1_1_2.getName(), leagueTeam1_2_3.getName(), 
				leagueTeam1_3_1.getName(), leagueTeam1_4_1.getName(), leagueTeam1_2_1.getName()));
		verify(input, times(1)).askForInt(getChooseWildcardMessage(leagueConference2, 
				leagueTeam2_1_2.getName(), leagueTeam2_2_1.getName(), 
				leagueTeam2_3_1.getName(), leagueTeam2_4_1.getName(), ""));
		verify(input, times(1)).askForInt(getChooseWildcardMessage(leagueConference2, 
				leagueTeam2_1_2.getName(), leagueTeam2_2_1.getName(), 
				leagueTeam2_3_1.getName(), leagueTeam2_4_1.getName(), leagueTeam2_1_1.getName()));
	}
	
	private String getChooseDivisionWinnerMessage(Conference conference, Division division) {
		StringBuilder divisionWinnerMessageBuilder = new StringBuilder();
		divisionWinnerMessageBuilder.append(conference.getName() + " " + 
				division.getName() + " Champion\n"); 
		divisionWinnerMessageBuilder.append(
				"Please enter in an integer corresponding to one of the following:\n");
		
		List<Team> divisionTeams = division.getTeams();
		int teamIndex = 1;
		for (Team team : divisionTeams) {
			divisionWinnerMessageBuilder.append(teamIndex + ". ");
			divisionWinnerMessageBuilder.append(team.getName());
			divisionWinnerMessageBuilder.append("\n");
			teamIndex++;
		}
		divisionWinnerMessageBuilder.deleteCharAt(
				divisionWinnerMessageBuilder.lastIndexOf("\n"));
		
		
		return divisionWinnerMessageBuilder.toString();
	}
	
	
	private String getChooseWildcardMessage(Conference conference, String divisionWinner1, 
			String divisionWinner2, String divisionWinner3, String divisionWinner4, 
			String otherWildcard) {
		StringBuilder wildcardMessageBuilder = new StringBuilder();
		
		wildcardMessageBuilder.append(conference.getName() + " Wildcard"); 
		wildcardMessageBuilder.append(
				"\nPlease enter in an integer corresponding to one of the following:\n");
		
		List<Team> conferenceTeams = conference.getTeams();
		int teamIndex = 1;
		for (Team team : conferenceTeams) {
			String teamName = team.getName();
			if (!divisionWinner1.equalsIgnoreCase(teamName) && 
					!divisionWinner2.equalsIgnoreCase(teamName) && 
					!divisionWinner3.equalsIgnoreCase(teamName) &&
					!divisionWinner4.equalsIgnoreCase(teamName) &&
					!teamName.equalsIgnoreCase(otherWildcard)) {
				wildcardMessageBuilder.append(teamIndex + ". ");
				wildcardMessageBuilder.append(teamName);
				wildcardMessageBuilder.append("\n");
				teamIndex++;
			}
		}
		wildcardMessageBuilder.deleteCharAt(wildcardMessageBuilder.lastIndexOf("\n"));
		
		return wildcardMessageBuilder.toString();
	}
	
	private void verifyChooseDivisionWinnersMessages() {
		List<NFLPlayoffTeam> conference1DivisionWinners = 
				playoffConference1.getDivisionWinners();
		List<NFLPlayoffTeam> conference1DivisionWinnersCopy = new ArrayList<NFLPlayoffTeam>();
		conference1DivisionWinnersCopy.addAll(conference1DivisionWinners);
		
		int conferenceSeed = 1;
		String chooseSeedFromDivisionWinnersMessage = 
				getChooseSeedFromDivisionWinnersMessage(conference1DivisionWinnersCopy,
				conferenceSeed);
		verify(input, times(3)).askForInt(chooseSeedFromDivisionWinnersMessage);
		
		conferenceSeed++;
		conference1DivisionWinnersCopy.remove(playoffTeam1_2_3);
		chooseSeedFromDivisionWinnersMessage = 
				getChooseSeedFromDivisionWinnersMessage(conference1DivisionWinnersCopy,
				conferenceSeed);
		verify(input, times(2)).askForInt(chooseSeedFromDivisionWinnersMessage);
	}

	private void verifyReseedWildcardMessages() {
		List<NFLPlayoffTeam> conference2Wildcards = new ArrayList<NFLPlayoffTeam>();
		conference2Wildcards.add(playoffConference2.getTeamWithSeed(5));
		conference2Wildcards.add(playoffConference2.getTeamWithSeed(6));
		
		StringBuilder reseedWildcardBuilder = new StringBuilder();
		reseedWildcardBuilder.append(leagueConference2.getName() + " Seed 5\n");
		int wildcardIndex = 1;
		for (NFLPlayoffTeam wildcard : conference2Wildcards) {
			Team leagueWildcard = wildcard.getTeam();
			String wildcardName = leagueWildcard.getName();
			reseedWildcardBuilder.append(wildcardIndex + ". " + wildcardName + "\n");
			wildcardIndex++;
		}
		reseedWildcardBuilder.deleteCharAt(reseedWildcardBuilder.length() - 1);
		String reseedWildcardMessage = reseedWildcardBuilder.toString();
		verify(input, times(3)).askForInt(reseedWildcardMessage);
	}
	
	private String getAllTeamAndRoundChancesMessage() {
		StringBuilder teamAndRoundsMessageBuilder = new StringBuilder();
		
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			teamAndRoundsMessageBuilder.append(conferenceName + "\n");
			
			for (int i = 1; i <= 6; i++) {
				NFLPlayoffTeam playoffTeam = playoffConference.getTeamWithSeed(i);
				Team leagueTeam = playoffTeam.getTeam();
				String teamName = leagueTeam.getName();
					
				teamAndRoundsMessageBuilder.append(i + ". " + teamName + "\n");
				teamAndRoundsMessageBuilder.append("Make Divisional Round: " + 
						playoffTeam.getChanceOfMakingDivisionalRound() + "%\n");
				teamAndRoundsMessageBuilder.append("Make Conference Round: " + 
						playoffTeam.getChanceOfMakingConferenceRound() + "%\n");
				teamAndRoundsMessageBuilder.append("Win Conference: " + 
						playoffTeam.getChanceOfMakingSuperBowl() + "%\n");
				teamAndRoundsMessageBuilder.append("Win Super Bowl: " + 
						playoffTeam.getChanceOfWinningSuperBowl() + "%\n");
				teamAndRoundsMessageBuilder.append("\n");
			}
			teamAndRoundsMessageBuilder.append("\n");
		}
		
		return teamAndRoundsMessageBuilder.toString();
	}
	
	private void verifySaveSettingsFailureOccurs() throws IOException {
		String expectedMessageWithSuccessfulSave = "Playoffs Save FAILED\n" +
				expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMessageWithSuccessfulSave);
		
		verify(playoffSettings).saveToSettingsFile(playoffs, fileWriterFactory);
	}
	
	private void verifyLoadSettingsFailureOccurs() throws IOException {
		String expectedMessageWithFailedLoad = "Playoffs Load FAILED\n" +
				expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMessageWithFailedLoad);
		
		verify(playoffSettings).loadSettingsFile(fileReaderFactory);
	}

	private String getChooseSeedFromDivisionWinnersMessage(
			List<NFLPlayoffTeam> conference1DivisionWinnersCopy,
			int conferenceSeed) {
		StringBuilder reseedDivisionWinnersMessage = new StringBuilder();
		
		reseedDivisionWinnersMessage.append(leagueConference1.getName() + 
				" Seed " + conferenceSeed + "\n");
		
		appendListOfUnchosenDivisionWinners(conference1DivisionWinnersCopy,
				reseedDivisionWinnersMessage);
		
		reseedDivisionWinnersMessage.deleteCharAt(reseedDivisionWinnersMessage.length() - 1);
		
		return reseedDivisionWinnersMessage.toString();
	}

	private void appendListOfUnchosenDivisionWinners(
			List<NFLPlayoffTeam> conference1DivisionWinnersCopy,
			StringBuilder reseedDivisionWinnersMessage) {
		int divisionWinnerIndex = 1;
		for (NFLPlayoffTeam divisionWinner : conference1DivisionWinnersCopy) {
			Team leagueDivisionWinner = divisionWinner.getTeam();
			String teamName = leagueDivisionWinner.getName();
			reseedDivisionWinnersMessage.append(divisionWinnerIndex + ". " + 
					teamName + "\n");
			divisionWinnerIndex++;
		}
	}
	
}
