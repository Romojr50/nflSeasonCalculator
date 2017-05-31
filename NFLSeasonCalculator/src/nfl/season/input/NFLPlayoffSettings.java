package nfl.season.input;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nfl.season.league.Conference;
import nfl.season.league.Division;
import nfl.season.league.League;
import nfl.season.league.Team;
import nfl.season.playoffs.NFLPlayoffConference;
import nfl.season.playoffs.NFLPlayoffDivision;
import nfl.season.playoffs.NFLPlayoffTeam;
import nfl.season.playoffs.NFLPlayoffs;

public class NFLPlayoffSettings {

	public String createConferencePlayoffTeamsLine(
			NFLPlayoffConference playoffConference) {
		StringBuilder playoffTeamsLineBuilder = new StringBuilder();
		
		List<NFLPlayoffTeam> playoffTeams = playoffConference.getTeamsInSeedOrder();
		
		if (playoffTeams.size() == 6 && !playoffTeams.contains(null)) {
			Conference leagueConference = playoffConference.getConference();
			String conferenceName = leagueConference.getName();
			playoffTeamsLineBuilder.append("=" + conferenceName + "=");
			
			for (int i = 0; i < playoffTeams.size(); i++) {
				NFLPlayoffTeam playoffTeam = playoffTeams.get(i);
				Team leagueTeam = playoffTeam.getTeam();
				String teamName = leagueTeam.getName();
				
				if (i < 4) {
					NFLPlayoffDivision playoffDivision = playoffConference.getDivision(playoffTeam);
					Division leagueDivision = playoffDivision.getDivision();
					String divisionName = leagueDivision.getName();
					playoffTeamsLineBuilder.append("/" + divisionName + "/");
				}
				
				playoffTeamsLineBuilder.append(teamName + ",");
			}
		}
		
		return playoffTeamsLineBuilder.toString();
	}

	public void loadConferencePlayoffTeamsLine(NFLPlayoffs playoffs, League nfl,
			String conferenceTeamsLine) {
		int indexOfEquals = conferenceTeamsLine.indexOf("=");
		int indexOfSecondEquals = conferenceTeamsLine.indexOf("=", 2);
		String conferenceName = conferenceTeamsLine.substring(indexOfEquals + 1, 
				indexOfSecondEquals);
		
		String lineWithoutConferenceName = conferenceTeamsLine.substring(
				indexOfSecondEquals + 1);
		String[] playoffTeamStrings = lineWithoutConferenceName.split(",");
		
		for (String playoffTeamString : playoffTeamStrings) {
			String teamName = "";
			
			int indexOfSlash = playoffTeamString.indexOf("/");
			if (indexOfSlash > -1) {
				int indexOfSecondSlash = playoffTeamString.indexOf("/", 2);
				String divisionName = playoffTeamString.substring(indexOfSlash + 1, 
						indexOfSecondSlash);
				teamName = playoffTeamString.substring(indexOfSecondSlash + 1);
				
				Team leagueTeam = nfl.getTeam(teamName);
				NFLPlayoffTeam playoffTeam = playoffs.createPlayoffTeam(leagueTeam);
				playoffs.setDivisionWinner(conferenceName, divisionName, playoffTeam);
			} else {
				teamName = playoffTeamString;
				Team leagueTeam = nfl.getTeam(teamName);
				NFLPlayoffTeam playoffTeam = playoffs.createPlayoffTeam(leagueTeam);
				playoffs.addWildcardTeam(conferenceName, playoffTeam);
			}
		}
	}

	public String createPlayoffSettingsString(NFLPlayoffs playoffs) {
		StringBuilder playoffSettingsBuilder = new StringBuilder();
		
		List<NFLPlayoffConference> playoffConferences = playoffs.getConferences();
		List<String> conferenceLines = new ArrayList<String>();
		for (NFLPlayoffConference  playoffConference : playoffConferences) {
			String conferenceLine = createConferencePlayoffTeamsLine(playoffConference);
			conferenceLines.add(conferenceLine);
		}
		
		if (!conferenceLines.contains("")) {
			for (String conferenceLine : conferenceLines) {
				playoffSettingsBuilder.append(conferenceLine);
				playoffSettingsBuilder.append("\n");
			}
		}
		
		return playoffSettingsBuilder.toString();
	}

	public void loadPlayoffSettingsString(NFLPlayoffs playoffs, League nfl,
			String playoffSettingsString) {
		String[] conferenceLines = playoffSettingsString.split("\n");
		for (String conferenceLine : conferenceLines) {
			loadConferencePlayoffTeamsLine(playoffs, nfl, conferenceLine);
		}
	}

	public boolean saveToSettingsFile(NFLPlayoffs playoffs,
			NFLFileWriterFactory fileWriterFactory) throws IOException {
		boolean success = true;
		
		FileOutputStream fileWriter = null;
		try {
			fileWriter = fileWriterFactory.createNFLPlayoffSettingsWriter();
			String playoffSettingsString = createPlayoffSettingsString(playoffs);
			fileWriter.write(playoffSettingsString.getBytes());
		} catch (IOException e) {
			success = false;
		} finally {
			fileWriter.close();
		}
		
		return success;
	}

}
