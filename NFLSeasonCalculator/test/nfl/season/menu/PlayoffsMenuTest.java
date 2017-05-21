package nfl.season.menu;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffConference;
import nfl.season.playoffs.NFLPlayoffDivision;
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayoffsMenuTest {

	private static final int SELECT_TEAMS_FOR_PLAYOFFS = 1;
	
	private static final int BACK_TO_MAIN_MENU = 2;
	
	private String expectedMenuMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private NFLPlayoffs playoffs;
	
	private List<NFLPlayoffConference> playoffConferences;
	
	@Mock
	private NFLPlayoffConference playoffConference1;
	
	@Mock
	private Conference leagueConference1;
	
	private List<NFLPlayoffDivision> conference1Divisions;
	
	private List<Team> conference1Teams;
	
	@Mock
	private NFLPlayoffConference playoffConference2;
	
	@Mock
	private Conference leagueConference2;
	
	private List<NFLPlayoffDivision> conference2Divisions;
	
	private List<Team> conference2Teams;
	
	@Mock
	private NFLPlayoffDivision playoffDivision1_1;
	
	@Mock
	private Division leagueDivision1_1;
	
	private List<Team> division1_1Teams;
	
	@Mock
	private NFLPlayoffDivision playoffDivision1_2;
	
	@Mock
	private Division leagueDivision1_2;
	
	private List<Team> division1_2Teams;
	
	@Mock
	private NFLPlayoffDivision playoffDivision2_1;
	
	@Mock
	private Division leagueDivision2_1;
	
	private List<Team> division2_1Teams;
	
	@Mock
	private NFLPlayoffDivision playoffDivision2_2;
	
	@Mock
	private Division leagueDivision2_2;
	
	private List<Team> division2_2Teams;
	
	@Mock
	private Team leagueTeam1_1_1;
	
	@Mock
	private NFLPlayoffTeam playoffTeam1_1_1;
	
	@Mock
	private Team leagueTeam1_1_2;
	
	@Mock
	private NFLPlayoffTeam playoffTeam1_1_2;
	
	@Mock
	private Team leagueTeam1_1_3;
	
	@Mock
	private NFLPlayoffTeam playoffTeam1_1_3;
	
	@Mock
	private Team leagueTeam1_2_1;
	
	@Mock
	private NFLPlayoffTeam playoffTeam1_2_1;
	
	@Mock
	private Team leagueTeam1_2_2;
	
	@Mock
	private NFLPlayoffTeam playoffTeam1_2_2;
	
	@Mock
	private Team leagueTeam1_2_3;
	
	@Mock
	private NFLPlayoffTeam playoffTeam1_2_3;
	
	@Mock
	private Team leagueTeam2_1_1;
	
	@Mock
	private NFLPlayoffTeam playoffTeam2_1_1;
	
	@Mock
	private Team leagueTeam2_1_2;
	
	@Mock
	private NFLPlayoffTeam playoffTeam2_1_2;
	
	@Mock
	private Team leagueTeam2_1_3;
	
	@Mock
	private NFLPlayoffTeam playoffTeam2_1_3;
	
	@Mock
	private Team leagueTeam2_2_1;
	
	@Mock
	private NFLPlayoffTeam playoffTeam2_2_1;
	
	@Mock
	private Team leagueTeam2_2_2;
	
	@Mock
	private NFLPlayoffTeam playoffTeam2_2_2;
	
	@Mock
	private Team leagueTeam2_2_3;
	
	@Mock
	private NFLPlayoffTeam playoffTeam2_2_3;
	
	private PlayoffsMenu playoffsMenu;
	
	@Before
	public void setUp() {
		playoffsMenu = new PlayoffsMenu(input, playoffs);
		
		expectedMenuMessage = "Please enter in an integer corresponding to one of the following:\n" +
				"1. Select Teams for Playoffs\n2. Back to Main Menu";
		
		playoffConferences = new ArrayList<NFLPlayoffConference>();
		playoffConferences.add(playoffConference1);
		playoffConferences.add(playoffConference2);
		when(playoffs.getConferences()).thenReturn(playoffConferences);
		
		setUpConferences();
		setUpDivisions();
		setUpTeams();
	}

	private void setUpConferences() {
		conference1Divisions = new ArrayList<NFLPlayoffDivision>();
		conference1Divisions.add(playoffDivision1_1);
		conference1Divisions.add(playoffDivision1_2);
		when(playoffConference1.getDivisions()).thenReturn(conference1Divisions);
		when(playoffConference1.getConference()).thenReturn(leagueConference1);
		when(leagueConference1.getName()).thenReturn("Conf 1");
		
		conference1Teams = new ArrayList<Team>();
		conference1Teams.add(leagueTeam1_1_1);
		conference1Teams.add(leagueTeam1_1_2);
		conference1Teams.add(leagueTeam1_1_3);
		conference1Teams.add(leagueTeam1_2_1);
		conference1Teams.add(leagueTeam1_2_2);
		conference1Teams.add(leagueTeam1_2_3);
		List<Team> conference1TeamsCopy = new ArrayList<Team>();
		conference1TeamsCopy.addAll(conference1Teams);
		when(leagueConference1.getTeams()).thenReturn(conference1TeamsCopy);
		
		conference2Divisions = new ArrayList<NFLPlayoffDivision>();
		conference2Divisions.add(playoffDivision2_1);
		conference2Divisions.add(playoffDivision2_2);
		when(playoffConference2.getDivisions()).thenReturn(conference2Divisions);
		when(playoffConference2.getConference()).thenReturn(leagueConference2);
		when(leagueConference2.getName()).thenReturn("Conf 2");
		
		conference2Teams = new ArrayList<Team>();
		conference2Teams.add(leagueTeam2_1_1);
		conference2Teams.add(leagueTeam2_1_2);
		conference2Teams.add(leagueTeam2_1_3);
		conference2Teams.add(leagueTeam2_2_1);
		conference2Teams.add(leagueTeam2_2_2);
		conference2Teams.add(leagueTeam2_2_3);
		List<Team> conference2TeamsCopy = new ArrayList<Team>();
		conference2TeamsCopy.addAll(conference2Teams);
		when(leagueConference2.getTeams()).thenReturn(conference2TeamsCopy);
	}

	private void setUpDivisions() {
		division1_1Teams = new ArrayList<Team>();
		division1_1Teams.add(leagueTeam1_1_1);
		division1_1Teams.add(leagueTeam1_1_2);
		division1_1Teams.add(leagueTeam1_1_3);
		when(leagueDivision1_1.getTeams()).thenReturn(division1_1Teams);
		when(playoffDivision1_1.getDivision()).thenReturn(leagueDivision1_1);
		when(leagueDivision1_1.getName()).thenReturn("Div 1 - 1");
		
		division1_2Teams = new ArrayList<Team>();
		division1_2Teams.add(leagueTeam1_2_1);
		division1_2Teams.add(leagueTeam1_2_2);
		division1_2Teams.add(leagueTeam1_2_3);
		when(leagueDivision1_2.getTeams()).thenReturn(division1_2Teams);
		when(playoffDivision1_2.getDivision()).thenReturn(leagueDivision1_2);
		when(leagueDivision1_2.getName()).thenReturn("Div 1 - 2");
		
		division2_1Teams = new ArrayList<Team>();
		division2_1Teams.add(leagueTeam2_1_1);
		division2_1Teams.add(leagueTeam2_1_2);
		division2_1Teams.add(leagueTeam2_1_3);
		when(leagueDivision2_1.getTeams()).thenReturn(division2_1Teams);
		when(playoffDivision2_1.getDivision()).thenReturn(leagueDivision2_1);
		when(leagueDivision2_1.getName()).thenReturn("Div 2 - 1");
		
		division2_2Teams = new ArrayList<Team>();
		division2_2Teams.add(leagueTeam2_2_1);
		division2_2Teams.add(leagueTeam2_2_2);
		division2_2Teams.add(leagueTeam2_2_3);
		when(leagueDivision2_2.getTeams()).thenReturn(division2_2Teams);
		when(playoffDivision2_2.getDivision()).thenReturn(leagueDivision2_2);
		when(leagueDivision2_2.getName()).thenReturn("Div 2 - 2");
	}

	private void setUpTeams() {
		when(playoffs.createPlayoffTeam(leagueTeam1_1_1)).thenReturn(playoffTeam1_1_1);
		when(playoffTeam1_1_1.getTeam()).thenReturn(leagueTeam1_1_1);
		when(leagueTeam1_1_1.getName()).thenReturn("Team 1 - 1 - 1");
		
		when(playoffs.createPlayoffTeam(leagueTeam1_1_2)).thenReturn(playoffTeam1_1_2);
		when(playoffTeam1_1_2.getTeam()).thenReturn(leagueTeam1_1_2);
		when(leagueTeam1_1_2.getName()).thenReturn("Team 1 - 1 - 2");
		
		when(playoffs.createPlayoffTeam(leagueTeam1_1_3)).thenReturn(playoffTeam1_1_3);
		when(playoffTeam1_1_3.getTeam()).thenReturn(leagueTeam1_1_3);
		when(leagueTeam1_1_3.getName()).thenReturn("Team 1 - 1 - 3");
		
		when(playoffs.createPlayoffTeam(leagueTeam1_2_1)).thenReturn(playoffTeam1_2_1);
		when(playoffTeam1_2_1.getTeam()).thenReturn(leagueTeam1_2_1);
		when(leagueTeam1_2_1.getName()).thenReturn("Team 1 - 2 - 1");
		
		when(playoffs.createPlayoffTeam(leagueTeam1_2_2)).thenReturn(playoffTeam1_2_2);
		when(playoffTeam1_2_2.getTeam()).thenReturn(leagueTeam1_2_2);
		when(leagueTeam1_2_2.getName()).thenReturn("Team 1 - 2 - 2");
		
		when(playoffs.createPlayoffTeam(leagueTeam1_2_3)).thenReturn(playoffTeam1_2_3);
		when(playoffTeam1_2_3.getTeam()).thenReturn(leagueTeam1_2_3);
		when(leagueTeam1_2_3.getName()).thenReturn("Team 1 - 2 - 3");
		
		when(playoffs.createPlayoffTeam(leagueTeam2_1_1)).thenReturn(playoffTeam2_1_1);
		when(playoffTeam2_1_1.getTeam()).thenReturn(leagueTeam2_1_1);
		when(leagueTeam2_1_1.getName()).thenReturn("Team 2 - 1 - 1");
		
		when(playoffs.createPlayoffTeam(leagueTeam2_1_2)).thenReturn(playoffTeam2_1_2);
		when(playoffTeam2_1_2.getTeam()).thenReturn(leagueTeam2_1_2);
		when(leagueTeam2_1_2.getName()).thenReturn("Team 2 - 1 - 2");
		
		when(playoffs.createPlayoffTeam(leagueTeam2_1_3)).thenReturn(playoffTeam2_1_3);
		when(playoffTeam2_1_3.getTeam()).thenReturn(leagueTeam2_1_3);
		when(leagueTeam2_1_3.getName()).thenReturn("Team 2 - 1 - 3");
		
		when(playoffs.createPlayoffTeam(leagueTeam2_2_1)).thenReturn(playoffTeam2_2_1);
		when(playoffTeam2_2_1.getTeam()).thenReturn(leagueTeam2_2_1);
		when(leagueTeam2_2_1.getName()).thenReturn("Team 2 - 2 - 1");
		
		when(playoffs.createPlayoffTeam(leagueTeam2_2_2)).thenReturn(playoffTeam2_2_2);
		when(playoffTeam2_2_2.getTeam()).thenReturn(leagueTeam2_2_2);
		when(leagueTeam2_2_2.getName()).thenReturn("Team 2 - 2 - 2");
		
		when(playoffs.createPlayoffTeam(leagueTeam2_2_3)).thenReturn(playoffTeam2_2_3);
		when(playoffTeam2_2_3.getTeam()).thenReturn(leagueTeam2_2_3);
		when(leagueTeam2_2_3.getName()).thenReturn("Team 2 - 2 - 3");
	}
	
	@Test
	public void invalidInputGivenOnMenuIsIgnored() {
		when(input.askForInt(anyString())).thenReturn(999, -3, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(3)).askForInt(expectedMenuMessage);
	}
	
	@Test
	public void selectTeamsForPlayoffsHasUserPutInTeamsForPlayoffs() {
		List<NFLPlayoffTeam> conference1DivisionWinners = new ArrayList<NFLPlayoffTeam>();
		conference1DivisionWinners.add(playoffTeam1_1_2);
		conference1DivisionWinners.add(playoffTeam1_2_3);
		when(playoffConference1.getDivisionWinners()).thenReturn(conference1DivisionWinners);
		
		List<NFLPlayoffTeam> conference2DivisionWinners = new ArrayList<NFLPlayoffTeam>();
		conference2DivisionWinners.add(playoffTeam2_1_2);
		conference2DivisionWinners.add(playoffTeam2_2_1);
		when(playoffConference2.getDivisionWinners()).thenReturn(conference2DivisionWinners);
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_FOR_PLAYOFFS, 
				2, 3, 3, 1, 2, 1, 1, 2, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		verifyChoosePlayoffTeamsMessages();
		
		verify(playoffs).clearPlayoffTeams();
		verify(playoffs).setDivisionWinner(leagueConference1.getName(), leagueDivision1_1.getName(), playoffTeam1_1_2);
		verify(playoffs).setDivisionWinner(leagueConference1.getName(), leagueDivision1_2.getName(), playoffTeam1_2_3);
		verify(playoffs).setDivisionWinner(leagueConference2.getName(), leagueDivision2_1.getName(), playoffTeam2_1_2);
		verify(playoffs).setDivisionWinner(leagueConference2.getName(), leagueDivision2_2.getName(), playoffTeam2_2_1);
		verify(playoffs).addWildcardTeam(leagueConference1.getName(), playoffTeam1_2_1);
		verify(playoffs).addWildcardTeam(leagueConference1.getName(), playoffTeam1_1_1);
		verify(playoffs).addWildcardTeam(leagueConference2.getName(), playoffTeam2_1_1);
		verify(playoffs).addWildcardTeam(leagueConference2.getName(), playoffTeam2_2_2);
	}
	
	@Test
	public void selectTeamsForPlayoffsUserPutsInInvalidInputWhichIsIgnored() {
		List<NFLPlayoffTeam> conference1DivisionWinners = new ArrayList<NFLPlayoffTeam>();
		conference1DivisionWinners.add(playoffTeam1_1_2);
		conference1DivisionWinners.add(playoffTeam1_2_3);
		when(playoffConference1.getDivisionWinners()).thenReturn(conference1DivisionWinners);
		
		List<NFLPlayoffTeam> conference2DivisionWinners = new ArrayList<NFLPlayoffTeam>();
		conference2DivisionWinners.add(playoffTeam2_1_2);
		conference2DivisionWinners.add(playoffTeam2_2_1);
		when(playoffConference2.getDivisionWinners()).thenReturn(conference2DivisionWinners);
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_FOR_PLAYOFFS, 
				5, -1, 2, 3, 5, -1, 3, 1, 2, 1, 1, 2, BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(2)).askForInt(expectedMenuMessage);
		
		when(leagueConference1.getTeams()).thenReturn(conference1Teams);
		when(leagueConference2.getTeams()).thenReturn(conference2Teams);
		verify(input, times(3)).askForInt(getChooseDivisionWinnerMessage(leagueConference1, 
				leagueDivision1_1));
		verify(input, times(3)).askForInt(getChooseWildcardMessage(leagueConference1, 
				leagueTeam1_1_2.getName(), leagueTeam1_2_3.getName(), ""));
	}
	
	private String getChooseDivisionWinnerMessage(Conference conference, Division division) {
		StringBuilder divisionWinnerMessageBuilder = new StringBuilder();
		divisionWinnerMessageBuilder.append(conference.getName() + " " + 
				division.getName() + " Champion\n"); 
		divisionWinnerMessageBuilder.append(
				"Please enter in an integer corresponding to one of the following:\n");
		
		List<Team> divisionTeams = division.getTeams();
		int teamIndex = 1;
		for (Team team : divisionTeams) {
			divisionWinnerMessageBuilder.append(teamIndex + ". ");
			divisionWinnerMessageBuilder.append(team.getName());
			divisionWinnerMessageBuilder.append("\n");
			teamIndex++;
		}
		
		return divisionWinnerMessageBuilder.toString();
	}
	
	private void verifyChoosePlayoffTeamsMessages() {
		when(leagueConference1.getTeams()).thenReturn(conference1Teams);
		when(leagueConference2.getTeams()).thenReturn(conference2Teams);
		
		verify(input, times(1)).askForInt(getChooseDivisionWinnerMessage(leagueConference1, 
				leagueDivision1_1));
		verify(input, times(1)).askForInt(getChooseDivisionWinnerMessage(leagueConference1, 
				leagueDivision1_2));
		verify(input, times(1)).askForInt(getChooseDivisionWinnerMessage(leagueConference2, 
				leagueDivision2_1));
		verify(input, times(1)).askForInt(getChooseDivisionWinnerMessage(leagueConference2, 
				leagueDivision2_2));
		verify(input, times(1)).askForInt(getChooseWildcardMessage(leagueConference1, 
				leagueTeam1_1_2.getName(), leagueTeam1_2_3.getName(), ""));
		verify(input, times(1)).askForInt(getChooseWildcardMessage(leagueConference1, 
				leagueTeam1_1_2.getName(), leagueTeam1_2_3.getName(), leagueTeam1_2_1.getName()));
		verify(input, times(1)).askForInt(getChooseWildcardMessage(leagueConference2, 
				leagueTeam2_1_2.getName(), leagueTeam2_2_1.getName(), ""));
		verify(input, times(1)).askForInt(getChooseWildcardMessage(leagueConference2, 
				leagueTeam2_1_2.getName(), leagueTeam2_2_1.getName(), leagueTeam2_1_1.getName()));
	}
	
	private String getChooseWildcardMessage(Conference conference, String divisionWinner1, 
			String divisionWinner2, String otherWildcard) {
		StringBuilder wildcardMessageBuilder = new StringBuilder();
		
		wildcardMessageBuilder.append(conference.getName() + " Wildcard"); 
		wildcardMessageBuilder.append(
				"\nPlease enter in an integer corresponding to one of the following:\n");
		
		List<Team> conferenceTeams = conference.getTeams();
		int teamIndex = 1;
		for (Team team : conferenceTeams) {
			String teamName = team.getName();
			if (!divisionWinner1.equalsIgnoreCase(teamName) && 
					!divisionWinner2.equalsIgnoreCase(teamName) && 
					!teamName.equalsIgnoreCase(otherWildcard)) {
				wildcardMessageBuilder.append(teamIndex + ". ");
				wildcardMessageBuilder.append(teamName);
				wildcardMessageBuilder.append("\n");
				teamIndex++;
			}
		}
		
		return wildcardMessageBuilder.toString();
	}
	
}
