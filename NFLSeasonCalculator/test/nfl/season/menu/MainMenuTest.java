package nfl.season.menu;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nfl.season.input.NFLSeasonInput;
import nfl.season.menu.MainMenu.MainMenuOptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainMenuTest {
	
	private static final int GO_TO_TEAMS_MENU = 1;
	
	private static final int GO_TO_PLAYOFFS_MENU = 2;

	private static final int EXIT = 3;

	private MainMenu mainMenu;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private TeamsMenu teamsMenu;
	
	@Mock
	private PlayoffsMenu playoffsMenu;
	
	String expectedMenuMessage;
	
	@Before
	public void setUp() {
		mainMenu = new MainMenu(input);
		mainMenu.setSubMenu(teamsMenu, MainMenuOptions.TEAMS.getOptionNumber());
		mainMenu.setSubMenu(playoffsMenu, MainMenuOptions.PLAYOFFS.getOptionNumber());
		
		expectedMenuMessage = MenuOptionsUtil.MENU_INTRO + "1. Edit Team Settings\n" +
				"2. Go to Playoffs Menu\n3. Exit";
	}
	
	@Test
	public void mainMenuPrintsOutOptionsAndGoesToTeamMenu() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_TEAMS_MENU, EXIT);
		
		mainMenu.launchMainMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(teamsMenu, times(1)).launchSubMenu();
	}
	
	@Test
	public void mainMenuPrintsOutOptionsAndGoesToPlayoffsMenu() {
		when(input.askForInt(anyString())).thenReturn(GO_TO_PLAYOFFS_MENU, EXIT);
		
		mainMenu.launchMainMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(playoffsMenu, times(1)).launchSubMenu();
	}
	
	@Test
	public void mainMenuIgnoresInputOutsideOfExpectedRange() {
		when(input.askForInt(anyString())).thenReturn(999, EXIT);
		
		mainMenu.launchMainMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
	}
	
}
