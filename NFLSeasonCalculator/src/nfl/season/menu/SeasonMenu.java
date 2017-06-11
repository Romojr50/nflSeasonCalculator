package nfl.season.menu;

import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.season.NFLManySeasonSimulator;
import nfl.season.season.NFLSeason;
import nfl.season.season.NFLSeasonTeam;
import nfl.season.season.NFLTiebreaker;
import nfl.season.season.SeasonWeek;

public class SeasonMenu extends SubMenu {

	public enum SeasonMenuOptions implements MenuOptions {
		LOAD_SEASON(1, "Load/Refresh the current season"),
		PRINT_OUT_WEEK(2, "Print out games in week"),
		PRINT_TEAM_SCHEDULE(3, "Print out team schedule"),
		PRINT_STANDINGS(4, "Print out League Standings"),
		SIMULATE_SEASON(5, "Simulate Season"),
		CLEAR_SIMULATIONS(6, "Clear Simulated Games"),
		SIMULATE_MANY_SEASONS(7, "Simulate Many Seasons and Tally Results"),
		PRINT_TEAM_SIMULATIONS(8, "Print Out Team Simulation Results"),
		EXIT(9, "Back to Main Menu");
		
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
		
		NFLTiebreaker tiebreaker = season.createNFLTiebreaker();
		while (selectedOption != SeasonMenuOptions.EXIT.optionNumber) {
			String seasonMenuMessage = MenuOptionsUtil.createMenuMessage(
					SeasonMenuOptions.class); 
			
			selectedOption = input.askForInt(seasonMenuPrefix + seasonMenuMessage);
			seasonMenuPrefix = "";
			
			if (SeasonMenuOptions.LOAD_SEASON.optionNumber == selectedOption) {
				input.printMessage("Loading season...");
				season.loadSeason(scoreStripReader, scoreStripMapper);
			} else if (SeasonMenuOptions.PRINT_OUT_WEEK.optionNumber == selectedOption) {
				seasonMenuPrefix = launchPrintWeekMenu(seasonMenuPrefix);
			} else if (SeasonMenuOptions.PRINT_TEAM_SCHEDULE.optionNumber == selectedOption) {
				launchPrintTeamScheduleMenu();
			} else if (SeasonMenuOptions.PRINT_STANDINGS.optionNumber == selectedOption) {
				seasonMenuPrefix = season.getLeagueStandings(tiebreaker);
			} else if (SeasonMenuOptions.SIMULATE_SEASON.optionNumber == selectedOption) {
				season.simulateSeason();
			} else if (SeasonMenuOptions.CLEAR_SIMULATIONS.optionNumber == selectedOption) {
				season.clearSimulatedResults();
			} else if (SeasonMenuOptions.SIMULATE_MANY_SEASONS.optionNumber == selectedOption) {
				input.printMessage("Simulating " + NFLSeason.MANY_SEASONS_NUMBER + 
						" Seasons...");
				NFLManySeasonSimulator simulator = season.createManySeasonsSimulator();
				simulator.clearSimulations();
				simulator.simulateManySeasons(tiebreaker, NFLSeason.MANY_SEASONS_NUMBER);
			} else if (SeasonMenuOptions.PRINT_TEAM_SIMULATIONS.optionNumber == selectedOption) {
				launchPrintTeamSimulationsMenu();
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
				
			if (inputIsValidForTeamSelect(selectedTeamNumber)) {
				NFLSeasonTeam selectedSeasonTeam = getNFLSeasonTeamFromTeamNumber(
						selectedTeamNumber);
				
				if (selectedSeasonTeam != null) {
					scheduleString = selectedSeasonTeam.getScheduleString();
				}
			}
		}
	}
	
	private void launchPrintTeamSimulationsMenu() {
		String teamListMessage = MenuOptionsUtil.buildTeamListMessage();
		int selectedTeamNumber = -1;
		String simulationString = "";
		
		while (selectedTeamNumber != MenuOptionsUtil.EXIT_FROM_TEAM_SELECT) {
			selectedTeamNumber = input.askForInt(simulationString + 
					teamListMessage);
				
			if (inputIsValidForTeamSelect(selectedTeamNumber)) {
				NFLSeasonTeam selectedSeasonTeam = getNFLSeasonTeamFromTeamNumber(
						selectedTeamNumber);
				
				if (selectedSeasonTeam != null) {
					simulationString = selectedSeasonTeam.getSimulatedResults(
							NFLSeason.MANY_SEASONS_NUMBER);
				}
			}
		}
	}

	private boolean inputIsValidForTeamSelect(int selectedTeamNumber) {
		return MenuOptionsUtil.EXIT_FROM_TEAM_SELECT != selectedTeamNumber
				&& selectedTeamNumber >= 1
				&& selectedTeamNumber <= NFLTeamEnum.values().length;
	}
	
	private NFLSeasonTeam getNFLSeasonTeamFromTeamNumber(int selectedTeamNumber) {
		League league = season.getLeague();
		Team selectedLeagueTeam = league.getTeam(selectedTeamNumber);
		NFLSeasonTeam selectedSeasonTeam = season.getTeam(
				selectedLeagueTeam.getName());
		return selectedSeasonTeam;
	}
	
}
