package nfl.season.menu;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.input.NFLSeasonInput;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.season.NFLSeason;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SeasonMenuTest {

	private static final int LOAD_SEASON = 1;
	
	private static final int BACK_TO_MAIN_MENU = 2;
	
	private String expectedMenuMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private NFLSeason season;
	
	@Mock
	private ScoreStripReader scoreStripReader;
	
	@Mock
	private ScoreStripMapper scoreStripMapper;
	
	private SeasonMenu seasonMenu;
	
	@Before
	public void setUp() {
		expectedMenuMessage = MenuOptionsUtil.MENU_INTRO + 
				"1. Load/Refresh the current season\n" +
				"2. Back to Main Menu";
		
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
	
}
