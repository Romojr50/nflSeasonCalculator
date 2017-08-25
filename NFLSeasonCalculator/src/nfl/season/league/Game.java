package nfl.season.league;

public class Game {

	private Matchup matchup;
	
	private Team homeTeam;
	
	private Team awayTeam;
	
	public Game(Matchup matchup) {
		this.matchup = matchup;
	}
	
	public Game(Team homeTeam, Team awayTeam) {
		Matchup matchup = homeTeam.getMatchup(awayTeam.getName());
		this.matchup = matchup;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}

	public Matchup getMatchup() {
		return matchup;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}
	
	public Team getAwayTeam() {
		return awayTeam;
	}

}
