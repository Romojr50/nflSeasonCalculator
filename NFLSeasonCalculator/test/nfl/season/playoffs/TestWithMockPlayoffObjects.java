package nfl.season.playoffs;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.League;
import nfl.season.league.Team;

import org.mockito.Mock;

public class TestWithMockPlayoffObjects {
	public List<NFLPlayoffConference> playoffConferences;
	
	public List<Conference> leagueConferences;
	
	@Mock
	public NFLPlayoffConference playoffConference1;
	
	@Mock
	public Conference leagueConference1;
	
	public List<NFLPlayoffDivision> conference1Divisions;
	
	public List<Division> conference1LeagueDivisions;
	
	public List<Team> conference1Teams;
	
	@Mock
	public NFLPlayoffConference playoffConference2;
	
	@Mock
	public Conference leagueConference2;
	
	public List<NFLPlayoffDivision> conference2Divisions;
	
	public List<Division> conference2LeagueDivisions;
	
	public List<Team> conference2Teams;
	
	@Mock
	public NFLPlayoffDivision playoffDivision1_1;
	
	@Mock
	public Division leagueDivision1_1;
	
	public List<Team> division1_1Teams;
	
	@Mock
	public NFLPlayoffDivision playoffDivision1_2;
	
	@Mock
	public Division leagueDivision1_2;
	
	public List<Team> division1_2Teams;
	
	@Mock
	public NFLPlayoffDivision playoffDivision1_3;
	
	@Mock
	public Division leagueDivision1_3;
	
	public List<Team> division1_3Teams;
	
	@Mock
	public NFLPlayoffDivision playoffDivision1_4;
	
	@Mock
	public Division leagueDivision1_4;
	
	public List<Team> division1_4Teams;
	
	@Mock
	public NFLPlayoffDivision playoffDivision2_1;
	
	@Mock
	public Division leagueDivision2_1;
	
	public List<Team> division2_1Teams;
	
	@Mock
	public NFLPlayoffDivision playoffDivision2_2;
	
	@Mock
	public Division leagueDivision2_2;
	
	public List<Team> division2_2Teams;
	
	@Mock
	public NFLPlayoffDivision playoffDivision2_3;
	
	@Mock
	public Division leagueDivision2_3;
	
	public List<Team> division2_3Teams;
	
	@Mock
	public NFLPlayoffDivision playoffDivision2_4;
	
	@Mock
	public Division leagueDivision2_4;
	
	public List<Team> division2_4Teams;
	
	@Mock
	public Team leagueTeam1_1_1;
	
	@Mock
	public NFLPlayoffTeam playoffTeam1_1_1;
	
	@Mock
	public Team leagueTeam1_1_2;
	
	@Mock
	public NFLPlayoffTeam playoffTeam1_1_2;
	
	@Mock
	public Team leagueTeam1_1_3;
	
	@Mock
	public NFLPlayoffTeam playoffTeam1_1_3;
	
	@Mock
	public Team leagueTeam1_2_1;
	
	@Mock
	public NFLPlayoffTeam playoffTeam1_2_1;
	
	@Mock
	public Team leagueTeam1_2_2;
	
	@Mock
	public NFLPlayoffTeam playoffTeam1_2_2;
	
	@Mock
	public Team leagueTeam1_2_3;
	
	@Mock
	public NFLPlayoffTeam playoffTeam1_2_3;
	
	@Mock
	public Team leagueTeam1_3_1;
	
	@Mock
	public NFLPlayoffTeam playoffTeam1_3_1;
	
	@Mock
	public Team leagueTeam1_4_1;
	
	@Mock
	public NFLPlayoffTeam playoffTeam1_4_1;
	
	@Mock
	public Team leagueTeam2_1_1;
	
	@Mock
	public NFLPlayoffTeam playoffTeam2_1_1;
	
	@Mock
	public Team leagueTeam2_1_2;
	
