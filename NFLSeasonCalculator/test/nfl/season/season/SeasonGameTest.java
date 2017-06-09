package nfl.season.season;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import nfl.season.league.Matchup;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SeasonGameTest {

	@Mock
	private Team homeTeam;
	
	private String homeName = "Home";
	
	@Mock
	private Team awayTeam;
	
	private String awayName = "Away";
	
	@Mock
	private Matchup matchup;
	
	private SeasonGame seasonGame;
	
	@Before
	public void setUp() {
		when(homeTeam.getName()).thenReturn(homeName);
		when(awayTeam.getName()).thenReturn(awayName);
		when(homeTeam.getMatchup(awayName)).thenReturn(matchup);
		when(matchup.getTeamHomeWinChance(homeName)).thenReturn(70);
		when(matchup.getTeamAwayWinChance(awayName)).thenReturn(30);
		seasonGame = new SeasonGame(homeTeam, awayTeam);
	}
	
	@Test
	public void simulateGameUsesProbabilitiesToSimulateWinner() {
		int homeTeamWins = 0;
		int awayTeamWins = 0;
		
		for (int i = 0; i < 100; i++) {
			seasonGame.simulateGame();
			Team simulatedWinner = seasonGame.getSimulatedWinner();
			assertNotNull(simulatedWinner);
			if (simulatedWinner.equals(homeTeam)) {
				homeTeamWins++;
			} else if (simulatedWinner.equals(awayTeam)) {
				awayTeamWins++;
			}
		}
		
		assertTrue(homeTeamWins > 0);
		assertTrue(awayTeamWins > 0);
		assertTrue(homeTeamWins > awayTeamWins);
	}
	
	@Test
	public void clearSimulatedResultClearsSimulatedResult() {
		seasonGame.simulateGame();
		seasonGame.clearSimulatedResult();
		Team simulatedWinner = seasonGame.getSimulatedWinner();
		assertNull(simulatedWinner);
	}
	
	@Test
	public void simulateGameButGameActuallyAlreadyHappenedSoSimulationDoesNotOccur() {
		seasonGame.setWinner(homeTeam);
		seasonGame.simulateGame();
		assertNull(seasonGame.getSimulatedWinner());
	}
	
}
