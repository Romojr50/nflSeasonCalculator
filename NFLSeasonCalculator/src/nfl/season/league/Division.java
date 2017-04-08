package nfl.season.league;

import java.util.List;

public class Division {

	private String name;
	
	private List<Team> teams;

	public Division(String name) {
		this.name = name;
	}

	public Object getName() {
		return name;
	}
	
}
