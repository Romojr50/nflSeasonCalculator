package nfl.season.league;

public class Team {

	public static final int CLEAR_RANKING = -1;

	public static final String UNSET_RANKING_DISPLAY = "Unset";
	
	private String name;
	
	private int powerRanking = CLEAR_RANKING;

	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPowerRanking(int powerRanking) {
		this.powerRanking = powerRanking;
	}

	public int getPowerRanking() {
		return this.powerRanking;
	}
	
}
