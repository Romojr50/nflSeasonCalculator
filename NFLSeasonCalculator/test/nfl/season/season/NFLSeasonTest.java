package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
import nfl.season.league.Matchup;
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
	private Matchup matchup1;
	
	@Mock
	private SeasonGame seasonGame2;
	
	@Mock
	private Matchup matchup2;
	
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
	
	@Mock
	private Team team1_2_3;
	
	private String team1_2_3Name = "Team 1 - 2 - 3";
	
	@Mock
	private Team team1_2_4;
	
	private String team1_2_4Name = "Team 1 - 2 - 4";
	
	private List<Team> division1_2Teams;
	
	@Mock
	private Team team1_3_1;
	
	private String team1_3_1Name = "Team 1 - 3 - 1";
	
	@Mock
	private Team team1_3_2;
	
	private String team1_3_2Name = "Team 1 - 3 - 2";
	
	@Mock
	private Team team1_3_3;
	
	private String team1_3_3Name = "Team 1 - 3 - 3";
	
	@Mock
	private Team team1_3_4;
	
	private String team1_3_4Name = "Team 1 - 3 - 4";
	
	private List<Team> division1_3Teams;
	
	@Mock
	private Team team2_1_1;
	
	private String team2_1_1Name = "Team 2 - 1 - 1";
	
	@Mock
	private Team team2_1_2;
	
	private String team2_1_2Name = "Team 2 - 1 - 2";
	
	@Mock
	private Team team2_1_3;
	
	private String team2_1_3Name = "Team 2 - 1 - 3";
	
	private List<Team> division2_1Teams;
	
	@Mock
	private Team team2_2_1;
	
	private String team2_2_1Name = "Team 2 - 2 - 1";
	
	private List<Team> division2_2Teams;
	
	@Mock
	private ScoreStripReader scoreStripReader;
	
	@Mock
	private ScoreStripMapper scoreStripMapper;
	
	@Mock
	private Ss scoreStripWeek;
	
	@Mock
	private NFLTiebreaker tiebreaker;
	
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
		when(seasonGame1.getMatchup()).thenReturn(matchup1);
		when(seasonGame1.alreadyHappened()).thenReturn(true);
		when(seasonGame1.getWinner()).thenReturn(team1_1_1);
		when(matchup1.getOpponentName(team1_1_1Name)).thenReturn(team1_1_2Name);
		
		when(seasonGame2.getHomeTeam()).thenReturn(team1_2_1);
		when(seasonGame2.getAwayTeam()).thenReturn(team1_3_1);
		when(seasonGame2.isDivisionGame()).thenReturn(false);
		when(seasonGame2.isConferenceGame()).thenReturn(true);
		when(seasonGame2.getMatchup()).thenReturn(matchup2);
		when(seasonGame2.alreadyHappened()).thenReturn(true);
		when(seasonGame2.wasATie()).thenReturn(true);
		when(matchup2.getOpponentName(team1_2_1Name)).thenReturn(team1_3_1Name);
		
		when(seasonGame3.getHomeTeam()).thenReturn(team1_1_3);
		when(seasonGame3.getAwayTeam()).thenReturn(team2_1_1);
		when(seasonGame3.isDivisionGame()).thenReturn(false);
		when(seasonGame3.isConferenceGame()).thenReturn(false);
		
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
		division1_2Teams.add(team1_2_3);
		division1_2Teams.add(team1_2_4);
		when(division1_2.getTeams()).thenReturn(division1_2Teams);
		
		division1_3Teams = new ArrayList<Team>();
		division1_3Teams.add(team1_3_1);
		division1_3Teams.add(team1_3_2);
		division1_3Teams.add(team1_3_3);
		division1_3Teams.add(team1_3_4);
		when(division1_3.getTeams()).thenReturn(division1_3Teams);
		
		division2_1Teams = new ArrayList<Team>();
		division2_1Teams.add(team2_1_1);
		division2_1Teams.add(team2_1_2);
		division2_1Teams.add(team2_1_3);
		when(division2_1.getTeams()).thenReturn(division2_1Teams);
		
		division2_2Teams = new ArrayList<Team>();
		division2_2Teams.add(team2_2_1);
		when(division2_2.getTeams()).thenReturn(division2_2Teams);
	}

	private void setUpTeams() {
		when(team1_1_1.getName()).thenReturn(team1_1_1Name);
		when(team1_1_2.getName()).thenReturn(team1_1_2Name);
		when(team1_1_3.getName()).thenReturn(team1_1_3Name);
		when(team1_2_1.getName()).thenReturn(team1_2_1Name);
		when(team1_2_2.getName()).thenReturn(team1_2_2Name);
		when(team1_2_3.getName()).thenReturn(team1_2_3Name);
		when(team1_2_4.getName()).thenReturn(team1_2_4Name);
		when(team1_3_1.getName()).thenReturn(team1_3_1Name);
		when(team1_3_2.getName()).thenReturn(team1_3_2Name);
		when(team1_3_3.getName()).thenReturn(team1_3_3Name);
		when(team1_3_4.getName()).thenReturn(team1_3_4Name);
		when(team2_1_1.getName()).thenReturn(team2_1_1Name);
		when(team2_1_2.getName()).thenReturn(team2_1_2Name);
		when(team2_1_3.getName()).thenReturn(team2_1_3Name);
		when(team2_2_1.getName()).thenReturn(team2_2_1Name);
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
		when(seasonGame1.getWinner()).thenReturn(team1_1_1);
		when(seasonGame1.alreadyHappened()).thenReturn(true);
		
		when(seasonGame2.alreadyHappened()).thenReturn(true);
		when(seasonGame2.wasATie()).thenReturn(true);
		
		season.initializeNFLRegularSeason(league);
		season.addWeek(week);
		
		SeasonWeek[] weeks = season.getWeeks();
		assertTrue(Arrays.asList(weeks).contains(week));
		
		SeasonWeek week3 = season.getWeek(WEEK_NUMBER);
		assertEquals(week, week3);
		
		NFLSeasonTeam seasonTeam1_1_1 = season.getTeam(team1_1_1Name);
		NFLSeasonTeam seasonTeam1_1_2 = season.getTeam(team1_1_2Name);
		SeasonGame[] teamGames = seasonTeam1_1_1.getSeasonGames();
		assertTrue(Arrays.asList(teamGames).contains(seasonGame1));
		assertSeasonGame1TalliesWinAndLoss(seasonTeam1_1_1, seasonTeam1_1_2);
		
		NFLSeasonTeam seasonTeam1_2_1 = season.getTeam(team1_2_1Name);
		SeasonGame[] team1_2_1Games = seasonTeam1_2_1.getSeasonGames();
		assertTrue(Arrays.asList(team1_2_1Games).contains(seasonGame2));
		assertSeasonGame2TalliesTie(seasonTeam1_2_1);
		
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
	
	@Test
	public void getWeekStringGetsAStringRepresentingAWeek() {
		season.initializeNFLRegularSeason(league);
		
		String weekString = season.getWeekString(week);
		
		StringBuilder expectedWeekStringBuilder = new StringBuilder();
		List<SeasonGame> weekGames = week.getSeasonGames();
		for (SeasonGame weekGame : weekGames) {
			Team homeTeam = weekGame.getHomeTeam();
			Team awayTeam = weekGame.getAwayTeam();
			expectedWeekStringBuilder.append(awayTeam.getName() + " at " + 
					homeTeam.getName() + "\n");
			
			appendGameResult(expectedWeekStringBuilder, weekGame);
		}
		
		assertEquals(expectedWeekStringBuilder.toString(), weekString);
	}
	
	@Test
	public void getWeekStringWithNullWeekSoEmptyWeekStringIsReturned() {
		season.initializeNFLRegularSeason(league);
		
		String weekString = season.getWeekString(null);
		
		assertEquals("This week is empty or null; please load the season", weekString);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getLeagueStandingsGetsStandingsFromConferencesAndBottomFive() {
		season.initializeNFLRegularSeason(league);
		
		NFLSeasonTeam seasonTeam1_1_1 = season.getTeam(team1_1_1Name);
		NFLSeasonTeam seasonTeam1_1_2 = season.getTeam(team1_1_2Name);
		NFLSeasonTeam seasonTeam1_1_3 = season.getTeam(team1_1_3Name);
		NFLSeasonTeam seasonTeam1_2_1 = season.getTeam(team1_2_1Name);
		NFLSeasonTeam seasonTeam1_2_2 = season.getTeam(team1_2_2Name);
		NFLSeasonTeam seasonTeam1_2_3 = season.getTeam(team1_2_3Name);
		NFLSeasonTeam seasonTeam1_2_4 = season.getTeam(team1_2_4Name);
		NFLSeasonTeam seasonTeam1_3_1 = season.getTeam(team1_3_1Name);
		NFLSeasonTeam seasonTeam1_3_2 = season.getTeam(team1_3_2Name);
		NFLSeasonTeam seasonTeam1_3_3 = season.getTeam(team1_3_3Name);
		NFLSeasonTeam seasonTeam1_3_4 = season.getTeam(team1_3_4Name);
		NFLSeasonTeam seasonTeam2_1_1 = season.getTeam(team2_1_1Name);
		NFLSeasonTeam seasonTeam2_1_2 = season.getTeam(team2_1_2Name);
		NFLSeasonTeam seasonTeam2_1_3 = season.getTeam(team2_1_3Name);
				
		when(tiebreaker.tiebreakManyTeams(any(List.class))).thenReturn(
				seasonTeam1_1_1, seasonTeam1_1_2,					//Div1_1 Standings
				seasonTeam1_2_1, seasonTeam1_2_2, seasonTeam1_2_3,	//Div1_2 Standings
				seasonTeam1_3_1, seasonTeam1_3_2, seasonTeam1_3_3,	//Div1_3 Standings
				seasonTeam1_1_1, seasonTeam1_2_1, seasonTeam1_1_2,  //Conf1 Standings
				seasonTeam1_3_4,
				seasonTeam2_1_1, seasonTeam2_1_2,					//Div2_1 Standings
				seasonTeam2_1_1, seasonTeam2_1_2, seasonTeam2_1_3,	//Conf2 Standings
				seasonTeam1_2_2, seasonTeam1_2_3, seasonTeam1_2_4,  //Leftovers
				seasonTeam1_3_2, seasonTeam1_3_3, seasonTeam1_1_3);					
		
		String leagueStandings = season.getLeagueStandings(tiebreaker);
		
		List<NFLSeasonTeam> bottomTeams = season.getBottomTeams();
		assertEquals(5, bottomTeams.size());
		assertEquals(seasonTeam1_1_3, bottomTeams.get(0));
		assertEquals(seasonTeam1_3_3, bottomTeams.get(1));
		assertEquals(seasonTeam1_3_2, bottomTeams.get(2));
		assertEquals(seasonTeam1_2_4, bottomTeams.get(3));
		assertEquals(seasonTeam1_2_3, bottomTeams.get(4));
		
		when(tiebreaker.tiebreakManyTeams(any(List.class))).thenReturn(
				seasonTeam1_1_1, seasonTeam1_1_2,					//Div1_1 Standings
				seasonTeam1_2_1, seasonTeam1_2_2, seasonTeam1_2_3,	//Div1_2 Standings
				seasonTeam1_3_1, seasonTeam1_3_2, seasonTeam1_3_3,	//Div1_3 Standings
				seasonTeam1_1_1, seasonTeam1_2_1, seasonTeam1_1_2,  //Conf1 Standings
				seasonTeam1_3_4,
				seasonTeam2_1_1, seasonTeam2_1_2,					//Div2_1 Standings
				seasonTeam2_1_1, seasonTeam2_1_2, seasonTeam2_1_3,	//Conf2 Standings
				seasonTeam1_2_2, seasonTeam1_2_3, seasonTeam1_2_4,  //Leftovers
				seasonTeam1_3_2, seasonTeam1_3_3, seasonTeam1_1_3);	
		
		StringBuilder standingsBuilder = new StringBuilder();
		List<NFLSeasonConference> seasonConferences = season.getConferences();
		for (NFLSeasonConference seasonConference : seasonConferences) {
			standingsBuilder.append(seasonConference.getConferenceStandingsString(
					tiebreaker));
			standingsBuilder.append("\n");
		}
		standingsBuilder.append("Bottom Teams:\n");
		for (int i = 1; i <= bottomTeams.size(); i++) {
			NFLSeasonTeam bottomTeam = bottomTeams.get(i - 1);
			Team leagueBottomTeam = bottomTeam.getTeam();
			String bottomTeamName = leagueBottomTeam.getName();
			standingsBuilder.append(i + ". " + bottomTeamName + "\n");
		}
		
		assertEquals(standingsBuilder.toString(), leagueStandings);
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
	
	private void assertSeasonGame1TalliesWinAndLoss(
			NFLSeasonTeam seasonTeam1_1_1, NFLSeasonTeam seasonTeam1_1_2) {
		assertEquals(1, seasonTeam1_1_1.getNumberOfWins());
		assertEquals(1, seasonTeam1_1_1.getWinsAgainst().size());
		assertEquals(1, seasonTeam1_1_1.getNumberOfConferenceWins());
		assertEquals(1, seasonTeam1_1_1.getNumberOfDivisionWins());
		assertEquals(1, seasonTeam1_1_2.getNumberOfLosses());
		assertEquals(1, seasonTeam1_1_2.getLossesAgainst().size());
		assertEquals(1, seasonTeam1_1_2.getNumberOfConferenceLosses());
		assertEquals(1, seasonTeam1_1_2.getNumberOfDivisionLosses());
	}

	private void assertSeasonGame2TalliesTie(NFLSeasonTeam seasonTeam1_2_1) {
		assertEquals(0, seasonTeam1_2_1.getNumberOfWins());
		assertEquals(0, seasonTeam1_2_1.getWinsAgainst().size());
		assertEquals(0, seasonTeam1_2_1.getNumberOfConferenceWins());
		assertEquals(0, seasonTeam1_2_1.getNumberOfDivisionWins());
		assertEquals(0, seasonTeam1_2_1.getNumberOfLosses());
		assertEquals(0, seasonTeam1_2_1.getLossesAgainst().size());
		assertEquals(0, seasonTeam1_2_1.getNumberOfConferenceLosses());
		assertEquals(0, seasonTeam1_2_1.getNumberOfDivisionLosses());
		assertEquals(1, seasonTeam1_2_1.getNumberOfTies());
		assertEquals(1, seasonTeam1_2_1.getTiesAgainst().size());
		assertEquals(1, seasonTeam1_2_1.getNumberOfConferenceTies());
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
