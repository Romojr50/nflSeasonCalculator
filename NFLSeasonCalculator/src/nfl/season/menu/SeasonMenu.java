package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.season.NFLSeason;

public class SeasonMenu extends SubMenu {

	public enum SeasonMenuOptions implements MenuOptions {
		LOAD_SEASON(1, "Load/Refresh the current season"), 
		EXIT(2, "Back to Main Menu");
		
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
		
		while (selectedOption != SeasonMenuOptions.EXIT.optionNumber) {
			String seasonMenuMessage = MenuOptionsUtil.createMenuMessage(
					SeasonMenuOptions.class); 
			
			selectedOption = input.askForInt(seasonMenuMessage);
			
			if (SeasonMenuOptions.LOAD_SEASON.optionNumber == selectedOption) {
				input.printMessage("Loading season...");
				season.loadSeason(scoreStripReader, scoreStripMapper);
			}
		}
	}
	
}
