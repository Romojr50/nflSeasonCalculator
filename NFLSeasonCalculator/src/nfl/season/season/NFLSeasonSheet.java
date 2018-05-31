package nfl.season.season;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class NFLSeasonSheet {

	public static final int NUMBER_OF_COLUMNS = 14;
	
	public static final String COLUMN_TEAM = "Team";
	public static final String COLUMN_AVERAGE_WINS = "Avg Wins";
	public static final String COLUMN_AVERAGE_LOSSES = "Avg Losses";
	public static final String COLUMN_BOTTOM_5 = "Bot 5";
	public static final String COLUMN_DIVISION_LAST = "Div Last";
	public static final String COLUMN_WINNING_SEASON = "Win Seas";
	public static final String COLUMN_PLAYOFFS = "Playoffs";
	public static final String COLUMN_DIVISION_CHAMPS = "Div Champs";
	public static final String COLUMN_ROUND_1_BYE = "Rnd 1 Bye";
	public static final String COLUMN_NUMBER_1_SEED = "#1 Seed";
	public static final String COLUMN_DIVISIONAL_ROUND = "Div Rnd";
	public static final String COLUMN_CONFERENCE_ROUND = "Conf Rnd";
	public static final String COLUMN_CONFERENCE_CHAMPS = "Conf Champs";
	public static final String COLUMN_SUPER_BOWL_CHAMPS = "SB Champs";

	public void createHeaderRow(Sheet sheet) {
		Row headerRow = sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue(COLUMN_TEAM);
		headerRow.createCell(1).setCellValue(COLUMN_AVERAGE_WINS);
		headerRow.createCell(2).setCellValue(COLUMN_AVERAGE_LOSSES);
		headerRow.createCell(3).setCellValue(COLUMN_BOTTOM_5);
		headerRow.createCell(4).setCellValue(COLUMN_DIVISION_LAST);
		headerRow.createCell(5).setCellValue(COLUMN_WINNING_SEASON);
		headerRow.createCell(6).setCellValue(COLUMN_PLAYOFFS);
		headerRow.createCell(7).setCellValue(COLUMN_DIVISION_CHAMPS);
		headerRow.createCell(8).setCellValue(COLUMN_ROUND_1_BYE);
		headerRow.createCell(9).setCellValue(COLUMN_NUMBER_1_SEED);
		headerRow.createCell(10).setCellValue(COLUMN_DIVISIONAL_ROUND);
		headerRow.createCell(11).setCellValue(COLUMN_CONFERENCE_ROUND);
		headerRow.createCell(12).setCellValue(COLUMN_CONFERENCE_CHAMPS);
		headerRow.createCell(13).setCellValue(COLUMN_SUPER_BOWL_CHAMPS);
	}

}
