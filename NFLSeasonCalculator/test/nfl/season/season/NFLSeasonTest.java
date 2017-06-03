package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.League;
import nfl.season.league.Team;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.scorestrip.Ss;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonTest {

	private static final int WEEK_NUMBER = 3;

	@Mock
	private SeasonGame seasonGame1;
	
	@Mock
	private SeasonGame seasonGame2;
	
	@Mock
	private SeasonGame seasonGame3;
	
	private List<SeasonGame> seasonGames;
	
	@Mock
	private SeasonWeek week;
	
	@Mock
	private League league;
	
	private List<Conference> conferences;
	
	@Mock
	private Conference conference1;
	
	@Mock
	private Conference conference2;
	
	private List<Division> conference1Divisions;
	
	@Mock
	private Division division1_1;
		
	@Mock
	private Division division1_2;
	
	@Mock
	private Division division1_3;
	
	private List<Division> conference2Divisions;
	
	@Mock
	private Division division2_1;
	
	@Mock
	private Division division2_2;
	
	@Mock
	private Team team1_1_1;
	
	private String team1_1_1Name = "Team 1 - 1 - 1";
	
	@Mock
	private Team team1_1_2;
	
	private String team1_1_2Name = "Team 1 - 1 - 2";
	
	@Mock
	private Team team1_1_3;
	
	private String team1_1_3Name = "Team 1 - 1 - 3";
	
	private List<Team> division1_1Teams;
	
	@Mock
	private Team team1_2_1;
	
	private String team1_2_1Name = "Team 1 - 2 - 1";
	
	@Mock
	private Team team1_2_2;
	
	private String team1_2_2Name = "Team 1 - 2 - 2";
	
	private List<Team> division1_2Teams;
	
	@Mock
	private Team team1_3_1;
	
	private String team1_3_1Name = "Team 1 - 3 - 1";
	
	private List<Team> division1_3Teams;
	
	@Mock
	private Team team2_1_1;
	
	private String team2_1_1Name = "Team 2 - 1 - 1";
	
	private List<Team> division2_1Teams;
	
	@Mock
	private ScoreStripReader scoreStripReader;
	
	@Mock
	private ScoreStripMapper scoreStripMapper;
	
	@Mock
	private Ss scoreStripWeek;
	
	private NFLSeason season;
	
	@Before
	public void setUp() {
		season = new NFLSeason();
		
		setUpWeek();
		setUpConferences();
		setUpDivisions();
		setUpTeams();
	}

	private void setUpWeek() {
		when(seasonGame1.getHomeTeam()).thenReturn(team1_1_1);
		when(seasonGame1.getAwayTeam()).thenReturn(team1_1_2);
		when(seasonGame1.isDivisionGame()).thenReturn(true);
		when(seasonGame1.isConferenceGame()).thenReturn(true);
		
		when(seasonGame2.getHomeTeam()).thenReturn(team1_2_1);
		when(seasonGame2.getAwayTeam()).thenReturn(team1_3_1);
		when(seasonGame2.isDivisionGame()).thenReturn(false);
		when(seasonGame1.isConferenceGame()).thenReturn(true);
		
		when(seasonGame3.getHomeTeam()).thenReturn(team1_1_3);
		when(seasonGame3.getAwayTeam()).thenReturn(team2_1_1);
		when(seasonGame2.isDivisionGame()).thenReturn(false);
		when(seasonGame1.isConferenceGame()).thenReturn(false);
		
		seasonGames = new ArrayList<SeasonGame>();
		seasonGames.add(seasonGame1);
		seasonGames.add(seasonGame2);
		seasonGames.add(seasonGame3);
		when(week.getSeasonGames()).thenReturn(seasonGames);
		when(week.getWeekNumber()).thenReturn(WEEK_NUMBER);
	}

	private void setUpConferences() {
		conferences = new ArrayList<Conference>();
		conferences.add(conference1);
		conferences.add(conference2);
		when(league.getConferences()).thenReturn(conferences);
		
		conference1Divisions = new ArrayList<Division>();
		conference1Divisions.add(division1_1);
		conference1Divisions.add(division1_2);
		conference1Divisions.add(division1_3);
		when(conference1.getDivisions()).thenReturn(conference1Divisions);
		
		conference2Divisions = new ArrayList<Division>();
		conference2Divisions.add(division2_1);
		conference2Divisions.add(division2_2);
		when(conference2.getDivisions()).thenReturn(conference2Divisions);
	}

	private void setUpDivisions() {
		division1_1Teams = new ArrayList<Team>();
		division1_1Teams.add(team1_1_1);
		division1_1Teams.add(team1_1_2);
		division1_1Teams.add(team1_1_3);
		when(division1_1.getTeams()).thenReturn(division1_1Teams);
		
		division1_2Teams = new ArrayList<Team>();
		division1_2Teams.add(team1_2_1);
		division1_2Teams.add(team1_2_2);
		when(division1_2.getTeams()).thenReturn(division1_2Teams);
		
		division1_3Teams = new ArrayList<Team>();
		division1_3Teams.add(team1_3_1);
		when(division1_3.getTeams()).thenReturn(division1_3Teams);
		
		division2_1Teams = new ArrayList<Team>();
		division2_1Teams.add(team2_1_1);
		when(division2_1.getTeams()).thenReturn(division2_1Teams);
	}

	private void setUpTeams() {
		when(team1_1_1.getName()).thenReturn(team1_1_1Name);
		when(team1_1_2.getName()).thenReturn(team1_1_2Name);
		when(team1_1_3.getName()).thenReturn(team1_1_3Name);
		when(team1_2_1.getName()).thenReturn(team1_2_1Name);
		when(team1_2_2.getName()).thenReturn(team1_2_2Name);
		when(team1_3_1.getName()).thenReturn(team1_3_1Name);
		when(team2_1_1.getName()).thenReturn(team2_1_1Name);
	}
	
	@Test
	public void seasonIsInitiatedWithAllTeams() {
		season.initializeNFLRegularSeason(league);
		
		List<NFLSeasonConference> seasonConferences = season.getConferences();
		
		List<Conference> returnedConferences = new ArrayList<Conference>();
		List<Division> returnedDivisions = new ArrayList<Division>();
		List<Team> returnedTeams = new ArrayList<Team>();
		List<NFLSeasonTeam> returnedSeasonTeams = new ArrayList<NFLSeasonTeam>();
		
		for (NFLSeasonConference seasonConference : seasonConferences) {
			returnedConferences.add(seasonConference.getConference());
			
			List<NFLSeasonDivision> seasonDivisions = seasonConference.getDivisions();
			for (NFLSeasonDivision seasonDivision : seasonDivisions) {
				returnedDivisions.add(seasonDivision.getDivision());
				
				List<NFLSeasonTeam> seasonTeams = seasonDivision.getTeams();
				for (NFLSeasonTeam seasonTeam : seasonTeams) {
					returnedSeasonTeams.add(seasonTeam);
					returnedTeams.add(seasonTeam.getTeam());
				}
			}
		}
		
		assertSeasonHasConferencesDivisionsAndTeams(returnedConferences,
				returnedDivisions, returnedTeams);
		
		NFLSeasonTeam returnedSeasonTeam1 = returnedSeasonTeams.get(0);
		assertEquals(NFLSeason.NUMBER_OF_WEEKS_IN_SEASON, 
				returnedSeasonTeam1.getSeasonGames().length);
	}
	
	@Test
	public void addWeekToSeasonAddsWeekToSeasonAndTeamSchedules() {
		season.initializeNFLRegularSeason(league);
		season.addWeek(week);
		
		SeasonWeek[] weeks = season.getWeeks();
		assertTrue(Arrays.asList(weeks).contains(week));
		
		SeasonWeek week3 = season.getWeek(WEEK_NUMBER);
		assertEquals(week, week3);
		
		NFLSeasonTeam seasonTeam1_1_1 = season.getTeam(team1_1_1Name);
		SeasonGame[] teamGames = seasonTeam1_1_1.getSeasonGames();
		assertTrue(Arrays.asList(teamGames).contains(seasonGame1));
		
		SeasonGame team1_1_1Week3Game = seasonTeam1_1_1.getSeasonGame(WEEK_NUMBER);
		assertEquals(seasonGame1, team1_1_1Week3Game);
		
		NFLSeasonTeam seasonTeam1_2_2 = season.getTeam(team1_2_2Name);
		SeasonGame team1_2_2Week3Game = seasonTeam1_2_2.getSeasonGame(WEEK_NUMBER);
		assertNull(team1_2_2Week3Game);
	}
	
	@Test
	public void loadSeasonTakesInReaderAndMapperAndLoadsWholeSeason() throws MalformedURLException {
		when(week.getWeekNumber()).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 
				12, 13, 14, 15, 16, 17);
		
		when(scoreStripMapper.mapScoreStripWeekToSeasonWeek(scoreStripWeek)).thenReturn(week);
		when(scoreStripReader.readScoreStripURL(anyString())).thenReturn(scoreStripWeek);
		
		season.initializeNFLRegularSeason(league);
		season.loadSeason(scoreStripReader, scoreStripMapper);
		
		SeasonWeek[] weeks = season.getWeeks();
		for (SeasonWeek week : weeks) {
			assertNotNull(week);
		}
		
		NFLSeasonTeam seasonTeam1_1_1 = season.getTeam(team1_1_1Name);
		
		for (int i = 1; i <= NFLSeason.NUMBER_OF_WEEKS_IN_SEASON; i++) {
			verify(scoreStripReader).generateScoreStripURL("2017", i);
			assertEquals(seasonGame1, seasonTeam1_1_1.getSeasonGame(i));
			
		}
		verify(scoreStripReader, times(17)).readScoreStripURL(anyString());
	}
	
	private void assertSeasonHasConferencesDivisionsAndTeams(
			List<Conference> returnedConferences,
			List<Division> returnedDivisions, List<Team> returnedTeams) {
		assertNotNull(season.getLeague());
		
		assertTrue(returnedConferences.contains(conference1));
		assertTrue(returnedConferences.contains(conference2));
		
		assertTrue(returnedDivisions.contains(division1_1));
		assertTrue(returnedDivisions.contains(division1_2));
		assertTrue(returnedDivisions.contains(division2_1));
		assertTrue(returnedDivisions.contains(division2_2));
		
		assertTrue(returnedTeams.contains(team1_1_1));
		assertTrue(returnedTeams.contains(team1_1_2));
		assertTrue(returnedTeams.contains(team1_2_1));
		assertTrue(returnedTeams.contains(team2_1_1));
		
		assertEquals(NFLSeason.NUMBER_OF_WEEKS_IN_SEASON, season.getWeeks().length);
	}
	
}
