package nfl.season.season;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	private Cell mockCell;
	
	@Mock
	private NFLSeasonTeam mockSeasonTeam;
	
	@Mock
	private Team mockTeam;
	
	private NFLSeasonSheet seasonSheet;
	
	@Before
	public void beforeEach() {
		when(mockSheet.createRow(0)).thenReturn(headerRow);
		when(headerRow.createCell(anyInt())).thenReturn(mockCell);
		when(teamRow.createCell(anyInt())).thenReturn(mockCell);
		
		when(mockSeasonTeam.getTeam()).thenReturn(mockTeam);
		
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
		int numberOfSeasons = 24;
		
		when(mockSheet.createRow(1)).thenReturn(teamRow);
		
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
	
}
