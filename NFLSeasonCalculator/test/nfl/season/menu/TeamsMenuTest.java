package nfl.season.menu;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.InputMismatchException;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;
import nfl.season.menu.MenuOptionsUtil;
import nfl.season.menu.SingleTeamMenu;
import nfl.season.menu.SubMenu;
import nfl.season.menu.TeamsMenu;
import nfl.season.menu.TeamsMenu.TeamsMenuOptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TeamsMenuTest {

	private static final int GO_TO_TEAM_SELECT = 1;

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
	private SingleTeamMenu singleTeamMenu;
	
	private String expectedMenuMessage;
	
	@Before
	public void setUp() {
		teamsMenu = new TeamsMenu(input, nfl);
		teamsMenu.setSubMenu(singleTeamMenu, TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		
		expectedMenuMessage = 
				MenuOptionsUtil.MENU_INTRO + "1. Select Team\n2. Set all Team Power Rankings\n3. Back to Main Menu";
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
	public void teamMenuIgnoresNonIntInput() {
		when(input.askForInt(anyString())).thenThrow(new InputMismatchException()).thenReturn(3);
		
		teamsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
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
	public void nonIntIsInputAtTeamSelectAndInputIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_TEAM_SELECT).thenThrow(
				new InputMismatchException()).thenReturn(EXIT_FROM_TEAM_SELECT, 
						EXIT_FROM_TEAMS_MENU);
		
		teamsMenu.launchSubMenu();
		
		String teamListMessage = buildTeamListMessage();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(teamListMessage);
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
	
}
