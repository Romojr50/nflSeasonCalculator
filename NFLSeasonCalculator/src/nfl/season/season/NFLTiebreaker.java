package nfl.season.season;

public class NFLTiebreaker {

	public static NFLSeasonTeam tiebreakTeams(NFLSeasonTeam team1,
			NFLSeasonTeam team2) {
		NFLSeasonTeam tieWinner = null;
		
		double team1WinPercent = calculateTeamWinPercent(team1);
		double team2WinPercent = calculateTeamWinPercent(team2);
		
		if (team1WinPercent > team2WinPercent) {
			tieWinner = team1;
		} else if (team2WinPercent > team1WinPercent) {
			tieWinner = team2;
		}
		
		return tieWinner;
	}
	
	private static double calculateTeamWinPercent(NFLSeasonTeam team) {
		return (team.getNumberOfWins() + (team.getNumberOfTies() / 2.0)) / 
				(team.getNumberOfWins() + team.getNumberOfLosses() + team.getNumberOfTies());
	}

}
