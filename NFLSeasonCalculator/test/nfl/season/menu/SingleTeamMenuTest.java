package nfl.season.menu;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
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
	private League nfl;
	
	@Mock
	private Team colts;
	
	@Mock
	private Team eagles;
	
	private SingleTeamMenu singleTeamMenu;
	
	@Before
	public void setUp() {
		singleTeamMenu = new SingleTeamMenu(input, nfl);
		singleTeamMenu.setTeam(colts);
		
		expectedMenuMessage = 
				MenuOptionsUtil.MENU_INTRO + "1. Set Power Ranking\n2. Back to Teams Menu";
		
		expectedPowerRankingsMessage = "Please enter in a number between 1-32:";
		
		when(colts.getName()).thenReturn("Colts");
		when(eagles.getName()).thenReturn("Eagles");
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
	
	@Test
	public void setPowerRankingThatOtherTeamAlreadyHasSoAskAndThenOverwriteOtherTeam() {
		int powerRanking = 11;
		String eaglesName = eagles.getName();
		String overwriteMessage = "The " + eaglesName + " already are #" + 
				powerRanking + ". Clear the " + eaglesName + " ranking and assign #" + 
				powerRanking + " to " + colts.getName() + "? (Y/N)";
		
		when(nfl.getTeamWithPowerRanking(powerRanking)).thenReturn(eagles);
		when(input.askForInt(anyString())).thenReturn(SET_POWER_RANKING, powerRanking, 
				SET_POWER_RANKING, powerRanking, EXIT_FROM_SINGLE_TEAM_MENU);
		when(input.askForString(overwriteMessage)).thenReturn("Y", "y");
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(expectedPowerRankingsMessage);
		verify(input, times(2)).askForString(overwriteMessage);
		verify(colts, times(2)).setPowerRanking(powerRanking);
		verify(eagles, times(2)).setPowerRanking(-1);
	}
	
	@Test
	public void setPowerRankingThatOtherTeamAlreadyHasSoAskAndThenAskForNewRanking() {
		int overwritePowerRanking = 8;
		int newPowerRanking = 15;
		String eaglesName = eagles.getName();
		String overwriteMessage = "The " + eaglesName + " already are #" + 
				overwritePowerRanking + ". Clear the " + eaglesName + " ranking and assign #" + 
				overwritePowerRanking + " to " + colts.getName() + "? (Y/N)";
		
		when(nfl.getTeamWithPowerRanking(overwritePowerRanking)).thenReturn(eagles);
		when(input.askForInt(anyString())).thenReturn(SET_POWER_RANKING, overwritePowerRanking, 
				overwritePowerRanking, newPowerRanking, EXIT_FROM_SINGLE_TEAM_MENU);
		when(input.askForString(overwriteMessage)).thenReturn("N", "n");
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(3)).askForInt(expectedPowerRankingsMessage);
		verify(input, times(2)).askForString(overwriteMessage);
		verify(colts, times(1)).setPowerRanking(newPowerRanking);
		verify(eagles, never()).setPowerRanking(anyInt());
	}
	
	@Test
	public void setPowerRankingThatOtherTeamAlreadyHasSoAskButGetBadInputWhichIsIgnored() {
		int overwritePowerRanking = 8;
		String eaglesName = eagles.getName();
		String overwriteMessage = "The " + eaglesName + " already are #" + 
				overwritePowerRanking + ". Clear the " + eaglesName + " ranking and assign #" + 
				overwritePowerRanking + " to " + colts.getName() + "? (Y/N)";
		
		when(nfl.getTeamWithPowerRanking(overwritePowerRanking)).thenReturn(eagles);
		when(input.askForInt(anyString())).thenReturn(SET_POWER_RANKING, overwritePowerRanking, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		when(input.askForString(overwriteMessage)).thenReturn("ab", "Y");
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt(expectedPowerRankingsMessage);
		verify(input, times(2)).askForString(overwriteMessage);
		verify(colts, times(1)).setPowerRanking(overwritePowerRanking);
		verify(eagles, times(1)).setPowerRanking(-1);
	}
	
}