	@Mock
	public NFLPlayoffTeam playoffTeam2_1_2;
	
	@Mock
	public Team leagueTeam2_1_3;
	
	@Mock
	public NFLPlayoffTeam playoffTeam2_1_3;
	
	@Mock
	public Team leagueTeam2_2_1;
	
	@Mock
	public NFLPlayoffTeam playoffTeam2_2_1;
	
	@Mock
	public Team leagueTeam2_2_2;
	
	@Mock
	public NFLPlayoffTeam playoffTeam2_2_2;
	
	@Mock
	public Team leagueTeam2_2_3;
	
	@Mock
	public NFLPlayoffTeam playoffTeam2_2_3;
	
	@Mock
	public Team leagueTeam2_3_1;
	
	@Mock
	public NFLPlayoffTeam playoffTeam2_3_1;
	
	@Mock
	public Team leagueTeam2_4_1;
	
	@Mock
	public NFLPlayoffTeam playoffTeam2_4_1;
	
	public void setUpMockObjects() {
		playoffConferences = new ArrayList<NFLPlayoffConference>();
		playoffConferences.add(playoffConference1);
		playoffConferences.add(playoffConference2);
		
		setUpConferences();
		setUpDivisions();
		setUpTeams();
	}
	
	public void setUpMockPlayoffsWithTeamsAndConferences(NFLPlayoffs playoffs) {
		when(playoffs.getConferences()).thenReturn(playoffConferences);
		when(playoffs.createPlayoffTeam(leagueTeam1_1_1)).thenReturn(playoffTeam1_1_1);
		when(playoffs.createPlayoffTeam(leagueTeam1_1_2)).thenReturn(playoffTeam1_1_2);
		when(playoffs.createPlayoffTeam(leagueTeam1_1_3)).thenReturn(playoffTeam1_1_3);
		when(playoffs.createPlayoffTeam(leagueTeam1_2_1)).thenReturn(playoffTeam1_2_1);
		when(playoffs.createPlayoffTeam(leagueTeam1_2_2)).thenReturn(playoffTeam1_2_2);
		when(playoffs.createPlayoffTeam(leagueTeam1_2_3)).thenReturn(playoffTeam1_2_3);
		when(playoffs.createPlayoffTeam(leagueTeam1_3_1)).thenReturn(playoffTeam1_3_1);
		when(playoffs.createPlayoffTeam(leagueTeam1_4_1)).thenReturn(playoffTeam1_4_1);
		when(playoffs.createPlayoffTeam(leagueTeam2_1_1)).thenReturn(playoffTeam2_1_1);
		when(playoffs.createPlayoffTeam(leagueTeam2_1_2)).thenReturn(playoffTeam2_1_2);
		when(playoffs.createPlayoffTeam(leagueTeam2_1_3)).thenReturn(playoffTeam2_1_3);
		when(playoffs.createPlayoffTeam(leagueTeam2_2_1)).thenReturn(playoffTeam2_2_1);
		when(playoffs.createPlayoffTeam(leagueTeam2_2_2)).thenReturn(playoffTeam2_2_2);
		when(playoffs.createPlayoffTeam(leagueTeam2_2_3)).thenReturn(playoffTeam2_2_3);
		when(playoffs.createPlayoffTeam(leagueTeam2_3_1)).thenReturn(playoffTeam2_3_1);
		when(playoffs.createPlayoffTeam(leagueTeam2_4_1)).thenReturn(playoffTeam2_4_1);
	}
	
