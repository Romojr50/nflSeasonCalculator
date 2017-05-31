package nfl.season.input;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class NFLFileWriterFactory extends NFLFileIO {

	public FileOutputStream createNFLTeamSettingsWriter() throws FileNotFoundException {
		return createNFLFileWriter(NFL_TEAM_SETTINGS_FILE_LOCATION);
	}

	public FileOutputStream createNFLPlayoffSettingsWriter() throws FileNotFoundException {
		return createNFLFileWriter(NFL_PLAYOFF_SETTINGS_FILE_LOCATION);
	}
	
	private FileOutputStream createNFLFileWriter(String fileLocation) 
			throws FileNotFoundException {
		FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
		
		return fileOutputStream;
	}

}
