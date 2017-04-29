package nfl.season.menu;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.Matchup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MatchupMenuTest {

	@Mock
	private Matchup matchup;
	
	private static final int SET_FIRST_TEAM_WIN_CHANCE = 1;
	
	private static final int SET_SECOND_TEAM_WIN_CHANCE = 2;
	
	private static final int EXIT_OPTION = 3;
	
	private String coltsName = "Colts";
	
	private String eaglesName = "Eagles";
	
	private String[] teamNames;
	
	private String expectedMenuMessage;
	
	private String expectedSetWinChanceMessage = 
			"Please enter in a number between 1 and 99";
	
	@Mock
	private NFLSeasonInput input;
	
	private MatchupMenu matchupMenu;
	
	@Before
	public void setUp() {
		matchupMenu = new MatchupMenu(input);
		matchupMenu.setMatchup(matchup);
		matchupMenu.setSelectedTeamName(coltsName);
		
		teamNames = new String[2];
		teamNames[0] = coltsName;
		teamNames[1] = eaglesName;
		
		when(matchup.getTeamNames()).thenReturn(teamNames);
		when(matchup.getOpponentName(coltsName)).thenReturn(eaglesName);
		
		setExpectedMenuMessage();
	}
	
	@Test
	public void teamsWinChancesAreSet() {
		int expectedTeam1WinChance = 15;
		int expectedTeam2WinChance = 48;
		
		when(input.askForInt(anyString())).thenReturn(SET_FIRST_TEAM_WIN_CHANCE, 
				expectedTeam1WinChance, SET_SECOND_TEAM_WIN_CHANCE, expectedTeam2WinChance,
				EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
		
		verify(input, times(2)).askForInt(expectedSetWinChanceMessage);
		
		verify(matchup).setTeamWinChance(coltsName, expectedTeam1WinChance);
		verify(matchup).setTeamWinChance(eaglesName, expectedTeam2WinChance);
	}
	
	@Test
	public void invalidMenuOptionIsEnteredSoInvalidOptionIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(999, EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		verify(input, times(2)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void invalidWinChanceIsEnteredSoInvalidWinChanceIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(SET_FIRST_TEAM_WIN_CHANCE, 
				100, 0, -1, 45, EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		
		verify(input, times(4)).askForInt(expectedSetWinChanceMessage);
		
		verify(matchup).setTeamWinChance(coltsName, 45);
		verify(matchup, never()).setTeamWinChance(coltsName, 100);
		verify(matchup, never()).setTeamWinChance(coltsName, 0);
		verify(matchup, never()).setTeamWinChance(coltsName, -2);
	}
	
	private void setExpectedMenuMessage() {
		String selectedTeamName = matchupMenu.getSelectedTeamName();
		String afterOneTeamMessage = " win chance\n";
		expectedMenuMessage = "Matchup: " + teamNames[0] + " vs. " + teamNames[1] + 
				"\n" + MenuOptionsUtil.MENU_INTRO + "1. Set " + teamNames[0] + 
				afterOneTeamMessage + "2. Set " + teamNames[1] + 
				afterOneTeamMessage + "3. Back to " + selectedTeamName + " Menu";
	}
	
}