	public void setUpMockLeague(League mockLeague) {
		when(mockLeague.getTeam("Team 1 - 1 - 1")).thenReturn(leagueTeam1_1_1);
		when(mockLeague.getTeam("Team 1 - 1 - 2")).thenReturn(leagueTeam1_1_2);
		when(mockLeague.getTeam("Team 1 - 1 - 3")).thenReturn(leagueTeam1_1_3);
		when(mockLeague.getTeam("Team 1 - 2 - 1")).thenReturn(leagueTeam1_2_1);
		when(mockLeague.getTeam("Team 1 - 2 - 2")).thenReturn(leagueTeam1_2_2);
		when(mockLeague.getTeam("Team 1 - 2 - 3")).thenReturn(leagueTeam1_2_3);
		when(mockLeague.getTeam("Team 1 - 3 - 1")).thenReturn(leagueTeam1_3_1);
		when(mockLeague.getTeam("Team 1 - 4 - 1")).thenReturn(leagueTeam1_4_1);
		
		when(mockLeague.getTeam("Team 2 - 1 - 1")).thenReturn(leagueTeam2_1_1);
		when(mockLeague.getTeam("Team 2 - 1 - 2")).thenReturn(leagueTeam2_1_2);
		when(mockLeague.getTeam("Team 2 - 1 - 3")).thenReturn(leagueTeam2_1_3);
		when(mockLeague.getTeam("Team 2 - 2 - 1")).thenReturn(leagueTeam2_2_1);
		when(mockLeague.getTeam("Team 2 - 2 - 2")).thenReturn(leagueTeam2_2_2);
		when(mockLeague.getTeam("Team 2 - 2 - 3")).thenReturn(leagueTeam2_2_3);
		when(mockLeague.getTeam("Team 2 - 3 - 1")).thenReturn(leagueTeam2_3_1);
		when(mockLeague.getTeam("Team 2 - 4 - 1")).thenReturn(leagueTeam2_4_1);
	}
	
