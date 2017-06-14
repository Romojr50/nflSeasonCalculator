package nfl.season.input;

public abstract class NFLFileIO {

	protected static final String NFL_TEAM_SETTINGS_FOLDER_LOCATION = 
			"TeamSettings";
	
	protected static final String NFL_TEAM_SETTINGS_FILE_LOCATION = 
			NFL_TEAM_SETTINGS_FOLDER_LOCATION + "/nflTeamSettings.txt";
	
	protected static final String NFL_PLAYOFF_SETTINGS_FOLDER_LOCATION = 
			"PlayoffSettings";
	
	protected static final String NFL_PLAYOFF_SETTINGS_FILE_LOCATION = 
			NFL_PLAYOFF_SETTINGS_FOLDER_LOCATION + "/nflPlayoffSettings.txt";
	
	protected static final String NFL_SEASON_SAVE_FOLDER_LOCATION = 
			"SeasonSave";
	
	protected static final String NFL_SEASON_SAVE_FILE_LOCATION = 
			NFL_SEASON_SAVE_FOLDER_LOCATION + "/nflRegularSeason.txt";
	
}
