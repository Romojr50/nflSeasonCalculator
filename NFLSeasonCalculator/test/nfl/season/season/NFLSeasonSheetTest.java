package nfl.season.season;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import nfl.season.input.NFLFileWriterFactory;
import nfl.season.league.Team;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonSheetTest {

	@Mock
	private NFLSeasonTeam mockSeasonTeam;
	
	@Mock
	private Team mockTeam;
	
	private List<NFLSeasonTeam> seasonTeamList;
	
	@Mock
	private NFLSeasonDivision mockDivision;
	
	private List<NFLSeasonDivision> seasonDivisionList;
	
	@Mock
	private NFLSeasonConference mockConference;
	
	private List<NFLSeasonConference> seasonConferenceList;
	
	@Mock
	private NFLSeason mockSeason;
	
	@Mock
	private NFLFileWriterFactory mockFileWriterFactory;
	
	@Mock
	private FileOutputStream mockFileWriter;
	
	private final int numberOfSeasons = 700;
	
	private final int numberOfTeamsPerDivision = 4;
	
	private String expectedTeamRow;
	
	private NFLSeasonSheet seasonSheet;
	
	@Before
	public void beforeEach() throws FileNotFoundException {
		seasonTeamList = new ArrayList<NFLSeasonTeam>();
		seasonTeamList.add(mockSeasonTeam);
		seasonTeamList.add(mockSeasonTeam);
		seasonTeamList.add(mockSeasonTeam);
		seasonTeamList.add(mockSeasonTeam);
		when(mockDivision.getTeams()).thenReturn(seasonTeamList);
		
		seasonDivisionList = new ArrayList<NFLSeasonDivision>();
		seasonDivisionList.add(mockDivision);
		seasonDivisionList.add(mockDivision);
		seasonDivisionList.add(mockDivision);
		seasonDivisionList.add(mockDivision);
		when(mockConference.getDivisions()).thenReturn(seasonDivisionList);
		
		seasonConferenceList = new ArrayList<NFLSeasonConference>();
		seasonConferenceList.add(mockConference);
		seasonConferenceList.add(mockConference);
		when(mockSeason.getConferences()).thenReturn(seasonConferenceList);
		
		when(mockFileWriterFactory.createNFLSeasonEstimatesWriter(anyString())).thenReturn(mockFileWriter);
		
		StringBuilder expectedTeamRowBuilder = new StringBuilder();
		expectedTeamRowBuilder.append("MockTeam,");
		expectedTeamRowBuilder.append("10,");
		expectedTeamRowBuilder.append("6,");
		expectedTeamRowBuilder.append("5,");
		expectedTeamRowBuilder.append("8,");
		expectedTeamRowBuilder.append("70,");
		expectedTeamRowBuilder.append("66,");
		expectedTeamRowBuilder.append("49,");
		expectedTeamRowBuilder.append("30,");
		expectedTeamRowBuilder.append("18,");
		expectedTeamRowBuilder.append("68,");
		expectedTeamRowBuilder.append("34,");
		expectedTeamRowBuilder.append("17,");
		expectedTeamRowBuilder.append("8\n");
		expectedTeamRow = expectedTeamRowBuilder.toString();
		
		setUpMockTeam();
		
		seasonSheet = new NFLSeasonSheet(mockFileWriterFactory);
	}
	
	@Test
	public void seasonSheetCreatesAHeaderRow() {
		String headerRow = seasonSheet.createHeaderRow();
		String expectedHeader = buildExpectedHeaderRow();
		assertEquals(expectedHeader, headerRow);
	}
	
	@Test
	public void seasonSheetCreatesATeamRow() {
		String teamRow = seasonSheet.createTeamRow(mockSeasonTeam, numberOfSeasons);
		
		assertEquals(expectedTeamRow, teamRow);
	}
	
	@Test
	public void seasonSheetCreatesRowsForTeamsInDivision() {
		int numberOfTeams = 4;
		
		String expectedDivisionString = "";
		for (int i = 0; i < numberOfTeams; i++) {
			expectedDivisionString = expectedDivisionString + expectedTeamRow;
		}
		
		String divisionString = seasonSheet.createDivisionRows(mockDivision, numberOfSeasons);
		
		assertEquals(expectedDivisionString, divisionString);
	}
	
	@Test
	public void seasonSheetCreatesRowsForTeamsInConference() {
		int numberOfDivisions = 4;
		
		String expectedConferenceString = buildExpectedStringFromDivisionsAndTeams(numberOfDivisions);
		
		String conferenceString = seasonSheet.createConferenceRows(mockConference, numberOfSeasons);
		
		assertEquals(expectedConferenceString, conferenceString);
	}

	@Test
	public void seasonSheetCreatesRowsForTeamsInLeague() {
		int numberOfConferences = 2;
		int numberOfDivisions = 4 * numberOfConferences;
		
		String expectedLeagueString = buildExpectedStringFromDivisionsAndTeams(numberOfDivisions);
		
		String leagueString = seasonSheet.createLeagueRows(mockSeason, numberOfSeasons);
		
		assertEquals(expectedLeagueString, leagueString);
	}
	
	@Test
	public void seasonSheetCreatesAFileWithSeasonEstimates() throws IOException {
		int numberOfConferences = 2;
		int numberOfDivisions = 4 * numberOfConferences;
		String expectedLeagueString = buildExpectedHeaderRow();
		expectedLeagueString = expectedLeagueString + 
				buildExpectedStringFromDivisionsAndTeams(numberOfDivisions);
		
		String folderPath = "someFolder";
		boolean success = seasonSheet.createSeasonEstimatesFile(folderPath, mockSeason, numberOfSeasons);
		
		verify(mockFileWriterFactory).createNFLSeasonEstimatesWriter(folderPath);
		verify(mockFileWriter).write(expectedLeagueString.getBytes());
		verify(mockFileWriter).close();
		
		assertTrue(success);
	}

	private void setUpMockTeam() {
		when(mockSeasonTeam.getTeam()).thenReturn(mockTeam);
		when(mockTeam.getName()).thenReturn("MockTeam");
		when(mockSeasonTeam.getSimulatedWins()).thenReturn(10 * numberOfSeasons);
		when(mockSeasonTeam.getSimulatedLosses()).thenReturn(6 * numberOfSeasons);
		when(mockSeasonTeam.getWasBottomTeam()).thenReturn((int) (0.05 * numberOfSeasons));
		when(mockSeasonTeam.getWasInDivisionCellar()).thenReturn((int) (0.08 * numberOfSeasons));
		when(mockSeasonTeam.getHadWinningSeason()).thenReturn((int) (7 * numberOfSeasons / 10));
		when(mockSeasonTeam.getMadePlayoffs()).thenReturn((int) (0.66 * numberOfSeasons));
		when(mockSeasonTeam.getWonDivision()).thenReturn((int) (0.49 * numberOfSeasons));
		when(mockSeasonTeam.getGotRoundOneBye()).thenReturn((int) (0.30 * numberOfSeasons));
		when(mockSeasonTeam.getGotOneSeed()).thenReturn((int) (0.18 * numberOfSeasons));
		when(mockSeasonTeam.getChanceToMakeDivisionalRound()).thenReturn(68 * numberOfSeasons);
		when(mockSeasonTeam.getChanceToMakeConferenceRound()).thenReturn(34 * numberOfSeasons);
		when(mockSeasonTeam.getChanceToWinConference()).thenReturn(17 * numberOfSeasons);
		when(mockSeasonTeam.getChanceToWinSuperBowl()).thenReturn(8 * numberOfSeasons);
	}
	
	private String buildExpectedHeaderRow() {
		StringBuilder expectedHeader = new StringBuilder();
		expectedHeader.append(NFLSeasonSheet.COLUMN_TEAM + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_AVERAGE_WINS + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_AVERAGE_LOSSES + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_BOTTOM_5 + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_DIVISION_LAST + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_WINNING_SEASON + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_PLAYOFFS + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_DIVISION_CHAMPS + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_ROUND_1_BYE + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_NUMBER_1_SEED + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_DIVISIONAL_ROUND + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_CONFERENCE_ROUND + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_CONFERENCE_CHAMPS + ",");
		expectedHeader.append(NFLSeasonSheet.COLUMN_SUPER_BOWL_CHAMPS);
		expectedHeader.append('\n');
		return expectedHeader.toString();
	}
	
	private String buildExpectedStringFromDivisionsAndTeams(int numberOfDivisions) {
		String expectedString = "";
		for (int i = 0; i < numberOfDivisions; i++) {
			for (int j = 0; j < numberOfTeamsPerDivision; j++) {
				expectedString = expectedString + expectedTeamRow;
			}
			expectedString = expectedString + "\n";
		}
		return expectedString;
	}
	
}
