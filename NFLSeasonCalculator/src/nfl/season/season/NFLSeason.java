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

	public static final int MANY_SEASONS_NUMBER = 10000;

	private League league;
	
	private List<NFLSeasonConference> conferences;
	
	private List<NFLSeasonTeam> bottomTeams;
	
	private SeasonWeek[] weeks;
	
	public NFLSeason() {
		conferences = new ArrayList<NFLSeasonConference>();
		bottomTeams = new ArrayList<NFLSeasonTeam>();
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
	
	public List<NFLSeasonTeam> getBottomTeams() {
		return bottomTeams;
	}
	
	public void setBottomTeams(NFLTiebreaker tiebreaker) {
		bottomTeams = new ArrayList<NFLSeasonTeam>();
		
		List<NFLSeasonTeam> seededTeams = new ArrayList<NFLSeasonTeam>();
		List<NFLSeasonTeam> nonSeededTeams = new ArrayList<NFLSeasonTeam>();
		for (NFLSeasonConference conference : conferences) {
			seededTeams.addAll(conference.getSeedsInOrder());
			nonSeededTeams.addAll(conference.getTeams());
		}
		nonSeededTeams.removeAll(seededTeams);
		
		while (nonSeededTeams.size() > 5) {
			nonSeededTeams.remove(tiebreaker.tiebreakManyTeams(nonSeededTeams));
		}
		
		while (nonSeededTeams.size() > 1) {
			NFLSeasonTeam nextTeam = tiebreaker.tiebreakManyTeams(nonSeededTeams);
			bottomTeams.add(0, nextTeam);
			nonSeededTeams.remove(nextTeam);
		}
		bottomTeams.add(0, nonSeededTeams.remove(0));
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
				appendGameResultToStringBuilder(weekStringBuilder, weekGame);
			}
			
			weekString = weekStringBuilder.toString();
		}
		
		return weekString;
	}
	
	public static void appendGameResultToStringBuilder(StringBuilder scheduleBuilder,
			SeasonGame seasonGame) {
		if (seasonGame.alreadyHappened()) {
			Team winner = seasonGame.getWinner();
			
			if (winner != null) {
				String winnerName = winner.getName();
				scheduleBuilder.append(", " + winnerName);
			} else if (seasonGame.wasATie()) {
				scheduleBuilder.append(", Tie");
			}
		}
	}
	
	public void compileLeagueResults(NFLTiebreaker tiebreaker) {
		for (NFLSeasonConference seasonConference : conferences) {
			seasonConference.compileConferenceResults(tiebreaker);
		}
		setBottomTeams(tiebreaker);
	}

	public String getLeagueStandings(NFLTiebreaker tiebreaker) {
		StringBuilder standingsBuilder = new StringBuilder();
		for (NFLSeasonConference seasonConference : conferences) {
			standingsBuilder.append(seasonConference.getConferenceStandingsString(
					tiebreaker));
			standingsBuilder.append("\n");
		}
		
		setBottomTeams(tiebreaker);
		
		standingsBuilder.append("Bottom Teams:\n");
		for (int i = 1; i <= bottomTeams.size(); i++) {
			NFLSeasonTeam bottomTeam = bottomTeams.get(i - 1);
			Team leagueBottomTeam = bottomTeam.getTeam();
			String bottomTeamName = leagueBottomTeam.getName();
			standingsBuilder.append(i + ". " + bottomTeamName + "\n");
		}
		
		return standingsBuilder.toString();
	}

	public NFLTiebreaker createNFLTiebreaker() {
		return new NFLTiebreaker(this);
	}
	
	public NFLManySeasonSimulator createManySeasonsSimulator() {
		return new NFLManySeasonSimulator(this);
	}

	public void simulateSeason() {
		for (NFLSeasonConference conference : conferences) {
			List<NFLSeasonTeam> conferenceTeams = conference.getTeams();
			for (NFLSeasonTeam team : conferenceTeams) {
				team.simulateSeason();
			}
		}
	}

	public void clearSimulatedResults() {
		for (NFLSeasonConference conference : conferences) {
			List<NFLSeasonTeam> conferenceTeams = conference.getTeams();
			for (NFLSeasonTeam team : conferenceTeams) {
				team.clearSimulatedGames();
			}
		}
	}

}
