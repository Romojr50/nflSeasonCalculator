package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.season.NFLSeason;
import nfl.season.season.SeasonWeek;

public class SeasonMenu extends SubMenu {

	public enum SeasonMenuOptions implements MenuOptions {
		LOAD_SEASON(1, "Load/Refresh the current season"),
		PRINT_OUT_WEEK(2, "Print out games in week"),
		EXIT(3, "Back to Main Menu");
		
		private int optionNumber;
		private String optionDescription;
		
		private SeasonMenuOptions(int optionNumber, String optionDescription) {
			this.optionNumber = optionNumber;
			this.optionDescription = optionDescription;
		}

		@Override
		public int getOptionNumber() {
			return optionNumber;
		}

		@Override
		public String getOptionDescription() {
			return optionDescription;
		}
	}
	
	private NFLSeasonInput input;
	
	private NFLSeason season;
	
	private ScoreStripReader scoreStripReader;
	
	private ScoreStripMapper scoreStripMapper;
	
	public SeasonMenu(NFLSeasonInput input, NFLSeason season, 
			ScoreStripReader scoreStripReader, ScoreStripMapper scoreStripMapper) {
		this.input = input;
		this.season = season;
		this.scoreStripReader = scoreStripReader;
		this.scoreStripMapper = scoreStripMapper;
	}
	
	@Override
	public void launchSubMenu() {
		int selectedOption = -1;
		String seasonMenuPrefix = "";
		
		while (selectedOption != SeasonMenuOptions.EXIT.optionNumber) {
			String seasonMenuMessage = MenuOptionsUtil.createMenuMessage(
					SeasonMenuOptions.class); 
			
			selectedOption = input.askForInt(seasonMenuPrefix + seasonMenuMessage);
			
			if (SeasonMenuOptions.LOAD_SEASON.optionNumber == selectedOption) {
				input.printMessage("Loading season...");
				season.loadSeason(scoreStripReader, scoreStripMapper);
			} else if (SeasonMenuOptions.PRINT_OUT_WEEK.optionNumber == selectedOption) {
				int selectedWeek = -1;
				
				while (selectedWeek < 1 || 
						selectedWeek > NFLSeason.NUMBER_OF_WEEKS_IN_SEASON) {
					selectedWeek = input.askForInt("Please enter in a number between 1-17:");
					
					if (selectedWeek >= 1 && 
							selectedWeek <= NFLSeason.NUMBER_OF_WEEKS_IN_SEASON) {
						SeasonWeek week = season.getWeek(selectedWeek);
						seasonMenuPrefix = season.getWeekString(week);
					}
				}
			}
		}
	}
	
}
