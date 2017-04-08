package nfl.season.league;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.enums.NFLConferenceEnum;
import nfl.season.league.enums.NFLDivisionEnum;
import nfl.season.league.enums.NFLTeamEnum;

public class League {
	
	public static final String NFL = "NFL";
	
	private String name;
	
	private List<Conference> conferences;
	
	public League(String name) {
		this.name = name;
	}

	public void initializeNFL() {
		conferences = new ArrayList<Conference>();
		for (NFLConferenceEnum nflConference : NFLConferenceEnum.values()) {
			String conferenceName = nflConference.name();
			Conference newConference = new Conference(conferenceName);
			conferences.add(newConference);
			
			for (NFLDivisionEnum nflDivision : NFLDivisionEnum.values()) {
				String divisionName = nflDivision.name();
				Division newDivision = new Division(divisionName);
				newConference.addDivision(newDivision);
				
				for (NFLTeamEnum nflTeam : NFLTeamEnum.values()) {
					if (nflTeam.getConference().name().equals(conferenceName) 
							&& nflTeam.getDivision().name().equals(divisionName)) {
						Team newTeam = new Team(nflTeam.name());
						newDivision.addTeam(newTeam);
					}
				}
			}
		}
	}
	
	public String getName() {
		return name;
	}

	public List<Conference> getConferences() {
		return conferences;
	}

	public Conference getConference(String inputName) {
		Conference returnConference = null;
		if (inputName != null && !"".equals(inputName) && conferences != null) {
			for (Conference conference : conferences) {
				String conferenceName = conference.getName();
				if (inputName.equals(conferenceName)) {
					returnConference = conference;
					break;
				}
			}
		}
		return returnConference;
	}

}
