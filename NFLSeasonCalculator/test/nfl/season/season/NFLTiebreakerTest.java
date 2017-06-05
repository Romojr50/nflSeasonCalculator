package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLTiebreakerTest {

	@Mock
	private NFLSeasonTeam team1_1;
	
	@Mock
	private NFLSeasonTeam team1_2;
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void divisionTieBreakBetweenTwoUnTiedTeamsGivesTeamWithBetterWinPercent() {
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(8);
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(6);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(6);
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfWins()).thenReturn(8);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_1.getNumberOfTies()).thenReturn(1);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(8);
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
}
