package nfl.season.league;

public class Matchup {

	public enum WinChanceModeEnum {
		CUSTOM_SETTING("Custom Setting"), POWER_RANKINGS("Power Rankings"), 
		ELO_RATINGS("Elo Ratings");
		public String winChanceModeDescription;
		
		private WinChanceModeEnum(String winChanceModeDescription) {
			this.winChanceModeDescription = winChanceModeDescription;
		}
	}
	
	private static final int BETTER_TEAM_WIN_CHANCE_WHEN_BETTER_BY_24_SPOTS = 90;

	private static final double WIN_CHANCE_DIFFERENCE_BY_SPOT = 1.523;
	
	private Team team1;
	
	private Team team2;
	
	private int team1NeutralWinChance;
	
	private int team2NeutralWinChance;
	
	private WinChanceModeEnum winChanceMode;
	
	public Matchup(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
		team1NeutralWinChance = 50;
		team2NeutralWinChance = (100 - team1NeutralWinChance);
		winChanceMode = WinChanceModeEnum.CUSTOM_SETTING;
	}
	
	public String getOpponentName(String teamName) {
		String opponentName = "";
		String team1Name = team1.getName();
		String team2Name = team2.getName();
		
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1Name)) {
				opponentName = team2Name;
			} else if (teamName.equalsIgnoreCase(team2Name)) {
				opponentName = team1Name;
			}
		}
		
		return opponentName;
	}

	public String[] getTeamNames() {
		String[] teamNames = new String[2];
		
		teamNames[0] = team1.getName();
		teamNames[1] = team2.getName();
		
		return teamNames;
	}
	
	public int getTeamNeutralWinChance(String teamName) {
		int returnWinChance = -1;
		
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				returnWinChance = team1NeutralWinChance;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				returnWinChance = team2NeutralWinChance;
			}
		}
		
		return returnWinChance;
	}

	public void setTeamNeutralWinChance(String teamName, int teamWinChance) {
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				team1NeutralWinChance = teamWinChance;
				team2NeutralWinChance = 100 - teamWinChance;
				winChanceMode = WinChanceModeEnum.CUSTOM_SETTING;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				team2NeutralWinChance = teamWinChance;
				team1NeutralWinChance = 100 - teamWinChance;
				winChanceMode = WinChanceModeEnum.CUSTOM_SETTING;
			}
		}
	}
	
	public int getTeamPowerRanking(String teamName) {
		int returnRanking = Team.CLEAR_RANKING;
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				returnRanking = team1.getPowerRanking();
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				returnRanking = team2.getPowerRanking();
			}
		}
		return returnRanking;
	}
	
	public int getTeamEloRating(String teamName) {
		int returnRating = -1;
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				returnRating = team1.getEloRating();
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				returnRating = team2.getEloRating();
			}
		}
		return returnRating;
	}

	public boolean calculateTeamWinChancesFromPowerRankings() {
		boolean success = false;
		int team1Ranking = team1.getPowerRanking();
		int team2Ranking = team2.getPowerRanking();

		if (Team.CLEAR_RANKING != team1Ranking && Team.CLEAR_RANKING != team2Ranking) {
			winChanceMode = WinChanceModeEnum.POWER_RANKINGS;
			boolean team1IsRankedHigher = true;
			if (team2Ranking < team1Ranking) {
				team1IsRankedHigher = false;
			}
			
			int betterWinChance = calculateBetterWinChance(team1Ranking,
					team2Ranking);
			
			if (team1IsRankedHigher) {
				team1NeutralWinChance = betterWinChance;
				team2NeutralWinChance = 100 - betterWinChance;
			} else {
				team2NeutralWinChance = betterWinChance;
				team1NeutralWinChance = 100 - betterWinChance;
			}
			success = true;
		}
		
		return success;
	}
	
	public boolean calculateTeamWinChancesFromEloRatings() {
		// 1 / (1 + 10 ^ ((ratingA - ratingB) / 400))
		boolean success = false;
		
		
		int team1Rating = team1.getEloRating();
		int team2Rating = team2.getEloRating();
		
		if (team1Rating > 0 && team2Rating > 0) {
			winChanceMode = WinChanceModeEnum.ELO_RATINGS;
			
			int ratingDifference = team2Rating - team1Rating;
			double powerFor10 = ratingDifference / 400.0;
			double tenToThePower = Math.pow(10, powerFor10);
			double dividend = 1 + tenToThePower;
			double winChanceAsDecimal = 1 / dividend;
			
			team1NeutralWinChance = (int) Math.round(winChanceAsDecimal * 100);
			team2NeutralWinChance = 100 - team1NeutralWinChance;
			success = true;
		}
		
		return success;
	}

	public WinChanceModeEnum getWinChanceMode() {
		return winChanceMode;
	}
	
	private int calculateBetterWinChance(int team1Ranking, int team2Ranking) {
		int rankingDifference = Math.abs(team1Ranking - team2Ranking);
		int rankingDifferenceComparedTo24Difference = rankingDifference - 24;
		
		int betterWinChance = (int) Math.round(BETTER_TEAM_WIN_CHANCE_WHEN_BETTER_BY_24_SPOTS + 
				(rankingDifferenceComparedTo24Difference * WIN_CHANCE_DIFFERENCE_BY_SPOT));
		return betterWinChance;
	}

}