	private void setUpConferences() {
		conference1Divisions = new ArrayList<NFLPlayoffDivision>();
		conference1LeagueDivisions = new ArrayList<Division>();
		conference1Divisions.add(playoffDivision1_1);
		conference1LeagueDivisions.add(leagueDivision1_1);
		conference1Divisions.add(playoffDivision1_2);
		conference1LeagueDivisions.add(leagueDivision1_2);
		conference1Divisions.add(playoffDivision1_3);
		conference1LeagueDivisions.add(leagueDivision1_3);
		conference1Divisions.add(playoffDivision1_4);
		conference1LeagueDivisions.add(leagueDivision1_4);
		when(playoffConference1.getDivisions()).thenReturn(conference1Divisions);
		when(leagueConference1.getDivisions()).thenReturn(conference1LeagueDivisions);
		when(playoffConference1.getConference()).thenReturn(leagueConference1);
		when(leagueConference1.getName()).thenReturn("Conf 1");
		
		conference1Teams = new ArrayList<Team>();
		conference1Teams.add(leagueTeam1_1_1);
		conference1Teams.add(leagueTeam1_1_2);
		conference1Teams.add(leagueTeam1_1_3);
		conference1Teams.add(leagueTeam1_2_1);
		conference1Teams.add(leagueTeam1_2_2);
		conference1Teams.add(leagueTeam1_2_3);
		conference1Teams.add(leagueTeam1_3_1);
		conference1Teams.add(leagueTeam1_4_1);
		when(playoffConference1.getDivision(playoffTeam1_1_1)).thenReturn(playoffDivision1_1);
		when(playoffConference1.getDivision(playoffTeam1_1_2)).thenReturn(playoffDivision1_1);
		when(playoffConference1.getDivision(playoffTeam1_1_3)).thenReturn(playoffDivision1_1);
		when(playoffConference1.getDivision(playoffTeam1_2_1)).thenReturn(playoffDivision1_2);
		when(playoffConference1.getDivision(playoffTeam1_2_2)).thenReturn(playoffDivision1_2);
		when(playoffConference1.getDivision(playoffTeam1_2_3)).thenReturn(playoffDivision1_2);
		when(playoffConference1.getDivision(playoffTeam1_3_1)).thenReturn(playoffDivision1_3);
		when(playoffConference1.getDivision(playoffTeam1_4_1)).thenReturn(playoffDivision1_4);
		List<Team> conference1TeamsCopy = new ArrayList<Team>();
		conference1TeamsCopy.addAll(conference1Teams);
		when(leagueConference1.getTeams()).thenReturn(conference1TeamsCopy);
		
		conference2Divisions = new ArrayList<NFLPlayoffDivision>();
		conference2LeagueDivisions = new ArrayList<Division>();
		conference2Divisions.add(playoffDivision2_1);
		conference2LeagueDivisions.add(leagueDivision2_1);
		conference2Divisions.add(playoffDivision2_2);
		conference2LeagueDivisions.add(leagueDivision2_2);
		conference2Divisions.add(playoffDivision2_3);
		conference2LeagueDivisions.add(leagueDivision2_3);
		conference2Divisions.add(playoffDivision2_4);
		conference2LeagueDivisions.add(leagueDivision2_4);
		when(playoffConference2.getDivisions()).thenReturn(conference2Divisions);
		when(leagueConference2.getDivisions()).thenReturn(conference2LeagueDivisions);
		when(playoffConference2.getConference()).thenReturn(leagueConference2);
		when(leagueConference2.getName()).thenReturn("Conf 2");
		
		conference2Teams = new ArrayList<Team>();
		conference2Teams.add(leagueTeam2_1_1);
		conference2Teams.add(leagueTeam2_1_2);
		conference2Teams.add(leagueTeam2_1_3);
		conference2Teams.add(leagueTeam2_2_1);
		conference2Teams.add(leagueTeam2_2_2);
		conference2Teams.add(leagueTeam2_2_3);
		conference2Teams.add(leagueTeam2_3_1);
		conference2Teams.add(leagueTeam2_4_1);
		when(playoffConference2.getDivision(playoffTeam2_1_1)).thenReturn(playoffDivision2_1);
		when(playoffConference2.getDivision(playoffTeam2_1_2)).thenReturn(playoffDivision2_1);
		when(playoffConference2.getDivision(playoffTeam2_1_3)).thenReturn(playoffDivision2_1);
		when(playoffConference2.getDivision(playoffTeam2_2_1)).thenReturn(playoffDivision2_2);
		when(playoffConference2.getDivision(playoffTeam2_2_2)).thenReturn(playoffDivision2_2);
		when(playoffConference2.getDivision(playoffTeam2_2_3)).thenReturn(playoffDivision2_2);
		when(playoffConference2.getDivision(playoffTeam2_3_1)).thenReturn(playoffDivision2_3);
		when(playoffConference2.getDivision(playoffTeam2_4_1)).thenReturn(playoffDivision2_4);
		List<Team> conference2TeamsCopy = new ArrayList<Team>();
		conference2TeamsCopy.addAll(conference2Teams);
		when(leagueConference2.getTeams()).thenReturn(conference2TeamsCopy);
	}

