package nfl.season.league;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TeamTest {

	private int defaultPowerRanking = 19;
	
	private int defaultHomeFieldAdvantage = 12;
	
	private int defaultEloRating = 1512;
	
	private Team team;
	
	@Before
	public void setUp() {
		team = new Team("TestTeam", defaultPowerRanking, defaultEloRating, 
				defaultHomeFieldAdvantage);
	}
	
	@Test
	public void resetToDefaultsResetsTeamSettingsToDefaults() {
		team.setHomeFieldAdvantage(29);
		team.setEloRating(1900);
		team.setPowerRanking(14);
		
		team.resetToDefaults();
		
		assertEquals(defaultPowerRanking, team.getPowerRanking());
		assertEquals(defaultEloRating, team.getEloRating());
		assertEquals(defaultHomeFieldAdvantage, team.getHomeFieldAdvantage());
	}
	
}
