package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
	
	private String team1_3Name = "Team 1 - 3";
	
	private String team2_1Name = "Team 2 - 1";
	
	private String team2_2Name = "Team 2 - 2";
	
	private String team2_3Name = "Team 2 - 3";
	
	private String team3_1Name = "Team 3 - 1";
	
	@Before
	public void setUp() {
		when(team1_1.getTeam()).thenReturn(leagueTeam1_1);
		when(leagueTeam1_1.getName()).thenReturn("Team 1 - 1");
		when(team1_2.getTeam()).thenReturn(leagueTeam1_2);
		when(leagueTeam1_2.getName()).thenReturn("Team 1 - 2");
		
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_1.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(3);
		
		when(team1_2.getNumberOfWins()).thenReturn(9);
		when(team1_2.getNumberOfLosses()).thenReturn(7);
		when(team1_2.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(3);
		
		setUpTeamWinsLossesTiesAgainst();
	}

	private void setUpTeamWinsLossesTiesAgainst() {
		List<String> team1_1Wins = new ArrayList<String>();
		List<String> team1_1Losses = new ArrayList<String>();
		List<String> team1_1Ties = new ArrayList<String>();
		
		team1_1Wins.add(leagueTeam1_2.getName());
		team1_1Losses.add(leagueTeam1_2.getName());
		
		when(team1_1.getWinsAgainst()).thenReturn(team1_1Wins);
		when(team1_1.getLossesAgainst()).thenReturn(team1_1Losses);
		when(team1_1.getTiesAgainst()).thenReturn(team1_1Ties);
		
		List<String> team1_2Wins = new ArrayList<String>();
		List<String> team1_2Losses = new ArrayList<String>();
		List<String> team1_2Ties = new ArrayList<String>();
		
		team1_2Wins.add(leagueTeam1_1.getName());
		team1_2Losses.add(leagueTeam1_1.getName());
		
		when(team1_2.getWinsAgainst()).thenReturn(team1_2Wins);
		when(team1_2.getLossesAgainst()).thenReturn(team1_2Losses);
		when(team1_2.getTiesAgainst()).thenReturn(team1_2Ties);
	}
	
	@Test
	public void divisionTieBreakBetweenTwoUnTiedTeamsGivesTeamWithBetterWinPercent() {
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(8);
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfWins()).thenReturn(9);
		when(team1_1.getNumberOfLosses()).thenReturn(6);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(6);
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfWins()).thenReturn(8);
		when(team1_1.getNumberOfLosses()).thenReturn(7);
		when(team1_1.getNumberOfTies()).thenReturn(1);
		when(team1_2.getNumberOfWins()).thenReturn(8);
		when(team1_2.getNumberOfLosses()).thenReturn(8);
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
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
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoTeamsBrokenByDivisionWinPercent() {
		when(team1_1.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_1.getNumberOfDivisionTies()).thenReturn(0);
		
		when(team1_2.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(4);
		when(team1_2.getNumberOfDivisionTies()).thenReturn(0);
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(4);
		when(team1_1.getNumberOfDivisionTies()).thenReturn(0);
		
		when(team1_2.getNumberOfDivisionWins()).thenReturn(4);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_2.getNumberOfDivisionTies()).thenReturn(0);
		
		assertEquals(team1_2, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
		
		when(team1_1.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_1.getNumberOfDivisionLosses()).thenReturn(2);
		when(team1_1.getNumberOfDivisionTies()).thenReturn(1);
		
		when(team1_2.getNumberOfDivisionWins()).thenReturn(5);
		when(team1_2.getNumberOfDivisionLosses()).thenReturn(3);
		when(team1_2.getNumberOfDivisionTies()).thenReturn(0);
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
	@Test
	public void divisionTieBreakBetweenTwoTiesBrokenByCommonGamesRecord() {
		List<String> team1_1TempWins = new ArrayList<String>();
		List<String> team1_1TempLosses = new ArrayList<String>();
		List<String> team1_1TempTies = new ArrayList<String>();
		
		team1_1TempWins.add(team1_3Name);
		team1_1TempWins.add(team2_1Name);
		team1_1TempLosses.add(team2_2Name);
		team1_1TempLosses.add(team2_3Name);
		
		List<String> team1_2TempWins = new ArrayList<String>();
		List<String> team1_2TempLosses = new ArrayList<String>();
		List<String> team1_2TempTies = new ArrayList<String>();
		
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
		
		assertEquals(team1_1, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
		
		team1_1TempLosses.add(team3_1Name);
		team1_2TempTies.add(team2_3Name);
		
		assertEquals(team1_2, NFLTiebreaker.tiebreakTeams(team1_1, team1_2));
	}
	
}
