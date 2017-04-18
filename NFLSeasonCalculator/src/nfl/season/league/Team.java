package nfl.season.league;

public class Team {

	private String name;
	
	private int powerRanking;

	public Team(String name) {
		this.name = name;
	}

	public Object getName() {
		return name;
	}

	public void setPowerRanking(int powerRanking) {
		this.powerRanking = powerRanking;
	}
	
}
