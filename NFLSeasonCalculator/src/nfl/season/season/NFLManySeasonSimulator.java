package nfl.season.season;

import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;

public class NFLManySeasonSimulator {

	private NFLSeason season;
	
	public NFLManySeasonSimulator(NFLSeason season) {
		this.season = season;
	}

	public void simulateOneSeason(NFLTiebreaker tiebreaker, NFLPlayoffs playoffs) {
		season.clearSimulatedResults();
		playoffs.clearPlayoffTeams();
		season.simulateSeason();
		
		season.compileLeagueResults(tiebreaker);
		
		List<NFLSeasonTeam> bottomTeams = season.getBottomTeams();
		for (NFLSeasonTeam bottomTeam : bottomTeams) {
			bottomTeam.addWasBottomTeam();
		}
		
		List<NFLSeasonConference> conferences = season.getConferences();
		for (NFLSeasonConference conference : conferences) {
			tallyPlayoffTeams(conference, playoffs);
			
			List<NFLSeasonTeam> conferenceTeams = conference.getTeams();
			for (NFLSeasonTeam conferenceTeam : conferenceTeams) {
				if (hadAWinningRecord(conferenceTeam)) {
					conferenceTeam.addHadWinningSeason();
				}
				int simulatedWins = conferenceTeam.getNumberOfWins();
				int simulatedLosses = conferenceTeam.getNumberOfLosses();
				conferenceTeam.addSimulatedWins(simulatedWins);
				conferenceTeam.addSimulatedLosses(simulatedLosses);
			}
			
			tallyDivisionTopAndBottom(conference);
		}
		
		tallyPlayoffResults(playoffs);
	}

	public void simulateManySeasons(NFLTiebreaker tiebreaker, NFLPlayoffs playoffs,
			int numberOfSeasons) {
		for (int i = 0; i < numberOfSeasons; i++) {
			simulateOneSeason(tiebreaker, playoffs);
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
	
	private void tallyPlayoffTeams(NFLSeasonConference conference, NFLPlayoffs playoffs) {
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
		
		addTeamsToNFLPlayoffs(conference, playoffs, seeds);
	}

	private boolean hadAWinningRecord(NFLSeasonTeam team) {
		double winPercent = NFLTiebreaker.calculateWinPercentFromWinsLossesAndTies(
				team.getNumberOfWins(), team.getNumberOfLosses(), team.getNumberOfTies());
		
		return (winPercent > 0.5);
	}

	private void tallyPlayoffResults(NFLPlayoffs playoffs) {
		playoffs.calculateChancesByRoundForAllPlayoffTeams();
		
		List<NFLSeasonConference> conferences = season.getConferences();
		for (NFLSeasonConference conference : conferences) {
			List<NFLSeasonTeam> seeds = conference.getSeedsInOrder();
			for (NFLSeasonTeam seed : seeds) {
				NFLPlayoffTeam playoffSeed = playoffs.getPlayoffVersionOfSeasonTeam(seed);
				seed.addToChanceToWinSuperBowl(playoffSeed.getChanceOfWinningSuperBowl());
				seed.addToChanceToWinConference(playoffSeed.getChanceOfMakingSuperBowl());
				seed.addToChanceToMakeConferenceRound(
						playoffSeed.getChanceOfMakingConferenceRound());
				seed.addToChanceToMakeDivisionalRound(
						playoffSeed.getChanceOfMakingDivisionalRound());;
			}
		}
	}
	
	private void addTeamsToNFLPlayoffs(NFLSeasonConference conference,
			NFLPlayoffs playoffs, List<NFLSeasonTeam> seeds) {
		Conference leagueConference = conference.getConference();
		String conferenceName = leagueConference.getName();
		List<NFLSeasonDivision> divisions = conference.getDivisions();
		for (NFLSeasonDivision division : divisions) {
			Division leagueDivision = division.getDivision();
			String divisionName = leagueDivision.getName();
			
			NFLSeasonTeam divisionWinner = division.getDivisionWinner();
			Team leagueDivisionWinner = divisionWinner.getTeam();
			NFLPlayoffTeam playoffDivisionWinner = playoffs.createPlayoffTeam(leagueDivisionWinner);
			playoffs.setDivisionWinner(conferenceName, divisionName, playoffDivisionWinner);
		}
		
		for (int seed = 5; seed <= seeds.size(); seed++) {
			NFLSeasonTeam wildcard = seeds.get(seed - 1);
			Team leagueWildcard = wildcard.getTeam();
			NFLPlayoffTeam playoffWildcard = playoffs.createPlayoffTeam(leagueWildcard);
			playoffs.addWildcardTeam(conferenceName, playoffWildcard);
		}
	}
	
}
