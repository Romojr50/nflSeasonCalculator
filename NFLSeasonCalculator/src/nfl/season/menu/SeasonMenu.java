package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.season.NFLSeason;
import nfl.season.season.NFLSeasonTeam;
import nfl.season.season.SeasonWeek;

public class SeasonMenu extends SubMenu {

	public enum SeasonMenuOptions implements MenuOptions {
		LOAD_SEASON(1, "Load/Refresh the current season"),
		PRINT_OUT_WEEK(2, "Print out games in week"),
		PRINT_TEAM_SCHEDULE(3, "Print out team schedule"),
		EXIT(4, "Back to Main Menu");
		
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
				seasonMenuPrefix = launchPrintWeekMenu(seasonMenuPrefix);
			} else if (SeasonMenuOptions.PRINT_TEAM_SCHEDULE.optionNumber == selectedOption) {
				launchPrintTeamScheduleMenu();
			}
		}
	}

	private String launchPrintWeekMenu(String seasonMenuPrefix) {
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
		return seasonMenuPrefix;
	}

	private void launchPrintTeamScheduleMenu() {
		String teamListMessage = MenuOptionsUtil.buildTeamListMessage();
		int selectedTeamNumber = -1;
		String scheduleString = "";
		
		while (selectedTeamNumber != MenuOptionsUtil.EXIT_FROM_TEAM_SELECT) {
			selectedTeamNumber = input.askForInt(scheduleString + 
					teamListMessage);
				
			if (MenuOptionsUtil.EXIT_FROM_TEAM_SELECT != selectedTeamNumber
					&& selectedTeamNumber >= 1
					&& selectedTeamNumber <= NFLTeamEnum.values().length) {
				League league = season.getLeague();
				Team selectedLeagueTeam = league.getTeam(selectedTeamNumber);
				NFLSeasonTeam selectedSeasonTeam = season.getTeam(
						selectedLeagueTeam.getName());
				
				if (selectedSeasonTeam != null) {
					scheduleString = selectedSeasonTeam.getScheduleString();
				}
			}
		}
	}
	
}
