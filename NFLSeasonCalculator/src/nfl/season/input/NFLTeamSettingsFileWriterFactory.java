package nfl.season.input;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class NFLTeamSettingsFileWriterFactory {

	private static final String NFL_TEAM_SETTINGS_FILE_LOCATION = 
			"TeamSettings/nflTeamSettings.txt";
	
	public FileOutputStream createNFLTeamSettingsWriter() throws FileNotFoundException {
		FileOutputStream fileOutputStream = new FileOutputStream(
				NFL_TEAM_SETTINGS_FILE_LOCATION);
		
		return fileOutputStream;
	}

}
