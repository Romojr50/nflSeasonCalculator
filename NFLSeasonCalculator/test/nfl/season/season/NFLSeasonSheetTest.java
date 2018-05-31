package nfl.season.season;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonSheetTest {

	@Mock
	private Sheet mockSheet;
	
	@Mock
	private Row headerRow;
	
	@Mock
	private Cell mockCell;
	
	private NFLSeasonSheet seasonSheet;
	
	@Before
	public void beforeEach() {
		when(mockSheet.createRow(0)).thenReturn(headerRow);
		when(headerRow.createCell(anyInt())).thenReturn(mockCell);
		
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
	
}
