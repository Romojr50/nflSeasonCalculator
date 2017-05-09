package nfl.season.league;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LeagueTest {

	String tenthTeamName = NFLTeamEnum.values()[9].getTeamName();
	
	@Test
	public void leagueIsInitializedWithNFLConferencesDivisionsAndTeams() {
		League nfl = new League(League.NFL);
		assertEquals(League.NFL, nfl.getName());
		
		nfl.initializeNFL();
		
		Conference afc = nfl.getConference(NFLConferenceEnum.AFC.name());
		assertNotNull(afc);
		assertConferenceHasCorrectDivisions(afc);
		
		Conference nfc = nfl.getConference(NFLConferenceEnum.NFC.name());
		assertNotNull(nfc);
		assertConferenceHasCorrectDivisions(nfc);
		
		assertConferencesHaveExpectedTeams(afc, nfc);
		
		List<Team> allTeams = nfl.getTeams();
		for (Team team : allTeams) {
			assertTeamIsPopulatedCorrectly(allTeams, team);
		}
		
	}

	@Test
	public void getTeamInLeagueReturnsTeamAtIndex() {
		League nfl = new League(League.NFL);
		nfl.initializeNFL();
		
		Team tenthTeam = nfl.getTeam(10);
		assertEquals(tenthTeamName, tenthTeam.getName());
	}
	
	@Test
	public void getAllTeamsInLeagueReturnsAllTeams() {
		League nfl = new League(League.NFL);
		nfl.initializeNFL();
		
		List<Team> allTeams = nfl.getTeams();
		
		assertEquals(NFLTeamEnum.values().length, allTeams.size());
		Team tenthTeam = allTeams.get(9);
		assertEquals(tenthTeamName, tenthTeam.getName());
	}
	
	@Test
	public void getTeamWithPowerRankingSoTeamWithThatPowerRankingIsReturned() {
		League nfl = new League(League.NFL);
		nfl.initializeNFL();
		
		List<Team> allTeams = nfl.getTeams();
		Team teamAtRankingTen = allTeams.get(17);
		teamAtRankingTen.setPowerRanking(10);
		
		Team returnTeam = nfl.getTeamWithPowerRanking(10);
		assertEquals(teamAtRankingTen, returnTeam);
	}

	private void assertConferenceHasCorrectDivisions(Conference conference) {
		Division east = conference.getDivision(NFLDivisionEnum.EAST.name());
		assertNotNull(east);
		Division north = conference.getDivision(NFLDivisionEnum.NORTH.name());
		assertNotNull(north);
		Division south = conference.getDivision(NFLDivisionEnum.SOUTH.name());
		assertNotNull(south);
		Division west = conference.getDivision(NFLDivisionEnum.WEST.name());
		assertNotNull(west);
	}
	
	private void assertConferencesHaveExpectedTeams(Conference afc,
			Conference nfc) {
		assertAFCHasExpectedTeams(afc);
		
		assertNFCHasExpectedTeams(nfc);
	}
	
	private NFLTeamEnum assertTeamIsPopulatedCorrectly(List<Team> allTeams,
			Team team) {
		String teamName = team.getName();
		
		assertTeamHasExpectedMatchups(allTeams, team, teamName);
		
		NFLTeamEnum correspondingEnum = null;
		for (NFLTeamEnum teamEnum : NFLTeamEnum.values()) {
			if (teamEnum.getTeamName().equalsIgnoreCase(teamName)) {
				correspondingEnum = teamEnum;
				break;
			}
		}
		
		int expectedDefaultHomeFieldAdvantage = correspondingEnum.getDefaultHomeFieldAdvantage();
		assertEquals(expectedDefaultHomeFieldAdvantage, team.getDefaultHomeFieldAdvantage());
		assertEquals(expectedDefaultHomeFieldAdvantage, team.getHomeFieldAdvantage());
		return correspondingEnum;
	}
	
	private void assertTeamHasExpectedMatchups(List<Team> allTeams, Team team,
			String teamName) {
		List<Matchup> teamMatchups = team.getMatchups();
		assertEquals(NFLTeamEnum.values().length - 1, teamMatchups.size());
		List<String> matchupTeamNames = new ArrayList<String>();
		for (Matchup matchup : teamMatchups) {
			matchupTeamNames.add(matchup.getOpponentName(teamName));
		}
		for (Team opponent : allTeams) {
			String opponentName = opponent.getName();
			if (!opponentName.equalsIgnoreCase(teamName)) {
				assertTrue(matchupTeamNames.contains(opponent.getName()));
			}
			Matchup teamMatchup = team.getMatchup(opponentName);
			Matchup opponentMatchup = opponent.getMatchup(teamName);
			assertEquals(teamMatchup, opponentMatchup);
		}
	}

	private void assertAFCHasExpectedTeams(Conference afc) {
		Division afcEast = afc.getDivision(NFLDivisionEnum.EAST.name());
		List<NFLTeamEnum> teamsExpectedInAFCEast = Arrays.asList(NFLTeamEnum.PATRIOTS, NFLTeamEnum.JETS, NFLTeamEnum.DOLPHINS, NFLTeamEnum.BILLS);
		assertDivisionHasExpectedTeams(afcEast, teamsExpectedInAFCEast);
		
		Division afcNorth = afc.getDivision(NFLDivisionEnum.NORTH.name());
		List<NFLTeamEnum> teamsExpectedInAFCNorth = Arrays.asList(NFLTeamEnum.STEELERS, NFLTeamEnum.RAVENS, NFLTeamEnum.BENGALS, NFLTeamEnum.BROWNS);
		assertDivisionHasExpectedTeams(afcNorth, teamsExpectedInAFCNorth);
		
		Division afcSouth = afc.getDivision(NFLDivisionEnum.SOUTH.name());
		List<NFLTeamEnum> teamsExpectedInAFCSouth = Arrays.asList(NFLTeamEnum.TEXANS, NFLTeamEnum.COLTS, NFLTeamEnum.TITANS, NFLTeamEnum.JAGUARS);
		assertDivisionHasExpectedTeams(afcSouth, teamsExpectedInAFCSouth);
		
		Division afcWest = afc.getDivision(NFLDivisionEnum.WEST.name());
		List<NFLTeamEnum> teamsExpectedInAFCWest = Arrays.asList(NFLTeamEnum.BRONCOS, NFLTeamEnum.RAIDERS, NFLTeamEnum.CHIEFS, NFLTeamEnum.CHARGERS);
		assertDivisionHasExpectedTeams(afcWest, teamsExpectedInAFCWest);
	}
	
	private void assertNFCHasExpectedTeams(Conference nfc) {
		Division nfcEast = nfc.getDivision(NFLDivisionEnum.EAST.name());
		List<NFLTeamEnum> teamsExpectedInNFCEast = Arrays.asList(NFLTeamEnum.GIANTS, NFLTeamEnum.COWBOYS, NFLTeamEnum.EAGLES, NFLTeamEnum.REDSKINS);
		assertDivisionHasExpectedTeams(nfcEast, teamsExpectedInNFCEast);
		
		Division nfcNorth = nfc.getDivision(NFLDivisionEnum.NORTH.name());
		List<NFLTeamEnum> teamsExpectedInNFCNorth = Arrays.asList(NFLTeamEnum.PACKERS, NFLTeamEnum.VIKINGS, NFLTeamEnum.LIONS, NFLTeamEnum.BEARS);
		assertDivisionHasExpectedTeams(nfcNorth, teamsExpectedInNFCNorth);
		
		Division nfcSouth = nfc.getDivision(NFLDivisionEnum.SOUTH.name());
		List<NFLTeamEnum> teamsExpectedInNFCSouth = Arrays.asList(NFLTeamEnum.FALCONS, NFLTeamEnum.SAINTS, NFLTeamEnum.PANTHERS, NFLTeamEnum.BUCCANEERS);
		assertDivisionHasExpectedTeams(nfcSouth, teamsExpectedInNFCSouth);
		
		Division nfcWest = nfc.getDivision(NFLDivisionEnum.WEST.name());
		List<NFLTeamEnum> teamsExpectedInNFCWest = Arrays.asList(NFLTeamEnum.SEAHAWKS, NFLTeamEnum.CARDINALS, NFLTeamEnum.NINERS, NFLTeamEnum.RAMS);
		assertDivisionHasExpectedTeams(nfcWest, teamsExpectedInNFCWest);
	}
	
	private void assertDivisionHasExpectedTeams(Division division,
			List<NFLTeamEnum> teamsExpectedInDivision) {
		for (NFLTeamEnum teamExpected : teamsExpectedInDivision) {
			Team expectedTeam = division.getTeam(teamExpected.getTeamName());
			assertNotNull(expectedTeam);
		}
	}
	
}