	private void setUpDivisions() {
		division1_1Teams = new ArrayList<Team>();
		division1_1Teams.add(leagueTeam1_1_1);
		division1_1Teams.add(leagueTeam1_1_2);
		division1_1Teams.add(leagueTeam1_1_3);
		when(leagueDivision1_1.getTeam("Team 1 - 1 - 1")).thenReturn(leagueTeam1_1_1);
		when(leagueDivision1_1.getTeam("Team 1 - 1 - 2")).thenReturn(leagueTeam1_1_2);
		when(leagueDivision1_1.getTeam("Team 1 - 1 - 3")).thenReturn(leagueTeam1_1_3);
		when(leagueDivision1_1.getTeams()).thenReturn(division1_1Teams);
		when(playoffDivision1_1.getDivision()).thenReturn(leagueDivision1_1);
		when(leagueDivision1_1.getName()).thenReturn("Div 1 - 1");
		
		division1_2Teams = new ArrayList<Team>();
		division1_2Teams.add(leagueTeam1_2_1);
		division1_2Teams.add(leagueTeam1_2_2);
		division1_2Teams.add(leagueTeam1_2_3);
		when(leagueDivision1_1.getTeam("Team 1 - 2 - 1")).thenReturn(leagueTeam1_2_1);
		when(leagueDivision1_1.getTeam("Team 1 - 2 - 2")).thenReturn(leagueTeam1_2_2);
		when(leagueDivision1_1.getTeam("Team 1 - 2 - 3")).thenReturn(leagueTeam1_2_3);
		when(leagueDivision1_2.getTeams()).thenReturn(division1_2Teams);
		when(playoffDivision1_2.getDivision()).thenReturn(leagueDivision1_2);
		when(leagueDivision1_2.getName()).thenReturn("Div 1 - 2");
		
		division1_3Teams = new ArrayList<Team>();
		division1_3Teams.add(leagueTeam1_3_1);
		when(leagueDivision1_1.getTeam("Team 1 - 3 - 1")).thenReturn(leagueTeam1_3_1);
		when(leagueDivision1_3.getTeams()).thenReturn(division1_3Teams);
		when(playoffDivision1_3.getDivision()).thenReturn(leagueDivision1_3);
		when(leagueDivision1_3.getName()).thenReturn("Div 1 - 3");
		
		division1_4Teams = new ArrayList<Team>();
		division1_4Teams.add(leagueTeam1_4_1);
		when(leagueDivision1_1.getTeam("Team 1 - 4 - 1")).thenReturn(leagueTeam1_4_1);
		when(leagueDivision1_4.getTeams()).thenReturn(division1_4Teams);
		when(playoffDivision1_4.getDivision()).thenReturn(leagueDivision1_4);
		when(leagueDivision1_4.getName()).thenReturn("Div 1 - 4");
		
		division2_1Teams = new ArrayList<Team>();
		division2_1Teams.add(leagueTeam2_1_1);
		division2_1Teams.add(leagueTeam2_1_2);
		division2_1Teams.add(leagueTeam2_1_3);
		when(leagueDivision1_1.getTeam("Team 2 - 1 - 1")).thenReturn(leagueTeam2_1_1);
		when(leagueDivision1_1.getTeam("Team 2 - 1 - 2")).thenReturn(leagueTeam2_1_2);
		when(leagueDivision1_1.getTeam("Team 2 - 1 - 3")).thenReturn(leagueTeam2_1_3);
		when(leagueDivision2_1.getTeams()).thenReturn(division2_1Teams);
		when(playoffDivision2_1.getDivision()).thenReturn(leagueDivision2_1);
		when(leagueDivision2_1.getName()).thenReturn("Div 2 - 1");
		
		division2_2Teams = new ArrayList<Team>();
		division2_2Teams.add(leagueTeam2_2_1);
		division2_2Teams.add(leagueTeam2_2_2);
		division2_2Teams.add(leagueTeam2_2_3);
		when(leagueDivision1_1.getTeam("Team 2 - 2 - 1")).thenReturn(leagueTeam2_2_1);
		when(leagueDivision1_1.getTeam("Team 2 - 2 - 2")).thenReturn(leagueTeam2_2_2);
		when(leagueDivision1_1.getTeam("Team 2 - 2 - 3")).thenReturn(leagueTeam2_2_3);
		when(leagueDivision2_2.getTeams()).thenReturn(division2_2Teams);
		when(playoffDivision2_2.getDivision()).thenReturn(leagueDivision2_2);
		when(leagueDivision2_2.getName()).thenReturn("Div 2 - 2");
		
		division2_3Teams = new ArrayList<Team>();
		division2_3Teams.add(leagueTeam2_3_1);
		when(leagueDivision1_1.getTeam("Team 2 - 3 - 1")).thenReturn(leagueTeam2_3_1);
		when(leagueDivision2_3.getTeams()).thenReturn(division2_3Teams);
		when(playoffDivision2_3.getDivision()).thenReturn(leagueDivision2_3);
		when(leagueDivision2_3.getName()).thenReturn("Div 2 - 3");
		
		division2_4Teams = new ArrayList<Team>();
		division2_4Teams.add(leagueTeam2_4_1);
		when(leagueDivision1_1.getTeam("Team 2 - 4 - 1")).thenReturn(leagueTeam2_4_1);
		when(leagueDivision2_4.getTeams()).thenReturn(division2_4Teams);
		when(playoffDivision2_4.getDivision()).thenReturn(leagueDivision2_4);
		when(leagueDivision2_4.getName()).thenReturn("Div 2 - 4");
	}

