package nfl.season.calculator;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.InputMismatchException;

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
	
	String expectedMenuMessage;
	
	@Before
	public void setUp() {
		mainMenu = new MainMenu(input);
		mainMenu.setSubMenu(teamsMenu, MainMenuOptions.TEAMS.getOptionNumber());
		
		expectedMenuMessage = MenuOptionsUtil.MENU_INTRO + "1. Edit Team Settings\n2. Exit";
	}
	
	@Test
	public void mainMenuPrintsOutOptionsAndGoesToTeamMenu() {
		when(input.askForInt(anyString())).thenReturn(1, 2);
		
		mainMenu.launchMainMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verify(teamsMenu, times(1)).launchSubMenu();
	}
	
	@Test
	public void mainMenuIgnoresNonIntInput() {
		when(input.askForInt(anyString())).thenThrow(new InputMismatchException()).thenReturn(2);
		
		mainMenu.launchMainMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void mainMenuIgnoresInputOutsideOfExpectedRange() {
		when(input.askForInt(anyString())).thenReturn(999, 2);
		
		mainMenu.launchMainMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
	}
	
}
