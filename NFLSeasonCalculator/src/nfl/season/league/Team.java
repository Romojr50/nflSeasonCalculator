package nfl.season.league;

import java.util.ArrayList;
import java.util.List;

public class Team {

	public static final int CLEAR_RANKING = -1;

	public static final String UNSET_RANKING_DISPLAY = "Unset";
	
	private String name;
	
	private int powerRanking = CLEAR_RANKING;
	
	private int eloRating = 1;
	
	private int homeFieldAdvantage = 1;
	
	private final int defaultHomeFieldAdvantage;
	
	private List<Matchup> matchups;

	public Team(String name, int defaultHomeFieldAdvantage) {
		this.name = name;
		this.defaultHomeFieldAdvantage = defaultHomeFieldAdvantage;
		this.homeFieldAdvantage = defaultHomeFieldAdvantage;
		this.matchups = new ArrayList<Matchup>();
	}

	public String getName() {
		return name;
	}
	
	public int getPowerRanking() {
		return this.powerRanking;
	}

	public void setPowerRanking(int powerRanking) {
		this.powerRanking = powerRanking;
	}

	public int getEloRating() {
		return eloRating;
	}

	public void setEloRating(int newEloRating) {
		this.eloRating = newEloRating;
	}
	
	public int getHomeFieldAdvantage() {
		return homeFieldAdvantage;
	}
	
	public void setHomeFieldAdvantage(int newHomeFieldAdvantage) {
		this.homeFieldAdvantage = newHomeFieldAdvantage;
	}

	public List<Matchup> getMatchups() {
		return matchups;
	}
	
	public Matchup getMatchup(String opponentName) {
		Matchup returnMatchup = null;
		
		if (opponentName != null) {
			for (Matchup matchup : matchups) {
				String matchupOpponentName = matchup.getOpponentName(name);
				if (opponentName.equalsIgnoreCase(matchupOpponentName)) {
					returnMatchup = matchup;
					break;
				}
			}
		}
		
		return returnMatchup;
	}

	public void addMatchup(Matchup matchup) {
		matchups.add(matchup);
	}

	public int getDefaultHomeFieldAdvantage() {
		return defaultHomeFieldAdvantage;
	}

	public void resetToDefaults() {
		homeFieldAdvantage = defaultHomeFieldAdvantage;
	}
	
}
