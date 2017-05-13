package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nfl.season.league.League;
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

	@Mock
	private Team colts;
	
	private static final String COLTS_NAME = "Colts";
	
	private static final String COLTS_LINE = "12,1542,9";
	
	private static final String COLTS_EAGLES_LINE = "-Eagles-56,P,63,C,51,H";
	
	private static final String COLTS_TEXANS_LINE = "-Texans-62,E,70,C,49,H";

	private static final String COLTS_CARDINALS_LINE = "-Cardinals-49,C,52,C,37,H";
	
	private static final String COLTS_SECTION = "=" + COLTS_NAME + "=\n" + COLTS_LINE + "\n" + 
			COLTS_EAGLES_LINE + "\n" + COLTS_TEXANS_LINE + "\n" + 
			COLTS_CARDINALS_LINE + "\n";
	
	@Mock
	private Team eagles;
	
	private static final String EAGLES_NAME = "Eagles";
	
	private static final String EAGLES_LINE = "9,1436,12";
	
	private static final String EAGLES_COLTS_LINE = "-Colts-44,P,49,C,37,H";
	
	private static final String EAGLES_TEXANS_LINE = "-Texans-59,E,89,C,43,H";
	
	private static final String EAGLES_CARDINALS_LINE = "-Cardinals-1,C,5,C,3,H";
	
	private static final String EAGLES_SECTION = "=" + EAGLES_NAME + "=\n" + EAGLES_LINE + "\n" + 
			EAGLES_COLTS_LINE + "\n" + EAGLES_TEXANS_LINE + "\n" + 
			EAGLES_CARDINALS_LINE + "\n";
	
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
	
	@Mock
	private Matchup eaglesColtsMatchup;
	
	@Mock
	private Matchup eaglesTexansMatchup;
	
	@Mock
	private Matchup eaglesCardinalsMatchup;
	
	private List<Matchup> coltsMatchups;
	
	private List<Matchup> eaglesMatchups;
	
	@Mock
	private League league;
	
	private List<Team> leagueTeamList;
	
	@Mock
	private FileOutputStream fileWriter;
	
	@Mock
	private NFLTeamSettingsFileWriterFactory fileWriterFactory;
	
	private NFLTeamSettings nflTeamSettings;
	
	@Before
	public void setUp() throws FileNotFoundException {
		nflTeamSettings = new NFLTeamSettings();
		
		setUpTeamWithSettings(colts, 12, 1542, 9);
		when(colts.getName()).thenReturn(COLTS_NAME);
		
		setUpTeamWithSettings(eagles, 9, 1436, 12);
		when(eagles.getName()).thenReturn(EAGLES_NAME);
		
		setUpTeamWithSettings(texans, 11, 1576, -5);
		setUpTeamWithSettings(cardinals, 22, 1403, -12);
		
		setUpMatchups();
		
		leagueTeamList = new ArrayList<Team>();
		leagueTeamList.add(colts);
		leagueTeamList.add(eagles);
		when(league.getTeams()).thenReturn(leagueTeamList);
		
		when(fileWriterFactory.createNFLTeamSettingsWriter()).thenReturn(fileWriter);
	}

	private void setUpTeamWithSettings(Team team, int powerRanking, int eloRating, 
			int homeFieldAdvantage) {
		when(team.getPowerRanking()).thenReturn(powerRanking);
		when(team.getEloRating()).thenReturn(eloRating);
		when(team.getHomeFieldAdvantage()).thenReturn(homeFieldAdvantage);
	}
	
	private void setUpMatchupWithSettings(Matchup matchup, String teamName, 
			String opponentName, int neutralWinChance, int homeWinChance, int awayWinChance, 
			WinChanceModeEnum neutralWinChanceMode) {
		when(matchup.getOpponentName(teamName)).thenReturn(opponentName);
		when(matchup.getTeamNeutralWinChance(teamName)).thenReturn(neutralWinChance);
		when(matchup.getWinChanceMode()).thenReturn(neutralWinChanceMode);
		when(matchup.getTeamHomeWinChance(teamName)).thenReturn(homeWinChance);
		when(matchup.getHomeAwayWinChanceMode(teamName)).thenReturn(
				HomeAwayWinChanceModeEnum.CUSTOM_SETTING);
		when(matchup.getTeamAwayWinChance(teamName)).thenReturn(awayWinChance);
		when(matchup.getHomeAwayWinChanceMode(opponentName)).thenReturn(
				HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE);
	}
	
	private void setUpMatchups() {
		setUpMatchupWithSettings(coltsEaglesMatchup, COLTS_NAME, EAGLES_NAME, 56, 
				63, 51, WinChanceModeEnum.POWER_RANKINGS);
		setUpMatchupWithSettings(coltsTexansMatchup, COLTS_NAME, texansName, 62, 
				70, 49, WinChanceModeEnum.ELO_RATINGS);
		setUpMatchupWithSettings(coltsCardinalsMatchup, COLTS_NAME, cardinalsName, 
				49, 52, 37, WinChanceModeEnum.CUSTOM_SETTING);
		
		setUpMatchupWithSettings(eaglesColtsMatchup, EAGLES_NAME, COLTS_NAME, 44, 
				49, 37, WinChanceModeEnum.POWER_RANKINGS);
		setUpMatchupWithSettings(eaglesTexansMatchup, EAGLES_NAME, texansName, 59, 
				89, 43, WinChanceModeEnum.ELO_RATINGS);
		setUpMatchupWithSettings(eaglesCardinalsMatchup, EAGLES_NAME, cardinalsName, 
				1, 5, 3, WinChanceModeEnum.CUSTOM_SETTING);
		
		coltsMatchups = new ArrayList<Matchup>();
		coltsMatchups.add(coltsEaglesMatchup);
		coltsMatchups.add(coltsTexansMatchup);
		coltsMatchups.add(coltsCardinalsMatchup);
		
		when(colts.getMatchups()).thenReturn(coltsMatchups);
		
		eaglesMatchups = new ArrayList<Matchup>();
		eaglesMatchups.add(eaglesColtsMatchup);
		eaglesMatchups.add(eaglesTexansMatchup);
		eaglesMatchups.add(eaglesCardinalsMatchup);
		
		when(eagles.getMatchups()).thenReturn(eaglesMatchups);
	}
	
	@Test
	public void createTeamLineCreatesLineForTeamSettings() {
		String coltsLine = nflTeamSettings.createTeamLine(colts);
		String eaglesLine = nflTeamSettings.createTeamLine(eagles);
		String texansLine = nflTeamSettings.createTeamLine(texans);
		String cardinalsLine = nflTeamSettings.createTeamLine(cardinals);
		
		assertEquals(COLTS_LINE, coltsLine);
		assertEquals(EAGLES_LINE, eaglesLine);
		assertEquals("11,1576,-5", texansLine);
		assertEquals("22,1403,-12", cardinalsLine);
	}
	
	@Test
	public void createMatchupLineCreatesLineForMatchupSettings() {
		String coltsEaglesLine = nflTeamSettings.createMatchupLine(COLTS_NAME, 
				coltsEaglesMatchup);
		String coltsTexansLine = nflTeamSettings.createMatchupLine(COLTS_NAME, 
				coltsTexansMatchup);
		String coltsCardinalsLine = nflTeamSettings.createMatchupLine(COLTS_NAME, 
				coltsCardinalsMatchup);
		
		String eaglesColtsLine = nflTeamSettings.createMatchupLine(EAGLES_NAME, 
				eaglesColtsMatchup);
		String eaglesTexansLine = nflTeamSettings.createMatchupLine(EAGLES_NAME, 
				eaglesTexansMatchup);
		String eaglesCardinalsLine = nflTeamSettings.createMatchupLine(EAGLES_NAME, 
				eaglesCardinalsMatchup);
		
		assertEquals(COLTS_EAGLES_LINE, coltsEaglesLine);
		assertEquals(COLTS_TEXANS_LINE, coltsTexansLine);
		assertEquals(COLTS_CARDINALS_LINE, coltsCardinalsLine);
		
		assertEquals(EAGLES_COLTS_LINE, eaglesColtsLine);
		assertEquals(EAGLES_TEXANS_LINE, eaglesTexansLine);
		assertEquals(EAGLES_CARDINALS_LINE, eaglesCardinalsLine);
	}
	
	@Test
	public void createTeamSectionCreatesLinesForTeamAndItsMatchups() {
		String coltsSection = nflTeamSettings.createTeamSection(colts);
		String eaglesSection = nflTeamSettings.createTeamSection(eagles);
		
		assertEquals(COLTS_SECTION, coltsSection);
		assertEquals(EAGLES_SECTION, eaglesSection);
	}
	
	@Test
	public void createTeamSettingsFileStringCombinesSectionsForEveryTeam() {
		String expectedTeamSettingsFileString = COLTS_SECTION + EAGLES_SECTION;
		
		String teamSettingsFileString = nflTeamSettings.createTeamSettingsFileString(league);
		
		assertEquals(expectedTeamSettingsFileString, teamSettingsFileString);
	}
	
	@Test
	public void saveToSettingsFileWritesAllSettingsToFile() {
		String teamSettingsFileString = nflTeamSettings.createTeamSettingsFileString(league);
		
		nflTeamSettings.saveToSettingsFile(league, fileWriterFactory);
		
		try {
			verify(fileWriterFactory).createNFLTeamSettingsWriter();
			verify(fileWriter).write(teamSettingsFileString.getBytes());
			verify(fileWriter).close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
