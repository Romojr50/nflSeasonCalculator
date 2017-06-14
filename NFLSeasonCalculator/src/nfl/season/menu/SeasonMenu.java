package nfl.season.menu;

import java.io.IOException;

import nfl.season.input.NFLFileReaderFactory;
import nfl.season.input.NFLFileWriterFactory;
import nfl.season.input.NFLRegularSeasonSave;
import nfl.season.input.NFLSeasonInput;
import nfl.season.league.League;
import nfl.season.league.NFLTeamEnum;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffs;
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
	
	private NFLPlayoffs playoffs;
	
	private ScoreStripReader scoreStripReader;
	
	private ScoreStripMapper scoreStripMapper;
	
	private NFLRegularSeasonSave seasonSave;
	
	private NFLFileWriterFactory fileWriterFactory;
	
	private NFLFileReaderFactory fileReaderFactory;
	
	public SeasonMenu(NFLSeasonInput input, NFLSeason season, 
			NFLPlayoffs playoffs, ScoreStripReader scoreStripReader, 
			ScoreStripMapper scoreStripMapper, NFLRegularSeasonSave seasonSave, 
			NFLFileWriterFactory fileWriterFactory, NFLFileReaderFactory fileReaderFactory) {
		this.input = input;
		this.season = season;
		this.playoffs = playoffs;
		this.scoreStripReader = scoreStripReader;
		this.scoreStripMapper = scoreStripMapper;
		this.seasonSave = seasonSave;
		this.fileWriterFactory = fileWriterFactory;
		this.fileReaderFactory = fileReaderFactory;
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
				loadSeasonFromNetOrFile();
			} else if (SeasonMenuOptions.PRINT_OUT_WEEK.optionNumber == selectedOption) {
				seasonMenuPrefix = launchPrintWeekMenu(seasonMenuPrefix);
			} else if (SeasonMenuOptions.PRINT_TEAM_SCHEDULE.optionNumber == selectedOption) {
				launchPrintTeamScheduleMenu();
			} else if (SeasonMenuOptions.PRINT_STANDINGS.optionNumber == selectedOption) {
				seasonMenuPrefix = season.getLeagueStandings(tiebreaker);
			} else if (SeasonMenuOptions.SIMULATE_SEASON.optionNumber == selectedOption) {
				seasonMenuPrefix = launchSimulateSeason();
			} else if (SeasonMenuOptions.CLEAR_SIMULATIONS.optionNumber == selectedOption) {
				season.clearSimulatedResults();
			} else if (SeasonMenuOptions.SIMULATE_MANY_SEASONS.optionNumber == selectedOption) {
				seasonMenuPrefix = launchSimulateManySeasons(tiebreaker);
			} else if (SeasonMenuOptions.PRINT_TEAM_SIMULATIONS.optionNumber == selectedOption) {
				launchPrintTeamSimulationsMenu();
			}
		}
	}

	private void loadSeasonFromNetOrFile() {
		input.printMessage("Loading season...");
		try {
			season.loadSeason(scoreStripReader, scoreStripMapper, seasonSave, 
					fileWriterFactory);
		} catch (Exception e) {
			input.printMessage(
					"Season could not be loaded from internet; loading from saved file...");
			String seasonString;
			try {
				seasonString = seasonSave.loadSeasonSave(fileReaderFactory);
				seasonSave.populateSeasonFromSeasonString(seasonString, season);
			} catch (IOException e1) {
				input.printMessage("Load season FAILED!");
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

	private String launchSimulateSeason() {
		String seasonMenuPrefix = "";
		
		if (season.getWeek(1) != null) {
			season.simulateSeason();
		} else {
			seasonMenuPrefix = "Season not loaded yet; Please load season\n";
		}
		return seasonMenuPrefix;
	}

	private String launchSimulateManySeasons(NFLTiebreaker tiebreaker) {
		String seasonMenuPrefix = "";
		
		if (season.getWeek(1) != null) {
			input.printMessage("Simulating " + NFLSeason.MANY_SEASONS_NUMBER + 
					" Seasons...");
			NFLManySeasonSimulator simulator = season.createManySeasonsSimulator();
			simulator.clearSimulations();
			simulator.simulateManySeasons(tiebreaker, playoffs, NFLSeason.MANY_SEASONS_NUMBER);
		} else {
			seasonMenuPrefix = "Season not loaded yet; Please load season\n";
		}
		return seasonMenuPrefix;
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
