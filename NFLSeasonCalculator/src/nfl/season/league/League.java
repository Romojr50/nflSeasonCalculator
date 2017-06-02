package nfl.season.league;

import java.util.ArrayList;
import java.util.List;

public class League {
	
	public static final String NFL = "NFL";
	
	private String name;
	
	private List<Conference> conferences;
	
	private List<Team> allTeams;
	
	public League(String name) {
		this.name = name;
		allTeams = new ArrayList<Team>();
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
						initializeTeam(newDivision, nflTeam);
					}
				}
			}
			
			initializeMatchups();
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

	public Team getTeam(int teamIndex) {
		Team returnTeam = null;
		int maxIndex = NFLTeamEnum.values().length;
		
		if (teamIndex > 0 && teamIndex <= maxIndex) {
			String teamName = NFLTeamEnum.values()[teamIndex - 1].getTeamName();
			
			for (Conference conference : conferences) {
				List<Division> divisions = conference.getDivisions();
				for (Division division : divisions) {
					List<Team> teams = division.getTeams();
					for (Team team : teams) {
						if (teamName.equals(team.getName())) {
							returnTeam = team;
							break;
						}
					}
				}
			}
		}
		
		return returnTeam;
	}
	
	public Team getTeam(String teamName) {
		Team returnTeam = null;
		
		if (teamName != null) {
			for (Team team : allTeams) {
				if (teamName.equalsIgnoreCase(team.getName())) {
					returnTeam = team;
					break;
				}
			}
		}
		
		return returnTeam;
	}

	public List<Team> getTeams() {
		List<Team> returnTeams = new ArrayList<Team>();
		returnTeams.addAll(allTeams);
		return returnTeams;
	}

	public Team getTeamWithPowerRanking(int powerRanking) {
		Team returnTeam = null;
		for (Team team : allTeams) {
			int teamPowerRanking = team.getPowerRanking();
			if (powerRanking == teamPowerRanking) {
				returnTeam = team;
				break;
			}
		}
		return returnTeam;
	}
	
	public boolean areInSameDivision(Team team1, Team team2) {
		boolean inSameDivision = false;
		
		String team1Name = team1.getName();
		String team2Name = team2.getName();
		
		for (Conference conference : conferences) {
			Team team1InConference = conference.getTeam(team1Name);
			if (team1InConference != null) {
				if (conference.getTeam(team2Name) != null) {
					inSameDivision = conferenceTeamsAreInSameDivision(
							team1Name, team2Name, conference);
				} else {
					break;
				}
			}
		}
		
		return inSameDivision;
	}

	public boolean areInSameConference(Team team1, Team team2) {
		boolean inSameConference = false;
		
		String team1Name = team1.getName();
		String team2Name = team2.getName();
		
		for (Conference conference : conferences) {
			Team team1InConference = conference.getTeam(team1Name);
			if (team1InConference != null) {
				if (conference.getTeam(team2Name) != null) {
					inSameConference = true;
				} else {
					break;
				}
			}
		}
		
		return inSameConference;
	}

	private void initializeTeam(Division newDivision, NFLTeamEnum nflTeam) {
		String newTeamName = nflTeam.getTeamName();
		Team newTeam = new Team(newTeamName, nflTeam.getDefaultPowerRanking(), 
				nflTeam.getDefaultEloRating(), nflTeam.getDefaultHomeFieldAdvantage());
		newDivision.addTeam(newTeam);
		allTeams.add(newTeam);
	}
	
	private void initializeMatchups() {
		for (Team team : allTeams) {
			String teamName = team.getName();
			for (Team opponent : allTeams) {
				String opponentName = opponent.getName();
				if (!teamName.equalsIgnoreCase(opponentName)) {
					Matchup matchup = team.getMatchup(opponentName);
					if (matchup == null) {
						Matchup newMatchup = new Matchup(team, opponent);
						newMatchup.calculateTeamWinChancesFromPowerRankings();
						newMatchup.calculateHomeWinChanceFromHomeFieldAdvantage(teamName);
						newMatchup.calculateHomeWinChanceFromHomeFieldAdvantage(opponentName);
						team.addMatchup(newMatchup);
						opponent.addMatchup(newMatchup);
					}
				}
			}
		}
	}

	private boolean conferenceTeamsAreInSameDivision(String team1Name, 
			String team2Name, Conference conference) {
		boolean inSameDivision = false;
		
		List<Division> divisions = conference.getDivisions();
		for (Division division : divisions) {
			Team team1InDivision = division.getTeam(team1Name);
			if (team1InDivision != null) {
				if (division.getTeam(team2Name) != null) {
					inSameDivision = true;
				} else {
					break;
				}
			}
		}
		
		return inSameDivision;
	}

}
