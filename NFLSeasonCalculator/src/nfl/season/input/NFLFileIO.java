package nfl.season.input;

public abstract class NFLFileIO {

	protected static final String NFL_TEAM_SETTINGS_FOLDER_LOCATION = 
			"TeamSettings";
	
	protected static final String NFL_TEAM_SETTINGS_FILE_NAME = "nflTeamSettings.txt";
	
	protected static final String NFL_TEAM_SETTINGS_FILE_LOCATION = 
			NFL_TEAM_SETTINGS_FOLDER_LOCATION + "/" + NFL_TEAM_SETTINGS_FILE_NAME;
	
	protected static final String NFL_PLAYOFF_SETTINGS_FOLDER_LOCATION = 
			"PlayoffSettings";
	
	protected static final String NFL_PLAYOFF_SETTINGS_FILE_NAME = 
			"nflPlayoffSettings.txt";
	
	protected static final String NFL_PLAYOFF_SETTINGS_FILE_LOCATION = 
			NFL_PLAYOFF_SETTINGS_FOLDER_LOCATION + "/" + NFL_PLAYOFF_SETTINGS_FILE_NAME;
	
	protected static final String NFL_SEASON_SAVE_FOLDER_LOCATION = 
			"SeasonSave";
	
	protected static final String NFL_SEASON_SAVE_FILE_NAME = "nflRegularSeason.txt";
	
	protected static final String NFL_SEASON_SAVE_FILE_LOCATION = 
			NFL_SEASON_SAVE_FOLDER_LOCATION + "/" + NFL_SEASON_SAVE_FILE_NAME;
	
	protected static final String NFL_SEASON_ESTIMATES_FILE_NAME = "nflSeasonEstimates.csv";
	
	protected static final String NFL_SEASON_ESTIMATES_FILE_LOCATION = 
			NFL_SEASON_SAVE_FOLDER_LOCATION + "/" + NFL_SEASON_ESTIMATES_FILE_NAME;
	
}
