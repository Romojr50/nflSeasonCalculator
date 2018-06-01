package nfl.season.season;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import nfl.season.league.Team;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonSheetTest {

	@Mock
	private Sheet mockSheet;
	
	@Mock
	private Row headerRow;
	
	@Mock
	private Row teamRow;
	
	@Mock
	private Row emptyRow;
	
	@Mock
	private Cell mockCell;
	
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
	
	private final int numberOfSeasons = 24;
	
	private final int numberOfTeamsPerDivision = 4;
	
	private NFLSeasonSheet seasonSheet;
	
	@Before
	public void beforeEach() {
		when(mockSheet.createRow(0)).thenReturn(headerRow);
		when(headerRow.createCell(anyInt())).thenReturn(mockCell);
		when(teamRow.createCell(anyInt())).thenReturn(mockCell);
		
		setUpMockTeam();
		
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
		
		seasonSheet = new NFLSeasonSheet();
	}
	
	@Test
	public void seasonSheetCreatesAHeaderRow() {
		seasonSheet.createHeaderRow(mockSheet);
		
		verify(headerRow, times(NFLSeasonSheet.NUMBER_OF_COLUMNS)).createCell(anyInt());
		
		InOrder inOrder = inOrder(mockCell);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_TEAM);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_AVERAGE_WINS);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_AVERAGE_LOSSES);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_BOTTOM_5);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_DIVISION_LAST);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_WINNING_SEASON);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_PLAYOFFS);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_DIVISION_CHAMPS);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_ROUND_1_BYE);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_NUMBER_1_SEED);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_DIVISIONAL_ROUND);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_CONFERENCE_ROUND);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_CONFERENCE_CHAMPS);
		inOrder.verify(mockCell).setCellValue(NFLSeasonSheet.COLUMN_SUPER_BOWL_CHAMPS);
	}
	
	@Test
	public void seasonSheetCreatesATeamRow() {
		when(mockSheet.getLastRowNum()).thenReturn(0);
		when(mockSheet.createRow(1)).thenReturn(teamRow);
		
		seasonSheet.createTeamRow(mockSheet, mockSeasonTeam, numberOfSeasons);
		
		verify(teamRow, times(NFLSeasonSheet.NUMBER_OF_COLUMNS)).createCell(anyInt());
		
		InOrder inOrder = inOrder(mockCell);
		inOrder.verify(mockCell).setCellValue("MockTeam");
		inOrder.verify(mockCell).setCellValue(10);
		inOrder.verify(mockCell).setCellValue(6);
		inOrder.verify(mockCell).setCellValue(5);
		inOrder.verify(mockCell).setCellValue(8);
		inOrder.verify(mockCell).setCellValue(70);
		inOrder.verify(mockCell).setCellValue(66);
		inOrder.verify(mockCell).setCellValue(49);
		inOrder.verify(mockCell).setCellValue(30);
		inOrder.verify(mockCell).setCellValue(18);
		inOrder.verify(mockCell).setCellValue(68);
		inOrder.verify(mockCell).setCellValue(34);
		inOrder.verify(mockCell).setCellValue(17);
		inOrder.verify(mockCell).setCellValue(8);
	}
	
	@Test
	public void seasonSheetCreatesRowsForTeamsInDivision() {
		int numberOfDivisions = 1;
		int numberOfTeams = 4;
		
		setUpTestForNumberOfTeamsInDivisions(numberOfDivisions);
		seasonSheet.createDivisionRows(mockSheet, mockDivision, numberOfSeasons);
		verifyCreateRowCalledForNumberOfTeamsAndDivisions(numberOfTeams, numberOfDivisions);
	}
	
	@Test
	public void seasonSheetCreatesRowsForTeamsInConference() {
		int numberOfDivisions = 4;
		int numberOfTeams = 4 * numberOfDivisions;
		
		setUpTestForNumberOfTeamsInDivisions(numberOfDivisions);
		seasonSheet.createConferenceRows(mockSheet, mockConference, numberOfSeasons);
		verifyCreateRowCalledForNumberOfTeamsAndDivisions(numberOfTeams, numberOfDivisions);
	}
	
	@Test
	public void seasonSheetCreatesRowsForTeamsInLeague() {
		int numberOfConferences = 2;
		int numberOfDivisions = 4 * numberOfConferences;
		int numberOfTeams = 4 * numberOfDivisions;
		
		setUpTestForNumberOfTeamsInDivisions(numberOfDivisions);
		seasonSheet.createLeagueRows(mockSheet, mockSeason, numberOfSeasons);
		verifyCreateRowCalledForNumberOfTeamsAndDivisions(numberOfTeams, numberOfDivisions);
	}

	private void setUpMockTeam() {
		when(mockSeasonTeam.getTeam()).thenReturn(mockTeam);
		when(mockTeam.getName()).thenReturn("MockTeam");
		when(mockSeasonTeam.getNumberOfWins()).thenReturn(10 * numberOfSeasons);
		when(mockSeasonTeam.getNumberOfLosses()).thenReturn(6 * numberOfSeasons);
		when(mockSeasonTeam.getWasBottomTeam()).thenReturn(5 * numberOfSeasons);
		when(mockSeasonTeam.getWasInDivisionCellar()).thenReturn(8 * numberOfSeasons);
		when(mockSeasonTeam.getHadWinningSeason()).thenReturn(70 * numberOfSeasons);
		when(mockSeasonTeam.getMadePlayoffs()).thenReturn(66 * numberOfSeasons);
		when(mockSeasonTeam.getWonDivision()).thenReturn(49 * numberOfSeasons);
		when(mockSeasonTeam.getGotRoundOneBye()).thenReturn(30 * numberOfSeasons);
		when(mockSeasonTeam.getGotOneSeed()).thenReturn(18 * numberOfSeasons);
		when(mockSeasonTeam.getChanceToMakeDivisionalRound()).thenReturn(68 * numberOfSeasons);
		when(mockSeasonTeam.getChanceToMakeConferenceRound()).thenReturn(34 * numberOfSeasons);
		when(mockSeasonTeam.getChanceToWinConference()).thenReturn(17 * numberOfSeasons);
		when(mockSeasonTeam.getChanceToWinSuperBowl()).thenReturn(8 * numberOfSeasons);
	}
	
	private void setUpTestForNumberOfTeamsInDivisions(int numberOfDivisions) {
		List<Integer> lastRowNumReturns = new ArrayList<Integer>();
		int currentRow = 0;
		for (int i = 0; i < numberOfDivisions; i++) {
			for (int j = 0; j < numberOfTeamsPerDivision; j++) {
				lastRowNumReturns.add(currentRow);
				when(mockSheet.createRow(currentRow + 1)).thenReturn(teamRow);
				currentRow++;
			}
			lastRowNumReturns.add(currentRow);
			when(mockSheet.createRow(currentRow + 1)).thenReturn(emptyRow);
			currentRow++;
		}
		when(mockSheet.getLastRowNum()).thenAnswer(AdditionalAnswers.returnsElementsOf(lastRowNumReturns));
	}
	
	private void verifyCreateRowCalledForNumberOfTeamsAndDivisions(int numberOfTeams, int numberOfDivisions) {
		int totalRows = numberOfTeams + numberOfDivisions;
		for (int i = 1; i <= totalRows; i++) {
			verify(mockSheet).createRow(i);
		}
	}
	
}
