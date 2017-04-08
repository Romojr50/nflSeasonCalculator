package nfl.season.league;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nfl.season.league.enums.NFLConferenceEnum;
import nfl.season.league.enums.NFLDivisionEnum;

import org.junit.Test;

public class LeagueTest {

	@Test
	public void leagueIsInitializedWithNFLConferencesDivisionsAndTeams() {
		League nfl = new League(League.NFL);
		assertEquals(League.NFL, nfl.getName());
		
		nfl.initializeNFL();
		
		Conference afc = nfl.getConference(NFLConferenceEnum.AFC.name());
		assertNotNull(afc);
		assertConferenceHasCorrectDivisions(afc);
		
		Conference nfc = nfl.getConference(NFLConferenceEnum.NFC.name());
		assertNotNull(nfc);
		assertConferenceHasCorrectDivisions(nfc);
	}

	private void assertConferenceHasCorrectDivisions(Conference conference) {
		Division east = conference.getDivision(NFLDivisionEnum.EAST.name());
		assertNotNull(east);
		Division north = conference.getDivision(NFLDivisionEnum.NORTH.name());
		assertNotNull(north);
		Division south = conference.getDivision(NFLDivisionEnum.SOUTH.name());
		assertNotNull(south);
		Division west = conference.getDivision(NFLDivisionEnum.WEST.name());
		assertNotNull(west);
	}
	
}
