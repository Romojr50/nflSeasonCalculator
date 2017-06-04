package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
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
		when(matchup1.getOpponentName(leagueTeamName)).thenReturn(opponent1Name);
		
		when(seasonGame2.getHomeTeam()).thenReturn(opponent2);
		when(seasonGame2.getAwayTeam()).thenReturn(leagueTeam);
		when(seasonGame2.getMatchup()).thenReturn(matchup2);
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
			} else {
				expectedScheduleBuilder.append("Bye");
			}
			expectedScheduleBuilder.append("\n");
			weekNumber++;
		}
		
		return expectedScheduleBuilder.toString();
	}
	
}
