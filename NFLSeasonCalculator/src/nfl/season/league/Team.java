package nfl.season.league;

public class Team {

	public static final int CLEAR_RANKING = -1;

	public static final String UNSET_RANKING_DISPLAY = "Unset";
	
	private String name;
	
	private int powerRanking = CLEAR_RANKING;
	
	private int teamLevel = 1;

	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public int getPowerRanking() {
		return this.powerRanking;
	}

	public void setPowerRanking(int powerRanking) {
		this.powerRanking = powerRanking;
	}

	public int getTeamLevel() {
		return teamLevel;
	}

	public void setTeamLevel(int newTeamLevel) {
		this.teamLevel = newTeamLevel;
	}
	
}
