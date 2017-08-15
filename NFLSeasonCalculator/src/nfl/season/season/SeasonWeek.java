package nfl.season.season;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeasonWeek implements Serializable {

	private static final long serialVersionUID = -8564854984902200611L;

	private int weekNumber;
	
	private List<SeasonGame> seasonGames;
	
	public SeasonWeek(int weekNumber) {
		this.weekNumber = weekNumber;
		seasonGames = new ArrayList<SeasonGame>();
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public List<SeasonGame> getSeasonGames() {
		return seasonGames;
	}

	public void addSeasonGame(SeasonGame seasonGame) {
		seasonGames.add(seasonGame);
	}

}
