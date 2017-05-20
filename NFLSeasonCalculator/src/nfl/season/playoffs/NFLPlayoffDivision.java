package nfl.season.playoffs;

import nfl.season.league.Division;

public class NFLPlayoffDivision {

	private Division division;
	
	private NFLPlayoffTeam divisionWinner = null;
	
	public NFLPlayoffDivision(Division division) {
		this.division = division;
	}

	public Division getDivision() {
		return division;
	}

	public NFLPlayoffTeam getDivisionWinner() {
		return divisionWinner;
	}

	public void setDivisionWinner(NFLPlayoffTeam divisionWinner) {
		this.divisionWinner = divisionWinner;
	}

}
