package nfl.season.menu;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.input.NFLSeasonInput;
import nfl.season.playoffs.NFLPlayoffs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayoffsMenuTest {

	private static final int SELECT_TEAMS_FOR_PLAYOFFS = 1;
	
	private static final int BACK_TO_MAIN_MENU = 2;
	
	private String expectedMenuMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private NFLPlayoffs playoffs;
	
	private PlayoffsMenu playoffsMenu;
	
	@Before
	public void setUp() {
		playoffsMenu = new PlayoffsMenu(input, playoffs);
		
		expectedMenuMessage = "Please enter in an integer corresponding to one of the following:\n" +
				"1. Select Teams for Playoffs\n2. Back to Main Menu";
	}
	
	@Test
	public void invalidInputGivenOnMenuIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(999, -3, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void selectTeamsForPlayoffsHasUserPutInTeamsForPlayoffs() {
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_FOR_PLAYOFFS, 
				2, 3, 3, 1, 1, 1, 4, 2, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		
	}
	
}
