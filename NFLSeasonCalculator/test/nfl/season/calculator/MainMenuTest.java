package nfl.season.calculator;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import nfl.season.calculator.MainMenu.MainMenuOptions;
import nfl.season.input.NFLSeasonInput;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainMenuTest {

	private MainMenu mainMenu;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private TeamsMenu teamsMenu;
	
	@Before
	public void setUp() {
		mainMenu = new MainMenu(input);
		mainMenu.setSubMenu(teamsMenu, MainMenuOptions.TEAMS.getOptionNumber());
	}
	
	@Test
	public void mainMenuPrintsOutOptionsAndGoesToTeams() {
		when(input.askForInt(anyString())).thenReturn(1, 2);
		String expectedMenuMessage = "Select tab:\n1. Edit Team Settings\n2. Exit";
		
		mainMenu.launchMainMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(teamsMenu, times(1)).launchSubMenu();
	}
	
}
