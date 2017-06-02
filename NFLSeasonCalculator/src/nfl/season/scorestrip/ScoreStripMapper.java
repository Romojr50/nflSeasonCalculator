package nfl.season.scorestrip;

import nfl.season.league.League;
import nfl.season.league.SeasonGame;
import nfl.season.league.Team;
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

}
