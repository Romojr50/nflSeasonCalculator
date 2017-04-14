package nfl.season.calculator;

import nfl.season.league.Team;

public class SingleTeamMenu extends SubMenu {

	Team selectedTeam;
	
	public void setTeam(Team team) {
		this.selectedTeam = team;
	}

}
