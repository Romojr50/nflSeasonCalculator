package nfl.season.season;

import java.util.List;

public class NFLManySeasonSimulator {

	private NFLSeason season;
	
	public NFLManySeasonSimulator(NFLSeason season) {
		this.season = season;
	}

	public void simulateOneSeason() {
		season.clearSimulatedResults();
		season.simulateSeason();
		
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
