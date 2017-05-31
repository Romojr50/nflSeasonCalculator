package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

	@Mock
	private NFLPlayoffs playoffs;
	
	private List<NFLPlayoffTeam> playoffConference1Teams;
	
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
	}
	
	@Test
	public void createPlayoffsLineCreatesLineForConferenceTeams() {
		String conferencePlayoffTeamsLine = 
				playoffSettings.createConferencePlayoffTeamsLine(playoffConference1);
		
		assertEquals("=Conf 1=Team 1 - 3 - 1,Team 1 - 4 - 1,Team 1 - 1 - 1,Team 1 - 2 - 1," +
				"Team 1 - 1 - 2,Team1 - 2 - 2", conferencePlayoffTeamsLine);
	}
	
}
