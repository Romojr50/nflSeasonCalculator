package nfl.season.league;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MatchupTest {

	@Mock
	private Team team1;
	
	@Mock
	private Team team2;
	
	private String team1Name = "First Team";
	
	private String team2Name = "The Other Team";
	
	private Matchup matchup;
	
	@Before
	public void setUp() {
		when(team1.getName()).thenReturn(team1Name);
		when(team2.getName()).thenReturn(team2Name);
		
		matchup = new Matchup(team1, team2);
	}
	
	@Test
	public void opponentNameIsLookedForSoOtherTeamNameIsReturned() {
		String team1Opponent = matchup.getOpponentName(team1Name);
		String team2Opponent = matchup.getOpponentName(team2Name);
		assertEquals(team2Name, team1Opponent);
		assertEquals(team1Name, team2Opponent);
	}
	
}
