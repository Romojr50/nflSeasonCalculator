package nfl.season.playoffs;

import java.io.Serializable;

import nfl.season.league.Division;

public class NFLPlayoffDivision implements Serializable {

	private static final long serialVersionUID = -5417223863649638553L;

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
