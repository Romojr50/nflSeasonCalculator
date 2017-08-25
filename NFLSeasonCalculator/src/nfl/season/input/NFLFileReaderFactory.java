package nfl.season.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class NFLFileReaderFactory extends NFLFileIO{

	public BufferedReader createNFLTeamSettingsReader() throws FileNotFoundException {
		return createNFLFileReader(NFL_TEAM_SETTINGS_FILE_LOCATION);
	}
	
	public BufferedReader createNFLTeamSettingsReader(String folderPath) throws FileNotFoundException {
		return createNFLFileReader(folderPath + "/" + NFL_TEAM_SETTINGS_FILE_NAME);
	}
	
	public BufferedReader createNFLPlayoffSettingsReader() throws FileNotFoundException {
		return createNFLFileReader(NFL_PLAYOFF_SETTINGS_FILE_LOCATION);
	}
	
	public BufferedReader createNFLPlayoffSettingsReader(String folderPath) throws FileNotFoundException {
		return createNFLFileReader(folderPath + "/" + NFL_PLAYOFF_SETTINGS_FILE_NAME);
	}
	
	public BufferedReader createNFLSeasonSaveReader() throws FileNotFoundException {
		return createNFLFileReader(NFL_SEASON_SAVE_FILE_LOCATION);
	}
	
	public BufferedReader createNFLSeasonSaveReader(String folderPath) throws FileNotFoundException {
		return createNFLFileReader(folderPath + "/" + NFL_SEASON_SAVE_FILE_NAME);
	}

	private BufferedReader createNFLFileReader(String fileLocation) throws FileNotFoundException {
		FileReader fileReader = new FileReader(fileLocation);
		
		BufferedReader nflTeamSettingsReader = new BufferedReader(fileReader);
		
		return nflTeamSettingsReader;
	}
	
}
