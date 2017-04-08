package nfl.season.league;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LeagueTest {

	@Test
	public void leagueIsInitializedWithNFLConferencesDivisionsAndTeams() {
		League nfl = new League(League.NFL);
		assertEquals(League.NFL, nfl.getName());
		
		nfl.initializeNFL();
		
		
	}
	
}
