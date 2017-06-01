package nfl.season.league;

import java.util.ArrayList;
import java.util.List;

public class Division {

	private String name;
	
	private List<Team> teams;

	public Division(String name) {
		this.name = name;
		teams = new ArrayList<Team>();
	}

	public String getName() {
		return name;
	}

	public Team getTeam(String name) {
		Team returnTeam = null;
		
		if (name != null && !"".equals(name)) {
			for (Team team : teams) {
				if (name.equals(team.getName())) {
					returnTeam = team;
					break;
				}
			}
		}
		
		return returnTeam;
	}
	
	public List<Team> getTeams() {
		return teams;
	}

	public void addTeam(Team newTeam) {
		teams.add(newTeam);
	}
	
}
