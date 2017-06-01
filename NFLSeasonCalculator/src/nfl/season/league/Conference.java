package nfl.season.league;

import java.util.ArrayList;
import java.util.List;

public class Conference {

	private String name;
	
	private List<Division> divisions;

	public Conference(String name) {
		this.name = name;
		divisions = new ArrayList<Division>();
	}

	public String getName() {
		return name;
	}

	public List<Division> getDivisions() {
		return divisions;
	}

	public Division getDivision(String name) {
		Division returnDivision = null;
		
		if (name != null && !"".equals(name)) {
			for (Division division : divisions) {
				if (name.equals(division.getName())) {
					returnDivision = division;
					break;
				}
			}
		}
		
		return returnDivision;
	}

	public void createDivision(String name) {
		Division newDivision = new Division(name);
		divisions.add(newDivision);
	}

	public void addDivision(Division newDivision) {
		divisions.add(newDivision);
	}

	public List<Team> getTeams() {
		List<Team> returnTeams = new ArrayList<Team>();
		for (Division division : divisions) {
			returnTeams.addAll(division.getTeams());
		}
		return returnTeams;
	}
	
}
