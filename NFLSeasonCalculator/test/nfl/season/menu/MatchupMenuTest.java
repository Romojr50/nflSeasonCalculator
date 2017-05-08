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
	
	private static final int CALCULATE_BASED_OFF_ELO_RATINGS = 4;
	
	private static final int SET_FIRST_TEAM_HOME_CHANCE = 5;
	
	private static final int SET_FIRST_TEAM_AWAY_CHANCE = 6;
	
	private static final int SET_SECOND_TEAM_HOME_CHANCE = 7;
	
	private static final int SET_SECOND_TEAM_AWAY_CHANCE = 8;
	
	private static final int CALCULATE_FIRST_HOME_AWAY_BASED_OFF_HOME_FIELD_ADVANTAGE = 9;
	
	private static final int CALCULATE_SECOND_HOME_AWAY_BASED_OFF_HOME_FIELD_ADVANTAGE = 10;
	
	private static final int EXIT_OPTION = 11;
	
	private String coltsName = "Colts";
	
	private String eaglesName = "Eagles";
	
	private int coltsNeutralWinChance = 55;
	
	private int coltsHomeWinChance = 66;
	
	private int coltsAwayWinChance = 49;
	
	private int eaglesNeutralWinChance = 100 - coltsNeutralWinChance;
	
	private int eaglesHomeWinChance = 100 - coltsAwayWinChance;
	
	private int eaglesAwayWinChance = 100 - coltsHomeWinChance;
	
	private String[] teamNames;
	
	private String expectedMenuMessage;
	
	private String expectedSetWinChanceMessage;
	
	private String expectedSetHomeWinChanceMessage;
	
	private String expectedSetAwayWinChanceMessage;
	
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
		when(matchup.getTeamNeutralWinChance(coltsName)).thenReturn(coltsNeutralWinChance);
		when(matchup.getTeamNeutralWinChance(eaglesName)).thenReturn(eaglesNeutralWinChance);
		when(matchup.getTeamHomeWinChance(coltsName)).thenReturn(coltsHomeWinChance);
		when(matchup.getTeamAwayWinChance(coltsName)).thenReturn(coltsAwayWinChance);
		when(matchup.getHomeAwayWinChanceMode(coltsName)).thenReturn(
				Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING);
		when(matchup.getTeamHomeWinChance(eaglesName)).thenReturn(eaglesHomeWinChance);
		when(matchup.getTeamAwayWinChance(eaglesName)).thenReturn(eaglesAwayWinChance);
		when(matchup.getHomeAwayWinChanceMode(eaglesName)).thenReturn(
				Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING);
		when(matchup.getTeamPowerRanking(coltsName)).thenReturn(15);
		when(matchup.getTeamPowerRanking(eaglesName)).thenReturn(2);
		when(matchup.getTeamEloRating(coltsName)).thenReturn(1450);
		when(matchup.getTeamEloRating(eaglesName)).thenReturn(1550);
		when(matchup.getWinChanceMode()).thenReturn(Matchup.WinChanceModeEnum.CUSTOM_SETTING);
		when(matchup.calculateTeamWinChancesFromPowerRankings()).thenReturn(true);
		when(matchup.calculateTeamWinChancesFromEloRatings()).thenReturn(true);
		
		setExpectedMenuMessage();
	}
	
	@Test
	public void teamsWinChancesAreSet() {
		int expectedTeam1WinChance = 15;
		int expectedTeam2WinChance = 48;
		
		when(matchup.getTeamNeutralWinChance(coltsName)).thenReturn(50, 50, expectedTeam1WinChance, 
				(100 - expectedTeam2WinChance), 50, expectedTeam1WinChance, 
				(100 - expectedTeam2WinChance), 50);
		when(matchup.getTeamNeutralWinChance(eaglesName)).thenReturn(50, (100 - expectedTeam1WinChance), 
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
		
		verify(matchup).setTeamNeutralWinChance(coltsName, expectedTeam1WinChance);
		verify(matchup).setTeamNeutralWinChance(eaglesName, expectedTeam2WinChance);
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
		
		verify(matchup).setTeamNeutralWinChance(coltsName, 45);
		verify(matchup, never()).setTeamNeutralWinChance(coltsName, 100);
		verify(matchup, never()).setTeamNeutralWinChance(coltsName, 0);
		verify(matchup, never()).setTeamNeutralWinChance(coltsName, -2);
	}
	
	@Test
	public void calculateWinChanceBasedOnPowerRankingsSoCalculationIsCalledFor() {
		verifySuccessfulCalculationDone(CALCULATE_BASED_OFF_POWER_RANKINGS, 
				Matchup.WinChanceModeEnum.POWER_RANKINGS);
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
	
	@Test
	public void calculateWinChanceFailsThenOtherOptionSelectedSoErrorMessageOnlyShownOnce() {
		when(matchup.calculateTeamWinChancesFromPowerRankings()).thenReturn(false);
		when(input.askForInt(anyString())).thenReturn(CALCULATE_BASED_OFF_POWER_RANKINGS, 
				SET_FIRST_TEAM_WIN_CHANCE, 56, EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		expectedMenuMessage = "Could not calculate; set Power Rankings on both " +
				"teams.\n" + expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void calculateWinChanceBasedOnEloRatingsSoCalculationIsCalledFor() {
		verifySuccessfulCalculationDone(CALCULATE_BASED_OFF_ELO_RATINGS, 
				Matchup.WinChanceModeEnum.ELO_RATINGS);
		verify(matchup).calculateTeamWinChancesFromEloRatings();
	}
	
	@Test
	public void calculateWinChanceFailsSoTellUserToSetEloRatings() {
		when(matchup.calculateTeamWinChancesFromEloRatings()).thenReturn(false);
		when(input.askForInt(anyString())).thenReturn(CALCULATE_BASED_OFF_ELO_RATINGS, 
				EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		expectedMenuMessage = "Could not calculate; set Elo Ratings on both " +
				"teams to be above 0.\n" + expectedMenuMessage;
		verify(input, times(1)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void setTeamHomeWinChancesSoCustomHomeWinChancesAreSet() {
		int team1HomeWinChance = 57;
		int team2HomeWinChance = 49;
		when(input.askForInt(anyString())).thenReturn(SET_FIRST_TEAM_HOME_CHANCE, 
				team1HomeWinChance, SET_SECOND_TEAM_HOME_CHANCE, team2HomeWinChance, 
				EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
		
		setExpectedSetHomeWinChanceMessage(coltsName);
		verify(input, times(1)).askForInt(expectedSetHomeWinChanceMessage);
		setExpectedSetHomeWinChanceMessage(eaglesName);
		verify(input, times(1)).askForInt(expectedSetHomeWinChanceMessage);
		
		verify(matchup, times(1)).setTeamHomeWinChance(coltsName, team1HomeWinChance);
		verify(matchup, times(1)).setTeamHomeWinChance(eaglesName, team2HomeWinChance);
	}
	
	@Test
	public void setTeamHomeWinChanceWithNonPositiveNumbersSoNonPositivesAreIgnored() {
		when(input.askForInt(anyString())).thenReturn(SET_FIRST_TEAM_HOME_CHANCE, 
				-1, 14, SET_SECOND_TEAM_HOME_CHANCE, 0, 12, EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
		
		setExpectedSetHomeWinChanceMessage(coltsName);
		verify(input, times(2)).askForInt(expectedSetHomeWinChanceMessage);
		setExpectedSetHomeWinChanceMessage(eaglesName);
		verify(input, times(2)).askForInt(expectedSetHomeWinChanceMessage);
		
		verify(matchup, never()).setTeamHomeWinChance(coltsName, -1);
		verify(matchup, never()).setTeamHomeWinChance(coltsName, 0);
		verify(matchup, never()).setTeamHomeWinChance(eaglesName, -1);
		verify(matchup, never()).setTeamHomeWinChance(eaglesName, 0);
	}
	
	@Test
	public void setTeamAwayWinChancesSoCustomAwayWinChancesAreSet() {
		int team1AwayWinChance = 57;
		int team2AwayWinChance = 49;
		when(input.askForInt(anyString())).thenReturn(SET_FIRST_TEAM_AWAY_CHANCE, 
				team1AwayWinChance, SET_SECOND_TEAM_AWAY_CHANCE, team2AwayWinChance, 
				EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
		
		setExpectedSetAwayWinChanceMessage(coltsName);
		verify(input, times(1)).askForInt(expectedSetAwayWinChanceMessage);
		setExpectedSetAwayWinChanceMessage(eaglesName);
		verify(input, times(1)).askForInt(expectedSetAwayWinChanceMessage);
		
		verify(matchup, times(1)).setTeamAwayWinChance(coltsName, team1AwayWinChance);
		verify(matchup, times(1)).setTeamAwayWinChance(eaglesName, team2AwayWinChance);
	}
	
	@Test
	public void setTeamAwayWinChanceWithNonPositiveNumbersSoNonPositivesAreIgnored() {
		when(input.askForInt(anyString())).thenReturn(SET_FIRST_TEAM_AWAY_CHANCE, 
				-1, 14, SET_SECOND_TEAM_AWAY_CHANCE, 0, 14, EXIT_OPTION);
		
		matchupMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
		
		setExpectedSetAwayWinChanceMessage(coltsName);
		verify(input, times(2)).askForInt(expectedSetAwayWinChanceMessage);
		setExpectedSetAwayWinChanceMessage(eaglesName);
		verify(input, times(2)).askForInt(expectedSetAwayWinChanceMessage);
		
		verify(matchup, never()).setTeamAwayWinChance(coltsName, -1);
		verify(matchup, never()).setTeamAwayWinChance(coltsName, 0);
		verify(matchup, never()).setTeamAwayWinChance(eaglesName, -1);
		verify(matchup, never()).setTeamAwayWinChance(eaglesName, 0);
	}
	
	@Test
	public void calculateHomeWinChanceBasedOnHomeFieldAdvantageSoCalculationIsCalledFor() {
		when(input.askForInt(anyString())).thenReturn(CALCULATE_FIRST_HOME_AWAY_BASED_OFF_HOME_FIELD_ADVANTAGE, 
				CALCULATE_SECOND_HOME_AWAY_BASED_OFF_HOME_FIELD_ADVANTAGE, EXIT_OPTION);
		when(matchup.getTeamHomeWinChance(coltsName)).thenReturn(55, 58, 58, 55, 58, 58);
		when(matchup.getTeamAwayWinChance(eaglesName)).thenReturn(45, 42, 42, 45, 42, 42);
		when(matchup.getTeamHomeWinChance(eaglesName)).thenReturn(46, 46, 51, 46, 46, 51);
		when(matchup.getTeamAwayWinChance(coltsName)).thenReturn(54, 54, 49, 54, 54, 49);
		when(matchup.getHomeAwayWinChanceMode(coltsName)).thenReturn(Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING, 
				Matchup.HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE, 
				Matchup.HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE, 
				Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING, 
				Matchup.HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE, 
				Matchup.HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE);

		
		matchupMenu.launchSubMenu();
		
		setExpectedMenuMessage();
		verify(input, times(1)).askForInt(expectedMenuMessage);
		
		setExpectedMenuMessage();
		verify(input, times(1)).askForInt(expectedMenuMessage);
		
		setExpectedMenuMessage();
		verify(input, times(1)).askForInt(expectedMenuMessage);
	}

	private void setExpectedMenuMessage() {
		String selectedTeamName = matchupMenu.getSelectedTeamName();
		String afterOneTeamMessage = " win chance\n";
		
		StringBuilder expectedMenuMessageBuilder = new StringBuilder();
		
		expectedMenuMessageBuilder.append("Matchup: " + teamNames[0] + " vs. " + 
				teamNames[1] + "\n");
		expectedMenuMessageBuilder.append("Current win chances:\n" + teamNames[0] + ": Neutral - " + 
				matchup.getTeamNeutralWinChance(teamNames[0]) + ", Home - " + 
				matchup.getTeamHomeWinChance(teamNames[0]) + ", Away - " + 
				matchup.getTeamAwayWinChance(teamNames[0]) + "\n" + teamNames[1] + 
				": Neutral - " + matchup.getTeamNeutralWinChance(teamNames[1]) + ", Home - " +
				matchup.getTeamHomeWinChance(teamNames[1]) + ", Away - " + 
				matchup.getTeamAwayWinChance(teamNames[1]) + "\n");
		expectedMenuMessageBuilder.append("Current win chance determiners: Neutral - " + 
				matchup.getWinChanceMode().winChanceModeDescription + 
				", " + teamNames[0] + " Home - " + 
				matchup.getHomeAwayWinChanceMode(teamNames[0]).winChanceModeDescription + 
				", " + teamNames[1] + " Home - " + 
				matchup.getHomeAwayWinChanceMode(teamNames[1]).winChanceModeDescription +
				"\n");
		expectedMenuMessageBuilder.append(MenuOptionsUtil.MENU_INTRO);
		expectedMenuMessageBuilder.append("1. Set " + teamNames[0] + " neutral" + afterOneTeamMessage);
		expectedMenuMessageBuilder.append("2. Set " + teamNames[1] + " neutral" + afterOneTeamMessage);
		expectedMenuMessageBuilder.append("3. Calculate and set win chances based " +
				"off teams' Power Rankings: #" + matchup.getTeamPowerRanking(teamNames[0]) + 
				" vs. #" + matchup.getTeamPowerRanking(teamNames[1]) + 
				"\n");
		expectedMenuMessageBuilder.append("4. Calculate and set win chances based off teams' Elo Ratings: " + 
				matchup.getTeamEloRating(teamNames[0]) + " vs. " + 
				matchup.getTeamEloRating(teamNames[1]) +
				"\n");
		expectedMenuMessageBuilder.append("5. Set " + teamNames[0] + " home" + afterOneTeamMessage);
		expectedMenuMessageBuilder.append("6. Set " + teamNames[0] + " away" + afterOneTeamMessage);
		expectedMenuMessageBuilder.append("7. Set " + teamNames[1] + " home" + afterOneTeamMessage);
		expectedMenuMessageBuilder.append("8. Set " + teamNames[1] + " away" + afterOneTeamMessage);
		expectedMenuMessageBuilder.append("9. Calculate " + teamNames[0] + " Home " +
				"Win Chance by set Home Field Advantage: " + 
				matchup.getTeamHomeFieldAdvantage(teamNames[0]) + "\n");
		expectedMenuMessageBuilder.append("10. Calculate " + teamNames[1] + " Home " +
				"Win Chance by set Home Field Advantage: " + 
				matchup.getTeamHomeFieldAdvantage(teamNames[1]) + "\n");
		expectedMenuMessageBuilder.append("11. Back to " + selectedTeamName + " Matchup List");
		
		expectedMenuMessage = expectedMenuMessageBuilder.toString();
	}
	
	private void setExpectedSetWinChanceMessage(String teamName) {
		expectedSetWinChanceMessage = "Current " + teamName + " neutral win chance: " + 
				matchup.getTeamNeutralWinChance(teamName) + 
				"\nPlease enter in a number between 1 and 99";
	}
	
	private void setExpectedSetHomeWinChanceMessage(String teamName) {
		expectedSetHomeWinChanceMessage = "Current " + teamName + " home win chance: " + 
				matchup.getTeamHomeWinChance(teamName) + 
				"\nPlease enter in a number between 1 and 99";
	}
	
	private void setExpectedSetAwayWinChanceMessage(String teamName) {
		expectedSetAwayWinChanceMessage = "Current " + teamName + " away win chance: " + 
				matchup.getTeamAwayWinChance(teamName) + 
				"\nPlease enter in a number between 1 and 99";
	}
	
	private void verifySuccessfulCalculationDone(int menuOption, 
			Matchup.WinChanceModeEnum winChanceMode) {
		when(input.askForInt(anyString())).thenReturn(menuOption, 
				EXIT_OPTION);
		when(matchup.getTeamNeutralWinChance(coltsName)).thenReturn(55, 58);
		when(matchup.getTeamNeutralWinChance(eaglesName)).thenReturn(45, 42);
		when(matchup.getWinChanceMode()).thenReturn(Matchup.WinChanceModeEnum.CUSTOM_SETTING,
				winChanceMode);
		
		matchupMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		
		when(matchup.getTeamNeutralWinChance(coltsName)).thenReturn(58);
		when(matchup.getTeamNeutralWinChance(eaglesName)).thenReturn(42);
		when(matchup.getWinChanceMode()).thenReturn(winChanceMode);
		
		setExpectedMenuMessage();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
	}
	
}
