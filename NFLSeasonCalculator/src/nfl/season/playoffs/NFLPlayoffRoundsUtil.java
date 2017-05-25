package nfl.season.playoffs;

import java.util.List;

import nfl.season.league.Matchup;
import nfl.season.league.Team;

public class NFLPlayoffRoundsUtil {

	public static void calculateChancesByRoundForAllPlayoffTeams(NFLPlayoffs playoffs) {
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		for (NFLPlayoffConference playoffConference : playoffConferences) {
			double[][] conferenceMatrix = getConferenceMatrix(playoffConference);
			List<NFLPlayoffTeam> playoffTeams = playoffConference.getTeamsInSeedOrder();
			
			NFLPlayoffTeam seed1 = playoffTeams.get(0);
			NFLPlayoffTeam seed2 = playoffTeams.get(1);
			NFLPlayoffTeam seed3 = playoffTeams.get(2);
			NFLPlayoffTeam seed4 = playoffTeams.get(3);
			NFLPlayoffTeam seed5 = playoffTeams.get(4);
			NFLPlayoffTeam seed6 = playoffTeams.get(5);
			
			seed1.setChanceOfMakingDivisionalRound(100);
			seed2.setChanceOfMakingDivisionalRound(100);
			seed3.setChanceOfMakingDivisionalRound((int) Math.round((conferenceMatrix[2][5] * 100)));
			seed4.setChanceOfMakingDivisionalRound((int) Math.round((conferenceMatrix[3][4] * 100)));
			seed5.setChanceOfMakingDivisionalRound((int) Math.round((conferenceMatrix[4][3] * 100)));
			seed6.setChanceOfMakingDivisionalRound((int) Math.round((conferenceMatrix[5][2] * 100)));
			
			seed1.setChanceOfMakingConferenceRound((int) Math.round((
					(conferenceMatrix[0][3] * conferenceMatrix[2][5] * conferenceMatrix[3][4]) + 
					(conferenceMatrix[0][4] * conferenceMatrix[2][5] * conferenceMatrix[4][3]) + 
					(conferenceMatrix[0][5] * conferenceMatrix[5][2])) * 100));
			seed2.setChanceOfMakingConferenceRound((int) Math.round((
					(conferenceMatrix[1][2] * conferenceMatrix[2][5]) +
					(conferenceMatrix[1][3] * conferenceMatrix[5][2] * conferenceMatrix[3][4]) +
					(conferenceMatrix[1][4] * conferenceMatrix[5][2] * conferenceMatrix[4][3])) * 100));
			seed3.setChanceOfMakingConferenceRound((int) Math.round((
					(seed3.getChanceOfMakingDivisionalRound() / 100.0) *
					conferenceMatrix[2][1]) * 100));
			seed4.setChanceOfMakingConferenceRound((int) Math.round((
					(seed4.getChanceOfMakingDivisionalRound() / 100.0) *
					((conferenceMatrix[4][0] * conferenceMatrix[2][5]) +
					(conferenceMatrix[4][1] * conferenceMatrix[5][2]))) * 100));
			seed5.setChanceOfMakingConferenceRound((int) Math.round((
					(seed5.getChanceOfMakingDivisionalRound() / 100.0) * 
					((conferenceMatrix[4][0] * conferenceMatrix[2][5]) +
					(conferenceMatrix[4][1] * conferenceMatrix[5][2]))) * 100));
			seed6.setChanceOfMakingConferenceRound((int) Math.round((
					(seed6.getChanceOfMakingDivisionalRound() / 100.0) *
					(conferenceMatrix[5][0])) * 100));
//			=C11*((C12*C2) + (C13*D2) + (D7*F5*C5*E2) + (D7*E6*C6*F2))
			seed1.setChanceOfMakingSuperBowl((int) Math.round((
					(seed1.getChanceOfMakingConferenceRound() / 100.0) *
					((seed2.getChanceOfMakingConferenceRound() / 100.0 * conferenceMatrix[0][1]) +
					(seed3.getChanceOfMakingConferenceRound() / 100.0 * conferenceMatrix[0][2]) +
					(conferenceMatrix[0][3] * conferenceMatrix[5][2] * conferenceMatrix[3][4] * conferenceMatrix[3][1]) +
					(conferenceMatrix[0][4] * conferenceMatrix[5][2] * conferenceMatrix[4][3] * conferenceMatrix[4][1]))) * 100));
		}
	}

	private static double[][] getConferenceMatrix(
			NFLPlayoffConference playoffConference) {
		double[][] conferenceMatrix = new double[6][6];
		
		List<NFLPlayoffTeam> seeds = playoffConference.getTeamsInSeedOrder();
		for (int seed = 1; seed <= 6; seed++) {
			NFLPlayoffTeam currentSeed = seeds.get(seed - 1);
			Team currentSeedTeam = currentSeed.getTeam();
			String teamName = currentSeedTeam.getName();
			for (int opponentSeed = 1; opponentSeed <= 6; opponentSeed++) {
				if (seed != opponentSeed) {
					NFLPlayoffTeam playoffOpponent = seeds.get(opponentSeed - 1);
					Team opponentTeam = playoffOpponent.getTeam();
					String opponentName = opponentTeam.getName();
					
					Matchup matchup = currentSeedTeam.getMatchup(opponentName);
					double winChance = 0;
					if (seed < opponentSeed) {
						winChance = matchup.getTeamHomeWinChance(teamName) / 100.0;
					} else if (opponentSeed < seed) {
						winChance = matchup.getTeamAwayWinChance(teamName) / 100.0;
					}
					
					conferenceMatrix[seed - 1][opponentSeed - 1] = winChance;
				}
			}
		}
		
		return conferenceMatrix;
	}
	
}
