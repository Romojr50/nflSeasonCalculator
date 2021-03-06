package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import nfl.season.league.Matchup;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonTeamTest {
	
	@Mock
	private Team leagueTeam;
	
	private String leagueTeamName = "League Team";
	
	@Mock
	private Team opponent1;
	
	private String opponent1Name = "Opponent 1";
	
	@Mock
	private Team opponent2;
	
	private String opponent2Name = "Opponent 2";
	
	@Mock
	private Team opponent3;
	
	private String opponent3Name = "Opponent 3";
	
	@Mock
	private Matchup matchup1;
	
	@Mock
	private Matchup matchup2;
	
	@Mock
	private Matchup matchup3;
	
	@Mock
	private SeasonGame seasonGame1;
	
	@Mock
	private SeasonGame seasonGame2;
	
	@Mock
	private SeasonGame seasonGame3;
	
	private NFLSeasonTeam seasonTeam;

	@Before
	public void setUp() {
		seasonTeam = new NFLSeasonTeam(leagueTeam);
		
		when(leagueTeam.getName()).thenReturn(leagueTeamName);
		when(opponent1.getName()).thenReturn(opponent1Name);
		when(opponent2.getName()).thenReturn(opponent2Name);
		when(opponent3.getName()).thenReturn(opponent3Name);
		
		when(seasonGame1.getHomeTeam()).thenReturn(leagueTeam);
		when(seasonGame1.getAwayTeam()).thenReturn(opponent1);
		when(seasonGame1.getMatchup()).thenReturn(matchup1);
		when(seasonGame1.alreadyHappened()).thenReturn(true);
		when(seasonGame1.getWinner()).thenReturn(leagueTeam);
		when(matchup1.getOpponentName(leagueTeamName)).thenReturn(opponent1Name);
		
		when(seasonGame2.getHomeTeam()).thenReturn(opponent2);
		when(seasonGame2.getAwayTeam()).thenReturn(leagueTeam);
		when(seasonGame2.getMatchup()).thenReturn(matchup2);
		when(seasonGame2.alreadyHappened()).thenReturn(true);
		when(seasonGame2.wasATie()).thenReturn(true);
		when(matchup2.getOpponentName(leagueTeamName)).thenReturn(opponent2Name);
		
		when(seasonGame3.getHomeTeam()).thenReturn(leagueTeam);
		when(seasonGame3.getAwayTeam()).thenReturn(opponent3);
		when(seasonGame3.getMatchup()).thenReturn(matchup3);
		when(matchup3.getOpponentName(leagueTeamName)).thenReturn(opponent3Name);
	}
	
	@Test
	public void getScheduleStringPutsAllGamesInScheduleIntoString() {
		addSeasonGamesToTeam();
		
		String seasonTeamString = seasonTeam.getScheduleString();
		
		String expectedScheduleString = getExpectedScheduleString();
		
		assertEquals(expectedScheduleString, seasonTeamString);
	}
	
	@Test
	public void getScheduleStringButScheduleIsEmptySoReturnEmptyMessage() {
		String seasonTeamString = seasonTeam.getScheduleString();
		
		String expectedScheduleString = "Team's schedule is empty\n";
		
		assertEquals(expectedScheduleString, seasonTeamString);
	}
	
	@Test
	public void simulateGamesSimulatesUnplayedAndUnsimulatedGamesAndAddsThemToTally() {
		setUpSimulatedGames();
		
		addSeasonGamesToTeam();
		
		assertEquals(6, seasonTeam.getNumberOfWins());
		assertEquals(0, seasonTeam.getNumberOfLosses());
		
		seasonTeam.simulateSeason();
		
		verify(seasonGame2, never()).simulateGame();
		verify(seasonGame3, times(5)).simulateGame();
		
		assertEquals(13, seasonTeam.getNumberOfWins());
		assertEquals(3, seasonTeam.getNumberOfLosses());
		
		setUpSimulatedGames();
		
		seasonTeam.simulateSeason();
		
		assertEquals(13, seasonTeam.getNumberOfWins());
		assertEquals(3, seasonTeam.getNumberOfLosses());
	}
	
	@Test
	public void clearSimulatedGamesClearsSimulatedGamesAndRemovesFromTally() {
		when(seasonGame1.alreadyHappened()).thenReturn(true);
		when(seasonGame1.getWinner()).thenReturn(leagueTeam);
		
		when(seasonGame2.alreadyHappened()).thenReturn(false);
		when(seasonGame2.getSimulatedWinner()).thenReturn(leagueTeam);
		
		when(seasonGame3.alreadyHappened()).thenReturn(false);
		when(seasonGame3.getSimulatedWinner()).thenReturn(opponent3);
		
		addSeasonGamesToTeam();
		
		seasonTeam.clearSimulatedGames();
		
		verify(seasonGame2, times(5)).clearSimulatedResult();
		verify(seasonGame3, times(5)).clearSimulatedResult();
		
		assertEquals(6, seasonTeam.getNumberOfWins());
		assertEquals(0, seasonTeam.getNumberOfLosses());
		
		List<String> winsAgainst = seasonTeam.getWinsAgainst();
		List<String> lossesAgainst = seasonTeam.getLossesAgainst();
		assertEquals(6, winsAgainst.size());
		assertEquals(0, lossesAgainst.size());
	}
	
	@Test
	public void getSimulatedResultsReportReturnsResultsOfSimulations() {
		for (int i = 0; i < 500; i++) {
			seasonTeam.addGotOneSeed();
		}
		String simulatedResults = seasonTeam.getSimulatedResults(1000);
		
		int averageWins = (int) Math.round(seasonTeam.getSimulatedWins() / 1000.0);
		int averageLosses = (int) Math.round(seasonTeam.getSimulatedLosses() / 1000.0);
		int chanceToGetOneSeed = (int) Math.round(seasonTeam.getGotOneSeed() / 1000.0 * 100.0);
		int chanceToGetRoundOneBye = (int) Math.round(seasonTeam.getGotRoundOneBye() / 1000.0 * 100.0);
		int chanceToWinDivision = (int) Math.round(seasonTeam.getWonDivision() / 1000.0 * 100.0);
		int chanceToMakePlayoffs = (int) Math.round(seasonTeam.getMadePlayoffs() / 1000.0 * 100.0);
		int chanceToHaveWinningSeason = (int) Math.round(seasonTeam.getHadWinningSeason() / 1000.0 * 100.0);
		int chanceToBeInCellar = (int) Math.round(seasonTeam.getWasInDivisionCellar() / 1000.0 * 100.0);
		int chanceToBeBottomTeam = (int) Math.round(seasonTeam.getWasBottomTeam() / 1000.0 * 100.0);
		int chanceToWinSuperBowl = (int) Math.round(seasonTeam.getChanceToWinSuperBowl() / 1000.0);
		int chanceToWinConference = (int) Math.round(seasonTeam.getChanceToWinConference() / 1000.0);
		int chanceToMakeConferenceRound = (int) Math.round(seasonTeam.getChanceToMakeConferenceRound() / 1000.0);
		int chanceToMakeDivisionalRound = (int) Math.round(seasonTeam.getChanceToMakeDivisionalRound() / 1000.0);
		
		StringBuilder resultsBuilder = new StringBuilder();
		resultsBuilder.append("Average Wins: ");
		resultsBuilder.append(averageWins + "\n");
		resultsBuilder.append("Average Losses: ");
		resultsBuilder.append(averageLosses + "\n");
		resultsBuilder.append("Percent Chance to...\n");
		resultsBuilder.append("Win Super Bowl: " + chanceToWinSuperBowl + "\n");
		resultsBuilder.append("Win Conference: " + chanceToWinConference + "\n");
		resultsBuilder.append("Get to Conference Round: " + chanceToMakeConferenceRound + "\n");
		resultsBuilder.append("Get to Divisional Round: " + chanceToMakeDivisionalRound + "\n");
		resultsBuilder.append("Win One Seed: " + chanceToGetOneSeed + "\n");
		resultsBuilder.append("Get Round One Bye: " + chanceToGetRoundOneBye + "\n");
		resultsBuilder.append("Win Division: " + chanceToWinDivision + "\n");
		resultsBuilder.append("Make Playoffs: " + chanceToMakePlayoffs + "\n");
		resultsBuilder.append("Have Winning Season: " + chanceToHaveWinningSeason + "\n");
		resultsBuilder.append("Be in Division Cellar: " + chanceToBeInCellar + "\n");
		resultsBuilder.append("Be in League Bottom 5: " + chanceToBeBottomTeam + "\n");
		
		String expectedResults = resultsBuilder.toString();
		expectedResults = expectedResults.replace(" 0", " < 1");
		
		assertEquals(expectedResults, simulatedResults);
	}
	
	@Test
	public void getSimulatedResultsButNoSimulationsDoneSoReturnNoSimulationsMessage() {
		String simulatedResults = seasonTeam.getSimulatedResults(1000);
		assertEquals("No simulations done yet; please run the Many Seasons Simulation\n",
				simulatedResults);
	}
	
	@Test
	public void clearSimulatedResultsClearsSimulatedNumbers() {
		for (int i = 0; i < 500; i++) {
			seasonTeam.addGotOneSeed();
			seasonTeam.addToChanceToWinSuperBowl(12);
			seasonTeam.addSimulatedLosses(15);
		}
		seasonTeam.clearSimulatedResults();
		
		assertEquals(0, seasonTeam.getGotOneSeed());
		assertEquals(0, seasonTeam.getGotRoundOneBye());
		assertEquals(0, seasonTeam.getWonDivision());
		assertEquals(0, seasonTeam.getMadePlayoffs());
		assertEquals(0, seasonTeam.getHadWinningSeason());
		assertEquals(0, seasonTeam.getWasInDivisionCellar());
		assertEquals(0, seasonTeam.getWasBottomTeam());
		assertEquals(0, seasonTeam.getSimulatedWins());
		assertEquals(0, seasonTeam.getSimulatedLosses());
		assertEquals(0, seasonTeam.getChanceToWinSuperBowl());
		assertEquals(0, seasonTeam.getChanceToWinConference());
		assertEquals(0, seasonTeam.getChanceToMakeConferenceRound());
		assertEquals(0, seasonTeam.getChanceToMakeDivisionalRound());
		assertEquals(0, seasonTeam.getSimulatedWins());
		assertEquals(0, seasonTeam.getSimulatedLosses());
	}

	private void addSeasonGamesToTeam() {
		seasonTeam.addSeasonGame(1, seasonGame1);
		seasonTeam.addSeasonGame(2, seasonGame2);
		seasonTeam.addSeasonGame(3, seasonGame3);
		seasonTeam.addSeasonGame(4, seasonGame1);
		seasonTeam.addSeasonGame(5, seasonGame2);
		seasonTeam.addSeasonGame(6, seasonGame3);
		seasonTeam.addSeasonGame(7, seasonGame1);
		seasonTeam.addSeasonGame(8, seasonGame2);
		seasonTeam.addSeasonGame(9, seasonGame3);
		seasonTeam.addSeasonGame(10, seasonGame1);
		seasonTeam.addSeasonGame(12, seasonGame2);
		seasonTeam.addSeasonGame(13, seasonGame3);
		seasonTeam.addSeasonGame(14, seasonGame1);
		seasonTeam.addSeasonGame(15, seasonGame2);
		seasonTeam.addSeasonGame(16, seasonGame3);
		seasonTeam.addSeasonGame(17, seasonGame1);
	}
	
	private String getExpectedScheduleString() {
		StringBuilder expectedScheduleBuilder = new StringBuilder();
		SeasonGame[] seasonGames = seasonTeam.getSeasonGames();
		int weekNumber = 1;
		for (SeasonGame seasonGame : seasonGames) {
			expectedScheduleBuilder.append("Week " + weekNumber + " ");
			if (seasonGame != null) {
				Matchup matchup = seasonGame.getMatchup();
				String opponentName = matchup.getOpponentName(leagueTeamName);
				
				Team homeTeam = seasonGame.getHomeTeam();
				if (homeTeam.equals(leagueTeam)) {
					expectedScheduleBuilder.append("vs. ");
				} else {
					expectedScheduleBuilder.append("at ");
				}
				
				expectedScheduleBuilder.append(opponentName);
				
				appendGameResult(expectedScheduleBuilder, seasonGame);
			} else {
				expectedScheduleBuilder.append("Bye");
			}
			expectedScheduleBuilder.append("\n");
			weekNumber++;
		}
		
		return expectedScheduleBuilder.toString();
	}
	
	private void setUpSimulatedGames() {
		when(seasonGame1.alreadyHappened()).thenReturn(true);
		when(seasonGame1.getWinner()).thenReturn(leagueTeam);
		
		when(seasonGame2.alreadyHappened()).thenReturn(false);
		when(seasonGame2.getSimulatedWinner()).thenReturn(leagueTeam);
		
		when(seasonGame3.alreadyHappened()).thenReturn(false);
		when(seasonGame3.getSimulatedWinner()).thenReturn(null, null, null, null, 
				null, null, opponent3, null, leagueTeam, null, leagueTeam, null, opponent3, 
				null, opponent3);
	}

	private void appendGameResult(StringBuilder expectedScheduleBuilder,
			SeasonGame seasonGame) {
		if (seasonGame.alreadyHappened()) {
			Team winner = seasonGame.getWinner();
			
			if (winner != null) {
				String winnerName = winner.getName();
				expectedScheduleBuilder.append(", " + winnerName);
			} else if (seasonGame.wasATie()) {
				expectedScheduleBuilder.append(", Tie");
			}
		}
	}
	
}
