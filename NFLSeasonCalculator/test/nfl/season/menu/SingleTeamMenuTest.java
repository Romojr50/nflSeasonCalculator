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
import nfl.season.league.Matchup;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SingleTeamMenuTest {
	
	private static final int SET_POWER_RANKING = 1;
	
	private static final int SET_ELO_RATING = 2;
	
	private static final int SET_HOME_FIELD_ADVANTAGE = 3;
	
	private static final int SET_DEFAULT_HOME_FIELD_ADVANTAGE = 4;
	
	private static final int SET_ALL_DEFAULTS = 5;

	private static final int CHOOSE_MATCHUP = 6;
	
	private static final int EXIT_FROM_SINGLE_TEAM_MENU = 7;
	
	private String expectedMenuMessage;
	
	private String expectedPowerRankingsMessage;
	
	private String expectedEloRatingMessage;
	
	private String expectedHomeFieldMessage;
	
	private String expectedMatchupMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private League nfl;
	
	@Mock
	private MatchupMenu matchupMenu;
	
	@Mock
	private Team colts;
	
	@Mock
	private Team eagles;
	
	@Mock
	private Matchup texansMatchup;
	
	@Mock
	private Matchup titansMatchup;
	
	@Mock
	private Matchup jaguarsMatchup;
	
	private List<Matchup> matchups;
	
	private SingleTeamMenu singleTeamMenu;
	
	@Before
	public void setUp() {
		singleTeamMenu = new SingleTeamMenu(input, nfl);
		singleTeamMenu.setTeam(colts);
		singleTeamMenu.setSubMenu(matchupMenu, 1);
		
		when(colts.getName()).thenReturn("Colts");
		when(colts.getPowerRanking()).thenReturn(19);
		when(colts.getEloRating()).thenReturn(48);
		when(colts.getHomeFieldAdvantage()).thenReturn(14);
		when(colts.getDefaultHomeFieldAdvantage()).thenReturn(10);
		
		setExpectedMenuMessage();
		setExpectedPowerRankingsMessage();
		setExpectedEloRatingMessage();
		setExpectedHomeFieldAdvantageMessage();
		
		when(eagles.getName()).thenReturn("Eagles");
		
		setupMatchups();
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
	public void powerRankingNotYetSetSoUnsetIsDisplayedAsPowerRanking() {
		when(colts.getPowerRanking()).thenReturn(Team.CLEAR_RANKING, Team.CLEAR_RANKING, 
				15);
		setExpectedMenuMessage();
		when(input.askForInt(anyString())).thenReturn(SET_POWER_RANKING, 15, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		
		expectedMenuMessage = expectedMenuMessage.replace("" + Team.CLEAR_RANKING, 
				Team.UNSET_RANKING_DISPLAY);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
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
		verify(eagles, times(2)).setPowerRanking(Team.CLEAR_RANKING);
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
	
	@Test
	public void setClearPowerRankingWhenOtherTeamsAreClearedButNoOverwriteMessageAppears() {
		when(colts.getPowerRanking()).thenReturn(15);
		when(eagles.getPowerRanking()).thenReturn(-1);
		
		when(input.askForInt(anyString())).thenReturn(SET_POWER_RANKING, Team.CLEAR_RANKING, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, never()).askForString(anyString());
		verify(nfl, never()).getTeamWithPowerRanking(anyInt());
	}
	
	@Test
	public void eloRatingIsSet() {
		int newEloRating = 17;
		
		when(input.askForInt(anyString())).thenReturn(SET_ELO_RATING, newEloRating, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt(expectedEloRatingMessage);
		
		verify(colts, times(1)).setEloRating(newEloRating);
	}
	
	@Test
	public void eloRatingInputIsInvalidSoInvalidInputIsIgnored() {
		int newEloRating = 17;
		
		when(input.askForInt(anyString())).thenReturn(SET_ELO_RATING, 0, newEloRating, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(expectedEloRatingMessage);
		
		verify(colts, never()).setEloRating(0);
		verify(colts, times(1)).setEloRating(newEloRating);
	}
	
	@Test
	public void homeFieldAdvantageIsSet() {
		int newHomeFieldAdvantage = 13;
		
		when(input.askForInt(anyString())).thenReturn(SET_HOME_FIELD_ADVANTAGE, 
				newHomeFieldAdvantage, EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt(expectedHomeFieldMessage);
		
		verify(colts, times(1)).setHomeFieldAdvantage(newHomeFieldAdvantage);
	}
	
	@Test
	public void homeFieldInputIsZeroOrNegativeSoHomeFieldIsStillSet() {
		when(input.askForInt(anyString())).thenReturn(SET_HOME_FIELD_ADVANTAGE, 0, 
				SET_HOME_FIELD_ADVANTAGE, -2, EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(expectedHomeFieldMessage);
		
		verify(colts, times(1)).setHomeFieldAdvantage(0);
		verify(colts, times(1)).setHomeFieldAdvantage(-2);
	}
	
	@Test
	public void defaultHomeFieldAdvantageIsSet() {
		when(input.askForInt(anyString())).thenReturn(SET_DEFAULT_HOME_FIELD_ADVANTAGE, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, never()).askForInt(expectedHomeFieldMessage);
		
		verify(colts, times(1)).setHomeFieldAdvantage(colts.getDefaultHomeFieldAdvantage());
	}
	
	@Test
	public void teamIsResetToDefault() {
		when(input.askForInt(anyString())).thenReturn(SET_ALL_DEFAULTS, 
				EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		
		verify(colts, times(1)).resetToDefaults();
	}
	
	@Test
	public void setSubMenuWithSingleTeamsMenuSoSingleTeamsMenuIsSet() {
		singleTeamMenu.setSubMenu(matchupMenu, 1);
		
		SubMenu returnedMatchupMenu = singleTeamMenu.getMatchupMenu();
		
		assertEquals(matchupMenu, returnedMatchupMenu);
		verify(matchupMenu).setSelectedTeamName(colts.getName());
	}
	
	@Test
	public void teamMatchupIsSelectedSoMatchupMenuIsOpened() {
		int matchupIndex = 2;
		Matchup expectedMatchup = matchups.get(matchupIndex - 1);
		
		when(input.askForInt(anyString())).thenReturn(CHOOSE_MATCHUP, matchupIndex, 
				matchups.size() + 1, EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(2)).askForInt(expectedMatchupMessage);
		
		verify(matchupMenu).setMatchup(expectedMatchup);
		verify(matchupMenu).launchSubMenu();
	}
	
	@Test
	public void inputOutsideOfExpectedRangeEnteredWhenSelectingMatchupSoInputIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(CHOOSE_MATCHUP, 67, -2, 2, 
				matchups.size() + 1, EXIT_FROM_SINGLE_TEAM_MENU);
		
		singleTeamMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(4)).askForInt(expectedMatchupMessage);
		verify(matchupMenu, times(1)).launchSubMenu();
	}
	
	private void setExpectedMenuMessage() {
		expectedMenuMessage = 
				colts.getName() + "\nPower Ranking: " + colts.getPowerRanking() + 
				"\nElo Rating: " + colts.getEloRating() + "\nHome Field Advantage: " + 
				colts.getHomeFieldAdvantage() + "\n" + MenuOptionsUtil.MENU_INTRO + 
				"1. Set Power Ranking\n2. Set Elo Rating\n3. Set Home Field Advantage\n" +
				"4. Set Home Field Advantage to Default\n5. Revert All Team Values to Defaults\n" +
				"6. Edit Matchup Settings\n7. Back to Teams Menu";
	}
	

	private void setExpectedPowerRankingsMessage() {
		expectedPowerRankingsMessage = "Currently #" + colts.getPowerRanking() + 
				"\nPlease enter in a number between 1-32 to set the team to that " +
				"ranking\nor -1 to clear this team's ranking:";
	}
	
	private void setExpectedEloRatingMessage() {
		expectedEloRatingMessage = "Current Elo Rating: " + colts.getEloRating() + 
				"\nPlease enter in an integer above 0";
	}
	
	private void setExpectedHomeFieldAdvantageMessage() {
		expectedHomeFieldMessage = "Current Home Field Advantage: " + 
				colts.getHomeFieldAdvantage() + "\nPlease enter in an integer above 0";
	}
	

	private void setupMatchups() {
		matchups = new ArrayList<Matchup>();
		matchups.add(jaguarsMatchup);
		matchups.add(titansMatchup);
		matchups.add(texansMatchup);
		
		String coltsName = colts.getName();
		when(jaguarsMatchup.getOpponentName(coltsName)).thenReturn("Jaguars");
		when(titansMatchup.getOpponentName(coltsName)).thenReturn("Titans");
		when(texansMatchup.getOpponentName(coltsName)).thenReturn("Texans");
		
		StringBuilder expectedMatchupMessageBuilder = new StringBuilder();
		expectedMatchupMessageBuilder.append(MenuOptionsUtil.MENU_INTRO);
		int matchupIndex = 1;
		for (Matchup matchup : matchups) {
			expectedMatchupMessageBuilder.append(matchupIndex + ". ");
			String opponentName = matchup.getOpponentName(colts.getName());
			expectedMatchupMessageBuilder.append(opponentName + "\n");
			matchupIndex++;
		}
		expectedMatchupMessageBuilder.append(matchupIndex + ". Back to Team Menu");
		expectedMatchupMessage = expectedMatchupMessageBuilder.toString();
		
		when(colts.getMatchups()).thenReturn(matchups);
	}
	
}
