package nfl.season.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nfl.season.calculator.MainMenu.MainMenuOptions;
import nfl.season.calculator.TeamsMenu.TeamsMenuOptions;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonCalculatorTest {

	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private League nfl;
	
	@Test
	public void mainMethodSetsUpMenusAndLaunchesMainMenu() {
		MainMenu mainMenu = NFLSeasonCalculator.createMainMenu(input, nfl);
		TeamsMenu teamsMenu = NFLSeasonCalculator.createTeamsMenu(input, nfl);
		
		assertNotNull(mainMenu.getSubMenu(MainMenuOptions.TEAMS.getOptionNumber()));
		
		SubMenu singleTeamMenu = teamsMenu.getSubMenu(
				TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		assertNotNull(singleTeamMenu);
		assertEquals(SingleTeamMenu.class, singleTeamMenu.getClass());
	}
	
}
