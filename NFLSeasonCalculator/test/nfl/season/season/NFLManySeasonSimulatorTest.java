package nfl.season.season;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLManySeasonSimulatorTest {

	@Mock
	private NFLSeason season;
	
	@Mock
	private NFLSeasonTeam bottomTeam1;
	
	@Mock
	private NFLSeasonTeam bottomTeam2;
	
	private List<NFLSeasonTeam> bottomTeams;
	
	@Mock
	private NFLSeasonConference conference1;
	
	@Mock
	private Conference leagueConference1;
	
	private String conference1Name = "Conf 1";
	
	@Mock
	private NFLSeasonDivision division1_1;
	
	@Mock
	private Division leagueDivision1_1;
	
	private String division1_1Name = "Div 1 - 1";
	
	@Mock
	private NFLSeasonDivision division1_2;
	
	@Mock
	private Division leagueDivision1_2;
	
	@Mock
	private NFLSeasonDivision division1_3;
	
	@Mock
	private Division leagueDivision1_3;
	
	private List<NFLSeasonDivision> conference1Divisions;
	
	@Mock
	private NFLSeasonTeam divisionWinner1_1;
	
	@Mock
	private NFLSeasonTeam divisionWinner1_2;
	
	@Mock
	private NFLSeasonTeam divisionWinner1_3;
	
	@Mock
	private NFLSeasonTeam divisionCellar1_1;
	
	@Mock
	private NFLSeasonTeam divisionCellar1_2;
	
	@Mock
	private NFLSeasonTeam divisionCellar1_3;
	
	@Mock
	private NFLSeasonTeam wildcard1_1;
	
	@Mock
	private NFLSeasonTeam wildcard1_2;
	
	private List<NFLSeasonTeam> conference1Teams;
	
	@Mock
	private NFLSeasonConference conference2;
	
	@Mock
	private Conference leagueConference2;
	
	@Mock
	private NFLSeasonDivision division2_1;
	
	@Mock
	private Division leagueDivision2_1;
	
	@Mock
	private NFLSeasonDivision division2_2;
	
	@Mock
	private Division leagueDivision2_2;
	
	@Mock
	private NFLSeasonDivision division2_3;
	
	@Mock
	private Division leagueDivision2_3;
	
	@Mock
	private NFLSeasonTeam divisionCellar2_1;
	
	@Mock
	private NFLSeasonTeam divisionCellar2_2;
	
	@Mock
	private NFLSeasonTeam divisionCellar2_3;
	
	private List<NFLSeasonDivision> conference2Divisions;
	
	@Mock
	private NFLSeasonTeam divisionWinner2_1;
	
	@Mock
	private NFLSeasonTeam divisionWinner2_2;
	
	@Mock
	private NFLSeasonTeam divisionWinner2_3;
	
	@Mock
	private NFLSeasonTeam wildcard2_1;
	
	@Mock
	private NFLSeasonTeam wildcard2_2;
	
	private List<NFLSeasonTeam> conference2Teams;
	
	private List<NFLSeasonConference> conferences;
	
	@Mock
	private NFLTiebreaker tiebreaker;
	
	@Mock
	private NFLPlayoffs playoffs;
	
	@Mock
	private NFLPlayoffTeam playoffDivisionWinner1_1;
	
	@Mock
	private Team leagueDivisionWinner1_1;
	
	@Mock
	private NFLPlayoffTeam playoffWildcard1_2;
	
	@Mock
	private NFLPlayoffTeam mockPlayoffTeam;
	
	@Mock
	private Team leagueWildcard1_2;
	
	private NFLManySeasonSimulator simulator;
	
	@Before
	public void setUp() {
		simulator = new NFLManySeasonSimulator(season);
		
		bottomTeams = new ArrayList<NFLSeasonTeam>();
		bottomTeams.add(bottomTeam1);
		bottomTeams.add(bottomTeam2);
		when(season.getBottomTeams()).thenReturn(bottomTeams);
		
		conferences = new ArrayList<NFLSeasonConference>();
		conferences.add(conference1);
		conferences.add(conference2);
		when(season.getConferences()).thenReturn(conferences);
		
		conference1Divisions = new ArrayList<NFLSeasonDivision>();
		conference1Divisions.add(division1_1);
		conference1Divisions.add(division1_2);
		conference1Divisions.add(division1_3);
		when(conference1.getDivisions()).thenReturn(conference1Divisions);
		when(conference1.getConference()).thenReturn(leagueConference1);
		when(leagueConference1.getName()).thenReturn(conference1Name);
		
		conference1Teams = new ArrayList<NFLSeasonTeam>();
		conference1Teams.add(divisionWinner1_1);
		conference1Teams.add(divisionWinner1_2);
		conference1Teams.add(divisionWinner1_3);
		conference1Teams.add(wildcard1_1);
		conference1Teams.add(wildcard1_2);
		when(conference1.getSeedsInOrder()).thenReturn(conference1Teams);
		when(conference1.getTeams()).thenReturn(conference1Teams);
		
		conference2Divisions = new ArrayList<NFLSeasonDivision>();
		conference2Divisions.add(division2_1);
		conference2Divisions.add(division2_2);
		conference2Divisions.add(division2_3);
		when(conference2.getDivisions()).thenReturn(conference2Divisions);
		when(conference2.getConference()).thenReturn(leagueConference2);
		
		conference2Teams = new ArrayList<NFLSeasonTeam>();
		conference2Teams.add(divisionWinner2_1);
		conference2Teams.add(divisionWinner2_2);
		conference2Teams.add(divisionWinner2_3);
		conference2Teams.add(wildcard2_1);
		conference2Teams.add(wildcard2_2);
		when(conference2.getSeedsInOrder()).thenReturn(conference2Teams);
		when(conference2.getTeams()).thenReturn(conference2Teams);
		
		when(division1_1.getDivision()).thenReturn(leagueDivision1_1);
		when(leagueDivision1_1.getName()).thenReturn(division1_1Name);
		when(division1_1.getDivisionWinner()).thenReturn(divisionWinner1_1);
		when(division1_1.getDivisionCellar()).thenReturn(divisionCellar1_1);
		when(division1_2.getDivision()).thenReturn(leagueDivision1_2);
		when(division1_2.getDivisionWinner()).thenReturn(divisionWinner1_2);
		when(division1_2.getDivisionCellar()).thenReturn(divisionCellar1_2);
		when(division1_3.getDivision()).thenReturn(leagueDivision1_3);
		when(division1_3.getDivisionWinner()).thenReturn(divisionWinner1_3);
		when(division1_3.getDivisionCellar()).thenReturn(divisionCellar1_3);
		
		when(divisionWinner1_1.getTeam()).thenReturn(leagueDivisionWinner1_1);
		when(divisionWinner1_1.getNumberOfWins()).thenReturn(9);
		when(divisionWinner1_1.getNumberOfLosses()).thenReturn(7);
		when(divisionWinner1_2.getNumberOfWins()).thenReturn(9);
		when(divisionWinner1_2.getNumberOfLosses()).thenReturn(7);
		when(divisionWinner1_3.getNumberOfWins()).thenReturn(9);
		when(divisionWinner1_3.getNumberOfLosses()).thenReturn(7);
		when(wildcard1_1.getNumberOfWins()).thenReturn(9);
		when(wildcard1_1.getNumberOfLosses()).thenReturn(7);
		when(wildcard1_2.getTeam()).thenReturn(leagueWildcard1_2);
		when(wildcard1_2.getNumberOfWins()).thenReturn(8);
		when(wildcard1_2.getNumberOfLosses()).thenReturn(8);
		
		when(division2_1.getDivision()).thenReturn(leagueDivision2_1);
		when(division2_1.getDivisionWinner()).thenReturn(divisionWinner2_1);
		when(division2_1.getDivisionCellar()).thenReturn(divisionCellar2_1);
		when(division2_2.getDivision()).thenReturn(leagueDivision2_2);
		when(division2_2.getDivisionWinner()).thenReturn(divisionWinner2_2);
		when(division2_2.getDivisionCellar()).thenReturn(divisionCellar2_2);
		when(division2_3.getDivision()).thenReturn(leagueDivision2_3);
		when(division2_3.getDivisionWinner()).thenReturn(divisionWinner2_3);
		when(division2_3.getDivisionCellar()).thenReturn(divisionCellar2_3);
		
		when(divisionWinner2_1.getNumberOfWins()).thenReturn(9);
		when(divisionWinner2_1.getNumberOfLosses()).thenReturn(7);
		when(divisionWinner2_2.getNumberOfWins()).thenReturn(9);
		when(divisionWinner2_2.getNumberOfLosses()).thenReturn(7);
		when(divisionWinner2_3.getNumberOfWins()).thenReturn(9);
		when(divisionWinner2_3.getNumberOfLosses()).thenReturn(7);
		when(wildcard2_1.getNumberOfWins()).thenReturn(9);
		when(wildcard2_1.getNumberOfLosses()).thenReturn(7);
		when(wildcard2_2.getNumberOfWins()).thenReturn(8);
		when(wildcard2_2.getNumberOfLosses()).thenReturn(8);
		
		when(bottomTeam1.getNumberOfWins()).thenReturn(2);
		when(bottomTeam1.getNumberOfLosses()).thenReturn(14);
		when(bottomTeam2.getNumberOfWins()).thenReturn(2);
		when(bottomTeam2.getNumberOfLosses()).thenReturn(14);
		
		when(playoffs.createPlayoffTeam(leagueDivisionWinner1_1)).thenReturn(
				playoffDivisionWinner1_1);
		when(playoffs.createPlayoffTeam(leagueWildcard1_2)).thenReturn(
				playoffWildcard1_2);
		when(playoffs.getPlayoffVersionOfSeasonTeam(divisionWinner1_1)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(divisionWinner1_2)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(divisionWinner1_3)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(wildcard1_1)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(wildcard1_2)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(divisionWinner2_1)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(divisionWinner2_2)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(divisionWinner2_3)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(wildcard2_1)).thenReturn(mockPlayoffTeam);
		when(playoffs.getPlayoffVersionOfSeasonTeam(wildcard2_2)).thenReturn(mockPlayoffTeam);
	}
	
	@Test
	public void simulatorSimulatesOneSeasonAndPutsResultsOnTeams() {
		when(playoffs.getPlayoffVersionOfSeasonTeam(divisionWinner1_1)).thenReturn(
				playoffDivisionWinner1_1);
		when(playoffDivisionWinner1_1.getChanceOfWinningSuperBowl()).thenReturn(11);
		when(playoffDivisionWinner1_1.getChanceOfMakingSuperBowl()).thenReturn(22);
		when(playoffDivisionWinner1_1.getChanceOfMakingConferenceRound()).thenReturn(33);
		when(playoffDivisionWinner1_1.getChanceOfMakingDivisionalRound()).thenReturn(44);
		
		simulator.simulateOneSeason(tiebreaker, playoffs);
		
		verify(season).clearSimulatedResults();
		verify(season).simulateSeason();
		verify(season).compileLeagueResults(tiebreaker);
		
		verifyTalliesForPlayoffTeams();
		verifyTalliesForBadTeams();
		verify(divisionWinner1_1).addSimulatedWins(9);
		verify(divisionWinner1_1).addSimulatedLosses(7);
		verify(wildcard1_1).addSimulatedWins(9);
		verify(wildcard1_1).addSimulatedLosses(7);
		verify(divisionWinner2_1).addSimulatedWins(9);
		verify(divisionWinner2_1).addSimulatedLosses(7);
		
		verify(divisionWinner1_1).addToChanceToWinSuperBowl(11);
		verify(divisionWinner1_1).addToChanceToWinConference(22);
		verify(divisionWinner1_1).addToChanceToMakeConferenceRound(33);
		verify(divisionWinner1_1).addToChanceToMakeDivisionalRound(44);
		
		verify(playoffs).clearPlayoffTeams();
		verify(playoffs).createPlayoffTeam(leagueDivisionWinner1_1);
		verify(playoffs).setDivisionWinner(conference1Name, division1_1Name, playoffDivisionWinner1_1);
		verify(playoffs).setTeamConferenceSeed(playoffDivisionWinner1_1, 1);
		verify(playoffs).addWildcardTeam(conference1Name, playoffWildcard1_2);
		verify(playoffs).calculateChancesByRoundForAllPlayoffTeams();
	}
	
	@Test
	public void simulatorClearsSimulatedResults() {
		simulator.clearSimulations();
		
		verify(season).clearSimulatedResults();
		
		verify(divisionWinner1_1).clearSimulatedResults();
		verify(wildcard2_2).clearSimulatedResults();
	}
	
	@Test
	public void simulatorSimulatesSeveralSeasons() {
		simulator.simulateManySeasons(tiebreaker, playoffs, 100);
		
		verify(season, times(100)).clearSimulatedResults();
		verify(season, times(100)).simulateSeason();
		
		verify(divisionWinner1_1, times(100)).addGotOneSeed();
		verify(divisionWinner1_1, times(100)).addGotRoundOneBye();
		verify(divisionWinner1_1, times(100)).addWonDivision();
		verify(divisionWinner1_1, times(100)).addMadePlayoffs();
		verify(divisionWinner1_1, times(100)).addHadWinningSeason();
	}

	private void verifyTalliesForPlayoffTeams() {
		verify(divisionWinner1_1).addGotOneSeed();
		verify(divisionWinner1_1).addGotRoundOneBye();
		verify(divisionWinner1_1).addWonDivision();
		verify(divisionWinner1_1).addMadePlayoffs();
		verify(divisionWinner1_1).addHadWinningSeason();
		
		verify(divisionWinner1_2).addGotRoundOneBye();
		verify(divisionWinner1_2).addWonDivision();
		verify(divisionWinner1_2).addMadePlayoffs();
		verify(divisionWinner1_2).addHadWinningSeason();
		
		verify(divisionWinner1_3).addWonDivision();
		verify(divisionWinner1_3).addMadePlayoffs();
		verify(divisionWinner1_3).addHadWinningSeason();
		
		verify(wildcard1_1).addMadePlayoffs();
		verify(wildcard1_1).addHadWinningSeason();
		
		verify(wildcard1_2).addMadePlayoffs();
		
		verify(divisionWinner2_1).addGotOneSeed();
		verify(divisionWinner2_1).addGotRoundOneBye();
		verify(divisionWinner2_1).addWonDivision();
		verify(divisionWinner2_1).addMadePlayoffs();
		verify(divisionWinner2_1).addHadWinningSeason();
		
		verify(divisionWinner2_2).addGotRoundOneBye();
		verify(divisionWinner2_2).addWonDivision();
		verify(divisionWinner2_2).addMadePlayoffs();
		verify(divisionWinner2_2).addHadWinningSeason();
		
		verify(divisionWinner2_3).addWonDivision();
		verify(divisionWinner2_3).addMadePlayoffs();
		verify(divisionWinner2_3).addHadWinningSeason();
		
		verify(wildcard2_1).addMadePlayoffs();
		verify(wildcard2_1).addHadWinningSeason();
		
		verify(wildcard2_2).addMadePlayoffs();
	}
	
	private void verifyTalliesForBadTeams() {
		verify(divisionCellar1_1).addWasInDivisionCellar();
		verify(divisionCellar1_2).addWasInDivisionCellar();
		verify(divisionCellar1_3).addWasInDivisionCellar();
		
		verify(divisionCellar2_1).addWasInDivisionCellar();
		verify(divisionCellar2_2).addWasInDivisionCellar();
		verify(divisionCellar2_3).addWasInDivisionCellar();
		
		verify(bottomTeam1).addWasBottomTeam();
		verify(bottomTeam2).addWasBottomTeam();
	}
	
}
