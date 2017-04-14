package nfl.season.calculator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.calculator.TeamsMenu.TeamsMenuOptions;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TeamsMenuTest {

	private static final int COLTS_INDEX = 10;

	private TeamsMenu teamsMenu;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private League nfl;
	
	@Mock
	Team colts;
	
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
		when(input.askForInt(anyString())).thenReturn(1, NFLTeamEnum.values().length + 1, 3);
		
		teamsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		
		String teamListMessage = buildTeamListMessage();
		
		verify(input, times(1)).askForInt(teamListMessage);
	}
	
	@Test
	public void teamIsSelectedAndPowerRankingIsSet() {
		when(input.askForInt(anyString())).thenReturn(1, COLTS_INDEX, NFLTeamEnum.values().length + 1, 3);
		when(nfl.getTeam(COLTS_INDEX)).thenReturn(colts);
		
		teamsMenu.launchSubMenu();
		
		String teamListMessage = buildTeamListMessage();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(teamListMessage);
		verify(singleTeamMenu).setTeam(colts);
		verify(singleTeamMenu, times(1)).launchSubMenu();
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
		}
		teamListMessage.append((NFLTeamEnum.values().length + 1) + ". Back to Team Menu");
		return teamListMessage.toString();
	}
	
}
