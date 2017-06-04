package nfl.season.season;

import java.util.ArrayList;
import java.util.List;

public class SeasonWeek {

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
