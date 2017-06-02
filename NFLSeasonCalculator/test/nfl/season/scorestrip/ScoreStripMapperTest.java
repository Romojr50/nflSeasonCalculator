package nfl.season.scorestrip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.League;
import nfl.season.league.SeasonGame;
import nfl.season.league.SeasonWeek;
import nfl.season.league.Team;
import nfl.season.scorestrip.Ss.Gms;
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
	private Team team3;
	
	private String team3Name = "Team3";
	
	@Mock
	private Team team4;
	
	private String team4Name = "Team4";
	
	@Mock
	private Team team5;
	
	private String team5Name = "Team5";
	
	@Mock
	private Team team6;
	
	private String team6Name = "Team6";
	
	@Mock
	private League league;
	
	@Mock
	private Ss scoreStripWeek;
	
	@Mock
	private Gms scoreStripGames;
	
	private int weekNumber = 3;
	
	private List<G> scoreStripGameList;
	
	@Mock
	private G scoreStripGame1_2;
	
	@Mock
	private G scoreStripGame3_4;
	
	@Mock
	private G scoreStripGame5_6;
	
	private ScoreStripMapper mapper;
	
	@Before
	public void setUp() {
		when(scoreStripGame1_2.getHnn()).thenReturn(team1Name);
		when(scoreStripGame1_2.getVnn()).thenReturn(team2Name);
		when(scoreStripGame3_4.getHnn()).thenReturn(team3Name);
		when(scoreStripGame3_4.getVnn()).thenReturn(team4Name);
		when(scoreStripGame5_6.getHnn()).thenReturn(team5Name);
		when(scoreStripGame5_6.getVnn()).thenReturn(team6Name);
		
		scoreStripGameList = new ArrayList<G>();
		scoreStripGameList.add(scoreStripGame1_2);
		scoreStripGameList.add(scoreStripGame3_4);
		scoreStripGameList.add(scoreStripGame5_6);
		
		when(scoreStripWeek.getGms()).thenReturn(scoreStripGames);
		
		String weekNumberString = "" + weekNumber;
		when(scoreStripGames.getW()).thenReturn(Byte.valueOf(weekNumberString));
		
		when(scoreStripGames.getG()).thenReturn(scoreStripGameList);
		
		when(league.getTeam(team1Name)).thenReturn(team1);
		when(league.getTeam(team2Name)).thenReturn(team2);
		when(league.getTeam(team3Name)).thenReturn(team3);
		when(league.getTeam(team4Name)).thenReturn(team4);
		when(league.getTeam(team5Name)).thenReturn(team5);
		when(league.getTeam(team6Name)).thenReturn(team6);
		
		when(league.areInSameDivision(team1, team2)).thenReturn(false);
		when(league.areInSameConference(team1, team2)).thenReturn(false);
		when(league.areInSameDivision(team3, team4)).thenReturn(false);
		when(league.areInSameConference(team3, team4)).thenReturn(true);
		when(league.areInSameDivision(team5, team6)).thenReturn(true);
		when(league.areInSameConference(team5, team6)).thenReturn(true);
		
		mapper = new ScoreStripMapper(league);
	}
	
	@Test 
	public void scoreStripMapperMapsAScoreStripGameToASeasonGame() {
		SeasonGame seasonGame = mapper.mapScoreStripGameToSeasonGame(scoreStripGame1_2);
		assertEquals(team1, seasonGame.getHomeTeam());
		assertEquals(team2, seasonGame.getAwayTeam());
		assertFalse(seasonGame.isDivisionGame());
		assertFalse(seasonGame.isConferenceGame());
		
		when(league.areInSameDivision(team1, team2)).thenReturn(false);
		when(league.areInSameConference(team1, team2)).thenReturn(true);
		seasonGame = mapper.mapScoreStripGameToSeasonGame(scoreStripGame1_2);
		assertFalse(seasonGame.isDivisionGame());
		assertTrue(seasonGame.isConferenceGame());
		
		when(league.areInSameDivision(team1, team2)).thenReturn(true);
		when(league.areInSameConference(team1, team2)).thenReturn(true);
		seasonGame = mapper.mapScoreStripGameToSeasonGame(scoreStripGame1_2);
		assertTrue(seasonGame.isDivisionGame());
		assertTrue(seasonGame.isConferenceGame());
	}
	
	@Test
	public void scoreStripMapperHasAGameWithEmptyTeamsSoNullIsReturned() {
		when(scoreStripGame1_2.getHnn()).thenReturn("");
		
		SeasonGame seasonGame = mapper.mapScoreStripGameToSeasonGame(scoreStripGame1_2);
		
		assertNull(seasonGame);
	}
	
	@Test
	public void scoreStripMapperMapsAScoreStripWeekToASeasonWeek() {
		SeasonWeek seasonWeek = mapper.mapScoreStripWeekToSeasonWeek(scoreStripWeek);
		
		assertEquals(weekNumber, seasonWeek.getWeekNumber());
		
		List<SeasonGame> seasonGames = seasonWeek.getSeasonGames();
		assertEquals(3, seasonGames.size());
		
		for (SeasonGame seasonGame : seasonGames) {
			assertNotNull(seasonGame.getHomeTeam());
		}
	}
	
	@Test
	public void scoreStripMapperEncountersANullGameButNoNullIsAddedToWeekGames() {
		when(scoreStripGame1_2.getHnn()).thenReturn("");
		
		SeasonWeek seasonWeek = mapper.mapScoreStripWeekToSeasonWeek(scoreStripWeek);
		
		List<SeasonGame> seasonGames = seasonWeek.getSeasonGames();
		assertEquals(2, seasonGames.size());
		assertFalse(seasonGames.contains(null));
	}
	
	@Test
	public void scoreStripMapperHasWeekWithZeroNumberSoNullWeekIsReturned() {
		when(scoreStripGames.getW()).thenReturn(Byte.valueOf("0"));
		
		SeasonWeek seasonWeek = mapper.mapScoreStripWeekToSeasonWeek(scoreStripWeek);
		
		assertNull(seasonWeek);
	}
	
}
