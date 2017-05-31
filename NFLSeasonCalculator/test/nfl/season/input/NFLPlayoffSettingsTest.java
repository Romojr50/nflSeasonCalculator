package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.League;
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;
import nfl.season.playoffs.TestWithMockPlayoffObjects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLPlayoffSettingsTest extends TestWithMockPlayoffObjects {

	private static final String CONFERENCE_1_TEAMS_LINE = "=Conf 1=" +
			"/Div 1 - 3/Team 1 - 3 - 1,/Div 1 - 4/Team 1 - 4 - 1," +
			"/Div 1 - 1/Team 1 - 1 - 1,/Div 1 - 2/Team 1 - 2 - 1," +
			"Team 1 - 1 - 2,Team 1 - 2 - 2,";
	
	private static final String CONFERENCE_2_TEAMS_LINE = "=Conf 2=" +
			"/Div 2 - 3/Team 2 - 3 - 1,/Div 2 - 4/Team 2 - 4 - 1," +
			"/Div 2 - 1/Team 2 - 1 - 1,/Div 2 - 2/Team 2 - 2 - 1," +
			"Team 2 - 1 - 2,Team 2 - 2 - 2,";
	
	private static final String PLAYOFF_SETTINGS_STRING = CONFERENCE_1_TEAMS_LINE + 
			"\n" + CONFERENCE_2_TEAMS_LINE + "\n";

	@Mock
	private NFLPlayoffs playoffs;
	
	@Mock
	private League nfl;
	
	private List<NFLPlayoffTeam> playoffConference1Teams;
	
	private List<NFLPlayoffTeam> playoffConference2Teams;
	
	private NFLPlayoffSettings playoffSettings;
	
	@Before
	public void setUp() {
		setUpMockObjects();
		setUpMockPlayoffsWithTeamsAndConferences(playoffs);
		
		playoffConference1Teams = new ArrayList<NFLPlayoffTeam>();
		playoffConference1Teams.add(playoffTeam1_3_1);
		playoffConference1Teams.add(playoffTeam1_4_1);
		playoffConference1Teams.add(playoffTeam1_1_1);
		playoffConference1Teams.add(playoffTeam1_2_1);
		playoffConference1Teams.add(playoffTeam1_1_2);
		playoffConference1Teams.add(playoffTeam1_2_2);
		when(playoffConference1.getTeamsInSeedOrder()).thenReturn(playoffConference1Teams);
		
		playoffConference2Teams = new ArrayList<NFLPlayoffTeam>();
		playoffConference2Teams.add(playoffTeam2_3_1);
		playoffConference2Teams.add(playoffTeam2_4_1);
		playoffConference2Teams.add(playoffTeam2_1_1);
		playoffConference2Teams.add(playoffTeam2_2_1);
		playoffConference2Teams.add(playoffTeam2_1_2);
		playoffConference2Teams.add(playoffTeam2_2_2);
		when(playoffConference2.getTeamsInSeedOrder()).thenReturn(playoffConference2Teams);
		
		setUpMockLeague(nfl);
		
		playoffSettings = new NFLPlayoffSettings();
	}

	@Test
	public void createPlayoffsLineCreatesLineForConferenceTeams() {
		String conferencePlayoffTeamsLine = 
				playoffSettings.createConferencePlayoffTeamsLine(playoffConference1);
		
		assertEquals(CONFERENCE_1_TEAMS_LINE, conferencePlayoffTeamsLine);
	}
	
	@Test
	public void createPlayoffsLineButNotAllTeamsSetSoEmptyLineReturned() {
		when(playoffConference1.getTeamsInSeedOrder()).thenReturn(new ArrayList<NFLPlayoffTeam>());
		
		String conferencePlayoffTeamsLine = 
				playoffSettings.createConferencePlayoffTeamsLine(playoffConference1);
		
		assertEquals("", conferencePlayoffTeamsLine);
	}
	
	@Test
	public void loadPlayoffsLineLoadsPlayoffTeamsIntoConference() {
		playoffSettings.loadConferencePlayoffTeamsLine(playoffs, nfl, CONFERENCE_1_TEAMS_LINE);
		
		verifyConference1TeamsSet();
	}
	
	@Test
	public void createPlayoffSettingsStringCreatesStringBasedOnAllConferences() {
		String playoffSettingsString = playoffSettings.createPlayoffSettingsString(playoffs);
		
		assertEquals(PLAYOFF_SETTINGS_STRING, playoffSettingsString);
	}
	
	@Test
	public void createPlayoffSettingsStringIsEmptyIfPlayoffTeamIsMissing() {
		when(playoffConference1.getTeamsInSeedOrder()).thenReturn(new ArrayList<NFLPlayoffTeam>());
		
		String playoffSettingsString = playoffSettings.createPlayoffSettingsString(playoffs);
		
		assertEquals("", playoffSettingsString);
	}
	
	@Test
	public void loadPlayoffSettingsStringSetsTeamsToAllConferences() {
		playoffSettings.loadPlayoffSettingsString(playoffs, nfl, PLAYOFF_SETTINGS_STRING);
		
		verifyConference1TeamsSet();
		verifyConference2TeamsSet();
	}
	
	private void verifyConference1TeamsSet() {
		verify(playoffs).setDivisionWinner(leagueConference1.getName(), leagueDivision1_3.getName(), playoffTeam1_3_1);
		verify(playoffs).setDivisionWinner(leagueConference1.getName(), leagueDivision1_4.getName(), playoffTeam1_4_1);
		verify(playoffs).setDivisionWinner(leagueConference1.getName(), leagueDivision1_1.getName(), playoffTeam1_1_1);
		verify(playoffs).setDivisionWinner(leagueConference1.getName(), leagueDivision1_2.getName(), playoffTeam1_2_1);
		
		verify(playoffs).addWildcardTeam(leagueConference1.getName(), playoffTeam1_1_2);
		verify(playoffs).addWildcardTeam(leagueConference1.getName(), playoffTeam1_2_2);
	}
	
	private void verifyConference2TeamsSet() {
		verify(playoffs).setDivisionWinner(leagueConference2.getName(), leagueDivision2_3.getName(), playoffTeam2_3_1);
		verify(playoffs).setDivisionWinner(leagueConference2.getName(), leagueDivision2_4.getName(), playoffTeam2_4_1);
		verify(playoffs).setDivisionWinner(leagueConference2.getName(), leagueDivision2_1.getName(), playoffTeam2_1_1);
		verify(playoffs).setDivisionWinner(leagueConference2.getName(), leagueDivision2_2.getName(), playoffTeam2_2_1);
		
		verify(playoffs).addWildcardTeam(leagueConference2.getName(), playoffTeam2_1_2);
		verify(playoffs).addWildcardTeam(leagueConference2.getName(), playoffTeam2_2_2);
	}
	
}
