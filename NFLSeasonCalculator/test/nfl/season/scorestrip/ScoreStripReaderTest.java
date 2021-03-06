package nfl.season.scorestrip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ScoreStripReaderTest {

	private ScoreStripReader scoreStripReader;
	
	@Before
	public void setUp() {
		scoreStripReader = new ScoreStripReader();
	}
	
	@Test
	public void scoreStripReaderReadsXMLFromURLAndPutsInPOJOs() throws MalformedURLException {
		Ss scoreStripReturn = scoreStripReader.readScoreStripURL(
				"http://www.nfl.com/ajax/scorestrip?season=2014&seasonType=REG&week=1");
		
		assertNotNull(scoreStripReturn);
		Gms scoreStripGames = scoreStripReturn.getGms();
		assertNotNull(scoreStripGames);
		List<G> gameList = scoreStripGames.getG();
		assertNotNull(gameList);
		assertFalse(gameList.size() == 0);
		
		G falconsGame = null;
		for (G game : gameList) {
			String homeTeam = game.getHnn();
			if ("Falcons".equalsIgnoreCase(homeTeam)) {
				falconsGame = game;
				break;
			}
		}
		
		assertNotNull(falconsGame);
		assertEquals("saints", falconsGame.getVnn());
		assertEquals("FO", falconsGame.getQ());
		
		String falconsScore = "37";
		assertEquals(falconsScore, falconsGame.getHs());
	}
	
	@Test(expected=MalformedURLException.class)
	public void scoreStripReaderThrowsExceptionWithBadURL() throws MalformedURLException {
		scoreStripReader.readScoreStripURL("What is this?");
	}
	
	@Test
	public void scoreStripReaderDoesNotGoToRightURLSoNullObjectReturned() throws MalformedURLException {
		Ss scoreStripReturn = scoreStripReader.readScoreStripURL("http://www.google.com");
		assertNull(scoreStripReturn);
	}
	
	@Test
	public void generateScoreStripURLUsesYearAndWeekNumberToGenerateURL() {
		String url = scoreStripReader.generateScoreStripURL("2017", 3);
		assertEquals("http://www.nfl.com/ajax/scorestrip?season=2017&seasonType=REG&week=3", url);
		
		url = scoreStripReader.generateScoreStripURL("2017", 5);
		assertEquals("http://www.nfl.com/ajax/scorestrip?season=2017&seasonType=REG&week=5", url);
		
		url = scoreStripReader.generateScoreStripURL("2014", 12);
		assertEquals("http://www.nfl.com/ajax/scorestrip?season=2014&seasonType=REG&week=12", url);
	}
	
}
