package nfl.season.scorestrip;

import java.util.List;

import nfl.season.league.League;
import nfl.season.league.SeasonGame;
import nfl.season.league.SeasonWeek;
import nfl.season.league.Team;
import nfl.season.scorestrip.Ss.Gms;
import nfl.season.scorestrip.Ss.Gms.G;

public class ScoreStripMapper {

	private League league;
	
	public ScoreStripMapper(League league) {
		this.league = league;
	}

	public SeasonGame mapScoreStripGameToSeasonGame(G scoreStripGame) {
		SeasonGame seasonGame = null;
		
		String homeTeamName = scoreStripGame.getHnn();
		String awayTeamName = scoreStripGame.getVnn();
		
		Team homeTeam = league.getTeam(homeTeamName);
		Team awayTeam = league.getTeam(awayTeamName);
		
		if (homeTeam != null && awayTeam != null) {
			seasonGame = new SeasonGame(homeTeam, awayTeam);
			
			if (league.areInSameConference(homeTeam, awayTeam)) {
				seasonGame.setIsConferenceGame(true);
				
				if (league.areInSameDivision(homeTeam, awayTeam)) {
					seasonGame.setIsDivisionGame(true);
				}
			}
		}
		
		return seasonGame;
	}

	public SeasonWeek mapScoreStripWeekToSeasonWeek(Ss scoreStripWeek) {
		Gms scoreStripGames = scoreStripWeek.getGms();
		byte scoreStripWeekNumber = scoreStripGames.getW();
		int weekNumber = (int) scoreStripWeekNumber;
		
		SeasonWeek seasonWeek = new SeasonWeek(weekNumber);
		
		List<G> scoreStripGameList = scoreStripGames.getG();
		for (G scoreStripGame : scoreStripGameList) {
			SeasonGame seasonGame = mapScoreStripGameToSeasonGame(scoreStripGame);
			seasonWeek.addSeasonGame(seasonGame);
		}
		
		return seasonWeek;
	}

}
