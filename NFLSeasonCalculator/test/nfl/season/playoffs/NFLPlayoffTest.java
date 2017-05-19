package nfl.season.playoffs;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.League;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLPlayoffTest {

	@Mock
	private League nfl;
	
	@Mock
	private Conference conference1;
	
	@Mock
	private Conference conference2;
	
	private List<Conference> conferences;
	
	private NFLPlayoffs playoffs;
	
	@Before
	public void setUp() {
		playoffs = new NFLPlayoffs(nfl);
		
		conferences = new ArrayList<Conference>();
		conferences.add(conference1);
		conferences.add(conference2);
		when(nfl.getConferences()).thenReturn(conferences);
	}
	
	@Test
	public void initializeNFLPlayoffsInitializesWithExpectedConferencesAndDivisions() {
		
	}
	
}
