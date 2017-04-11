package nfl.season.calculator;

import static org.junit.Assert.assertNotNull;
import nfl.season.calculator.MainMenu.MainMenuOptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonCalculatorTest {

	@Test
	public void mainMethodSetsUpMenusAndLaunchesMainMenu() {
		MainMenu mainMenu = NFLSeasonCalculator.createMainMenu();
		
		assertNotNull(mainMenu.getSubMenu(MainMenuOptions.TEAMS.getOptionNumber()));
	}
	
}
