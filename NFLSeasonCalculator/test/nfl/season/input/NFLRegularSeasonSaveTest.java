package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nfl.season.league.League;
import nfl.season.league.Team;
import nfl.season.season.NFLSeason;
import nfl.season.season.SeasonGame;
import nfl.season.season.SeasonWeek;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLRegularSeasonSaveTest {
	
	private static String HOME_TEAM_1_NAME = "Home1";
	
	private static String HOME_TEAM_2_NAME = "Home2";
	
	private static String AWAY_TEAM_1_NAME = "Away1";
	
	private static String AWAY_TEAM_2_NAME = "Away2";

	private static String GAME_1_STRING = AWAY_TEAM_1_NAME + " " + HOME_TEAM_1_NAME + ",";
	
	private static String GAME_2_STRING = AWAY_TEAM_2_NAME + " " + HOME_TEAM_2_NAME + " " +
			AWAY_TEAM_2_NAME + ",";
	
	private static String WEEK_1_STRING = "1," + GAME_1_STRING + GAME_2_STRING + "\n";
	
	private static String WEEK_2_STRING = "2," + GAME_2_STRING + GAME_1_STRING + "\n";
	
	private static String SEASON_STRING = WEEK_1_STRING + WEEK_2_STRING;
	
	@Mock
	private SeasonGame seasonGame1;
	
	@Mock
	private SeasonGame seasonGame2;
	
	@Mock
	private NFLSeason season;
	
	@Mock
	private League league;
	
	private SeasonWeek[] weeks;
	
	@Mock
	private SeasonWeek week1;
	
	private List<SeasonGame> week1Games;
	
	@Mock
	private SeasonWeek week2;
	
	private List<SeasonGame> week2Games;
	
	@Mock
	private Team homeTeam1;
	
	@Mock
	private Team homeTeam2;
	
	@Mock
	private Team awayTeam1;
	
	@Mock
	private Team awayTeam2;
	
	@Mock
	private FileOutputStream fileWriter;
	
	@Mock
	private NFLFileWriterFactory fileWriterFactory;
	
	@Mock
	private BufferedReader fileReader;
	
	@Mock
	private NFLFileReaderFactory fileReaderFactory;
	
	private NFLRegularSeasonSave seasonSave;
	
	@Before
	public void setUp() throws FileNotFoundException {
		when(seasonGame1.getHomeTeam()).thenReturn(homeTeam1);
		when(seasonGame1.getAwayTeam()).thenReturn(awayTeam1);
		
		when(homeTeam1.getName()).thenReturn(HOME_TEAM_1_NAME);
		when(awayTeam1.getName()).thenReturn(AWAY_TEAM_1_NAME);
		
		when(seasonGame2.getHomeTeam()).thenReturn(homeTeam2);
		when(seasonGame2.getAwayTeam()).thenReturn(awayTeam2);
		when(seasonGame2.getWinner()).thenReturn(awayTeam2);
		
		when(homeTeam2.getName()).thenReturn(HOME_TEAM_2_NAME);
		when(awayTeam2.getName()).thenReturn(AWAY_TEAM_2_NAME);
		
		week1Games = new ArrayList<SeasonGame>();
		week1Games.add(seasonGame1);
		week1Games.add(seasonGame2);
		when(week1.getSeasonGames()).thenReturn(week1Games);
		when(week1.getWeekNumber()).thenReturn(1);
		
		week2Games = new ArrayList<SeasonGame>();
		week2Games.add(seasonGame2);
		week2Games.add(seasonGame1);
		when(week2.getSeasonGames()).thenReturn(week2Games);
		when(week2.getWeekNumber()).thenReturn(2);
		
		weeks = new SeasonWeek[2];
		weeks[0] = week1;
		weeks[1] = week2;
		when(season.getWeeks()).thenReturn(weeks);
		when(season.getLeague()).thenReturn(league);
		
		when(league.getTeam(HOME_TEAM_1_NAME)).thenReturn(homeTeam1);
		when(league.getTeam(AWAY_TEAM_1_NAME)).thenReturn(awayTeam1);
		when(league.getTeam(HOME_TEAM_2_NAME)).thenReturn(homeTeam2);
		when(league.getTeam(AWAY_TEAM_2_NAME)).thenReturn(awayTeam2);
		
		when(fileWriterFactory.createNFLSeasonSaveWriter()).thenReturn(fileWriter);
		when(fileWriterFactory.createNFLSeasonSaveWriter(anyString())).thenReturn(fileWriter);
		when(fileReaderFactory.createNFLSeasonSaveReader()).thenReturn(fileReader);
		when(fileReaderFactory.createNFLSeasonSaveReader(anyString())).thenReturn(fileReader);
		
		seasonSave = new NFLRegularSeasonSave();
	}
	
	@Test
	public void createGameStringCreatesAStringFromAGame() {
		String gameString = seasonSave.getGameString(seasonGame1);
		
		assertEquals(GAME_1_STRING, gameString);
		
		when(seasonGame1.getWinner()).thenReturn(homeTeam1);
		
		gameString = seasonSave.getGameString(seasonGame1);
		
		String expectedGame = AWAY_TEAM_1_NAME + " " + HOME_TEAM_1_NAME + " " + 
				HOME_TEAM_1_NAME + ",";
		
		assertEquals(expectedGame, gameString);
	}
	
	@Test
	public void createWeekStringCreatesAStringFromAWeek() {
		String weekString = seasonSave.getWeekString(week1);
		
		assertEquals(WEEK_1_STRING, weekString);
	}
	
	@Test
	public void createSeasonStringCreatesAStringFromASeason() {
		String seasonString = seasonSave.getSeasonString(season);
		
		assertEquals(SEASON_STRING, seasonString);
	}
	
	@Test
	public void saveToSeasonFileWritesAllWeeksToFile() throws IOException {
		String seasonString = seasonSave.getSeasonString(season);
		
		boolean success = seasonSave.saveToSeasonFile(season, fileWriterFactory);
		
		verify(fileWriterFactory).createNFLSeasonSaveWriter();
		verify(fileWriter).write(seasonString.getBytes());
		verify(fileWriter).close();
		
		assertTrue(success);
	}
	
	@Test
	public void saveToSeasonFileWritesAllWeeksToFileOnFilepath() throws IOException {
		String folderPath = "file";
		String seasonString = seasonSave.getSeasonString(season);
		
		boolean success = seasonSave.saveToSeasonFile(season, fileWriterFactory, folderPath);
		
		verify(fileWriterFactory).createNFLSeasonSaveWriter(folderPath);
		verify(fileWriter).write(seasonString.getBytes());
		verify(fileWriter).close();
		
		assertTrue(success);
	}
	
	@Test
	public void saveToSettingsFileFailsButFileWriterIsStillClosed() throws IOException {
		boolean success = true;
		
		doThrow(new IOException()).when(fileWriter).write(any(byte[].class));
			
		success = seasonSave.saveToSeasonFile(season, fileWriterFactory);
		verify(fileWriter).close();
		assertFalse(success);
	}
	
	@Test
	public void createGameFromGameStringUsesStringToCreateGame() {
		SeasonGame seasonGame = seasonSave.createGameFromGameString(GAME_1_STRING, league);
		
		assertEquals(homeTeam1, seasonGame.getHomeTeam());
		assertEquals(awayTeam1, seasonGame.getAwayTeam());
		
		seasonGame = seasonSave.createGameFromGameString(GAME_2_STRING, league);
		
		assertEquals(homeTeam2, seasonGame.getHomeTeam());
		assertEquals(awayTeam2, seasonGame.getAwayTeam());
		assertEquals(awayTeam2, seasonGame.getWinner());
	}
	
	@Test
	public void createWeekFromWeekStringUsesStringToCreateWeek() {
		SeasonWeek week = seasonSave.createWeekFromWeekString(WEEK_1_STRING, league);
		
		List<SeasonGame> weekGames = week.getSeasonGames();
		assertEquals(2, weekGames.size());
		
		SeasonGame weekGame1 = weekGames.get(0);
		SeasonGame weekGame2 = weekGames.get(1);
		
		assertEquals(homeTeam1, weekGame1.getHomeTeam());
		assertEquals(awayTeam1, weekGame1.getAwayTeam());
		assertEquals(homeTeam2, weekGame2.getHomeTeam());
		assertEquals(awayTeam2, weekGame2.getAwayTeam());
	}
	
	@Test
	public void populateSeasonFromSeasonStringUsesStringToPopulateSeason() {
		seasonSave.populateSeasonFromSeasonString(SEASON_STRING, season);
		
		verify(season, times(2)).addWeek(any(SeasonWeek.class));
	}
	
	@Test
	public void loadSeasonSaveFileReadsFromSeasonSaveFile() {
		String[] loadedSeasonLines = new String[3];
		loadedSeasonLines[0] = "This is the first line";
		loadedSeasonLines[1] = "Another line!";
		
		String expectedTeamSettingsFileString = loadedSeasonLines[0] + 
				"\n" + loadedSeasonLines[1] + "\n";
		
		try {
			when(fileReader.readLine()).thenReturn(loadedSeasonLines[0], 
					loadedSeasonLines[1], loadedSeasonLines[2], null);
			String returnedTeamSettingsFileString = seasonSave.loadSeasonSave(
					fileReaderFactory);
			
			verify(fileReaderFactory).createNFLSeasonSaveReader();
			verify(fileReader).close();
			assertEquals(returnedTeamSettingsFileString, expectedTeamSettingsFileString);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}
	
	@Test
	public void loadSeasonSaveFileReadsFromSeasonSaveFileAtFolderPath() {
		String[] loadedSeasonLines = new String[3];
		loadedSeasonLines[0] = "This is the first line";
		loadedSeasonLines[1] = "Another line!";
		
		String expectedTeamSettingsFileString = loadedSeasonLines[0] + 
				"\n" + loadedSeasonLines[1] + "\n";
		
		try {
			when(fileReader.readLine()).thenReturn(loadedSeasonLines[0], 
					loadedSeasonLines[1], loadedSeasonLines[2], null);
			String returnedTeamSettingsFileString = seasonSave.loadSeasonSave(
					fileReaderFactory, "folder");
			
			verify(fileReaderFactory).createNFLSeasonSaveReader("folder");
			verify(fileReader).close();
			assertEquals(returnedTeamSettingsFileString, expectedTeamSettingsFileString);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
	}
	
	@Test
	public void loadSeasonSaveFileFailsButFileReaderIsStillClosed() throws IOException {
		try {
			when(fileReader.readLine()).thenThrow(new IOException());
			
			seasonSave.loadSeasonSave(fileReaderFactory);
			
		} catch (IOException e) {
			verify(fileReader).close();
		}
	}
	
}
