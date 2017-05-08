package nfl.season.league;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
		assertEquals(50, matchup.getTeamNeutralWinChance(team1Name));
		assertEquals(50, matchup.getTeamNeutralWinChance(team2Name));
		assertEquals(50, matchup.getTeamHomeWinChance(team1Name));
		assertEquals(50, matchup.getTeamHomeWinChance(team2Name));
		assertEquals(Matchup.WinChanceModeEnum.CUSTOM_SETTING, matchup.getWinChanceMode());
		assertEquals(Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING, 
				matchup.getHomeAwayWinChanceMode(team1Name));
		assertEquals(Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING, 
				matchup.getHomeAwayWinChanceMode(team2Name));
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
	public void getTeamPowerRankingReturnsTeamPowerRankings() {
		int team1Ranking = 7;
		int team2Ranking = 26;
		when(team1.getPowerRanking()).thenReturn(team1Ranking);
		when(team2.getPowerRanking()).thenReturn(team2Ranking);
		
		int team1ReturnedRanking = matchup.getTeamPowerRanking(team1Name);
		int team2ReturnedRanking = matchup.getTeamPowerRanking(team2Name);
		
		assertEquals(team1Ranking, team1ReturnedRanking);
		assertEquals(team2Ranking, team2ReturnedRanking);
	}
	
	@Test
	public void getTeamEloRatingReturnsTeamEloRatings() {
		int team1EloRating = 1425;
		int team2EloRating = 1530;
		when(team1.getEloRating()).thenReturn(team1EloRating);
		when(team2.getEloRating()).thenReturn(team2EloRating);
		
		int team1ReturnedRating = matchup.getTeamEloRating(team1Name);
		int team2ReturnedRating = matchup.getTeamEloRating(team2Name);
		
		assertEquals(team1EloRating, team1ReturnedRating);
		assertEquals(team2EloRating, team2ReturnedRating);
	}
	
	@Test
	public void setTeamNeutralWinChanceSoBothTeamsHaveNeutralWinChanceAltered() {
		int expectedTeam1WinChance = 18;
		int expectedTeam2WinChance = 74;
		
		matchup.setTeamNeutralWinChance(team1Name, expectedTeam1WinChance);
		
		int team1WinChance = matchup.getTeamNeutralWinChance(team1Name);
		int team2WinChance = matchup.getTeamNeutralWinChance(team2Name);
		
		assertEquals(expectedTeam1WinChance, team1WinChance);
		assertEquals((100 - expectedTeam1WinChance), team2WinChance);
		assertEquals(Matchup.WinChanceModeEnum.CUSTOM_SETTING, matchup.getWinChanceMode());
		
		matchup.setTeamNeutralWinChance(team2Name, expectedTeam2WinChance);
		
		team1WinChance = matchup.getTeamNeutralWinChance(team1Name);
		team2WinChance = matchup.getTeamNeutralWinChance(team2Name);
		
		assertEquals((100 - expectedTeam2WinChance), team1WinChance);
		assertEquals(expectedTeam2WinChance, team2WinChance);
		assertEquals(Matchup.WinChanceModeEnum.CUSTOM_SETTING, matchup.getWinChanceMode());
	}
	
	@Test
	public void setTeamHomeWinChanceSoBothTeamsHaveHomeWinChanceAltered() {
		int expectedTeam1WinChance = 18;
		int expectedTeam2WinChance = 74;
		
		matchup.setTeamHomeWinChance(team1Name, expectedTeam1WinChance);
		
		int team1WinChance = matchup.getTeamHomeWinChance(team1Name);
		int team2WinChance = matchup.getTeamAwayWinChance(team2Name);
		
		assertEquals(expectedTeam1WinChance, team1WinChance);
		assertEquals((100 - expectedTeam1WinChance), team2WinChance);
		assertEquals(Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING, matchup.getHomeAwayWinChanceMode(team1Name));
		
		matchup.setTeamHomeWinChance(team2Name, expectedTeam2WinChance);
		
		team1WinChance = matchup.getTeamAwayWinChance(team1Name);
		team2WinChance = matchup.getTeamHomeWinChance(team2Name);
		
		assertEquals((100 - expectedTeam2WinChance), team1WinChance);
		assertEquals(expectedTeam2WinChance, team2WinChance);
		assertEquals(Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING, matchup.getHomeAwayWinChanceMode(team2Name));
	}
	
	@Test
	public void setTeamAwayWinChanceSoBothTeamsHaveAwayWinChanceAltered() {
		int expectedTeam1WinChance = 18;
		int expectedTeam2WinChance = 74;
		
		matchup.setTeamAwayWinChance(team1Name, expectedTeam1WinChance);
		
		int team1WinChance = matchup.getTeamAwayWinChance(team1Name);
		int team2WinChance = matchup.getTeamHomeWinChance(team2Name);
		
		assertEquals(expectedTeam1WinChance, team1WinChance);
		assertEquals((100 - expectedTeam1WinChance), team2WinChance);
		assertEquals(Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING, matchup.getHomeAwayWinChanceMode(team2Name));
		
		matchup.setTeamAwayWinChance(team2Name, expectedTeam2WinChance);
		
		team1WinChance = matchup.getTeamHomeWinChance(team1Name);
		team2WinChance = matchup.getTeamAwayWinChance(team2Name);
		
		assertEquals((100 - expectedTeam2WinChance), team1WinChance);
		assertEquals(expectedTeam2WinChance, team2WinChance);
		assertEquals(Matchup.HomeAwayWinChanceModeEnum.CUSTOM_SETTING, matchup.getHomeAwayWinChanceMode(team1Name));
	}
	
	@Test
	public void getTeamHomeFieldAdvantageReturnsAskedForTeamsHomeFieldAdvantage() {
		int expectedTeam1HomeFieldAdvantage = 12;
		int expectedTeam2HomeFieldAdvantage = -3;
		
		when(team1.getHomeFieldAdvantage()).thenReturn(expectedTeam1HomeFieldAdvantage);
		when(team2.getHomeFieldAdvantage()).thenReturn(expectedTeam2HomeFieldAdvantage);
		
		assertEquals(expectedTeam1HomeFieldAdvantage, matchup.getTeamHomeFieldAdvantage(team1Name));
		assertEquals(expectedTeam2HomeFieldAdvantage, matchup.getTeamHomeFieldAdvantage(team2Name));
	}
	
	@Test
	public void calculateTeamWinChancesFromPowerRankingsUsesRankingsToCalculateWinChances() {
		testRankingCalculation(90, 1, 25);
		testRankingCalculation(10, 25, 1);
		testRankingCalculation(55, 1, 2);
		testRankingCalculation(82, 8, 27);
		testRankingCalculation(18, 24, 5);
		testRankingCalculation(37, 18, 12);
	}
	
	@Test
	public void calculateTeamWinChancesFromPowerRankingsButRankingsAreClearedSoNoCalculationIsDone() {
		int initialTeam1WinChance = 49;
		matchup.setTeamNeutralWinChance(team1Name, initialTeam1WinChance);
		
		when(team1.getPowerRanking()).thenReturn(Team.CLEAR_RANKING);
		when(team2.getPowerRanking()).thenReturn(5);
		
		boolean calculationSuccessful = matchup.calculateTeamWinChancesFromPowerRankings();
		
		assertNoCalculationsOrWinChanceSet(initialTeam1WinChance,
				calculationSuccessful);
		
		when(team1.getPowerRanking()).thenReturn(7);
		when(team2.getPowerRanking()).thenReturn(Team.CLEAR_RANKING);
		
		calculationSuccessful = matchup.calculateTeamWinChancesFromPowerRankings();
		
		assertNoCalculationsOrWinChanceSet(initialTeam1WinChance,
				calculationSuccessful);
	}
	
	@Test
	public void calculateTeamWinChanceFromEloRatingUsesEloRatingsToDetermineWinChance() {
		// 1 / (1 + 10 ^ ((ratingA - ratingB) / 400))
		testEloCalculation(36, 1450, 1550);
		testEloCalculation(50, 1500, 1500);
		testEloCalculation(98, 1800, 1100);
		testEloCalculation(75, 1578, 1391);
		testEloCalculation(46, 1490, 1515);
	}
	
	@Test
	public void calculateTeamWinChanceFroEloRatingButEloRatingsAreNegativeSoNoCalculationDone() {
		int initialTeam1WinChance = 49;
		matchup.setTeamNeutralWinChance(team1Name, initialTeam1WinChance);
		
		when(team1.getEloRating()).thenReturn(1500);
		when(team2.getEloRating()).thenReturn(-1);
		
		boolean calculationSuccessful = matchup.calculateTeamWinChancesFromEloRatings();
		
		assertNoCalculationsOrWinChanceSet(initialTeam1WinChance,
				calculationSuccessful);
		
		when(team1.getEloRating()).thenReturn(-86);
		when(team2.getEloRating()).thenReturn(1800);
		
		calculationSuccessful = matchup.calculateTeamWinChancesFromEloRatings();
		
		assertNoCalculationsOrWinChanceSet(initialTeam1WinChance, calculationSuccessful);
	}
	
	@Test
	public void calculateHomeAwayWinChancesFromHomeFieldAdvantageSoHomeAwayWinChancesAreCalculated() {
		testHomeFieldAdvantageCalculation(10, 15);
		testHomeFieldAdvantageCalculation(0, 35);
		testHomeFieldAdvantageCalculation(25, 14);
	}
	
	private void testRankingCalculation(
			int expectedTeam1WinChance, int team1Ranking, int team2Ranking) {
		when(team1.getPowerRanking()).thenReturn(team1Ranking);
		when(team2.getPowerRanking()).thenReturn(team2Ranking);
		
		boolean calculationSuccessful = matchup.calculateTeamWinChancesFromPowerRankings();
		
		assertTrue(calculationSuccessful);
		assertEquals(Matchup.WinChanceModeEnum.POWER_RANKINGS, matchup.getWinChanceMode());
		assertEquals(expectedTeam1WinChance, matchup.getTeamNeutralWinChance(team1Name));
		assertEquals((100 - expectedTeam1WinChance), matchup.getTeamNeutralWinChance(team2Name));
	}
	
	private void assertNoCalculationsOrWinChanceSet(int initialTeam1WinChance,
			boolean calculationSuccessful) {
		assertFalse(calculationSuccessful);
		assertEquals(Matchup.WinChanceModeEnum.CUSTOM_SETTING, matchup.getWinChanceMode());
		assertEquals(initialTeam1WinChance, matchup.getTeamNeutralWinChance(team1Name));
		assertEquals((100 - initialTeam1WinChance), matchup.getTeamNeutralWinChance(team2Name));
	}
	
	private void testEloCalculation(int expectedTeam1WinChance, int team1Rating,
			int team2Rating) {
		when(team1.getEloRating()).thenReturn(team1Rating);
		when(team2.getEloRating()).thenReturn(team2Rating);
		
		boolean calculationSuccessful = matchup.calculateTeamWinChancesFromEloRatings();
		
		assertTrue(calculationSuccessful);
		assertEquals(Matchup.WinChanceModeEnum.ELO_RATINGS, matchup.getWinChanceMode());
		assertEquals(expectedTeam1WinChance, matchup.getTeamNeutralWinChance(team1Name));
		assertEquals((100 - expectedTeam1WinChance), matchup.getTeamNeutralWinChance(team2Name));
	}
	
	private void testHomeFieldAdvantageCalculation(int team1HomeFieldAdvantage,
			int team2HomeFieldAdvantage) {
		int team1NeutralWinChance = 55;
		int team2NeutralWinChance = 100 - team1NeutralWinChance;
		when(team1.getHomeFieldAdvantage()).thenReturn(team1HomeFieldAdvantage);
		when(team2.getHomeFieldAdvantage()).thenReturn(team2HomeFieldAdvantage);
		matchup.setTeamNeutralWinChance(team1Name, team1NeutralWinChance);
		matchup.setTeamNeutralWinChance(team2Name, team2NeutralWinChance);
		
		int expectedTeam1WinChance = team1NeutralWinChance + Math.round(team1HomeFieldAdvantage / 2);
		int expectedTeam2WinChance = 100 - expectedTeam1WinChance;
		
		boolean calculationSuccessful = matchup.calculateHomeWinChanceFromHomeFieldAdvantage(team1Name);
		
		assertTrue(calculationSuccessful);
		assertEquals(Matchup.HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE, matchup.getHomeAwayWinChanceMode(team1Name));
		assertEquals(expectedTeam1WinChance, matchup.getTeamHomeWinChance(team1Name));
		assertEquals(expectedTeam2WinChance, matchup.getTeamAwayWinChance(team2Name));
		
		expectedTeam2WinChance = team2NeutralWinChance + Math.round(team2HomeFieldAdvantage / 2);
		expectedTeam1WinChance = 100 - expectedTeam2WinChance;
		
		calculationSuccessful = matchup.calculateHomeWinChanceFromHomeFieldAdvantage(team2Name);
		
		assertTrue(calculationSuccessful);
		assertEquals(Matchup.HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE, matchup.getHomeAwayWinChanceMode(team2Name));
		assertEquals(expectedTeam1WinChance, matchup.getTeamAwayWinChance(team1Name));
		assertEquals(expectedTeam2WinChance, matchup.getTeamHomeWinChance(team2Name));
	}
	
}
