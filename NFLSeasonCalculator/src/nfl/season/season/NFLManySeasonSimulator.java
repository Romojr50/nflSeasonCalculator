package nfl.season.season;

import java.util.List;

public class NFLManySeasonSimulator {

	private NFLSeason season;
	
	public NFLManySeasonSimulator(NFLSeason season) {
		this.season = season;
	}

	public void simulateOneSeason(NFLTiebreaker tiebreaker) {
		season.clearSimulatedResults();
		season.simulateSeason();
		
		season.compileLeagueResults(tiebreaker);
		
		List<NFLSeasonTeam> bottomTeams = season.getBottomTeams();
		for (NFLSeasonTeam bottomTeam : bottomTeams) {
			bottomTeam.addWasBottomTeam();
		}
		
		List<NFLSeasonConference> conferences = season.getConferences();
		for (NFLSeasonConference conference : conferences) {
			tallyPlayoffTeams(conference);
			
			List<NFLSeasonTeam> conferenceTeams = conference.getTeams();
			for (NFLSeasonTeam conferenceTeam : conferenceTeams) {
				if (hadAWinningRecord(conferenceTeam)) {
					conferenceTeam.addHadWinningSeason();
				}
			}
			
			tallyDivisionTopAndBottom(conference);
		}
	}

	public void simulateManySeasons(NFLTiebreaker tiebreaker, int numberOfSeasons) {
		for (int i = 0; i < numberOfSeasons; i++) {
			simulateOneSeason(tiebreaker);
		}
	}
	
	public void clearSimulations() {
		season.clearSimulatedResults();
		
		List<NFLSeasonConference> conferences = season.getConferences();
		for (NFLSeasonConference conference : conferences) {
			List<NFLSeasonTeam> teams = conference.getTeams();
			for (NFLSeasonTeam team : teams) {
				team.clearSimulatedResults();
			}
		}
	}

	private void tallyDivisionTopAndBottom(NFLSeasonConference conference) {
		List<NFLSeasonDivision> conferenceDivisions = conference.getDivisions();
		for (NFLSeasonDivision conferenceDivision : conferenceDivisions) {
			NFLSeasonTeam divisionWinner = conferenceDivision.getDivisionWinner();
			divisionWinner.addWonDivision();
			NFLSeasonTeam divisionCellar = conferenceDivision.getDivisionCellar();
			divisionCellar.addWasInDivisionCellar();
		}
	}
	
	private void tallyPlayoffTeams(NFLSeasonConference conference) {
		List<NFLSeasonTeam> seeds = conference.getSeedsInOrder();
		for (int seed = 1; seed <= seeds.size(); seed++) {
			NFLSeasonTeam seedTeam = seeds.get(seed - 1);
			seedTeam.addMadePlayoffs();
			
			if (seed == 1) {
				seedTeam.addGotOneSeed();
			}
			if (seed <= 2) {
				seedTeam.addGotRoundOneBye();
			}
		}
	}

	private boolean hadAWinningRecord(NFLSeasonTeam team) {
		double winPercent = NFLTiebreaker.calculateWinPercentFromWinsLossesAndTies(
				team.getNumberOfWins(), team.getNumberOfLosses(), team.getNumberOfTies());
		
		return (winPercent > 0.5);
	}

}
