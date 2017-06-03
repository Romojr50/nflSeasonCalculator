package nfl.season.season;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.League;
import nfl.season.playoffs.NFLPlayoffConference;
import nfl.season.playoffs.NFLPlayoffDivision;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class NFLSeasonTest {

	private SeasonGame seasonGame1;
	
	private SeasonGame seasonGame2;
	
	private SeasonGame seasonGame3;
	
	private List<SeasonGame> seasonGames;
	
	@Mock
	private SeasonWeek week;
	
	@Mock
	private League league;
	
	private List<Conference> conferences;
	
	@Mock
	private Conference conference1;
	
	@Mock
	private Conference conference2;
	
	private List<Division> conference1Divisions;
	
	@Mock
	private Division division1_1;
		
	@Mock
	private Division division1_2;
	
	private List<Division> conference2Divisions;
	
	@Mock
	private Division division2_1;
	
	@Mock
	private Division division2_2;
	
	private NFLSeason season;
	
	@Before
	public void setUp() {
		season = new NFLSeason();
		
		seasonGames = new ArrayList<SeasonGame>();
		seasonGames.add(seasonGame1);
		seasonGames.add(seasonGame2);
		seasonGames.add(seasonGame3);
		when(week.getSeasonGames()).thenReturn(seasonGames);
		
		conferences = new ArrayList<Conference>();
		conferences.add(conference1);
		conferences.add(conference2);
		when(league.getConferences()).thenReturn(conferences);
		
		conference1Divisions = new ArrayList<Division>();
		conference1Divisions.add(division1_1);
		conference1Divisions.add(division1_2);
		when(conference1.getDivisions()).thenReturn(conference1Divisions);
		
		conference2Divisions = new ArrayList<Division>();
		conference2Divisions.add(division2_1);
		conference2Divisions.add(division2_2);
		when(conference2.getDivisions()).thenReturn(conference2Divisions);
	}
	
	@Test
	public void seasonIsInitiatedWithAllTeams() {
		season.initializeNFLRegularSeason(league);
		
		List<NFLSeasonConference> seasonConferences = season.getConferences();
		
		List<Conference> returnedConferences = new ArrayList<Conference>();
		List<Division> returnedDivisions = new ArrayList<Division>();
		for (NFLSeasonConference seasonConference : seasonConferences) {
			returnedConferences.add(seasonConference.getConference());
			
			List<NFLSeasonDivision> seasonDivisions = seasonConference.getDivisions();
			for (NFLSeasonDivision seasonDivision : seasonDivisions) {
				returnedDivisions.add(seasonDivision.getDivision());
			}
		}
		assertTrue(returnedConferences.contains(conference1));
		assertTrue(returnedConferences.contains(conference2));
		
		assertTrue(returnedDivisions.contains(division1_1));
		assertTrue(returnedDivisions.contains(division1_2));
		assertTrue(returnedDivisions.contains(division2_1));
		assertTrue(returnedDivisions.contains(division2_2));
	}
	
	@Test
	public void addWeekToSeasonAddsWeekToSeasonAndTeamSchedules() {
//		season.addWeek(week);
//		
//		List<SeasonWeek> weeks = season.getWeeks();
//		assertEquals(1, weeks.size());
	}
	
}
