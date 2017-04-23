package nfl.season.menu;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.input.NFLSeasonInput;
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

	private static final int EXIT_FROM_TEAMS_MENU = 3;
	
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
	
	int[] indexesChosenForPowerRankingsInOrder = {2, 2, 1, 1};
	
	@Mock
	private SingleTeamMenu singleTeamMenu;
	
	private String expectedMenuMessage;
	
	String confirmationMessage;
	
	@Before
	public void setUp() {
		teamsMenu = new TeamsMenu(input, nfl);
		teamsMenu.setSubMenu(singleTeamMenu, TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		
		expectedMenuMessage = 
				MenuOptionsUtil.MENU_INTRO + "1. Select Team\n2. Set all Team Power Rankings\n3. Back to Main Menu";
		
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
	
}
