package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import nfl.season.league.Division;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonDivisionTest {

	@Mock
	private NFLSeasonTeam team1;
	
	@Mock
	private Team leagueTeam1;
	
	private String team1Name = "Team 1 - 1";
	
	@Mock
	private NFLSeasonTeam team2;
	
	@Mock
	private Team leagueTeam2;
	
	private String team2Name = "Team 1 - 2";
	
	@Mock
	private NFLSeasonTeam team3;
	
	@Mock
	private Team leagueTeam3;
	
	private String team3Name = "Team 1 - 3";
	
	@Mock
	private NFLSeasonTeam team4;
	
	@Mock
	private Team leagueTeam4;
	
	private String team4Name = "Team 1 - 4";
	
	@Mock
	private NFLTiebreaker tiebreaker;
	
	@Mock
	private Division leagueDivision;
	
	private NFLSeasonDivision division;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		when(team1.getTeam()).thenReturn(leagueTeam1);
		when(leagueTeam1.getName()).thenReturn(team1Name);
		when(team2.getTeam()).thenReturn(leagueTeam2);
		when(leagueTeam2.getName()).thenReturn(team2Name);
		when(team3.getTeam()).thenReturn(leagueTeam3);
		when(leagueTeam3.getName()).thenReturn(team3Name);
		when(team4.getTeam()).thenReturn(leagueTeam4);
		when(leagueTeam4.getName()).thenReturn(team4Name);
		
		when(tiebreaker.tiebreakManyTeams(any(List.class))).thenReturn(team3, team2, team4);
		
		division = new NFLSeasonDivision(leagueDivision);
		division.addTeam(team1);
		division.addTeam(team2);
		division.addTeam(team3);
		division.addTeam(team4);
	}
	
	@Test
	public void divisionDeterminesOrder() {
		division.setTeamsInOrder(tiebreaker);
		
		List<NFLSeasonTeam> teamsInOrder = division.getTeamsInOrder();
		assertEquals(4, teamsInOrder.size());
		assertEquals(team3, teamsInOrder.get(0));
		assertEquals(team2, teamsInOrder.get(1));
		assertEquals(team4, teamsInOrder.get(2));
		assertEquals(team1, teamsInOrder.get(3));
		
		assertEquals(team3, division.getDivisionWinner());
		assertEquals(team1, division.getDivisionCellar());
	}
	
	@Test
	public void getDivisionStandingsStringUsesTeamsInOrderToGiveString() {
		String conferenceName = "Conf";
		String divisionStandings = division.getDivisionStandingsString(conferenceName, 
				tiebreaker);
		List<NFLSeasonTeam> teamsInOrder = division.getTeamsInOrder();
		assertEquals(4, teamsInOrder.size());
		assertEquals(team3, teamsInOrder.get(0));
		
		StringBuilder standingsBuilder = new StringBuilder();
		standingsBuilder.append(conferenceName + " " + leagueDivision.getName() + "\n");
		for (int i = 1; i <= teamsInOrder.size(); i++) {
			NFLSeasonTeam nextTeam = teamsInOrder.get(i - 1);
			Team leagueNextTeam = nextTeam.getTeam();
			String nextTeamName = leagueNextTeam.getName();
			standingsBuilder.append(i + ". " + nextTeamName + "\n");
		}
		
		String expectedDivisionStandings = standingsBuilder.toString();
		assertEquals(expectedDivisionStandings, divisionStandings);
	}
	
}
