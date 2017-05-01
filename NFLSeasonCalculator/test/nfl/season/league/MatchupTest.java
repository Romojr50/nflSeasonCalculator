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
	public void matchupIsInitializedWith50sAndCustomSetting() {
		assertEquals(50, matchup.getTeamWinChance(team1Name));
		assertEquals(50, matchup.getTeamWinChance(team2Name));
		assertEquals(Matchup.WinChanceModeEnum.CUSTOM_SETTING, matchup.getWinChanceMode());
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
		assertEquals(Matchup.WinChanceModeEnum.CUSTOM_SETTING, matchup.getWinChanceMode());
		
		matchup.setTeamWinChance(team2Name, expectedTeam2WinChance);
		
		team1WinChance = matchup.getTeamWinChance(team1Name);
		team2WinChance = matchup.getTeamWinChance(team2Name);
		
		assertEquals((100 - expectedTeam2WinChance), team1WinChance);
		assertEquals(expectedTeam2WinChance, team2WinChance);
		assertEquals(Matchup.WinChanceModeEnum.CUSTOM_SETTING, matchup.getWinChanceMode());
	}
	
	@Test
	public void calculateTeamWinChancesFromPowerRankingsUsesRankingsToCalculateWinChances() {
		testWinChanceCalculation(90, 1, 25);
		testWinChanceCalculation(10, 25, 1);
		testWinChanceCalculation(55, 1, 2);
		testWinChanceCalculation(82, 8, 27);
		testWinChanceCalculation(18, 24, 5);
		testWinChanceCalculation(37, 18, 12);
	}

	private void testWinChanceCalculation(
			int expectedTeam1WinChance, int team1Ranking, int team2Ranking) {
		when(team1.getPowerRanking()).thenReturn(team1Ranking);
		when(team2.getPowerRanking()).thenReturn(team2Ranking);
		
		matchup.calculateTeamWinChancesFromPowerRankings();
		
		assertEquals(Matchup.WinChanceModeEnum.POWER_RANKINGS, matchup.getWinChanceMode());
		assertEquals(expectedTeam1WinChance, matchup.getTeamWinChance(team1Name));
		assertEquals((100 - expectedTeam1WinChance), matchup.getTeamWinChance(team2Name));
	}
	
}
