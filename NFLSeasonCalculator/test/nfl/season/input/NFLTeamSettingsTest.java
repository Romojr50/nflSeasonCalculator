package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Matchup;
import nfl.season.league.Matchup.HomeAwayWinChanceModeEnum;
import nfl.season.league.Matchup.WinChanceModeEnum;
import nfl.season.league.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLTeamSettingsTest {
	
	private static final String COLTS_LINE = "12,1542,9";
	
	private static final String COLTS_EAGLES_LINE = "-Eagles-56,P,63,C,51,H";
	
	private static final String COLTS_TEXANS_LINE = "-Texans-62,E,70,C,49,H";

	private static final String COLTS_CARDINALS_LINE = "-Cardinals-49,C,52,C,37,H";

	@Mock
	private Team colts;
	
	private String coltsName = "Colts";
	
	@Mock
	private Team eagles;
	
	private String eaglesName = "Eagles";
	
	@Mock
	private Team texans;
	
	private String texansName = "Texans";
	
	@Mock
	private Team cardinals;
	
	private String cardinalsName = "Cardinals";
	
	@Mock
	private Matchup coltsEaglesMatchup;
	
	@Mock
	private Matchup coltsTexansMatchup;
	
	@Mock
	private Matchup coltsCardinalsMatchup;
	
	private List<Matchup> matchups;
	
	private NFLTeamSettings nflTeamSettings;
	
	@Before
	public void setUp() {
		nflTeamSettings = new NFLTeamSettings();
		
		setUpTeamWithSettings(colts, 12, 1542, 9);
		when(colts.getName()).thenReturn(coltsName);
		
		setUpTeamWithSettings(eagles, 9, 1436, 12);
		setUpTeamWithSettings(texans, 11, 1576, -5);
		setUpTeamWithSettings(cardinals, 22, 1403, -12);
		
		setUpMatchupWithSettings(coltsEaglesMatchup, eaglesName, 56, 63, 51, 
				WinChanceModeEnum.POWER_RANKINGS);
		setUpMatchupWithSettings(coltsTexansMatchup, texansName, 62, 70, 49, 
				WinChanceModeEnum.ELO_RATINGS);
		setUpMatchupWithSettings(coltsCardinalsMatchup, cardinalsName, 49, 52, 37, 
				WinChanceModeEnum.CUSTOM_SETTING);
		matchups = new ArrayList<Matchup>();
		matchups.add(coltsEaglesMatchup);
		matchups.add(coltsTexansMatchup);
		matchups.add(coltsCardinalsMatchup);
		
		when(colts.getMatchups()).thenReturn(matchups);
	}

	private void setUpTeamWithSettings(Team team, int powerRanking, int eloRating, 
			int homeFieldAdvantage) {
		when(team.getPowerRanking()).thenReturn(powerRanking);
		when(team.getEloRating()).thenReturn(eloRating);
		when(team.getHomeFieldAdvantage()).thenReturn(homeFieldAdvantage);
	}
	
	private void setUpMatchupWithSettings(Matchup matchup, String opponentName, 
			int neutralWinChance, int homeWinChance, int awayWinChance, 
			WinChanceModeEnum neutralWinChanceMode) {
		when(matchup.getOpponentName(coltsName)).thenReturn(opponentName);
		when(matchup.getTeamNeutralWinChance(coltsName)).thenReturn(neutralWinChance);
		when(matchup.getWinChanceMode()).thenReturn(neutralWinChanceMode);
		when(matchup.getTeamHomeWinChance(coltsName)).thenReturn(homeWinChance);
		when(matchup.getHomeAwayWinChanceMode(coltsName)).thenReturn(
				HomeAwayWinChanceModeEnum.CUSTOM_SETTING);
		when(matchup.getTeamAwayWinChance(coltsName)).thenReturn(awayWinChance);
		when(matchup.getHomeAwayWinChanceMode(opponentName)).thenReturn(
				HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE);
	}
	
	@Test
	public void createTeamLineCreatesLineForTeamSettings() {
		String coltsLine = nflTeamSettings.createTeamLine(colts);
		String eaglesLine = nflTeamSettings.createTeamLine(eagles);
		String texansLine = nflTeamSettings.createTeamLine(texans);
		String cardinalsLine = nflTeamSettings.createTeamLine(cardinals);
		
		assertEquals(COLTS_LINE, coltsLine);
		assertEquals("9,1436,12", eaglesLine);
		assertEquals("11,1576,-5", texansLine);
		assertEquals("22,1403,-12", cardinalsLine);
	}
	
	@Test
	public void createMatchupLineCreatesLineForMatchupSettings() {
		String coltsEaglesLine = nflTeamSettings.createMatchupLine(coltsName, 
				coltsEaglesMatchup);
		String coltsTexansLine = nflTeamSettings.createMatchupLine(coltsName, 
				coltsTexansMatchup);
		String coltsCardinalsLine = nflTeamSettings.createMatchupLine(coltsName, 
				coltsCardinalsMatchup);
		
		assertEquals(COLTS_EAGLES_LINE, coltsEaglesLine);
		assertEquals(COLTS_TEXANS_LINE, coltsTexansLine);
		assertEquals(COLTS_CARDINALS_LINE, coltsCardinalsLine);
	}
	
	@Test
	public void createTeamSectionCreatesLinesForTeamAndItsMatchups() {
		String coltsSection = nflTeamSettings.createTeamSection(colts);
		
		String expectedColtsSection = "=" + coltsName + "=\n" + COLTS_LINE + "\n" + 
				COLTS_EAGLES_LINE + "\n" + COLTS_TEXANS_LINE + "\n" + 
				COLTS_CARDINALS_LINE + "\n";
		
		assertEquals(expectedColtsSection, coltsSection);
	}
	
}
