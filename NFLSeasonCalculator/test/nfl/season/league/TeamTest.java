package nfl.season.league;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import nfl.season.league.Matchup.HomeAwayWinChanceModeEnum;
import nfl.season.league.Matchup.WinChanceModeEnum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TeamTest {

	private int defaultPowerRanking = 19;
	
	private int defaultHomeFieldAdvantage = 12;
	
	private int defaultEloRating = 1512;
	
	@Mock
	private Matchup matchup1;
	
	private String opponent1;
	
	@Mock
	private Matchup matchup2;
	
	private String opponent2;
	
	private String teamName;
	
	private Team team;
	
	@Before
	public void setUp() {
		teamName = "TestTeam";
		
		team = new Team(teamName, defaultPowerRanking, defaultEloRating, 
				defaultHomeFieldAdvantage);
		
		when(matchup1.getWinChanceMode()).thenReturn(WinChanceModeEnum.POWER_RANKINGS);
		when(matchup2.getWinChanceMode()).thenReturn(WinChanceModeEnum.ELO_RATINGS);
		
		when(matchup1.getHomeAwayWinChanceMode(teamName)).thenReturn(
				HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE);
		
		when(matchup1.getOpponentName(teamName)).thenReturn(opponent1);
		when(matchup2.getOpponentName(teamName)).thenReturn(opponent2);
		
		team.addMatchup(matchup1);
		team.addMatchup(matchup2);
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
		verify(matchup1, times(2)).calculateTeamWinChancesFromPowerRankings();
	}
	
	@Test
	public void setPowerRankingSoMatchupsBasedOnPowerRankingAreRecalculated() {
		team.setPowerRanking(13);
		
		verify(matchup1).calculateTeamWinChancesFromPowerRankings();
		verify(matchup1, never()).calculateHomeWinChanceFromHomeFieldAdvantage(teamName);
		verify(matchup2, never()).calculateTeamWinChancesFromPowerRankings();
		
		when(matchup1.getHomeAwayWinChanceMode(opponent1)).thenReturn(
				HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE);
		team.setPowerRanking(19);
		verify(matchup1, never()).calculateHomeWinChanceFromHomeFieldAdvantage(opponent1);
	}

	@Test
	public void setPowerRankingToClearSoMatchupsNotRecalculated() {
		team.setPowerRanking(Team.CLEAR_RANKING);
		
		verify(matchup1, never()).calculateTeamWinChancesFromPowerRankings();
		verify(matchup1, never()).calculateHomeWinChanceFromHomeFieldAdvantage(anyString());
		verify(matchup1).setWinChanceMode(WinChanceModeEnum.CUSTOM_SETTING);
		verify(matchup2, never()).setWinChanceMode(any(WinChanceModeEnum.class));
	}
	
	@Test
	public void setHomeFieldAdvantageSoHomeWinChancesAreRecalculated() {
		team.setHomeFieldAdvantage(21);
		
		verify(matchup1).calculateHomeWinChanceFromHomeFieldAdvantage(teamName);
		verify(matchup1, never()).calculateHomeWinChanceFromHomeFieldAdvantage(opponent1);
		verify(matchup2, never()).calculateHomeWinChanceFromHomeFieldAdvantage(anyString());
	}
	
	@Test
	public void setEloRatingSoMatchupsAreRecalculated() {
		team.setEloRating(1502);
		
		verify(matchup1, never()).calculateTeamWinChancesFromEloRatings();
		verify(matchup2).calculateTeamWinChancesFromEloRatings();
		
		when(matchup2.getHomeAwayWinChanceMode(teamName)).thenReturn(
				HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE);
		team.setEloRating(1578);
		verify(matchup2, never()).calculateHomeWinChanceFromHomeFieldAdvantage(teamName);
		
		when(matchup2.getHomeAwayWinChanceMode(opponent2)).thenReturn(
				HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE);
		team.setEloRating(1456);
		verify(matchup2, never()).calculateHomeWinChanceFromHomeFieldAdvantage(opponent2);
	}
	
	@Test
	public void calculateAllMatchupsUsingPowerRankingsGoesThroughAllMatchups() {
		team.calculateAllMatchupsUsingPowerRankings();
		
		verify(matchup1).calculateTeamWinChancesFromPowerRankings();
		verify(matchup2).calculateTeamWinChancesFromPowerRankings();
	}
	
	@Test
	public void calculateAllMatchupsUsingEloRatingsGoesThroughAllMatchups() {
		team.calculateAllMatchupsUsingEloRatings();
		
		verify(matchup1).calculateTeamWinChancesFromEloRatings();
		verify(matchup2).calculateTeamWinChancesFromEloRatings();
	}
	
}
