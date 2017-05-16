package nfl.season.input;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class NFLTeamSettingsFileWriterFactory extends NFLTeamSettingsFileIO {

	public FileOutputStream createNFLTeamSettingsWriter() throws FileNotFoundException {
		FileOutputStream fileOutputStream = new FileOutputStream(
				NFL_TEAM_SETTINGS_FILE_LOCATION);
		
		return fileOutputStream;
	}

}
