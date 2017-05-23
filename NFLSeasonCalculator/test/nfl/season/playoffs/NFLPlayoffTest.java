package nfl.season.playoffs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.Game;
import nfl.season.league.League;
import nfl.season.league.Matchup;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLPlayoffTest extends TestWithMockPlayoffObjects {

	@Mock
	private League nfl;
	
	@Mock
	private Conference conference1;
	
	private String conference1Name = "AFC";
	
	@Mock
	private Conference conference2;
	
	private String conference2Name = "NFC";
	
	private List<Conference> conferences;
	
	@Mock
	private Division division1_1;
	
	private String division1_1Name = "Northwest";
	
	@Mock
	private Division division1_2;
	
	private String division1_2Name = "Southeast";
	
	private List<Division> conference1Divisions;
	
	@Mock
	private Division division2_1;
	
	private String division2_1Name = "Northeast";
	
	@Mock
	private Division division2_2;
	
	private String division2_2Name = "Southwest";
	
	@Mock
	private NFLPlayoffTeam playoffTeam1;
	
	@Mock
	private Team leagueTeam1;
	
	@Mock
	private NFLPlayoffTeam playoffTeam2;
	
	@Mock
	private Team leagueTeam2;
	
	@Mock
	private NFLPlayoffTeam playoffTeam3;
	
	@Mock
	private Team leagueTeam3;
	
	@Mock
	private NFLPlayoffTeam playoffTeam4;
	
	@Mock
	private Team leagueTeam4;
	
	@Mock
	private Matchup matchup1_3;
	
	@Mock
	private Matchup matchup1_4;
	
	@Mock
	private Matchup matchup2_3;
	
	@Mock
	private Matchup matchup2_4;
	
	private List<Division> conference2Divisions;
	
	private NFLPlayoffs playoffs;
	
	@Before
	public void setUp() {
		playoffs = new NFLPlayoffs(nfl);
		
		conferences = new ArrayList<Conference>();
		conferences.add(conference1);
		conferences.add(conference2);
		when(nfl.getConferences()).thenReturn(conferences);
		
		conference1Divisions = new ArrayList<Division>();
		conference1Divisions.add(division1_1);
		conference1Divisions.add(division1_2);
		when(conference1.getDivisions()).thenReturn(conference1Divisions);
		when(conference1.getName()).thenReturn(conference1Name);
		
		conference2Divisions = new ArrayList<Division>();
		conference2Divisions.add(division2_1);
		conference2Divisions.add(division2_2);
		when(conference2.getDivisions()).thenReturn(conference2Divisions);
		when(conference2.getName()).thenReturn(conference2Name);
		
		when(division1_1.getName()).thenReturn(division1_1Name);
		when(division1_2.getName()).thenReturn(division1_2Name);
		when(division2_1.getName()).thenReturn(division2_1Name);
		when(division2_2.getName()).thenReturn(division2_2Name);
		
		setUpTeams();
		
		super.setUpMockObjects();
	}

	private void setUpTeams() {
		when(playoffTeam1.getTeam()).thenReturn(leagueTeam1);
		when(playoffTeam2.getTeam()).thenReturn(leagueTeam2);
		when(playoffTeam3.getTeam()).thenReturn(leagueTeam3);
		when(playoffTeam4.getTeam()).thenReturn(leagueTeam4);
		
		when(leagueTeam1.getName()).thenReturn("Team 1");
		when(leagueTeam2.getName()).thenReturn("Team 2");
		when(leagueTeam3.getName()).thenReturn("Team 3");
		when(leagueTeam4.getName()).thenReturn("Team 4");
		
		when(leagueTeam1.getMatchup(leagueTeam3.getName())).thenReturn(matchup1_3);
		when(leagueTeam1.getMatchup(leagueTeam4.getName())).thenReturn(matchup1_4);
		when(leagueTeam2.getMatchup(leagueTeam3.getName())).thenReturn(matchup2_3);
		when(leagueTeam2.getMatchup(leagueTeam4.getName())).thenReturn(matchup2_4);
	}
	
	@Test
	public void initializeNFLPlayoffsInitializesWithExpectedConferencesAndDivisions() {
		playoffs.initializeNFLPlayoffs();
		
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		
		List<Conference> returnedConferences = new ArrayList<Conference>();
		List<Division> returnedDivisions = new ArrayList<Division>();
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			returnedConferences.add(playoffConference.getConference());
			
			List<NFLPlayoffDivision> playoffDivisions = playoffConference.getDivisions();
			for (NFLPlayoffDivision playoffDivision : playoffDivisions) {
				returnedDivisions.add(playoffDivision.getDivision());
			}
		}
		assertTrue(returnedConferences.contains(conference1));
		assertTrue(returnedConferences.contains(conference2));
		
		assertTrue(returnedDivisions.contains(division1_1));
		assertTrue(returnedDivisions.contains(division1_2));
		assertTrue(returnedDivisions.contains(division2_1));
		assertTrue(returnedDivisions.contains(division2_2));
	}
	
	@Test
	public void setDivisionWinnerSetsAWinnerToAPlayoffDivision() {
		when(playoffTeam1.getConferenceSeed()).thenReturn(1);
		when(playoffTeam2.getConferenceSeed()).thenReturn(2);
		
		playoffs.initializeNFLPlayoffs();
		
		playoffs.setDivisionWinner(conference1.getName(), division1_2.getName(), playoffTeam1);
		playoffs.setDivisionWinner(conference1.getName(), division1_1.getName(), playoffTeam2);
		
		verify(playoffTeam1).setConferenceSeed(1);
		verify(playoffTeam2).setConferenceSeed(2);
		assertEquals(playoffTeam1, playoffs.getDivisionWinner(conference1.getName(), division1_2.getName()));
		assertEquals(playoffTeam2, playoffs.getDivisionWinner(conference1.getName(), division1_1.getName()));
		assertEquals(playoffTeam1, playoffs.getTeamByConferenceSeed(conference1.getName(), 1));
		assertEquals(playoffTeam2, playoffs.getTeamByConferenceSeed(conference1.getName(), 2));
	}
	
	@Test
	public void setDivisionWinnerOverDivisionThatAlreadyHadWinnerSoWinnerIsOverwritten() {
		when(playoffTeam1.getConferenceSeed()).thenReturn(1);
		when(playoffTeam2.getConferenceSeed()).thenReturn(1);
		
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1.getName(), division1_2.getName(), playoffTeam1);
		playoffs.setDivisionWinner(conference1.getName(), division1_2.getName(), playoffTeam2);
		
		verify(playoffTeam2).setConferenceSeed(1);
		assertEquals(playoffTeam2, playoffs.getDivisionWinner(conference1.getName(), division1_2.getName()));
		assertEquals(playoffTeam2, playoffs.getTeamByConferenceSeed(conference1.getName(), 1));
	}
	
	@Test
	public void setDivisionWinnerOnMismatchedDivisionAndConferenceSoNoWinnerIsSet() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1.getName(), division2_2.getName(), playoffTeam1);
		
		verify(playoffTeam1, never()).setConferenceSeed(anyInt());
		assertEquals(null, playoffs.getDivisionWinner(conference1.getName(), division2_2.getName()));
		assertEquals(null, playoffs.getTeamByConferenceSeed(conference1.getName(), 1));
		assertEquals(null, playoffs.getTeamByConferenceSeed(conference2.getName(), 1));
	}
	
	@Test
	public void setConferenceSeedWithAnotherTeamAlreadyHavingSeedSoSeedsAreSwitched() {
		when(playoffTeam1.getConferenceSeed()).thenReturn(1);
		when(playoffTeam2.getConferenceSeed()).thenReturn(2);
		
		playoffs.initializeNFLPlayoffs();
		
		playoffs.setDivisionWinner(conference1.getName(), division1_2.getName(), playoffTeam1);
		playoffs.setDivisionWinner(conference1.getName(), division1_1.getName(), playoffTeam2);
		playoffs.setTeamConferenceSeed(playoffTeam2, 1);
		
		verify(playoffTeam1).setConferenceSeed(2);
		verify(playoffTeam2).setConferenceSeed(1);
		when(playoffTeam1.getConferenceSeed()).thenReturn(2);
		when(playoffTeam2.getConferenceSeed()).thenReturn(1);
		
		assertEquals(playoffTeam1, playoffs.getDivisionWinner(conference1.getName(), division1_2.getName()));
		assertEquals(playoffTeam2, playoffs.getDivisionWinner(conference1.getName(), division1_1.getName()));
		assertEquals(playoffTeam1, playoffs.getTeamByConferenceSeed(conference1.getName(), 2));
		assertEquals(playoffTeam2, playoffs.getTeamByConferenceSeed(conference1.getName(), 1));
	}
	
	@Test
	public void setConferenceSeedOnTeamThatDoesNotAlreadyHaveSeedSoSeedIsNotSet() {
		when(playoffTeam1.getConferenceSeed()).thenReturn(1);
		
		playoffs.initializeNFLPlayoffs();
		
		playoffs.setDivisionWinner(conference1.getName(), division1_2.getName(), playoffTeam1);
		playoffs.setTeamConferenceSeed(playoffTeam2, 1);
		
		verify(playoffTeam1, never()).setConferenceSeed(NFLPlayoffConference.CLEAR_SEED);
		verify(playoffTeam2, never()).setConferenceSeed(anyInt());
		
		assertEquals(playoffTeam1, playoffs.getTeamByConferenceSeed(conference1.getName(), 1));
	}
	
	@Test
	public void addWildcardTeamToConferenceAddsWildcardTeamsToConferenceUpToTwo() {
		when(playoffTeam1.getConferenceSeed()).thenReturn(5);
		when(playoffTeam2.getConferenceSeed()).thenReturn(6);
		when(playoffTeam3.getConferenceSeed()).thenReturn(NFLPlayoffConference.CLEAR_SEED);
		
		playoffs.initializeNFLPlayoffs();
		
		playoffs.addWildcardTeam(conference1.getName(), playoffTeam1);
		playoffs.addWildcardTeam(conference1.getName(), playoffTeam2);
		playoffs.addWildcardTeam(conference1.getName(), playoffTeam3);
		
		verify(playoffTeam1).setConferenceSeed(5);
		verify(playoffTeam2).setConferenceSeed(6);
		verify(playoffTeam3).setConferenceSeed(6);
		when(playoffTeam3.getConferenceSeed()).thenReturn(6);
		
		assertEquals(playoffTeam1, playoffs.getTeamByConferenceSeed(conference1.getName(), 5));
		assertEquals(playoffTeam3, playoffs.getTeamByConferenceSeed(conference1.getName(), 6));
		
		NFLPlayoffConference playoffConference = playoffs.getConference(conference1.getName());
		List<NFLPlayoffTeam> playoffTeams = playoffConference.getTeams();
		assertTrue(playoffTeams.contains(playoffTeam1));
		assertTrue(playoffTeams.contains(playoffTeam3));
		assertFalse(playoffTeams.contains(playoffTeam2));
	}
	
	@Test
	public void clearPlayoffTeamsClearsAllPlayoffTeamLists() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_2Name, playoffTeam1);
		playoffs.setDivisionWinner(conference2Name, division2_1Name, playoffTeam2);
		playoffs.addWildcardTeam(conference1Name, playoffTeam3);
		
		playoffs.clearPlayoffTeams();
		
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			for (int i = 1; i <= 6; i++) {
				assertNull(playoffs.getTeamByConferenceSeed(conferenceName, i));
			}
			
			assertEquals(0, playoffConference.getDivisionWinners().size());
			assertEquals(0, playoffConference.getTeams().size());
			
			List<NFLPlayoffDivision> playoffDivisions = playoffConference.getDivisions();
			for (NFLPlayoffDivision playoffDivision : playoffDivisions) {
				assertNull(playoffDivision.getDivisionWinner());
			}
		}
	}
	
	@Test
	public void generateWildcardMatchupsForConferenceGeneratesTheConferenceWildcardGames() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_1Name, playoffTeam2);
		playoffs.setDivisionWinner(conference1Name, division1_2Name, playoffTeam1);
		playoffs.setTeamConferenceSeed(playoffTeam2, 3);
		playoffs.setTeamConferenceSeed(playoffTeam1, 4);
		playoffs.addWildcardTeam(conference1Name, playoffTeam4);
		playoffs.addWildcardTeam(conference1Name, playoffTeam3);
		
		when(playoffTeam2.getConferenceSeed()).thenReturn(3);
		when(playoffTeam1.getConferenceSeed()).thenReturn(4);
		when(playoffTeam4.getConferenceSeed()).thenReturn(5);
		when(playoffTeam3.getConferenceSeed()).thenReturn(6);
		
		List<Game> wildcardGames = playoffs.getWildcardGames(conference1Name);
		
		assertGameListHasCorrectMatchupsAndHomeTeams(wildcardGames, matchup1_4, matchup2_3);
	}
	
	@Test
	public void generateWildcardMatchupsButNotAllTeamsAreSetSoEmptyListIsReturned() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_1Name, playoffTeam2);
		playoffs.setDivisionWinner(conference1Name, division1_2Name, playoffTeam1);
		playoffs.setTeamConferenceSeed(playoffTeam2, 3);
		playoffs.setTeamConferenceSeed(playoffTeam1, 4);
		playoffs.addWildcardTeam(conference1Name, playoffTeam4);
		
		when(playoffTeam2.getConferenceSeed()).thenReturn(3);
		when(playoffTeam1.getConferenceSeed()).thenReturn(4);
		when(playoffTeam4.getConferenceSeed()).thenReturn(5);
		
		List<Game> wildcardGames = playoffs.getWildcardGames(conference1Name);
		
		assertEquals(0, wildcardGames.size());
	}
	
	@Test
	public void generateDivisionalMatchupsSoWildcardWinnersAreUsedToGetMatchups() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_1Name, playoffTeam1);
		playoffs.setDivisionWinner(conference1Name, division1_2Name, playoffTeam2);
		playoffs.addWildcardTeam(conference1Name, playoffTeam3);
		playoffs.addWildcardTeam(conference1Name, playoffTeam4);
		
		when(playoffTeam1.getConferenceSeed()).thenReturn(1);
		when(playoffTeam2.getConferenceSeed()).thenReturn(2);
		when(playoffTeam3.getConferenceSeed()).thenReturn(3);
		when(playoffTeam4.getConferenceSeed()).thenReturn(4);
		
		playoffs.setWildcardWinners(conference1Name, playoffTeam3, playoffTeam4);
		
		List<Game> divisionalGames = playoffs.getDivisionalGames(conference1Name);
		
		assertGameListHasCorrectMatchupsAndHomeTeams(divisionalGames, matchup1_4, matchup2_3);
		
		when(playoffTeam3.getConferenceSeed()).thenReturn(6);
		when(playoffTeam4.getConferenceSeed()).thenReturn(4);
		
		divisionalGames = playoffs.getDivisionalGames(conference1Name);
		
		assertGameListHasCorrectMatchupsAndHomeTeams(divisionalGames, matchup1_3, matchup2_4);
	}
	
	@Test
	public void generateDivisionalMatchupsButNotAllTeamsSetSoEmptyListIsReturned() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_1Name, playoffTeam1);
		playoffs.setDivisionWinner(conference1Name, division1_2Name, playoffTeam2);
		playoffs.addWildcardTeam(conference1Name, playoffTeam3);
		playoffs.addWildcardTeam(conference1Name, playoffTeam4);
		
		when(playoffTeam1.getConferenceSeed()).thenReturn(1);
		when(playoffTeam2.getConferenceSeed()).thenReturn(2);
		when(playoffTeam3.getConferenceSeed()).thenReturn(3);
		when(playoffTeam4.getConferenceSeed()).thenReturn(4);
		
		List<Game> divisionalGames = playoffs.getDivisionalGames(conference1Name);
		
		assertEquals(0, divisionalGames.size());
		
		playoffs.setWildcardWinners(conference1Name, playoffTeam3, playoffTeam4);
		when(playoffTeam1.getConferenceSeed()).thenReturn(5);
		
		divisionalGames = playoffs.getDivisionalGames(conference1Name);
		
		assertEquals(0, divisionalGames.size());
	}
	
	@Test
	public void generateConferenceMatchupsTakesDivisionalWinnersAndGetsTheirMatchup() {
		when(playoffTeam1.getConferenceSeed()).thenReturn(3);
		when(playoffTeam4.getConferenceSeed()).thenReturn(5);
		
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_1Name, playoffTeam1);
		playoffs.setDivisionWinner(conference1Name, division1_2Name, playoffTeam4);
		
		playoffs.setDivisionalRoundWinners(conference1Name, playoffTeam1, playoffTeam4);
		
		Game conferenceGame = playoffs.getConferenceGame(conference1Name);
		
		assertEquals(matchup1_4, conferenceGame.getMatchup());
		assertEquals(leagueTeam1, conferenceGame.getHomeTeam());
	}
	
	@Test
	public void generateConferenceMatchupsButDivisionalRoundWinnersNotSetSoNullIsReturned() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_1Name, playoffTeam1);
		playoffs.setDivisionWinner(conference1Name, division1_2Name, playoffTeam4);
		
		Game conferenceGame = playoffs.getConferenceGame(conference1Name);
		
		assertNull(conferenceGame);
	}
	
	@Test
	public void getSuperBowlMatchupPitsTwoConferenceWinners() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_1Name, playoffTeam1);
		playoffs.setDivisionWinner(conference2Name, division2_1Name, playoffTeam4);
		
		playoffs.setConferenceWinner(conference1Name, playoffTeam1);
		playoffs.setConferenceWinner(conference2Name, playoffTeam4);
		
		Game superBowlGame = playoffs.getSuperBowlGame();
		
		assertEquals(matchup1_4, superBowlGame.getMatchup());
		assertNull(superBowlGame.getHomeTeam());
	}
	
	@Test
	public void getSuperBowlMatchupButConferenceWinnersNotSetSoNullIsReturned() {
		playoffs.initializeNFLPlayoffs();
		playoffs.setDivisionWinner(conference1Name, division1_1Name, playoffTeam1);
		playoffs.setDivisionWinner(conference2Name, division2_1Name, playoffTeam4);
		
		playoffs.setConferenceWinner(conference1Name, playoffTeam1);
		
		Game superBowlGame = playoffs.getSuperBowlGame();
		
		assertNull(superBowlGame);
	}
	
	@Test
	public void populateTeamsByPowerRankingsUsesTeamPowerRankingsToChoosePlayoffTeams() {
		List<Conference> leagueConferences = new ArrayList<Conference>();
		leagueConferences.add(leagueConference1);
		leagueConferences.add(leagueConference2);
		when(nfl.getConferences()).thenReturn(leagueConferences);
		
		when(leagueTeam1_1_1.getPowerRanking()).thenReturn(4);
		when(leagueTeam1_1_2.getPowerRanking()).thenReturn(9);
		when(leagueTeam1_1_3.getPowerRanking()).thenReturn(29);
		
		when(leagueTeam1_2_1.getPowerRanking()).thenReturn(2);
		when(leagueTeam1_2_2.getPowerRanking()).thenReturn(1);
		when(leagueTeam1_2_3.getPowerRanking()).thenReturn(30);
		
		when(leagueTeam2_1_1.getPowerRanking()).thenReturn(7);
		when(leagueTeam2_1_2.getPowerRanking()).thenReturn(8);
		when(leagueTeam2_1_3.getPowerRanking()).thenReturn(6);
		
		when(leagueTeam2_2_1.getPowerRanking()).thenReturn(12);
		when(leagueTeam2_2_2.getPowerRanking()).thenReturn(15);
		when(leagueTeam2_2_3.getPowerRanking()).thenReturn(18);
		
		playoffs.initializeNFLPlayoffs();
		boolean success = playoffs.populateTeamsByPowerRankings();
		assertTrue(success);
		
		assertEquals(leagueTeam1_1_1, playoffs.getDivisionWinner(leagueConference1.getName(), leagueDivision1_1.getName()).getTeam());
		assertEquals(leagueTeam1_2_2, playoffs.getDivisionWinner(leagueConference1.getName(), leagueDivision1_2.getName()).getTeam());
		assertEquals(leagueTeam2_1_3, playoffs.getDivisionWinner(leagueConference2.getName(), leagueDivision2_1.getName()).getTeam());
		assertEquals(leagueTeam2_2_1, playoffs.getDivisionWinner(leagueConference2.getName(), leagueDivision2_2.getName()).getTeam());
		
		assertEquals(leagueTeam1_2_2, playoffs.getTeamByConferenceSeed(leagueConference1.getName(), 1).getTeam());
		assertEquals(leagueTeam1_1_1, playoffs.getTeamByConferenceSeed(leagueConference1.getName(), 2).getTeam());
		assertEquals(leagueTeam1_2_1, playoffs.getTeamByConferenceSeed(leagueConference1.getName(), 5).getTeam());
		assertEquals(leagueTeam1_1_2, playoffs.getTeamByConferenceSeed(leagueConference1.getName(), 6).getTeam());
		
		assertEquals(leagueTeam2_1_3, playoffs.getTeamByConferenceSeed(leagueConference2.getName(), 1).getTeam());
		assertEquals(leagueTeam2_2_1, playoffs.getTeamByConferenceSeed(leagueConference2.getName(), 2).getTeam());
		assertEquals(leagueTeam2_1_1, playoffs.getTeamByConferenceSeed(leagueConference2.getName(), 5).getTeam());
		assertEquals(leagueTeam2_1_2, playoffs.getTeamByConferenceSeed(leagueConference2.getName(), 6).getTeam());
	}
	
	@Test
	public void populateTeamsByPowerRankingsFailsIfNotAllPowerRankingsAreSet() {
		List<Conference> leagueConferences = new ArrayList<Conference>();
		leagueConferences.add(leagueConference1);
		leagueConferences.add(leagueConference2);
		when(nfl.getConferences()).thenReturn(leagueConferences);
		
		when(leagueTeam1_1_1.getPowerRanking()).thenReturn(4);
		when(leagueTeam1_1_2.getPowerRanking()).thenReturn(9);
		when(leagueTeam1_1_3.getPowerRanking()).thenReturn(29);
		
		when(leagueTeam1_2_1.getPowerRanking()).thenReturn(2);
		when(leagueTeam1_2_2.getPowerRanking()).thenReturn(1);
		when(leagueTeam1_2_3.getPowerRanking()).thenReturn(30);
		
		when(leagueTeam2_1_1.getPowerRanking()).thenReturn(7);
		when(leagueTeam2_1_2.getPowerRanking()).thenReturn(8);
		when(leagueTeam2_1_3.getPowerRanking()).thenReturn(6);
		
		when(leagueTeam2_2_1.getPowerRanking()).thenReturn(12);
		when(leagueTeam2_2_2.getPowerRanking()).thenReturn(15);
		when(leagueTeam2_2_3.getPowerRanking()).thenReturn(Team.CLEAR_RANKING);
		
		playoffs.initializeNFLPlayoffs();
		boolean success = playoffs.populateTeamsByPowerRankings();
		assertFalse(success);
		
		assertNull(playoffs.getDivisionWinner(leagueConference1.getName(), leagueDivision1_1.getName()));
		assertNull(playoffs.getDivisionWinner(leagueConference1.getName(), leagueDivision1_2.getName()));
		assertNull(playoffs.getDivisionWinner(leagueConference2.getName(), leagueDivision2_1.getName()));
		assertNull(playoffs.getDivisionWinner(leagueConference2.getName(), leagueDivision2_2.getName()));
		
		assertNull(playoffs.getTeamByConferenceSeed(leagueConference1.getName(), 1));
		assertNull(playoffs.getTeamByConferenceSeed(leagueConference1.getName(), 2));
		assertNull(playoffs.getTeamByConferenceSeed(leagueConference1.getName(), 5));
		assertNull(playoffs.getTeamByConferenceSeed(leagueConference1.getName(), 6));
	}
	
	private void assertGameListHasCorrectMatchupsAndHomeTeams(
			List<Game> divisionalGames, Matchup matchup1, Matchup matchup2) {
		List<Matchup> divisionalMatchups = new ArrayList<Matchup>();
		List<Team> homeTeams = new ArrayList<Team>();
		for (Game divisionalGame : divisionalGames) {
			divisionalMatchups.add(divisionalGame.getMatchup());
			homeTeams.add(divisionalGame.getHomeTeam());
		}
		assertTrue(divisionalMatchups.contains(matchup1));
		assertTrue(divisionalMatchups.contains(matchup2));
		assertTrue(homeTeams.contains(leagueTeam1));
		assertTrue(homeTeams.contains(leagueTeam2));
	}
	
}
