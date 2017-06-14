package nfl.season.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class NFLFileWriterFactory extends NFLFileIO {

	public FileOutputStream createNFLTeamSettingsWriter() throws FileNotFoundException {
		createFolderIfItDoesNotExist(NFL_TEAM_SETTINGS_FOLDER_LOCATION);
		return createNFLFileWriter(NFL_TEAM_SETTINGS_FILE_LOCATION);
	}

	public FileOutputStream createNFLPlayoffSettingsWriter() throws FileNotFoundException {
		createFolderIfItDoesNotExist(NFL_PLAYOFF_SETTINGS_FOLDER_LOCATION);
		return createNFLFileWriter(NFL_PLAYOFF_SETTINGS_FILE_LOCATION);
	}
	
	public FileOutputStream createNFLSeasonSaveWriter() throws FileNotFoundException {
		createFolderIfItDoesNotExist(NFL_SEASON_SAVE_FOLDER_LOCATION);
		return createNFLFileWriter(NFL_SEASON_SAVE_FILE_LOCATION);
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

}
