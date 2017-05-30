package nfl.season.menu;

import static org.junit.Assert.assertEquals;
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

import nfl.season.input.NFLSeasonInput;
import nfl.season.input.NFLTeamSettings;
import nfl.season.input.NFLTeamSettingsFileReaderFactory;
import nfl.season.input.NFLTeamSettingsFileWriterFactory;
import nfl.season.league.League;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;
import nfl.season.menu.TeamsMenu.TeamsMenuOptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TeamsMenuTest {

	private static final int GO_TO_TEAM_SELECT = 1;
	
	private static final int GO_TO_SET_ALL_RANKINGS = 2;
	
	private static final int SET_ALL_MATCHUPS_BY_ELO = 3;
	
	private static final int RESET_TO_DEFAULTS = 4;
	
	private static final int LOAD_SAVED_SETTINGS = 5;
	
	private static final int SAVE_CURRENT_TEAM_SETTINGS = 6;

	private static final int EXIT_FROM_TEAMS_MENU = 7;
	
	private static final int EXIT_FROM_TEAM_SELECT = NFLTeamEnum.values().length + 1;

	private static final int COLTS_INDEX = 10;

	private TeamsMenu teamsMenu;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private League nfl;
	
	@Mock
	private Team colts;
	
	@Mock
	private Team patriots;
	
	@Mock
	private Team falcons;
	
	@Mock
	private Team steelers;
	
	@Mock
	private Team packers;
	
	private List<Team> mockTeams;
	
	private int[] indexesChosenForPowerRankingsInOrder = {2, 2, 1, 1};
	
	@Mock
	private SingleTeamMenu singleTeamMenu;
	
	@Mock
	private NFLTeamSettings nflTeamSettings;
	
	@Mock
	private NFLTeamSettingsFileWriterFactory fileWriterFactory;
	
	@Mock
	private NFLTeamSettingsFileReaderFactory fileReaderFactory;
	
	private String loadedSettingsFileString = "Load Settings File";
	
	private String expectedMenuMessage;
	
	private String confirmationMessage;
	
	@Before
	public void setUp() {
		teamsMenu = new TeamsMenu(input, nfl, nflTeamSettings, fileWriterFactory, 
				fileReaderFactory);
		teamsMenu.setSubMenu(singleTeamMenu, TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		
		expectedMenuMessage = 
				MenuOptionsUtil.MENU_INTRO + "1. Select Team\n" +
				"2. Set all Team Power Rankings\n" +
				"3. Set all Teams' Matchups using Elo Rating Calculations\n" + 
				"4. Revert All Teams and Matchups to Default Settings\n" +
				"5. Load Saved Team and Matchup Settings\n" +
				"6. Save Current Team and Matchup Settings\n" +
				"7. Back to Main Menu";
		
		confirmationMessage = "All rankings will be cleared. Proceed? (Y/N)";
		
		when(steelers.getName()).thenReturn("Steelers");
		when(patriots.getName()).thenReturn("Patriots");
		when(falcons.getName()).thenReturn("Falcons");
		when(packers.getName()).thenReturn("Packers");
		
		mockTeams = new ArrayList<Team>();
		mockTeams.add(steelers);
		mockTeams.add(patriots);
		mockTeams.add(falcons);
		mockTeams.add(packers);
		List<Team> mockTeamsCopy = new ArrayList<Team>();
		mockTeamsCopy.addAll(mockTeams);
		when(nfl.getTeams()).thenReturn(mockTeamsCopy);
	}
	
	@Test
	public void teamMenuIsLaunchedAndSelectTeamIsSelectedSoTeamsAreListed() {
		when(input.askForInt(anyString())).thenReturn(1, EXIT_FROM_TEAM_SELECT, EXIT_FROM_TEAMS_MENU);
		
		teamsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		
		String teamListMessage = buildTeamListMessage();
		
		verify(input, times(1)).askForInt(teamListMessage);
	}
	
	@Test
	public void teamMenuIgnoresInputOutsideOfExpectedRange() {
		when(input.askForInt(anyString())).thenReturn(-2, EXIT_FROM_TEAMS_MENU);
		
		teamsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void teamIsSelectedAndPowerRankingIsSet() {
		when(input.askForInt(anyString())).thenReturn(1, COLTS_INDEX, 
				EXIT_FROM_TEAM_SELECT, EXIT_FROM_TEAMS_MENU);
		when(nfl.getTeam(COLTS_INDEX)).thenReturn(colts);
		
		teamsMenu.launchSubMenu();
		
		String teamListMessage = buildTeamListMessage();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(teamListMessage);
		verify(singleTeamMenu).setTeam(colts);
		verify(singleTeamMenu, times(1)).launchSubMenu();
	}
	
	@Test
	public void intOutsideExpectedInputIsPutInTeamSelectAndInputIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_TEAM_SELECT, 999, 
				EXIT_FROM_TEAM_SELECT, EXIT_FROM_TEAMS_MENU);
		
		teamsMenu.launchSubMenu();
		
		String teamListMessage = buildTeamListMessage();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(teamListMessage);
	}
	
	@Test
	public void setSubMenuWithSingleTeamsMenuSoSingleTeamsMenuIsSet() {
		teamsMenu.setSubMenu(singleTeamMenu, TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		
		SubMenu returnedSingleTeamMenu = teamsMenu.getSingleTeamMenu();
		
		assertEquals(singleTeamMenu, returnedSingleTeamMenu);
	}
	
	@Test
	public void setAllRankingsIsSelectedAndThenExitedImmediately() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_SET_ALL_RANKINGS, 
				EXIT_FROM_TEAM_SELECT, EXIT_FROM_TEAMS_MENU);
		when(input.askForString(confirmationMessage)).thenReturn("Y");
		
		teamsMenu.launchSubMenu();
		
		String teamListMessage = buildTeamListMessage();
		teamListMessage = "Set #1\n" + teamListMessage;
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verifyTeamListMessagesForSetAllRankings(1, 1);
		
		verifyNoPowerRankingsWereSet();
	}

	@Test
	public void setAllRankingsPutInFourRankingsSoFourRankingsAreSet() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_SET_ALL_RANKINGS, 
				indexesChosenForPowerRankingsInOrder[0], 
				indexesChosenForPowerRankingsInOrder[1], 
				indexesChosenForPowerRankingsInOrder[2],
				indexesChosenForPowerRankingsInOrder[3],
				EXIT_FROM_TEAMS_MENU);
		when(input.askForString(confirmationMessage)).thenReturn("Y");
		
		teamsMenu.launchSubMenu();
		
		verifyTeamListMessagesForSetAllRankings(4, 1);
		verify(input, times(1)).askForString(confirmationMessage);
		verify(input, never()).askForInt(
				"Set #5\nPlease enter in an integer corresponding to one of "
				+ "the following:\n33. Back to Team Menu");
		
		verify(patriots).setPowerRanking(Team.CLEAR_RANKING);
		verify(falcons).setPowerRanking(Team.CLEAR_RANKING);
		verify(steelers).setPowerRanking(Team.CLEAR_RANKING);
		verify(packers).setPowerRanking(Team.CLEAR_RANKING);
		
		verify(patriots).setPowerRanking(1);
		verify(falcons).setPowerRanking(2);
		verify(steelers).setPowerRanking(3);
		verify(packers).setPowerRanking(4);
	}
	
	@Test
	public void setAllRankingsButBackOutOfConfirmationMessageSoNoRankingIsAskedFor() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_SET_ALL_RANKINGS, EXIT_FROM_TEAMS_MENU);
		when(input.askForString(confirmationMessage)).thenReturn("N");
		
		teamsMenu.launchSubMenu();
		
		verifyNoPowerRankingsWereSet();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForString(confirmationMessage);
	}
	
	@Test
	public void setAllRankingsButInvalidInputIsPutIntoConfirmationMessageAndIgnored() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_SET_ALL_RANKINGS, EXIT_FROM_TEAMS_MENU);
		when(input.askForString(confirmationMessage)).thenReturn("ab", "N");
		
		teamsMenu.launchSubMenu();
		
		verifyNoPowerRankingsWereSet();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForString(confirmationMessage);
	}
	
	@Test
	public void setAllRankingsIsDoneOneIsSetThenOldMaxIsSelectedAndIgnored() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_SET_ALL_RANKINGS, 
				indexesChosenForPowerRankingsInOrder[0],
				mockTeams.size(),
				EXIT_FROM_TEAM_SELECT, 
				EXIT_FROM_TEAMS_MENU);
		when(input.askForString(confirmationMessage)).thenReturn("Y");
		
		teamsMenu.launchSubMenu();
		
		verifyTeamListMessagesForSetAllRankings(2, 2);
	}
	
	@Test
	public void setAllMatchupsToEloRatingsSetsAllMatchupsForAllTeams() {
		when(input.askForInt(anyString())).thenReturn(SET_ALL_MATCHUPS_BY_ELO, 
				EXIT_FROM_TEAMS_MENU);
		
		teamsMenu.launchSubMenu();
		
		for (Team team : mockTeams) {
			verify(team).calculateAllMatchupsUsingEloRatings();
		}
	}
	
	@Test
	public void revertAllTeamsToDefaultsSoTeamsAreReset() {
		when(input.askForInt(anyString())).thenReturn(RESET_TO_DEFAULTS, EXIT_FROM_TEAMS_MENU);
		
		teamsMenu.launchSubMenu();
		
		for (Team team : mockTeams) {
			verify(team).resetToDefaults();
		}
	}
	
	@Test
	public void saveTeamSettingsCallsOnTeamSettingsSaver() throws IOException {
		when(input.askForInt(anyString())).thenReturn(SAVE_CURRENT_TEAM_SETTINGS, 
				EXIT_FROM_TEAMS_MENU);
		when(nflTeamSettings.saveToSettingsFile(any(League.class), 
				any(NFLTeamSettingsFileWriterFactory.class))).thenReturn(true);
		
		teamsMenu.launchSubMenu();
		
		String expectedMessageWithSuccessfulSave = "Team Settings Saved Successfully\n" +
				expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMessageWithSuccessfulSave);
		
		verify(nflTeamSettings).saveToSettingsFile(nfl, fileWriterFactory);
	}
	
	@Test
	public void saveTeamSettingsFailsSoUserIsNotified() throws IOException {
		when(input.askForInt(anyString())).thenReturn(SAVE_CURRENT_TEAM_SETTINGS, 
				EXIT_FROM_TEAMS_MENU);
		doThrow(new IOException()).when(nflTeamSettings).saveToSettingsFile(nfl, 
				fileWriterFactory);
		
		teamsMenu.launchSubMenu();
		
		verifySaveSettingsFailureOccurs();
	}
	
	@Test
	public void saveTeamSettingsFailsWithFalseBooleanSoUserIsNotified() throws IOException {
		when(input.askForInt(anyString())).thenReturn(SAVE_CURRENT_TEAM_SETTINGS,
				EXIT_FROM_TEAMS_MENU);
		when(nflTeamSettings.saveToSettingsFile(nfl, fileWriterFactory)).thenReturn(false);
		
		teamsMenu.launchSubMenu();
		
		verifySaveSettingsFailureOccurs();
	}
	
	@Test
	public void loadTeamSettingsPullsInSavedSettings() throws IOException {
		when(input.askForInt(anyString())).thenReturn(LOAD_SAVED_SETTINGS, 
				EXIT_FROM_TEAMS_MENU);
		when(nflTeamSettings.loadSettingsFile(fileReaderFactory)).thenReturn(
				loadedSettingsFileString);
		
		teamsMenu.launchSubMenu();
		
		verify(nflTeamSettings).loadSettingsFile(fileReaderFactory);
		verify(nflTeamSettings).setTeamsSettingsFromTeamSettingsFileString(nfl, 
				loadedSettingsFileString);
		
		String expectedMessageWithSuccessfulLoad = "Team Settings Loaded Successfully\n" +
				expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMessageWithSuccessfulLoad);
	}
	
	@Test
	public void loadTeamSettingsFailsWithExceptionSoFailMessageIsDisplayed() throws IOException {
		when(input.askForInt(anyString())).thenReturn(LOAD_SAVED_SETTINGS, 
				EXIT_FROM_TEAMS_MENU);
		when(nflTeamSettings.loadSettingsFile(fileReaderFactory)).thenThrow(new IOException());
		
		teamsMenu.launchSubMenu();
		
		verifyLoadSettingsFailureOccurs();
	}
	
	@Test
	public void loadTeamSettingsFailsWithEmptyResponseSoFailMessageIsDisplayed() throws IOException {
		when(input.askForInt(anyString())).thenReturn(LOAD_SAVED_SETTINGS, 
				EXIT_FROM_TEAMS_MENU);
		when(nflTeamSettings.loadSettingsFile(fileReaderFactory)).thenReturn("");
		
		teamsMenu.launchSubMenu();
		
		verifyLoadSettingsFailureOccurs();
	}

	private String buildTeamListMessage() {
		StringBuilder teamListMessage = new StringBuilder();
		teamListMessage.append(MenuOptionsUtil.MENU_INTRO);
		int teamIndex = 1;
		for (NFLTeamEnum nflTeam : NFLTeamEnum.values()) {
			teamListMessage.append(teamIndex + ". ");
			teamListMessage.append(nflTeam.getTeamName());
			teamListMessage.append("\n");
			teamIndex++;
		}
		teamListMessage.append(EXIT_FROM_TEAM_SELECT + ". Back to Team Menu");
		return teamListMessage.toString();
	}
	
	private void verifyTeamListMessagesForSetAllRankings(int numberOfRankingsToSet, 
			int numberOfRepeatsOfLastMessage) {
		List<Team> mockTeamsCopy = new ArrayList<Team>();
		mockTeamsCopy.addAll(mockTeams);
		for (int i = 1; i <= numberOfRankingsToSet; i++) {
			StringBuilder teamListForSetAllRankings = new StringBuilder();
			teamListForSetAllRankings.append("Set #" + i + "\n");
			teamListForSetAllRankings.append(MenuOptionsUtil.MENU_INTRO);
			int teamIndex = 1;
			for (Team team : mockTeamsCopy) {
				teamListForSetAllRankings.append(teamIndex + ". ");
				teamListForSetAllRankings.append(team.getName());
				teamListForSetAllRankings.append("\n");
				teamIndex++;
			}
			teamListForSetAllRankings.append(EXIT_FROM_TEAM_SELECT + ". Back to Team Menu");
			
			if (i == numberOfRankingsToSet) {
				verify(input, times(numberOfRepeatsOfLastMessage)).askForInt(
						teamListForSetAllRankings.toString());
			} else {
				verify(input, times(1)).askForInt(teamListForSetAllRankings.toString());
			}
			
			mockTeamsCopy.remove((indexesChosenForPowerRankingsInOrder[i - 1]) - 1);
		}
	}
	
	private void verifyNoPowerRankingsWereSet() {
		verify(patriots, never()).setPowerRanking(anyInt());
		verify(falcons, never()).setPowerRanking(anyInt());
		verify(steelers, never()).setPowerRanking(anyInt());
		verify(packers, never()).setPowerRanking(anyInt());
	}
	
	private void verifySaveSettingsFailureOccurs() throws IOException {
		String expectedMessageWithSuccessfulSave = "Team Settings Save FAILED\n" +
				expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMessageWithSuccessfulSave);
		
		verify(nflTeamSettings).saveToSettingsFile(nfl, fileWriterFactory);
	}

	private void verifyLoadSettingsFailureOccurs() throws IOException {
		String expectedMessageWithFailedLoad = "Team Settings Load FAILED\n" +
				expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMessageWithFailedLoad);
		
		verify(nflTeamSettings).loadSettingsFile(fileReaderFactory);
	}
	
}
