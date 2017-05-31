package nfl.season.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class NFLFileReaderFactory extends NFLFileIO{

	public BufferedReader createNFLTeamSettingsReader() throws FileNotFoundException {
		return createNFLFileReader(NFL_TEAM_SETTINGS_FILE_LOCATION);
	}
	
	public BufferedReader createNFLPlayoffSettingsReader() throws FileNotFoundException {
		return createNFLFileReader(NFL_PLAYOFF_SETTINGS_FILE_LOCATION);
	}

	private BufferedReader createNFLFileReader(String fileLocation) throws FileNotFoundException {
		FileReader fileReader = new FileReader(fileLocation);
		
		BufferedReader nflTeamSettingsReader = new BufferedReader(fileReader);
		
		return nflTeamSettingsReader;
	}
	
}
