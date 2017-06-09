package nfl.season.season;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Division;
import nfl.season.league.Team;

public class NFLSeasonDivision {

	private Division leagueDivision;
	
	private List<NFLSeasonTeam> teams;
	
	private List<NFLSeasonTeam> teamsInOrder;
	
	public NFLSeasonDivision(Division leagueDivision) {
		this.leagueDivision = leagueDivision;
		teams = new ArrayList<NFLSeasonTeam>();
		teamsInOrder = new ArrayList<NFLSeasonTeam>();
	}
	
	public Division getDivision() {
		return leagueDivision;
	}

	public List<NFLSeasonTeam> getTeams() {
		return teams;
	}

	public void addTeam(NFLSeasonTeam seasonTeam) {
		teams.add(seasonTeam);
		teamsInOrder.add(seasonTeam);
	}

	public void setTeamsInOrder(NFLTiebreaker tiebreaker) {
		teamsInOrder = new ArrayList<NFLSeasonTeam>();
		
		List<NFLSeasonTeam> teamsCopy = new ArrayList<NFLSeasonTeam>();
		teamsCopy.addAll(teams);
		
		while (teamsCopy.size() > 1) {
			NFLSeasonTeam nextTeam = tiebreaker.tiebreakManyTeams(teamsCopy);
			teamsCopy.remove(nextTeam);
			teamsInOrder.add(nextTeam);
		}
		teamsInOrder.add(teamsCopy.get(0));
	}

	public List<NFLSeasonTeam> getTeamsInOrder() {
		return teamsInOrder;
	}

	public NFLSeasonTeam getDivisionWinner() {
		NFLSeasonTeam divisionWinner = null;
		
		if (teamsInOrder != null && teamsInOrder.size() > 0) {
			divisionWinner = teamsInOrder.get(0);
		}
		
		return divisionWinner;
	}

	public NFLSeasonTeam getDivisionCellar() {
		NFLSeasonTeam divisionCellar = null;
		
		if (teamsInOrder != null && teamsInOrder.size() > 0) {
			divisionCellar = teamsInOrder.get(teamsInOrder.size() - 1);
		}
		
		return divisionCellar;
	}

	public String getDivisionStandingsString(String conferenceName, 
			NFLTiebreaker tiebreaker) {
		setTeamsInOrder(tiebreaker);
		
		StringBuilder standingsBuilder = new StringBuilder();
		standingsBuilder.append(conferenceName + " " + leagueDivision.getName() + "\n");
		for (int i = 1; i <= teamsInOrder.size(); i++) {
			NFLSeasonTeam nextTeam = teamsInOrder.get(i - 1);
			Team leagueNextTeam = nextTeam.getTeam();
			String nextTeamName = leagueNextTeam.getName();
			standingsBuilder.append(i + ". " + nextTeamName + "\n");
		}
		
		String divisionStandings = standingsBuilder.toString();
		
		return divisionStandings;
	}

}
