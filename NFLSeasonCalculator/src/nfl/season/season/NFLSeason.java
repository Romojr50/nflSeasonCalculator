package nfl.season.season;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.League;
import nfl.season.league.Team;
import nfl.season.scorestrip.ScoreStripMapper;
import nfl.season.scorestrip.ScoreStripReader;
import nfl.season.scorestrip.Ss;

public class NFLSeason {

	public static final int NUMBER_OF_WEEKS_IN_SEASON = 17;

	private League league;
	
	private List<NFLSeasonConference> conferences;
	
	private SeasonWeek[] weeks;
	
	public NFLSeason() {
		conferences = new ArrayList<NFLSeasonConference>();
		weeks = new SeasonWeek[NUMBER_OF_WEEKS_IN_SEASON];
	}
	
	public void initializeNFLRegularSeason(League league) {
		this.league = league;
		
		List<Conference> leagueConferences = league.getConferences();
		for (Conference leagueConference : leagueConferences) {
			NFLSeasonConference seasonConference = 
					new NFLSeasonConference(leagueConference);
			conferences.add(seasonConference);
			
			List<Division> leagueDivisions = leagueConference.getDivisions();
			for (Division leagueDivision : leagueDivisions) {
				NFLSeasonDivision seasonDivision = 
						new NFLSeasonDivision(leagueDivision);
				seasonConference.addDivision(seasonDivision);
				
				List<Team> leagueTeams = leagueDivision.getTeams();
				for (Team leagueTeam : leagueTeams) {
					NFLSeasonTeam seasonTeam = new NFLSeasonTeam(leagueTeam);
					seasonDivision.addTeam(seasonTeam);
				}
			}
		}
	}
	
	public League getLeague() {
		return league;
	}
	
	public List<NFLSeasonConference> getConferences() {
		return conferences;
	}
	
	public NFLSeasonTeam getTeam(String teamName) {
		NFLSeasonTeam returnTeam = null;
		
		for (NFLSeasonConference conference : conferences) {
			List<NFLSeasonDivision> divisions = conference.getDivisions();
			for (NFLSeasonDivision division : divisions) {
				List<NFLSeasonTeam> teams = division.getTeams();
				for (NFLSeasonTeam team : teams) {
					Team leagueTeam = team.getTeam();
					if (leagueTeam.getName().equalsIgnoreCase(teamName)) {
						returnTeam = team;
						break;
					}
				}
			}
		}
		return returnTeam;
	}

	public SeasonWeek[] getWeeks() {
		return weeks;
	}

	public SeasonWeek getWeek(int weekNumber) {
		SeasonWeek returnWeek = null;
		
		if (weekNumber >= 1 && weekNumber <= NUMBER_OF_WEEKS_IN_SEASON) {
			returnWeek = weeks[weekNumber - 1];
		}
		
		return returnWeek;
	}

	public void addWeek(SeasonWeek week) {
		int weekNumber = week.getWeekNumber();
		
		if (weekNumber >= 1 && weekNumber <= NUMBER_OF_WEEKS_IN_SEASON) {
			weeks[weekNumber - 1] = week;
			
			List<SeasonGame> seasonGames = week.getSeasonGames();
			for (SeasonGame seasonGame : seasonGames) {
				Team homeTeam = seasonGame.getHomeTeam();
				Team awayTeam = seasonGame.getAwayTeam();
				
				String homeTeamName = homeTeam.getName();
				String awayTeamName = awayTeam.getName();
				
				NFLSeasonTeam homeSeasonTeam = getTeam(homeTeamName);
				NFLSeasonTeam awaySeasonTeam = getTeam(awayTeamName);
				homeSeasonTeam.addSeasonGame(weekNumber, seasonGame);
				awaySeasonTeam.addSeasonGame(weekNumber, seasonGame);
			}
		}
	}

	public void loadSeason(ScoreStripReader scoreStripReader,
			ScoreStripMapper scoreStripMapper) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		String yearString = "" + year;
		
		for (int i = 1; i <= NUMBER_OF_WEEKS_IN_SEASON; i++) {
			String url = scoreStripReader.generateScoreStripURL(yearString, i);
			try {
				Ss scoreStripWeek = scoreStripReader.readScoreStripURL(url);
				SeasonWeek seasonWeek = 
						scoreStripMapper.mapScoreStripWeekToSeasonWeek(scoreStripWeek);
				addWeek(seasonWeek);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public String getWeekString(SeasonWeek week) {
		String weekString = "This week is empty or null; please load the season";
		
		if (week != null) {
			StringBuilder weekStringBuilder = new StringBuilder();
			List<SeasonGame> weekGames = week.getSeasonGames();
			for (SeasonGame weekGame : weekGames) {
				Team homeTeam = weekGame.getHomeTeam();
				Team awayTeam = weekGame.getAwayTeam();
				weekStringBuilder.append(awayTeam.getName() + " at " + 
						homeTeam.getName() + "\n");
			}
			
			weekString = weekStringBuilder.toString();
		}
		
		return weekString;
	}

}
