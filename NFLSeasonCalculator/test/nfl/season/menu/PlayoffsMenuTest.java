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
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;
import nfl.season.playoffs.TestWithMockPlayoffObjects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayoffsMenuTest extends TestWithMockPlayoffObjects {

	private static final int SELECT_TEAMS_FOR_PLAYOFFS = 1;
	
	private static final int SELECT_TEAMS_BY_POWER_RANKINGS = 2;
	
	private static final int SELECT_TEAMS_BY_ELO_RATINGS = 3;
	
	private static final int BACK_TO_MAIN_MENU = 4;
	
	private String expectedMenuMessage;
	
	@Mock
	private NFLSeasonInput input;
	
	@Mock
	private NFLPlayoffs playoffs;
	
	private PlayoffsMenu playoffsMenu;
	
	@Before
	public void setUp() {
		playoffsMenu = new PlayoffsMenu(input, playoffs);
		
		super.setUpMockObjects();
		super.setUpMockPlayoffsWithTeamsAndConferences(playoffs);
		setExpectedMenuMessage();
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
	
	@Test
	public void choosePlayoffTeamsBasedOnPowerRankingsCallsPlayoffsToGetTeamsByPowerRankings() {
		when(playoffs.populateTeamsByPowerRankings()).thenReturn(true);
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_BY_POWER_RANKINGS, 
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt("Teams set based on Power Rankings\n" + expectedMenuMessage);
		verify(playoffs, times(1)).populateTeamsByPowerRankings();
	}
	
	@Test
	public void choosePlayoffTeamsOnPowerRankingsButNotAllRankingsSetSoPlayoffTeamsNotSet() {
		when(playoffs.populateTeamsByPowerRankings()).thenReturn(false);
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_BY_POWER_RANKINGS, 
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt("Need to set Power Rankings on all teams first\n" + 
				expectedMenuMessage);
		verify(playoffs, times(1)).populateTeamsByPowerRankings();
	}
	
	@Test
	public void choosePlayoffTeamsOnEloRatingsHasPlayoffsPopulateTeamsByEloRatings() {
when(playoffs.populateTeamsByPowerRankings()).thenReturn(true);
		
		when(input.askForInt(anyString())).thenReturn(SELECT_TEAMS_BY_ELO_RATINGS, 
				BACK_TO_MAIN_MENU);
		
		playoffsMenu.launchSubMenu();
		
		verify(input, times(1)).askForInt(expectedMenuMessage);
		verify(input, times(1)).askForInt("Teams set based on Elo Ratings\n" + expectedMenuMessage);
		verify(playoffs, times(1)).populateTeamsByEloRatings();
	}
	
	private void setExpectedMenuMessage() {
		StringBuilder expectedMenuBuilder = new StringBuilder();
		
		expectedMenuBuilder.append("Current Playoff Teams\n");
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			expectedMenuBuilder.append(conferenceName + "\n");
			
			for (int i = 1; i <= 6; i++) {
				NFLPlayoffTeam playoffTeam = playoffConference.getTeamWithSeed(i);
				if (playoffTeam == null) {
					expectedMenuBuilder.append(i + ". Unset\n");
				} else {
					Team leagueTeam = playoffTeam.getTeam();
					String teamName = leagueTeam.getName();
					
					expectedMenuBuilder.append(i + ". " + teamName + "\n");
				}
			}
			expectedMenuBuilder.append("\n");
		}
		
		expectedMenuBuilder.append("Please enter in an integer corresponding to one of the following:\n");
		expectedMenuBuilder.append("1. Select Teams for Playoffs\n");
		expectedMenuBuilder.append("2. Select Playoff Teams Based on Power Rankings\n");
		expectedMenuBuilder.append("3. Select Playoff Teams Based on Elo Ratings\n");
		expectedMenuBuilder.append("4. Back to Main Menu");
		
		expectedMenuMessage = expectedMenuBuilder.toString();
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
		divisionWinnerMessageBuilder.deleteCharAt(
				divisionWinnerMessageBuilder.lastIndexOf("\n"));
		
		
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
		wildcardMessageBuilder.deleteCharAt(wildcardMessageBuilder.lastIndexOf("\n"));
		
		return wildcardMessageBuilder.toString();
	}
	
}
