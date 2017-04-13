package nfl.season.calculator;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.NFLTeamEnum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TeamsMenuTest {

	private TeamsMenu teamsMenu;
	
	@Mock
	private NFLSeasonInput input;
	
	private String expectedMenuMessage;
	
	@Before
	public void setUp() {
		teamsMenu = new TeamsMenu(input);
		
		expectedMenuMessage = 
				MenuOptionsUtil.MENU_INTRO + "1. Select Team\n2. Set all Team Power Rankings\n3. Back to Main Menu";
	}
	
	@Test
	public void teamMenuIsLaunchedAndSelectTeamIsSelectedSoTeamsAreListed() {
		when(input.askForInt(anyString())).thenReturn(1, NFLTeamEnum.values().length + 1, 3);
		
		teamsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		
		StringBuilder teamListMessage = new StringBuilder();
		teamListMessage.append(MenuOptionsUtil.MENU_INTRO);
		int teamIndex = 1;
		for (NFLTeamEnum nflTeam : NFLTeamEnum.values()) {
			teamListMessage.append(teamIndex + ". ");
			teamListMessage.append(nflTeam.getTeamName());
			teamListMessage.append("\n");
		}
		teamListMessage.append((NFLTeamEnum.values().length + 1) + ". Back to Team Menu");
		
		verify(input, times(1)).askForInt(teamListMessage.toString());
	}
	
}
