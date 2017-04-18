package nfl.season.menu;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SingleTeamMenuTest {
	
	private static final int SET_POWER_RANKING = 1;

	private static final int EXIT_FROM_SINGLE_TEAM_MENU = 2;
	
	private String expectedMenuMessage;
	
	private String expectedPowerRankingsMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private Team colts;
	
	private SingleTeamMenu singleTeamMenu;
	
	@Before
	public void setUp() {
		singleTeamMenu = new SingleTeamMenu(input);
		singleTeamMenu.setTeam(colts);
		
		expectedMenuMessage = 
				MenuOptionsUtil.MENU_INTRO + "1. Set Power Ranking\n2. Back to Teams Menu";
		
		expectedPowerRankingsMessage = "Please enter in a number between 1-32:";
	}
	
	@Test
	public void teamPowerRankingIsSet() {
		when(input.askForInt(anyString())).thenReturn(SET_POWER_RANKING, 15, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt(expectedPowerRankingsMessage);
		
		verify(colts, times(1)).setPowerRanking(15);
	}
	
	@Test
	public void intOutsideOfExpectedRangeIsInputSoInputIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(-2, EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void intOutsideOfExpectedRangeIsInputForPowerRankingSoInputIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(SET_POWER_RANKING, 999, 13, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(expectedPowerRankingsMessage);
		verify(colts, times(1)).setPowerRanking(13);
	}
	
}
