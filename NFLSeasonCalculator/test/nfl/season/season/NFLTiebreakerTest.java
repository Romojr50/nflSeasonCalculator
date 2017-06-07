package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.League;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLTiebreakerTest {

	@Mock
	private NFLSeasonTeam team1_1;
	
	@Mock
	private Team leagueTeam1_1;
	
	private String team1_1Name = "Team 1 - 1";
	
	@Mock
	private NFLSeasonTeam team1_2;
	
	@Mock
	private Team leagueTeam1_2;
	
	private String team1_2Name = "Team 1 - 2";
	
	@Mock
	private NFLSeasonTeam team1_3;
	
	@Mock
	private Team leagueTeam1_3;
	
	private String team1_3Name = "Team 1 - 3";
	
	@Mock
	private NFLSeasonTeam team1_4;
	
	@Mock
	private Team leagueTeam1_4;
	
	private String team1_4Name = "Team 1 - 4";
	
	@Mock
	private NFLSeasonTeam team2_1;
	
	@Mock
	private Team leagueTeam2_1;
	
	private String team2_1Name = "Team 2 - 1";
	
	@Mock
	private NFLSeasonTeam team2_2;
	
	@Mock
	private Team leagueTeam2_2;
	
	private String team2_2Name = "Team 2 - 2";
	
	@Mock
	private NFLSeasonTeam team2_3;
	
	@Mock
	private Team leagueTeam2_3;
	
	private String team2_3Name = "Team 2 - 3";
	
	@Mock
	private NFLSeasonTeam team3_1;
	
	@Mock
	private Team leagueTeam3_1;
	
	private String team3_1Name = "Team 3 - 1";
	
	@Mock
	private NFLSeason season;
	
	@Mock
	private League league;
	
	private NFLTiebreaker tiebreaker;
	
	@Before
	public void setUp() {
		setUpTeamNames();
		setUpTeamRecords();
		setUpTeamWinsLossesTiesAgainst();
		
		when(season.getTeam(team1_1Name)).thenReturn(team1_1);
		when(season.getTeam(team1_2Name)).thenReturn(team1_2);
		when(season.getTeam(team1_3Name)).thenReturn(team1_3);
		when(season.getTeam(team1_4Name)).thenReturn(team1_4);
		when(season.getTeam(team2_1Name)).thenReturn(team2_1);
		when(season.getTeam(team2_2Name)).thenReturn(team2_2);
		when(season.getTeam(team2_3Name)).thenReturn(team2_3);
		when(season.getTeam(team3_1Name)).thenReturn(team3_1);
		
		when(season.getLeague()).thenReturn(league);
		when(league.areInSameDivision(leagueTeam1_1, leagueTeam1_2)).thenReturn(true);
		when(league.areInSameDivision(leagueTeam1_1, leagueTeam1_3)).thenReturn(true);
		when(league.areInSameDivision(leagueTeam1_1, leagueTeam1_4)).thenReturn(true);
		when(league.areInSameDivision(leagueTeam1_2, leagueTeam1_3)).thenReturn(true);
		when(league.areInSameDivision(leagueTeam1_2, leagueTeam1_4)).thenReturn(true);
		when(league.areInSameDivision(leagueTeam1_3, leagueTeam1_4)).thenReturn(true);
		
		tiebreaker = new NFLTiebreaker(season);
	}

	private void setUpTeamNames() {
		when(team1_1.getTeam()).thenReturn(leagueTeam1_1);
		when(leagueTeam1_1.getName()).thenReturn(team1_1Name);
		when(team1_2.getTeam()).thenReturn(leagueTeam1_2);
		when(leagueTeam1_2.getName()).thenReturn(team1_2Name);
		when(team1_3.getTeam()).thenReturn(leagueTeam1_3);
		when(leagueTeam1_3.getName()).thenReturn(team1_3Name);
		when(team1_4.getTeam()).thenReturn(leagueTeam1_4);
		when(leagueTeam1_4.getName()).thenReturn(team1_4Name);
		when(team2_1.getTeam()).thenReturn(leagueTeam2_1);
		when(leagueTeam2_1.getName()).thenReturn(team2_1Name);
		when(team2_2.getTeam()).thenReturn(leagueTeam2_2);
		when(leagueTeam2_2.getName()).thenReturn(team2_2Name);
		when(team2_3.getTeam()).thenReturn(leagueTeam2_3);
		when(leagueTeam2_3.getName()).thenReturn(team2_3Name);
		when(team3_1.getTeam()).thenReturn(leagueTeam3_1);
		when(leagueTeam3_1.getName()).thenReturn(team3_1Name);
	}

	private void setUpTeamRecords() {
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_1.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_1.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_1.getNumberOfConferenceLosses()).thenReturn(3);
		
		when(team1_2.getNumberOfWins()).thenReturn(9);
		when(team1_2.getNumberOfLosses()).thenReturn(7);
		when(team1_2.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_2.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_2.getNumberOfConferenceLosses()).thenReturn(3);
		
		when(team1_3.getNumberOfWins()).thenReturn(9);
		when(team1_3.getNumberOfLosses()).thenReturn(7);
		when(team1_3.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_3.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_3.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_3.getNumberOfConferenceLosses()).thenReturn(3);
		
		when(team1_4.getNumberOfWins()).thenReturn(9);
		when(team1_4.getNumberOfLosses()).thenReturn(7);
		when(team1_4.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_4.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_4.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_4.getNumberOfConferenceLosses()).thenReturn(3);
		
		when(team2_1.getNumberOfWins()).thenReturn(9);
		when(team2_1.getNumberOfLosses()).thenReturn(7);
		
		when(team2_2.getNumberOfWins()).thenReturn(9);
		when(team2_2.getNumberOfLosses()).thenReturn(7);
		
		when(team2_3.getNumberOfWins()).thenReturn(9);
		when(team2_3.getNumberOfLosses()).thenReturn(7);
		
		when(team3_1.getNumberOfWins()).thenReturn(9);
		when(team3_1.getNumberOfLosses()).thenReturn(7);
	}

	private void setUpTeamWinsLossesTiesAgainst() {
		List<String> team1_1Wins = new ArrayList<String>();
		List<String> team1_1Losses = new ArrayList<String>();
		List<String> team1_1Ties = new ArrayList<String>();
		
		team1_1Wins.add(leagueTeam1_2.getName());
		team1_1Wins.add(leagueTeam2_1.getName());
		team1_1Losses.add(leagueTeam1_2.getName());
		team1_1Losses.add(leagueTeam1_3.getName());
		
		when(team1_1.getWinsAgainst()).thenReturn(team1_1Wins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1Losses);
		when(team1_1.getTiesAgainst()).thenReturn(team1_1Ties);
		
		List<String> team1_2Wins = new ArrayList<String>();
		List<String> team1_2Losses = new ArrayList<String>();
		List<String> team1_2Ties = new ArrayList<String>();
		
		team1_2Wins.add(leagueTeam1_1.getName());
		team1_2Wins.add(leagueTeam2_2.getName());
		team1_2Losses.add(leagueTeam1_1.getName());
		team1_2Losses.add(leagueTeam2_3.getName());
		
		when(team1_2.getWinsAgainst()).thenReturn(team1_2Wins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_2Losses);
		when(team1_2.getTiesAgainst()).thenReturn(team1_2Ties);
		
		List<String> team1_3Wins = new ArrayList<String>();
		List<String> team1_3Losses = new ArrayList<String>();
		List<String> team1_3Ties = new ArrayList<String>();
		
		team1_3Wins.add(leagueTeam1_2.getName());
		team1_3Wins.add(leagueTeam2_1.getName());
		team1_3Losses.add(leagueTeam1_2.getName());
		team1_3Losses.add(leagueTeam1_4.getName());
		
		when(team1_3.getWinsAgainst()).thenReturn(team1_3Wins);
		when(team1_3.getLossesAgainst()).thenReturn(team1_3Losses);
		when(team1_3.getTiesAgainst()).thenReturn(team1_3Ties);
		
		List<String> team1_4Wins = new ArrayList<String>();
		List<String> team1_4Losses = new ArrayList<String>();
		List<String> team1_4Ties = new ArrayList<String>();
		
		team1_4Wins.add(leagueTeam1_2.getName());
		team1_4Wins.add(leagueTeam2_1.getName());
		team1_4Losses.add(leagueTeam1_2.getName());
		team1_4Losses.add(leagueTeam1_3.getName());
		
		when(team1_4.getWinsAgainst()).thenReturn(team1_4Wins);
		when(team1_4.getLossesAgainst()).thenReturn(team1_4Losses);
		when(team1_4.getTiesAgainst()).thenReturn(team1_4Ties);
	}
	
	@Test
	public void tieBreakAlwaysResolvesToSomeTeam() {
		assertNotNull(tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoUnTiedTeamsGivesTeamWithBetterWinPercent() {
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(8);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(6);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(6);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfWins()).thenReturn(8);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_1.getNumberOfTies()).thenReturn(1);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(8);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoTeamsBrokenByHeadToHead() {
		List<String> team1_1TempWins = new ArrayList<String>();
		team1_1TempWins.add(leagueTeam1_2.getName());
		team1_1TempWins.add(leagueTeam1_2.getName());
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		
		List<String> team1_2TempLosses = new ArrayList<String>();
		team1_2TempLosses.add(leagueTeam1_1.getName());
		team1_2TempLosses.add(leagueTeam1_1.getName());
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoTeamsBrokenByDivisionWinPercent() {
		when(team1_1.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_1.getNumberOfDivisionTies()).thenReturn(0);
		
		when(team1_2.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(4);
		when(team1_2.getNumberOfDivisionTies()).thenReturn(0);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(4);
		when(team1_1.getNumberOfDivisionTies()).thenReturn(0);
		
		when(team1_2.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_2.getNumberOfDivisionTies()).thenReturn(0);
		
		assertEquals(team1_2, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(2);
		when(team1_1.getNumberOfDivisionTies()).thenReturn(1);
		
		when(team1_2.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_2.getNumberOfDivisionTies()).thenReturn(0);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoTiesBrokenByCommonGamesRecord() {
		List<String> team1_1TempWins = new ArrayList<String>();
		List<String> team1_1TempLosses = new ArrayList<String>();
		List<String> team1_1TempTies = new ArrayList<String>();
		
		team1_1TempWins.add(team1_3Name);
		team1_1TempWins.add(team1_3Name);
		team1_1TempWins.add(team2_1Name);
		team1_1TempLosses.add(team2_2Name);
		team1_1TempLosses.add(team2_3Name);
		
		List<String> team1_2TempWins = new ArrayList<String>();
		List<String> team1_2TempLosses = new ArrayList<String>();
		List<String> team1_2TempTies = new ArrayList<String>();
		
		team1_2TempWins.add(team2_2Name);
		team1_2TempWins.add(team2_2Name);
		team1_2TempWins.add(team3_1Name);
		team1_2TempLosses.add(team1_3Name);
		team1_2TempLosses.add(team2_1Name);
		
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1TempLosses);
		when(team1_1.getTiesAgainst()).thenReturn(team1_1TempTies);
		
		when(team1_2.getWinsAgainst()).thenReturn(team1_2TempWins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		when(team1_2.getTiesAgainst()).thenReturn(team1_2TempTies);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		team1_1TempLosses.add(team3_1Name);
		team1_2TempTies.add(team2_3Name);
		
		assertEquals(team1_2, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoTeamsBrokenByConferenceRecord() {
		when(team1_1.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_1.getNumberOfConferenceLosses()).thenReturn(3);
		when(team1_1.getNumberOfConferenceTies()).thenReturn(0);
		
		when(team1_2.getNumberOfConferenceWins()).thenReturn(4);
		when(team1_2.getNumberOfConferenceLosses()).thenReturn(4);
		when(team1_2.getNumberOfConferenceTies()).thenReturn(0);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfConferenceWins()).thenReturn(4);
		when(team1_1.getNumberOfConferenceLosses()).thenReturn(4);
		when(team1_1.getNumberOfConferenceTies()).thenReturn(0);
		
		when(team1_2.getNumberOfConferenceWins()).thenReturn(4);
		when(team1_2.getNumberOfConferenceLosses()).thenReturn(3);
		when(team1_2.getNumberOfConferenceTies()).thenReturn(0);
		
		assertEquals(team1_2, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_1.getNumberOfConferenceLosses()).thenReturn(2);
		when(team1_1.getNumberOfConferenceTies()).thenReturn(1);
		
		when(team1_2.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_2.getNumberOfConferenceLosses()).thenReturn(3);
		when(team1_2.getNumberOfConferenceTies()).thenReturn(0);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoTeamsBrokenByStrengthOfVictory() {
		when(team2_2.getNumberOfWins()).thenReturn(10);
		when(team2_2.getNumberOfLosses()).thenReturn(6);
		
		assertEquals(team1_2, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team2_1.getNumberOfWins()).thenReturn(10);
		when(team2_1.getNumberOfLosses()).thenReturn(5);
		when(team2_1.getNumberOfTies()).thenReturn(1);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoTeamsBrokenByStrengthOfSchedule() {
		testStrengthOfScheduleTieBreak();
	}
	
	@Test
	public void conferenceTieBreakBetweenTwoTeamsBrokenByConferenceTieBreaker() {
		when(league.areInSameDivision(leagueTeam1_1, leagueTeam1_2)).thenReturn(false);
		
		when(team1_1.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(3);
		
		when(team1_2.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(4);
		
		List<String> team1_1TempWins = new ArrayList<String>();		
		team1_1TempWins.add(team1_3Name);
		team1_1TempWins.add(team2_1Name);
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_1.getLossesAgainst()).thenReturn(new ArrayList<String>());
		
		List<String> team1_2TempLosses = new ArrayList<String>();
		team1_2TempLosses.add(team1_3Name);
		team1_2TempLosses.add(team2_1Name);
		when(team1_2.getWinsAgainst()).thenReturn(new ArrayList<String>());
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		
		when(team1_1.getNumberOfConferenceWins()).thenReturn(4);
		when(team1_1.getNumberOfConferenceLosses()).thenReturn(4);
		
		when(team1_2.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_2.getNumberOfConferenceLosses()).thenReturn(3);
		
		assertEquals(team1_2, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void conferenceTieBreakBetweenTwoTeamsBrokenByCommonGames() {
		when(league.areInSameDivision(leagueTeam1_1, leagueTeam1_2)).thenReturn(false);
		
		List<String> team1_1TempWins = new ArrayList<String>();
		List<String> team1_1TempLosses = new ArrayList<String>();
		
		team1_1TempWins.add(team1_3Name);
		team1_1TempWins.add(team2_1Name);
		team1_1TempLosses.add(team2_2Name);
		team1_1TempLosses.add(team2_2Name);
		
		List<String> team1_2TempWins = new ArrayList<String>();
		List<String> team1_2TempLosses = new ArrayList<String>();
		
		team1_2TempWins.add(team2_2Name);
		team1_2TempLosses.add(team1_3Name);
		team1_2TempLosses.add(team1_3Name);
		team1_2TempLosses.add(team2_1Name);
		
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1TempLosses);
		
		when(team1_2.getWinsAgainst()).thenReturn(team1_2TempWins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void conferenceTieBreakBetweenTwoTeamsBrokenByStrengthOfVictory() {
		when(league.areInSameDivision(leagueTeam1_1, leagueTeam1_2)).thenReturn(false);
		
		List<String> team1_1TempWins = new ArrayList<String>();
		List<String> team1_1TempLosses = new ArrayList<String>();
		
		team1_1TempWins.add(team1_3Name);
		team1_1TempWins.add(team2_1Name);
		team1_1TempLosses.add(team2_2Name);
		
		List<String> team1_2TempWins = new ArrayList<String>();
		List<String> team1_2TempLosses = new ArrayList<String>();
		
		team1_2TempWins.add(team2_3Name);
		team1_2TempWins.add(team2_3Name);
		team1_2TempWins.add(team2_3Name);
		team1_2TempLosses.add(team1_3Name);
		team1_2TempLosses.add(team1_3Name);
		team1_2TempLosses.add(team2_1Name);
		
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1TempLosses);
		
		when(team1_2.getWinsAgainst()).thenReturn(team1_2TempWins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		
		when(team2_2.getNumberOfWins()).thenReturn(10);
		when(team2_2.getNumberOfLosses()).thenReturn(6);
		
		assertEquals(team1_2, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void conferenceTieBreakBetweenTwoTeamsBrokenByStrengthOfScheduleTieBreak() {
		when(league.areInSameDivision(leagueTeam1_1, leagueTeam1_2)).thenReturn(false);
		testStrengthOfScheduleTieBreak();
	}
	
	@Test
	public void tieBreakOfManyTeamsNeverReturnsNull() {
		assertNotNull(tiebreaker.tiebreakManyTeams(team1_1, team1_2, team1_3, team1_4));
	}
	
	@Test
	public void divisionTieBreakBetweenManyUnTiedTeamsGivesTeamWithBetterWinPercentOrTwoTeamTiebreaker() {
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(8);
		when(team1_3.getNumberOfWins()).thenReturn(8);
		when(team1_3.getNumberOfLosses()).thenReturn(8);
		when(team1_4.getNumberOfWins()).thenReturn(8);
		when(team1_4.getNumberOfLosses()).thenReturn(8);
		
		assertEquals(team1_1, tiebreaker.tiebreakManyTeams(team1_1, team1_2, 
				team1_3, team1_4));

		when(team1_2.getNumberOfWins()).thenReturn(9);
		when(team1_2.getNumberOfLosses()).thenReturn(7);
		setUpTeam1_2WithBetterConferenceRecord();
		
		assertEquals(team1_2, tiebreaker.tiebreakManyTeams(team1_1, team1_2, 
				team1_3, team1_4));
	}
	
	@Test
	public void divisionTieBreakBetweenManyTeamsBrokenByHeadToHead() {
		setUpManyHeadToHeadWinsLosses();
		
		assertEquals(team1_1, tiebreaker.tiebreakManyTeams(
				team1_1, team1_2, team1_3, team1_4));
		
		List<String> currentTeam1_1Wins = team1_1.getWinsAgainst();
		List<String> currentTeam1_1Losses = team1_1.getLossesAgainst();
		when(team1_2.getWinsAgainst()).thenReturn(currentTeam1_1Wins);
		when(team1_2.getLossesAgainst()).thenReturn(currentTeam1_1Losses);
		setUpTeam1_2WithBetterConferenceRecord();
		
		assertEquals(team1_2, tiebreaker.tiebreakManyTeams(
				team1_1, team1_2, team1_3, team1_4));
	}
	
	@Test
	public void divisionTieBreakBetweenManyTeamsBrokenByDivisionWinPercent() {
		when(team1_1.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_2.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(4);
		when(team1_3.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_3.getNumberOfDivisionLosses()).thenReturn(4);
		when(team1_4.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_4.getNumberOfDivisionLosses()).thenReturn(4);
		
		assertEquals(team1_1, tiebreaker.tiebreakManyTeams(
				team1_1, team1_2, team1_3, team1_4));
		
		when(team1_2.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(3);
		setUpTeam1_2WithBetterConferenceRecord();
		
		assertEquals(team1_2, tiebreaker.tiebreakManyTeams(
				team1_1, team1_2, team1_3, team1_4));
	}
	
	@Test
	public void divisionTieBreakBetweenManyTeamsBrokenByCommonGameWinPercent() {
		List<String> team1_1TempWins = new ArrayList<String>();
		List<String> team1_1TempLosses = new ArrayList<String>();
		team1_1TempWins.add(team2_3Name);
		team1_1TempWins.add(team2_3Name);
		team1_1TempWins.add(team2_1Name);
		team1_1TempLosses.add(team2_2Name);
		team1_1TempLosses.add(team2_3Name);
		
		List<String> team1_2TempWins = new ArrayList<String>();
		List<String> team1_2TempLosses = new ArrayList<String>();
		team1_2TempWins.add(team2_2Name);
		team1_2TempWins.add(team2_2Name);
		team1_2TempWins.add(team3_1Name);
		team1_2TempLosses.add(team2_3Name);
		team1_2TempLosses.add(team2_1Name);
		
		List<String> team1_3TempWins = new ArrayList<String>();
		List<String> team1_3TempLosses = new ArrayList<String>();
		team1_3TempWins.add(team2_2Name);
		team1_3TempWins.add(team2_2Name);
		team1_3TempWins.add(team3_1Name);
		team1_3TempLosses.add(team2_3Name);
		team1_3TempLosses.add(team2_1Name);
		
		List<String> team1_4TempWins = new ArrayList<String>();
		List<String> team1_4TempLosses = new ArrayList<String>();
		team1_4TempWins.add(team2_2Name);
		team1_4TempWins.add(team2_2Name);
		team1_4TempWins.add(team3_1Name);
		team1_4TempLosses.add(team2_3Name);
		team1_4TempLosses.add(team2_1Name);
		
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1TempLosses);
		when(team1_2.getWinsAgainst()).thenReturn(team1_2TempWins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		when(team1_3.getWinsAgainst()).thenReturn(team1_3TempWins);
		when(team1_3.getLossesAgainst()).thenReturn(team1_3TempLosses);
		when(team1_4.getWinsAgainst()).thenReturn(team1_4TempWins);
		when(team1_4.getLossesAgainst()).thenReturn(team1_4TempLosses);
		
		assertEquals(team1_1, tiebreaker.tiebreakManyTeams(team1_1, team1_2, team1_3, team1_4));
		
		when(team1_2.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_1TempLosses);
		setUpTeam1_2WithBetterConferenceRecord();
		
		assertEquals(team1_2, tiebreaker.tiebreakManyTeams(team1_1, team1_2, team1_3, team1_4));
	}
	
	@Test
	public void divisionTieBreakBetweenManyTeamsBrokenByConferenceTieBreak() {
		setUpTeam1_2WithBetterConferenceRecord();
		
		assertEquals(team1_2, tiebreaker.tiebreakManyTeams(team1_1, team1_2, team1_3, team1_4));
	}
	
	@Test
	public void divisionTieBreakBetweenManyTeamsBrokenByStrengthOfVictory() {
		when(team2_3.getNumberOfWins()).thenReturn(12);
		when(team2_3.getNumberOfLosses()).thenReturn(4);
		
		List<String> team1_1TempWins = new ArrayList<String>();
		List<String> team1_1TempLosses = new ArrayList<String>();
		team1_1TempWins.add(team2_2Name);
		team1_1TempWins.add(team3_1Name);
		team1_1TempLosses.add(team2_1Name);
		
		List<String> team1_2TempWins = new ArrayList<String>();
		List<String> team1_2TempLosses = new ArrayList<String>();
		team1_2TempWins.add(team2_2Name);
		team1_2TempWins.add(team3_1Name);
		team1_2TempLosses.add(team2_1Name);
		
		List<String> team1_3TempWins = new ArrayList<String>();
		List<String> team1_3TempLosses = new ArrayList<String>();
		team1_3TempWins.add(team2_2Name);
		team1_3TempWins.add(team2_3Name);
		team1_3TempLosses.add(team2_1Name);
		
		List<String> team1_4TempWins = new ArrayList<String>();
		List<String> team1_4TempLosses = new ArrayList<String>();
		team1_4TempWins.add(team2_2Name);
		team1_4TempWins.add(team3_1Name);
		team1_4TempLosses.add(team2_1Name);
		
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1TempLosses);
		when(team1_2.getWinsAgainst()).thenReturn(team1_2TempWins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		when(team1_3.getWinsAgainst()).thenReturn(team1_3TempWins);
		when(team1_3.getLossesAgainst()).thenReturn(team1_3TempLosses);
		when(team1_4.getWinsAgainst()).thenReturn(team1_4TempWins);
		when(team1_4.getLossesAgainst()).thenReturn(team1_4TempLosses);
		
		assertEquals(team1_3, tiebreaker.tiebreakManyTeams(team1_1, team1_2, team1_3, team1_4));
	}
	
	@Test
	public void divisionTieBreakBetweenManyTeamsBrokenByStrengthOfSchedule() {
		when(team2_3.getNumberOfWins()).thenReturn(12);
		when(team2_3.getNumberOfLosses()).thenReturn(4);
		
		List<String> team1_1TempWins = new ArrayList<String>();
		List<String> team1_1TempLosses = new ArrayList<String>();
		team1_1TempWins.add(team2_2Name);
		team1_1TempWins.add(team3_1Name);
		team1_1TempLosses.add(team2_1Name);
		
		List<String> team1_2TempWins = new ArrayList<String>();
		List<String> team1_2TempLosses = new ArrayList<String>();
		team1_2TempWins.add(team2_2Name);
		team1_2TempWins.add(team3_1Name);
		team1_2TempLosses.add(team2_1Name);
		
		List<String> team1_3TempWins = new ArrayList<String>();
		List<String> team1_3TempLosses = new ArrayList<String>();
		team1_3TempWins.add(team2_2Name);
		team1_3TempWins.add(team3_1Name);
		team1_3TempLosses.add(team2_1Name);
		
		List<String> team1_4TempWins = new ArrayList<String>();
		List<String> team1_4TempLosses = new ArrayList<String>();
		team1_4TempWins.add(team2_2Name);
		team1_4TempWins.add(team3_1Name);
		team1_4TempLosses.add(team2_3Name);
		
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1TempLosses);
		when(team1_2.getWinsAgainst()).thenReturn(team1_2TempWins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		when(team1_3.getWinsAgainst()).thenReturn(team1_3TempWins);
		when(team1_3.getLossesAgainst()).thenReturn(team1_3TempLosses);
		when(team1_4.getWinsAgainst()).thenReturn(team1_4TempWins);
		when(team1_4.getLossesAgainst()).thenReturn(team1_4TempLosses);
		
		assertEquals(team1_4, tiebreaker.tiebreakManyTeams(team1_1, team1_2, team1_3, team1_4));
	}

	private void testStrengthOfScheduleTieBreak() {
		when(team1_3.getNumberOfWins()).thenReturn(8);
		when(team1_3.getNumberOfLosses()).thenReturn(8);
		
		assertEquals(team1_2, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_3.getNumberOfWins()).thenReturn(9);
		when(team1_3.getNumberOfLosses()).thenReturn(6);
		when(team1_3.getNumberOfTies()).thenReturn(1);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
		
		List<String> team1_1Losses = team1_1.getLossesAgainst();
		team1_1Losses.remove(team1_3Name);
		List<String> team1_1Ties = team1_1.getTiesAgainst();
		team1_1Ties.add(team1_3Name);
		
		assertEquals(team1_1, tiebreaker.tiebreakTeams(team1_1, team1_2));
	}	
	
	private void setUpTeam1_2WithBetterConferenceRecord() {
		when(team1_2.getNumberOfConferenceWins()).thenReturn(6);
		when(team1_2.getNumberOfConferenceLosses()).thenReturn(2);
		
		when(team1_1.getNumberOfConferenceWins()).thenReturn(5);
		when(team1_1.getNumberOfConferenceLosses()).thenReturn(3);
	}
	
	private void setUpManyHeadToHeadWinsLosses() {
		List<String> team1_1TempWins = new ArrayList<String>();
		List<String> team1_1TempLosses = new ArrayList<String>();
		team1_1TempWins.add(leagueTeam1_3.getName());
		team1_1TempWins.add(leagueTeam1_4.getName());
		team1_1TempWins.add(leagueTeam1_3.getName());
		team1_1TempWins.add(leagueTeam1_4.getName());
		team1_1TempLosses.add(leagueTeam2_1.getName());
		team1_1TempLosses.add(leagueTeam2_1.getName());
		team1_1TempLosses.add(leagueTeam2_1.getName());
		team1_1TempLosses.add(leagueTeam2_1.getName());
		team1_1TempLosses.add(leagueTeam2_1.getName());
		when(team1_1.getWinsAgainst()).thenReturn(team1_1TempWins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1TempLosses);
		
		List<String> team1_2TempLosses = new ArrayList<String>();
		team1_2TempLosses.add(leagueTeam1_1.getName());
		team1_2TempLosses.add(leagueTeam1_1.getName());
		team1_2TempLosses.add(leagueTeam1_1.getName());
		team1_2TempLosses.add(leagueTeam1_1.getName());
		when(team1_2.getLossesAgainst()).thenReturn(team1_2TempLosses);
		
		List<String> team1_3TempLosses = new ArrayList<String>();
		team1_3TempLosses.add(leagueTeam1_1.getName());
		team1_3TempLosses.add(leagueTeam1_1.getName());
		team1_3TempLosses.add(leagueTeam1_1.getName());
		team1_3TempLosses.add(leagueTeam1_1.getName());
		when(team1_3.getLossesAgainst()).thenReturn(team1_3TempLosses);
		
		List<String> team1_4TempWins = new ArrayList<String>();
		List<String> team1_4TempLosses = new ArrayList<String>();
		team1_4TempWins.add(leagueTeam2_1.getName());
		team1_4TempWins.add(leagueTeam2_1.getName());
		team1_4TempWins.add(leagueTeam2_1.getName());
		team1_4TempWins.add(leagueTeam2_1.getName());
		team1_4TempWins.add(leagueTeam2_1.getName());
		team1_4TempLosses.add(leagueTeam1_1.getName());
		team1_4TempLosses.add(leagueTeam1_1.getName());
		team1_4TempLosses.add(leagueTeam1_1.getName());
		team1_4TempLosses.add(leagueTeam1_1.getName());
		when(team1_4.getWinsAgainst()).thenReturn(team1_4TempWins);
		when(team1_4.getLossesAgainst()).thenReturn(team1_4TempLosses);
	}
}
