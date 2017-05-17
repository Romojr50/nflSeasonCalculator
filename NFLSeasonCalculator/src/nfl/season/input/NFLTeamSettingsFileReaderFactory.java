package nfl.season.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class NFLTeamSettingsFileReaderFactory extends NFLTeamSettingsFileIO{

	public BufferedReader createNFLTeamSettingsReader() throws FileNotFoundException {
		FileReader fileReader = new FileReader(NFL_TEAM_SETTINGS_FILE_LOCATION);
		
		BufferedReader nflTeamSettingsReader = new BufferedReader(fileReader);
		
		return nflTeamSettingsReader;
	}
	
}
