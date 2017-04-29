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
	
	@Test
	public void getTeamNamesReturnsBothTeamNames() {
		String[] teamNames = matchup.getTeamNames();
		assertEquals(2, teamNames.length);
		assertEquals(team1Name, teamNames[0]);
		assertEquals(team2Name, teamNames[1]);
	}
	
	@Test
	public void setTeamWinChanceSoBothTeamsHaveWinChanceAltered() {
		int expectedTeam1WinChance = 18;
		int expectedTeam2WinChance = 74;
		
		matchup.setTeamWinChance(team1Name, expectedTeam1WinChance);
		
		int team1WinChance = matchup.getTeamWinChance(team1Name);
		int team2WinChance = matchup.getTeamWinChance(team2Name);
		
		assertEquals(expectedTeam1WinChance, team1WinChance);
		assertEquals((100 - expectedTeam1WinChance), team2WinChance);
		
		matchup.setTeamWinChance(team2Name, expectedTeam2WinChance);
		
		team1WinChance = matchup.getTeamWinChance(team1Name);
		team2WinChance = matchup.getTeamWinChance(team2Name);
		
		assertEquals((100 - expectedTeam2WinChance), team1WinChance);
		assertEquals(expectedTeam2WinChance, team2WinChance);
	}
	
}
