package nfl.season.menu;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.input.NFLSeasonInput;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.season.NFLSeason;
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
	
	private static final int BACK_TO_MAIN_MENU = 3;
	
	private String expectedMenuMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private NFLSeason season;
	
	@Mock
	private SeasonWeek week1;
	
	@Mock
	private SeasonWeek week3;
	
	@Mock
	private SeasonWeek week15;
	
	@Mock
	private ScoreStripReader scoreStripReader;
	
	@Mock
	private ScoreStripMapper scoreStripMapper;
	
	private SeasonMenu seasonMenu;
	
	@Before
	public void setUp() {
		expectedMenuMessage = MenuOptionsUtil.MENU_INTRO + 
				"1. Load/Refresh the current season\n" +
				"2. Print out games in week\n" +
				"3. Back to Main Menu";
		
		when(season.getWeek(1)).thenReturn(week1);
		when(season.getWeek(3)).thenReturn(week3);
		when(season.getWeek(15)).thenReturn(week15);
		
		seasonMenu = new SeasonMenu(input, season, scoreStripReader, scoreStripMapper);
	}
	
	@Test
	public void invalidInputGivenOnMenuIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(999, -3, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void loadSeasonIsSelectedSoSeasonIsLoaded() {
		when(input.askForInt(anyString())).thenReturn(LOAD_SEASON, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(season).loadSeason(scoreStripReader, scoreStripMapper);
		verify(input, times(1)).printMessage("Loading season...");
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
	}
	
	@Test
	public void printOutWeekIgnoreInvalidInput() {
		when(input.askForInt(anyString())).thenReturn(PRINT_WEEK, 18, 0, 3, BACK_TO_MAIN_MENU);
		
		seasonMenu.launchSubMenu();
		
		verify(season).getWeek(3);
		verify(season).getWeekString(week3);
		
		verify(input, times(3)).askForInt("Please enter in a number between 1-17:");
	}
	
}
