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
	
	private static final int CALCULATE_BASED_OFF_POWER_RANKINGS = 3;
	
	private static final int EXIT_OPTION = 4;
	
	private String coltsName = "Colts";
	
	private String eaglesName = "Eagles";
	
	private int coltsWinChance = 55;
	
	private int eaglesWinChance = 45;
	
	private String[] teamNames;
	
	private String expectedMenuMessage;
	
	private String expectedSetWinChanceMessage;
	
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
		when(matchup.getTeamWinChance(coltsName)).thenReturn(coltsWinChance);
		when(matchup.getTeamWinChance(eaglesName)).thenReturn(eaglesWinChance);
		when(matchup.getTeamPowerRanking(coltsName)).thenReturn(15);
		when(matchup.getTeamPowerRanking(eaglesName)).thenReturn(2);
		when(matchup.getWinChanceMode()).thenReturn(Matchup.WinChanceModeEnum.CUSTOM_SETTING);
		when(matchup.calculateTeamWinChancesFromPowerRankings()).thenReturn(true);
		
		setExpectedMenuMessage();
	}
	
	@Test
	public void teamsWinChancesAreSet() {
		int expectedTeam1WinChance = 15;
		int expectedTeam2WinChance = 48;
		
		when(matchup.getTeamWinChance(coltsName)).thenReturn(50, 50, expectedTeam1WinChance, 
				(100 - expectedTeam2WinChance), 50, expectedTeam1WinChance, 
				(100 - expectedTeam2WinChance), 50);
		when(matchup.getTeamWinChance(eaglesName)).thenReturn(50, (100 - expectedTeam1WinChance), 
				(100 - expectedTeam1WinChance), expectedTeam2WinChance, 50, 
				(100 - expectedTeam1WinChance), expectedTeam2WinChance, 
				(100 - expectedTeam1WinChance));
		
		when(input.askForInt(anyString())).thenReturn(SET_FIRST_TEAM_WIN_CHANCE, 
				expectedTeam1WinChance, SET_SECOND_TEAM_WIN_CHANCE, expectedTeam2WinChance,
				EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		setExpectedMenuMessage();
		verify(input, times(1)).askForInt(expectedMenuMessage);
		setExpectedMenuMessage();
		verify(input, times(1)).askForInt(expectedMenuMessage);
		setExpectedMenuMessage();
		verify(input, times(1)).askForInt(expectedMenuMessage);
		
		setExpectedSetWinChanceMessage(coltsName);
		verify(input, times(1)).askForInt(expectedSetWinChanceMessage);
		setExpectedSetWinChanceMessage(eaglesName);
		verify(input, times(1)).askForInt(expectedSetWinChanceMessage);
		
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
		setExpectedSetWinChanceMessage(coltsName);
		verify(input, times(4)).askForInt(expectedSetWinChanceMessage);
		
		verify(matchup).setTeamWinChance(coltsName, 45);
		verify(matchup, never()).setTeamWinChance(coltsName, 100);
		verify(matchup, never()).setTeamWinChance(coltsName, 0);
		verify(matchup, never()).setTeamWinChance(coltsName, -2);
	}
	
	@Test
	public void calculateWinChanceBasedOnPowerRankingsSoCalculationIsCalledFor() {
		when(input.askForInt(anyString())).thenReturn(CALCULATE_BASED_OFF_POWER_RANKINGS, 
				EXIT_OPTION);
		when(matchup.getTeamWinChance(coltsName)).thenReturn(55, 58);
		when(matchup.getTeamWinChance(eaglesName)).thenReturn(45, 42);
		when(matchup.getWinChanceMode()).thenReturn(Matchup.WinChanceModeEnum.CUSTOM_SETTING,
				Matchup.WinChanceModeEnum.POWER_RANKINGS);
		
		matchupMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		
		when(matchup.getTeamWinChance(coltsName)).thenReturn(58);
		when(matchup.getTeamWinChance(eaglesName)).thenReturn(42);
		when(matchup.getWinChanceMode()).thenReturn(Matchup.WinChanceModeEnum.POWER_RANKINGS);
		
		setExpectedMenuMessage();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		
		verify(matchup).calculateTeamWinChancesFromPowerRankings();
	}
	
	@Test
	public void calculateWinChanceFailsSoTellUserToSetPowerRankings() {
		when(matchup.calculateTeamWinChancesFromPowerRankings()).thenReturn(false);
		when(input.askForInt(anyString())).thenReturn(CALCULATE_BASED_OFF_POWER_RANKINGS, 
				EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		expectedMenuMessage = "Could not calculate; set Power Rankings on both " +
				"teams.\n" + expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMenuMessage);
	}
	
	private void setExpectedMenuMessage() {
		String selectedTeamName = matchupMenu.getSelectedTeamName();
		String afterOneTeamMessage = " win chance\n";
		expectedMenuMessage = "Matchup: " + teamNames[0] + " vs. " + teamNames[1] + 
				"\n" + "Current win chances:\n" + teamNames[0] + ": " + 
				matchup.getTeamWinChance(teamNames[0]) + "\n" + teamNames[1] + 
				": " + matchup.getTeamWinChance(teamNames[1]) + "\n" + 
				"Current win chance determiner: " + 
				matchup.getWinChanceMode().winChanceModeDescription + "\n" +
				MenuOptionsUtil.MENU_INTRO + "1. Set " + teamNames[0] + 
				afterOneTeamMessage + "2. Set " + teamNames[1] + 
				afterOneTeamMessage + "3. Calculate and set win chances based " +
				"off teams' Power Rankings: #" + matchup.getTeamPowerRanking(teamNames[0]) + 
				" vs. #" + matchup.getTeamPowerRanking(teamNames[1]) + 
				"\n4. Back to " + selectedTeamName + " Matchup List";
	}
	
	private void setExpectedSetWinChanceMessage(String teamName) {
		expectedSetWinChanceMessage = "Current " + teamName + " win chance: " + 
				matchup.getTeamWinChance(teamName) + 
				"\nPlease enter in a number between 1 and 99";
	}
	
}
