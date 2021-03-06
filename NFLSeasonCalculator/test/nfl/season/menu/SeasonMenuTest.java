package nfl.season.menu;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import nfl.season.input.NFLFileReaderFactory;
import nfl.season.input.NFLFileWriterFactory;
import nfl.season.input.NFLRegularSeasonSave;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffs;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.season.NFLManySeasonSimulator;
import nfl.season.season.NFLSeason;
import nfl.season.season.NFLSeasonTeam;
import nfl.season.season.NFLTiebreaker;
import nfl.season.season.SeasonWeek;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SeasonMenuTest {

	private static final int LOAD_SEASON = 1;
	
	private static final int PRINT_WEEK = 2;
	
	private static final int PRINT_TEAM_SCHEDULE = 3;
	
	private static final int PRINT_STANDINGS = 4;
	
	private static final int SIMULATE_SEASON = 5;
	
	private static final int CLEAR_SIMULATIONS = 6;
	
	private static final int SIMULATE_MANY_SEASONS = 7;
	
	private static final int PRINT_TEAM_SIMULATIONS = 8;
	
	private static final int BACK_TO_MAIN_MENU = 9;
	
	private String expectedMenuMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private NFLSeasonTeam seasonTeam;
	
	@Mock
	private Team leagueTeam;
	
	private String teamName = "Season Team";
	
	private String scheduleString = "Schedule String";
	
	private String simulationString = "Simulation String";
	
	@Mock
	private League league;
	
	private String leagueStandings = "League Standings\n";
	
	@Mock
	private NFLSeason season;
	
	@Mock
	private NFLTiebreaker tiebreaker;
	
	@Mock
	private NFLManySeasonSimulator simulator;
	
	@Mock
	private NFLPlayoffs playoffs;
	
	@Mock
	private SeasonWeek week1;
	
	@Mock
	private SeasonWeek week3;
	
	private String weekString = "Week String";
	
	@Mock
	private SeasonWeek week15;
	
	@Mock
	private ScoreStripReader scoreStripReader;
	
	@Mock
	private ScoreStripMapper scoreStripMapper;
	
	@Mock
	private NFLRegularSeasonSave seasonSave;
	
	@Mock
	private NFLFileWriterFactory fileWriterFactory;
	
	@Mock
	private NFLFileReaderFactory fileReaderFactory;
	
	private String seasonString = "Season string";
	
	private SeasonMenu seasonMenu;
	
	@Before
	public void setUp() throws IOException {
		expectedMenuMessage = MenuOptionsUtil.MENU_INTRO + 
				"1. Load/Refresh the current season\n" +
				"2. Print out games in week\n" +
				"3. Print out team schedule\n" +
				"4. Print out League Standings\n" +
				"5. Simulate Season\n" +
				"6. Clear Simulated Games\n" +
				"7. Simulate Many Seasons and Tally Results\n" +
				"8. Print Out Team Simulation Results\n" +
				"9. Back to Main Menu";
		
		when(season.getWeek(1)).thenReturn(week1);
		when(season.getWeek(3)).thenReturn(week3);
		when(season.getWeekString(week3)).thenReturn(weekString);
		when(season.getWeek(15)).thenReturn(week15);
		when(season.getTeam(teamName)).thenReturn(seasonTeam);
		when(season.getLeague()).thenReturn(league);
		when(season.createNFLTiebreaker()).thenReturn(tiebreaker);
		when(season.createManySeasonsSimulator()).thenReturn(simulator);
		when(season.getLeagueStandings(tiebreaker)).thenReturn(leagueStandings);
		when(league.getTeam(10)).thenReturn(leagueTeam);
		when(leagueTeam.getName()).thenReturn(teamName);
		when(seasonTeam.getScheduleString()).thenReturn(scheduleString);
		when(seasonTeam.getSimulatedResults(NFLSeason.MANY_SEASONS_NUMBER)).thenReturn(
				simulationString);
		
		when(seasonSave.loadSeasonSave(fileReaderFactory)).thenReturn(seasonString);
		
		seasonMenu = new SeasonMenu(input, season, playoffs, 
				scoreStripReader, scoreStripMapper, seasonSave, fileWriterFactory,
				fileReaderFactory);
	}
	
	@Test
	public void invalidInputGivenOnMenuIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(999, -3, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void loadSeasonIsSelectedSoSeasonIsLoaded() throws Exception {
		when(input.askForInt(anyString())).thenReturn(LOAD_SEASON, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(season).loadSeason(scoreStripReader, scoreStripMapper, seasonSave, 
				fileWriterFactory);
		verify(input, times(1)).printMessage("Loading season...");
	}
	
	@Test
	public void loadSeasonIsSelectedButCouldNotReadScoreStripSoFileIsLoaded() throws Exception {
		when(input.askForInt(anyString())).thenReturn(LOAD_SEASON, BACK_TO_MAIN_MENU);
		doThrow(new Exception("Exc")).when(season).loadSeason(
				scoreStripReader, scoreStripMapper, seasonSave, fileWriterFactory);
		
		seasonMenu.launchSubMenu();
		
		verify(season).loadSeason(scoreStripReader, scoreStripMapper, seasonSave, 
				fileWriterFactory);
		verify(input, times(1)).printMessage("Loading season...");
		verify(input, times(1)).printMessage(
				"Season could not be loaded from internet; loading from saved file...");
		verify(seasonSave).loadSeasonSave(fileReaderFactory);
		verify(seasonSave).populateSeasonFromSeasonString(seasonString, season);
	}
	
	@Test
	public void printOutWeekSoSelectAWeekToPrintOut() {
		when(input.askForInt(anyString())).thenReturn(PRINT_WEEK, 3, PRINT_WEEK, 15, 
				PRINT_WEEK, 1, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(season).getWeek(3);
		verify(season).getWeek(15);
		verify(season).getWeekString(week3);
		verify(season).getWeekString(week15);
		verify(season).getWeek(1);
		verify(season).getWeekString(week1);
		
		verify(input, times(3)).askForInt("Please enter in a number between 1-17:");
		verify(input).askForInt(weekString + expectedMenuMessage);
	}
	
	@Test
	public void printOutWeekIgnoreInvalidInput() {
		when(input.askForInt(anyString())).thenReturn(PRINT_WEEK, 18, 0, 3, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(season).getWeek(3);
		verify(season).getWeekString(week3);
		
		verify(input, times(3)).askForInt("Please enter in a number between 1-17:");
	}
	
	@Test
	public void printOutTeamScheduleSoSelectTeamAndPrintSchedule() {
		when(input.askForInt(anyString())).thenReturn(PRINT_TEAM_SCHEDULE, 10, 
				MenuOptionsUtil.EXIT_FROM_TEAM_SELECT, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(seasonTeam).getScheduleString();
		
		String teamListMessage = MenuOptionsUtil.buildTeamListMessage();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input).askForInt(scheduleString + teamListMessage);
	}
	
	@Test
	public void printOutTeamScheduleIgnoresInvalidInput() {
		when(input.askForInt(anyString())).thenReturn(PRINT_TEAM_SCHEDULE, 
				-1, 34, 10, MenuOptionsUtil.EXIT_FROM_TEAM_SELECT, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		String teamListMessage = MenuOptionsUtil.buildTeamListMessage();
		verify(input, times(3)).askForInt(teamListMessage);
		verify(input).askForInt(scheduleString + teamListMessage);
	}
	
	@Test
	public void printOutStandingsPrintsOutStandingsFromSeason() {
		when(input.askForInt(anyString())).thenReturn(PRINT_STANDINGS, SIMULATE_SEASON, 
				BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt(leagueStandings + expectedMenuMessage);
		verify(season).getLeagueStandings(any(NFLTiebreaker.class));
	}
	
	@Test
	public void simulateSeasonCallsOnSeasonToSimulate() {
		when(input.askForInt(anyString())).thenReturn(SIMULATE_SEASON, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(season).simulateSeason();
	}
	
	@Test
	public void simulateSeasonButSeasonNotLoadedSoNoSimulationDone() {
		when(season.getWeek(1)).thenReturn(null);
		
		when(input.askForInt(anyString())).thenReturn(SIMULATE_SEASON, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(season, never()).simulateSeason();
		verify(input).askForInt("Season not loaded yet; Please load season\n" + 
				expectedMenuMessage);
	}
	
	@Test
	public void clearSimulatedResultsCallsOnSeasonToClearSimulatedGames() {
		when(input.askForInt(anyString())).thenReturn(CLEAR_SIMULATIONS, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(season).clearSimulatedResults();
	}
	
	@Test
	public void simulateManySeasonsCallsOnSimulatorToSimulateManySeasons() {
		when(input.askForInt(anyString())).thenReturn(SIMULATE_MANY_SEASONS, 
				BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(simulator).clearSimulations();
		verify(simulator).simulateManySeasons(tiebreaker, playoffs, 
				NFLSeason.MANY_SEASONS_NUMBER);
		verify(input, times(1)).printMessage("Simulating " + 
				NFLSeason.MANY_SEASONS_NUMBER + " Seasons...");
	}
	
	@Test
	public void simulateManySeasonsButSeasonNotLoadedYetSoNoSimulationsDone() {
		when(season.getWeek(1)).thenReturn(null);
		
		when(input.askForInt(anyString())).thenReturn(SIMULATE_MANY_SEASONS, 
				BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(simulator, never()).clearSimulations();
		verify(simulator, never()).simulateManySeasons(tiebreaker, playoffs,
				NFLSeason.MANY_SEASONS_NUMBER);
		verify(input, never()).printMessage("Simulating " + 
				NFLSeason.MANY_SEASONS_NUMBER + " Seasons...");
		verify(input).askForInt("Season not loaded yet; Please load season\n" + 
				expectedMenuMessage);
	}
	
	@Test
	public void selectAndPrintOutTeamSimulatedResultsReport() {
		when(input.askForInt(anyString())).thenReturn(PRINT_TEAM_SIMULATIONS, 10, 
				MenuOptionsUtil.EXIT_FROM_TEAM_SELECT, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		String teamListMessage = MenuOptionsUtil.buildTeamListMessage();
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(input).askForInt(simulationString + teamListMessage);
	}
	
}
