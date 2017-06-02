package nfl.season.scorestrip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import nfl.season.league.League;
import nfl.season.league.SeasonGame;
import nfl.season.league.Team;
import nfl.season.scorestrip.Ss.Gms.G;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ScoreStripMapperTest {

	@Mock
	private Team team1;
	
	private String team1Name = "Team1";
	
	@Mock
	private Team team2;
	
	private String team2Name = "Team2";
	
	@Mock
	private League league;
	
	@Mock
	private G scoreStripGame;
	
	private ScoreStripMapper mapper;
	
	@Before
	public void setUp() {
		when(league.getTeam(team1Name)).thenReturn(team1);
		when(league.getTeam(team2Name)).thenReturn(team2);
		
		when(scoreStripGame.getHnn()).thenReturn(team1Name);
		when(scoreStripGame.getVnn()).thenReturn(team2Name);
		
		mapper = new ScoreStripMapper(league);
	}
	
	@Test 
	public void scoreStripMapperMapsAScoreStripGameToASeasonGame() {
		when(league.areInSameDivision(team1, team2)).thenReturn(false);
		when(league.areInSameConference(team1, team2)).thenReturn(false);
		
		SeasonGame seasonGame = mapper.mapScoreStripGameToSeasonGame(scoreStripGame);
		assertEquals(team1, seasonGame.getHomeTeam());
		assertEquals(team2, seasonGame.getAwayTeam());
		assertFalse(seasonGame.isDivisionGame());
		assertFalse(seasonGame.isConferenceGame());
		
		when(league.areInSameDivision(team1, team2)).thenReturn(false);
		when(league.areInSameConference(team1, team2)).thenReturn(true);
		seasonGame = mapper.mapScoreStripGameToSeasonGame(scoreStripGame);
		assertFalse(seasonGame.isDivisionGame());
		assertTrue(seasonGame.isConferenceGame());
		
		when(league.areInSameDivision(team1, team2)).thenReturn(true);
		when(league.areInSameConference(team1, team2)).thenReturn(true);
		seasonGame = mapper.mapScoreStripGameToSeasonGame(scoreStripGame);
		assertTrue(seasonGame.isDivisionGame());
		assertTrue(seasonGame.isConferenceGame());
	}
	
}
