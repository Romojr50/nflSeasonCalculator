package nfl.season.league;

public class Matchup {

	private String team1Name;
	
	private String team2Name;
	
	public Matchup(String team1Name, String team2Name) {
		this.team1Name = team1Name;
		this.team2Name = team2Name;
	}
	
	public String getOpponentName(String teamName) {
		String opponentName = "";
		
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
