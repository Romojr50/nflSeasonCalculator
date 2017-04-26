package nfl.season.league;

public class Matchup {

	private Team team1;
	
	private Team team2;
	
	public Matchup(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
	}
	
	public String getOpponentName(String teamName) {
		String opponentName = "";
		String team1Name = team1.getName();
		String team2Name = team2.getName();
		
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1Name)) {
				opponentName = team2Name;
			} else if (teamName.equalsIgnoreCase(team2Name)) {
				opponentName = team1Name;
			}
		}
		
		return opponentName;
	}

}
