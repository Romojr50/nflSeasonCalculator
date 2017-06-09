package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonConferenceTest {
	
	@Mock
	private NFLSeasonDivision division1;
	
	List<NFLSeasonTeam> division1Teams;
	
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
	private NFLSeasonDivision division2;
	
	List<NFLSeasonTeam> division2Teams;
	
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
	private NFLSeasonDivision division3;
	
	List<NFLSeasonTeam> division3Teams;
	
	@Mock
	private NFLSeasonTeam team3_1;
	
	@Mock
	private Team leagueTeam3_1;
	
	private String team3_1Name = "Team 3 - 1";
	
	@Mock
	private NFLSeasonTeam team3_2;
	
	@Mock
	private Team leagueTeam3_2;
	
	private String team3_2Name = "Team 3 - 2";
	
	@Mock
	private NFLSeasonTeam team3_3;
	
	@Mock
	private Team leagueTeam3_3;
	
	private String team3_3Name = "Team 3 - 3";
	
	@Mock
	private Conference leagueConference;
	
	private String conferenceName = "Conf 1";
	
	@Mock
	private NFLTiebreaker tiebreaker;
	
	private NFLSeasonConference conference;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		when(team1_1.getTeam()).thenReturn(leagueTeam1_1);
		when(leagueTeam1_1.getName()).thenReturn(team1_1Name);
		when(team1_2.getTeam()).thenReturn(leagueTeam1_2);
		when(leagueTeam1_2.getName()).thenReturn(team1_2Name);
		when(team1_3.getTeam()).thenReturn(leagueTeam1_3);
		when(leagueTeam1_3.getName()).thenReturn(team1_3Name);
		division1Teams = new ArrayList<NFLSeasonTeam>();
		division1Teams.add(team1_1);
		division1Teams.add(team1_2);
		division1Teams.add(team1_3);
		when(division1.getTeams()).thenReturn(division1Teams);
		when(division1.getDivisionWinner()).thenReturn(team1_1);
		
		when(team2_1.getTeam()).thenReturn(leagueTeam2_1);
		when(leagueTeam2_1.getName()).thenReturn(team2_1Name);
		when(team2_2.getTeam()).thenReturn(leagueTeam2_2);
		when(leagueTeam2_2.getName()).thenReturn(team2_2Name);
		when(team2_3.getTeam()).thenReturn(leagueTeam2_3);
		when(leagueTeam2_3.getName()).thenReturn(team2_3Name);
		division2Teams = new ArrayList<NFLSeasonTeam>();
		division2Teams.add(team2_1);
		division2Teams.add(team2_2);
		division2Teams.add(team2_3);
		when(division2.getTeams()).thenReturn(division2Teams);
		when(division2.getDivisionWinner()).thenReturn(team2_1);
		
		when(team3_1.getTeam()).thenReturn(leagueTeam3_1);
		when(leagueTeam3_1.getName()).thenReturn(team3_1Name);
		when(team3_2.getTeam()).thenReturn(leagueTeam3_2);
		when(leagueTeam3_2.getName()).thenReturn(team3_2Name);
		when(team3_3.getTeam()).thenReturn(leagueTeam3_3);
		when(leagueTeam3_3.getName()).thenReturn(team3_3Name);
		division3Teams = new ArrayList<NFLSeasonTeam>();
		division3Teams.add(team3_1);
		division3Teams.add(team3_2);
		division3Teams.add(team3_3);
		when(division3.getTeams()).thenReturn(division3Teams);
		when(division3.getDivisionWinner()).thenReturn(team3_1);
		
		when(division1.getDivisionStandingsString(leagueConference.getName(), 
				tiebreaker)).thenReturn("Div 1 Standings\n");
		when(division2.getDivisionStandingsString(leagueConference.getName(), 
				tiebreaker)).thenReturn("Div 2 Standings\n");
		when(division3.getDivisionStandingsString(leagueConference.getName(), 
				tiebreaker)).thenReturn("Div 3 Standings\n");
		
		when(leagueConference.getName()).thenReturn(conferenceName);
		conference = new NFLSeasonConference(leagueConference);
		conference.addDivision(division1);
		conference.addDivision(division2);
		conference.addDivision(division3);
		
		when(tiebreaker.tiebreakManyTeams(any(List.class))).thenReturn(
				team1_1, team3_1, team3_3, team2_2);
	}
	
	@Test
	public void conferenceStandingsCombineDivisionStandingsAndPrintWildcards() {
		String conferenceStandings = conference.getConferenceStandingsString(tiebreaker);
		
		StringBuilder standingsBuilder = new StringBuilder();
		
		List<NFLSeasonDivision> divisions = conference.getDivisions();
		for (NFLSeasonDivision division : divisions) {
			standingsBuilder.append(division.getDivisionStandingsString(
					leagueConference.getName(), tiebreaker));
		}
		
		standingsBuilder.append("\n");
		standingsBuilder.append(conferenceName + " Seeds:\n");
		
		List<NFLSeasonTeam> seedsInOrder = conference.getSeedsInOrder();
		assertEquals(team1_1, seedsInOrder.get(0));
		assertEquals(team3_1, seedsInOrder.get(1));
		assertEquals(team2_1, seedsInOrder.get(2));
		assertEquals(team3_3, seedsInOrder.get(3));
		assertEquals(team2_2, seedsInOrder.get(4));
		
		for (int i = 1; i <= seedsInOrder.size(); i++) {
			NFLSeasonTeam seed = seedsInOrder.get(i - 1);
			Team leagueSeed = seed.getTeam();
			String seedName = leagueSeed.getName();
			standingsBuilder.append(i + ". " + seedName + "\n");
		}
		
		assertEquals(standingsBuilder.toString(), conferenceStandings);
	}
	
}
