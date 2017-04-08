package nfl.season.league;

import java.util.List;

public class League {
	
	public static final String NFL = "NFL";
	
	private String name;
	
	private List<Conference> conferences;
	
	public League(String name) {
		this.name = name;
	}

	public void initializeNFL() {
		
	}
	
	public String getName() {
		return name;
	}

	public List<Conference> getConferences() {
		return conferences;
	}

}
