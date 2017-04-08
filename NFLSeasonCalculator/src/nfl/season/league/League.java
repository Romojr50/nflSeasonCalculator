package nfl.season.league;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.enums.NFLConferenceEnum;
import nfl.season.league.enums.NFLDivisionEnum;

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
			Conference newConference = new Conference(nflConference.name());
			conferences.add(newConference);
			
			for (NFLDivisionEnum nflDivision : NFLDivisionEnum.values()) {
				newConference.createDivision(nflDivision.name());
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
