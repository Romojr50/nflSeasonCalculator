package nfl.season.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.menu.MainMenu;
import nfl.season.menu.MainMenu.MainMenuOptions;
import nfl.season.menu.MatchupMenu;
import nfl.season.menu.SingleTeamMenu;
import nfl.season.menu.SubMenu;
import nfl.season.menu.TeamsMenu;
import nfl.season.menu.TeamsMenu.TeamsMenuOptions;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.season.NFLSeason;

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
	
	@Mock
	private NFLSeason season;
	
	@Mock
	private ScoreStripReader scoreStripReader;
	
	@Mock
	private ScoreStripMapper scoreStripMapper;
	
	@Test
	public void mainMethodSetsUpMenusAndLaunchesMainMenu() {
		MainMenu mainMenu = NFLSeasonCalculator.createMainMenu(input, nfl);
		TeamsMenu teamsMenu = NFLSeasonCalculator.createTeamsMenu(input, nfl);
		NFLSeasonCalculator.createSeasonMenu(input, nfl);
		NFLSeasonCalculator.createPlayoffsMenu(input, nfl);
		
		assertNotNull(mainMenu.getSubMenu(MainMenuOptions.TEAMS.getOptionNumber()));
		assertNotNull(mainMenu.getSubMenu(MainMenuOptions.SEASON.getOptionNumber()));
		assertNotNull(mainMenu.getSubMenu(MainMenuOptions.PLAYOFFS.getOptionNumber()));
		
		SubMenu singleTeamMenu = teamsMenu.getSubMenu(
				TeamsMenuOptions.SELECT_TEAM.getOptionNumber());
		assertNotNull(singleTeamMenu);
		assertEquals(SingleTeamMenu.class, singleTeamMenu.getClass());
		
		SubMenu matchupMenu = singleTeamMenu.getSubMenu(1);
		assertNotNull(matchupMenu);
		assertEquals(MatchupMenu.class, matchupMenu.getClass());
	}
	
}