	private void setUpTeams() {
		when(playoffTeam1_1_1.getTeam()).thenReturn(leagueTeam1_1_1);
		when(leagueTeam1_1_1.getName()).thenReturn("Team 1 - 1 - 1");
		
		when(playoffTeam1_1_2.getTeam()).thenReturn(leagueTeam1_1_2);
		when(leagueTeam1_1_2.getName()).thenReturn("Team 1 - 1 - 2");
		
		when(playoffTeam1_1_3.getTeam()).thenReturn(leagueTeam1_1_3);
		when(leagueTeam1_1_3.getName()).thenReturn("Team 1 - 1 - 3");
		
		when(playoffTeam1_2_1.getTeam()).thenReturn(leagueTeam1_2_1);
		when(leagueTeam1_2_1.getName()).thenReturn("Team 1 - 2 - 1");
		
		when(playoffTeam1_2_2.getTeam()).thenReturn(leagueTeam1_2_2);
		when(leagueTeam1_2_2.getName()).thenReturn("Team 1 - 2 - 2");
		
		when(playoffTeam1_2_3.getTeam()).thenReturn(leagueTeam1_2_3);
		when(leagueTeam1_2_3.getName()).thenReturn("Team 1 - 2 - 3");
		
		when(playoffTeam1_3_1.getTeam()).thenReturn(leagueTeam1_3_1);
		when(leagueTeam1_3_1.getName()).thenReturn("Team 1 - 3 - 1");
		
		when(playoffTeam1_4_1.getTeam()).thenReturn(leagueTeam1_4_1);
		when(leagueTeam1_4_1.getName()).thenReturn("Team 1 - 4 - 1");
		
		when(playoffTeam2_1_1.getTeam()).thenReturn(leagueTeam2_1_1);
		when(leagueTeam2_1_1.getName()).thenReturn("Team 2 - 1 - 1");
		
		when(playoffTeam2_1_2.getTeam()).thenReturn(leagueTeam2_1_2);
		when(leagueTeam2_1_2.getName()).thenReturn("Team 2 - 1 - 2");
		
		when(playoffTeam2_1_3.getTeam()).thenReturn(leagueTeam2_1_3);
		when(leagueTeam2_1_3.getName()).thenReturn("Team 2 - 1 - 3");
		
		when(playoffTeam2_2_1.getTeam()).thenReturn(leagueTeam2_2_1);
		when(leagueTeam2_2_1.getName()).thenReturn("Team 2 - 2 - 1");
		
		when(playoffTeam2_2_2.getTeam()).thenReturn(leagueTeam2_2_2);
		when(leagueTeam2_2_2.getName()).thenReturn("Team 2 - 2 - 2");
		
		when(playoffTeam2_2_3.getTeam()).thenReturn(leagueTeam2_2_3);
		when(leagueTeam2_2_3.getName()).thenReturn("Team 2 - 2 - 3");
		
		when(playoffTeam2_3_1.getTeam()).thenReturn(leagueTeam2_3_1);
		when(leagueTeam2_3_1.getName()).thenReturn("Team 2 - 3 - 1");
		
		when(playoffTeam2_4_1.getTeam()).thenReturn(leagueTeam2_4_1);
		when(leagueTeam2_4_1.getName()).thenReturn("Team 2 - 4 - 1");
	}
}
