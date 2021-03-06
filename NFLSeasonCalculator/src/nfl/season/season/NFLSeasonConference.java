package nfl.season.season;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Team;

public class NFLSeasonConference implements Serializable {

	private static final long serialVersionUID = 4373781177415763621L;

	private Conference leagueConference;
	
	private List<NFLSeasonDivision> divisions;
	
	private List<NFLSeasonTeam> seedsInOrder;
	
	public NFLSeasonConference(Conference leagueConference) {
		this.leagueConference = leagueConference;
		divisions = new ArrayList<NFLSeasonDivision>();
		seedsInOrder = new ArrayList<NFLSeasonTeam>();
	}
	
	public Conference getConference() {
		return leagueConference;
	}

	public List<NFLSeasonDivision> getDivisions() {
		return divisions;
	}

	public void addDivision(NFLSeasonDivision seasonDivision) {
		divisions.add(seasonDivision);
	}
	
	public List<NFLSeasonTeam> getTeams() {
		List<NFLSeasonTeam> allTeams = new ArrayList<NFLSeasonTeam>();
		
		for (NFLSeasonDivision division : divisions) {
			allTeams.addAll(division.getTeams());
		}
		
		return allTeams;
	}

	public List<NFLSeasonTeam> getSeedsInOrder() {
		return seedsInOrder;
	}
	
	public void setSeedsInOrder(NFLTiebreaker tiebreaker) {
		seedsInOrder = new ArrayList<NFLSeasonTeam>();
		
		List<NFLSeasonTeam> divisionWinners = new ArrayList<NFLSeasonTeam>();
		List<NFLSeasonTeam> nonDivisionWinners = new ArrayList<NFLSeasonTeam>();
		for (NFLSeasonDivision division : divisions) {
			NFLSeasonTeam divisionWinner = division.getDivisionWinner();
			divisionWinners.add(divisionWinner);
			
			nonDivisionWinners.addAll(division.getTeams());
			nonDivisionWinners.remove(divisionWinner);
		}
		
		while (divisionWinners.size() > 1) {
			NFLSeasonTeam nextSeed = tiebreaker.tiebreakManyTeams(divisionWinners);
			seedsInOrder.add(nextSeed);
			divisionWinners.remove(nextSeed);
		}
		seedsInOrder.add(divisionWinners.get(0));
		
		for (int i = 0; i < 2; i++) {
			NFLSeasonTeam nextSeed = tiebreaker.tiebreakManyTeams(nonDivisionWinners);
			seedsInOrder.add(nextSeed);
			nonDivisionWinners.remove(nextSeed);
		}
	}
	
	public void compileConferenceResults(NFLTiebreaker tiebreaker) {
		for (NFLSeasonDivision division : divisions) {
			division.setTeamsInOrder(tiebreaker);
		}
		setSeedsInOrder(tiebreaker);
	}

	public String getConferenceStandingsString(NFLTiebreaker tiebreaker) {
		StringBuilder standingsBuilder = new StringBuilder();
		for (NFLSeasonDivision division : divisions) {
			standingsBuilder.append(division.getDivisionStandingsString(
					leagueConference.getName(), tiebreaker));
		}

		String conferenceName = leagueConference.getName();
		standingsBuilder.append("\n");
		standingsBuilder.append(conferenceName + " Seeds:\n");
		setSeedsInOrder(tiebreaker);
		
		for (int i = 1; i <= seedsInOrder.size(); i++) {
			NFLSeasonTeam seed = seedsInOrder.get(i - 1);
			Team leagueSeed = seed.getTeam();
			String seedName = leagueSeed.getName();
			standingsBuilder.append(i + ". " + seedName + "\n");
		}
		
		return standingsBuilder.toString();
	}

}
