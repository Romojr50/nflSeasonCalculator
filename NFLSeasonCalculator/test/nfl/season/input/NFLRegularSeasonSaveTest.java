package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	private static String HOME_TEAM_1_NAME = "Home 1";
	
	private static String HOME_TEAM_2_NAME = "Home 2";
	
	private static String AWAY_TEAM_1_NAME = "Away 1";
	
	private static String AWAY_TEAM_2_NAME = "Away 2";

	private static String GAME_1_STRING = AWAY_TEAM_1_NAME + " " + HOME_TEAM_1_NAME + ",";
	
	private static String GAME_2_STRING = AWAY_TEAM_2_NAME + " " + HOME_TEAM_2_NAME + " " +
			AWAY_TEAM_2_NAME + ",";
	
	private static String WEEK_1_STRING = GAME_1_STRING + GAME_2_STRING + "\n";
	
	private static String WEEK_2_STRING = GAME_2_STRING + GAME_1_STRING + "\n";
	
	@Mock
	private SeasonGame seasonGame1;
	
	@Mock
	private SeasonGame seasonGame2;
	
	@Mock
	private NFLSeason season;
	
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
		
		week2Games = new ArrayList<SeasonGame>();
		week2Games.add(seasonGame2);
		week2Games.add(seasonGame1);
		when(week2.getSeasonGames()).thenReturn(week2Games);
		
		weeks = new SeasonWeek[2];
		weeks[0] = week1;
		weeks[1] = week2;
		when(season.getWeeks()).thenReturn(weeks);
		
		when(fileWriterFactory.createNFLSeasonSaveWriter()).thenReturn(fileWriter);
		
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
		
		String expectedString = WEEK_1_STRING + WEEK_2_STRING;
		
		assertEquals(expectedString, seasonString);
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
	public void saveToSettingsFileFailsButFileWriterIsStillClosed() throws IOException {
		boolean success = true;
		
		doThrow(new IOException()).when(fileWriter).write(any(byte[].class));
			
		success = seasonSave.saveToSeasonFile(season, fileWriterFactory);
		verify(fileWriter).close();
		assertFalse(success);
	}
	
}
