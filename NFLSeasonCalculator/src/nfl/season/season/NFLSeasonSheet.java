package nfl.season.season;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import nfl.season.input.NFLFileWriterFactory;
import nfl.season.league.Team;

public class NFLSeasonSheet {

	public static final String COLUMN_TEAM = "Team";
	public static final String COLUMN_AVERAGE_WINS = "Avg Wins";
	public static final String COLUMN_AVERAGE_LOSSES = "Avg Losses";
	public static final String COLUMN_BOTTOM_5 = "Bot 5";
	public static final String COLUMN_DIVISION_LAST = "Div Last";
	public static final String COLUMN_WINNING_SEASON = "Win Seas";
	public static final String COLUMN_PLAYOFFS = "Playoffs";
	public static final String COLUMN_DIVISION_CHAMPS = "Div Champs";
	public static final String COLUMN_ROUND_1_BYE = "Rnd 1 Bye";
	public static final String COLUMN_NUMBER_1_SEED = "#1 Seed";
	public static final String COLUMN_DIVISIONAL_ROUND = "Div Rnd";
	public static final String COLUMN_CONFERENCE_ROUND = "Conf Rnd";
	public static final String COLUMN_CONFERENCE_CHAMPS = "Conf Champs";
	public static final String COLUMN_SUPER_BOWL_CHAMPS = "SB Champs";

	private NFLFileWriterFactory fileWriterFactory;
	
	public NFLSeasonSheet(NFLFileWriterFactory fileWriterFactory) {
		this.fileWriterFactory = fileWriterFactory;
	}

	public String createHeaderRow() {
		StringBuilder header = new StringBuilder();
		header.append(COLUMN_TEAM + ",");
		header.append(COLUMN_AVERAGE_WINS + ",");
		header.append(COLUMN_AVERAGE_LOSSES + ",");
		header.append(COLUMN_BOTTOM_5 + ",");
		header.append(COLUMN_DIVISION_LAST + ",");
		header.append(COLUMN_WINNING_SEASON + ",");
		header.append(COLUMN_PLAYOFFS + ",");
		header.append(COLUMN_DIVISION_CHAMPS + ",");
		header.append(COLUMN_ROUND_1_BYE + ",");
		header.append(COLUMN_NUMBER_1_SEED + ",");
		header.append(COLUMN_DIVISIONAL_ROUND + ",");
		header.append(COLUMN_CONFERENCE_ROUND + ",");
		header.append(COLUMN_CONFERENCE_CHAMPS + ",");
		header.append(COLUMN_SUPER_BOWL_CHAMPS);
		header.append('\n');
		
		return header.toString();
	}

	public String createTeamRow(NFLSeasonTeam seasonTeam, int numberOfSeasons) {
		StringBuilder teamRow = new StringBuilder();
		
		Team team = seasonTeam.getTeam();
		teamRow.append(team.getName() + ",");
		
		double numberOfSeasonsDouble = (double) numberOfSeasons;
		double numberOfHundredSimulations = Math.round(numberOfSeasonsDouble / 100);
		if (numberOfHundredSimulations == 0) {
			numberOfHundredSimulations = 1;
		}
		teamRow.append((int) Math.round(seasonTeam.getSimulatedWins() / numberOfSeasonsDouble) + ",");
		teamRow.append((int) Math.round(seasonTeam.getSimulatedLosses() / numberOfSeasonsDouble) + ",");
		teamRow.append((int) Math.round(seasonTeam.getWasBottomTeam() / numberOfHundredSimulations) + ",");
		teamRow.append((int) Math.round(seasonTeam.getWasInDivisionCellar() / numberOfHundredSimulations) + ",");
		teamRow.append((int) Math.round(seasonTeam.getHadWinningSeason() / numberOfHundredSimulations) + ",");
		teamRow.append((int) Math.round(seasonTeam.getMadePlayoffs() / numberOfHundredSimulations) + ",");
		teamRow.append((int) Math.round(seasonTeam.getWonDivision() / numberOfHundredSimulations) + ",");
		teamRow.append((int) Math.round(seasonTeam.getGotRoundOneBye() / numberOfHundredSimulations) + ",");
		teamRow.append((int) Math.round(seasonTeam.getGotOneSeed() / numberOfHundredSimulations) + ",");
		teamRow.append((int) Math.round(seasonTeam.getChanceToMakeDivisionalRound() / numberOfSeasonsDouble) + ",");
		teamRow.append((int) Math.round(seasonTeam.getChanceToMakeConferenceRound() / numberOfSeasonsDouble) + ",");
		teamRow.append((int) Math.round(seasonTeam.getChanceToWinConference() / numberOfSeasonsDouble) + ",");
		teamRow.append((int) Math.round(seasonTeam.getChanceToWinSuperBowl() / numberOfSeasonsDouble) + "\n");
		
		return teamRow.toString();
	}

	public String createDivisionRows(NFLSeasonDivision division, int numberOfSeasons) {
		StringBuilder divisionBuilder = new StringBuilder();
		
		List<NFLSeasonTeam> seasonTeams = division.getTeams();
		for (NFLSeasonTeam seasonTeam : seasonTeams) {
			divisionBuilder.append(createTeamRow(seasonTeam, numberOfSeasons));
		}
		
		return divisionBuilder.toString();
	}

	public String createConferenceRows(NFLSeasonConference conference, int numberOfSeasons) {
		StringBuilder conferenceBuilder = new StringBuilder();
		
		List<NFLSeasonDivision> seasonDivisions = conference.getDivisions();
		for (NFLSeasonDivision seasonDivision : seasonDivisions) {
			conferenceBuilder.append(createDivisionRows(seasonDivision, numberOfSeasons));
			conferenceBuilder.append("\n");
		}
		
		return conferenceBuilder.toString();
	}

	public String createLeagueRows(NFLSeason season, int numberOfSeasons) {
		StringBuilder leagueBuilder = new StringBuilder();
		
		List<NFLSeasonConference> seasonConferences = season.getConferences();
		for (NFLSeasonConference seasonConference : seasonConferences) {
			leagueBuilder.append(createConferenceRows(seasonConference, numberOfSeasons));
		}
		
		return leagueBuilder.toString();
	}

	public boolean createSeasonEstimatesFile(String folderPath, NFLSeason season,
			int numberOfSeasons) throws IOException {
		boolean success = true;
		
		FileOutputStream fileWriter = null;
		
		try {
			fileWriter = fileWriterFactory.createNFLSeasonEstimatesWriter(folderPath);
			String leagueString = createHeaderRow();
			leagueString = leagueString + createLeagueRows(season, numberOfSeasons);
			fileWriter.write(leagueString.getBytes());
		} catch (IOException e) {
			success = false;
		} finally {
			fileWriter.close();
		}
		
		return success;
	}

}
