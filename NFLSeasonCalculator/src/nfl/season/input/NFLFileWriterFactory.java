package nfl.season.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NFLFileWriterFactory extends NFLFileIO {

	public FileOutputStream createNFLTeamSettingsWriter() throws FileNotFoundException {
		createFolderIfItDoesNotExist(NFL_TEAM_SETTINGS_FOLDER_LOCATION);
		return createNFLFileWriter(NFL_TEAM_SETTINGS_FILE_LOCATION);
	}
	
	public FileOutputStream createNFLTeamSettingsWriter(String folderPath) throws FileNotFoundException {
		createFolderIfItDoesNotExist(folderPath);
		return createNFLFileWriter(folderPath + "/" + NFL_TEAM_SETTINGS_FILE_NAME);
	}

	public FileOutputStream createNFLPlayoffSettingsWriter() throws FileNotFoundException {
		createFolderIfItDoesNotExist(NFL_PLAYOFF_SETTINGS_FOLDER_LOCATION);
		return createNFLFileWriter(NFL_PLAYOFF_SETTINGS_FILE_LOCATION);
	}
	
	public FileOutputStream createNFLPlayoffSettingsWriter(String folderPath) throws FileNotFoundException {
		createFolderIfItDoesNotExist(folderPath);
		return createNFLFileWriter(folderPath + "/" + NFL_PLAYOFF_SETTINGS_FILE_NAME);
	}
	
	public FileOutputStream createNFLSeasonSaveWriter() throws FileNotFoundException {
		createFolderIfItDoesNotExist(NFL_SEASON_SAVE_FOLDER_LOCATION);
		return createNFLFileWriter(NFL_SEASON_SAVE_FILE_LOCATION);
	}
	
	public FileOutputStream createNFLSeasonSaveWriter(String folderPath) throws FileNotFoundException {
		createFolderIfItDoesNotExist(folderPath);
		return createNFLFileWriter(folderPath + "/" + NFL_SEASON_SAVE_FILE_NAME);
	}
	
	public FileOutputStream createNFLSeasonEstimatesWriter(String folderPath) throws FileNotFoundException {
		createFolderIfItDoesNotExist(folderPath);
		String filepath = getSeasonEstimatesFilepath(folderPath);
		return createNFLFileWriter(filepath);
	}
	
	public Workbook createNFLSeasonEstimatesWorkbook() {
		return new XSSFWorkbook();
	}
	
	private void createFolderIfItDoesNotExist(String folderLocation) {
		File directory = new File(folderLocation);
	    if (!directory.exists()){
	        directory.mkdir();
	    }
	}
	
	private FileOutputStream createNFLFileWriter(String fileLocation) 
			throws FileNotFoundException {
		FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
		
		return fileOutputStream;
	}

	public String getSeasonEstimatesFilepath(String folderPath) {
		String filepath = folderPath + "/" + NFL_SEASON_ESTIMATES_FILE_NAME;
		return filepath;
	}

}
