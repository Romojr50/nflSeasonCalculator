package nfl.season.league;

public class Matchup {

	public enum WinChanceModeEnum {
		CUSTOM_SETTING("Custom Setting"), POWER_RANKINGS("Power Rankings");
		public String winChanceModeDescription;
		
		private WinChanceModeEnum(String winChanceModeDescription) {
			this.winChanceModeDescription = winChanceModeDescription;
		}
	}
	
	private static final int BETTER_TEAM_WIN_CHANCE_WHEN_BETTER_BY_24_SPOTS = 90;

	private static final double WIN_CHANCE_DIFFERENCE_BY_SPOT = 1.523;
	
	private Team team1;
	
	private Team team2;
	
	private int team1WinChance;
	
	private int team2WinChance;
	
	private WinChanceModeEnum winChanceMode;
	
	public Matchup(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
		team1WinChance = 50;
		team2WinChance = (100 - team1WinChance);
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
	
	public int getTeamWinChance(String teamName) {
		int returnWinChance = -1;
		
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				returnWinChance = team1WinChance;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				returnWinChance = team2WinChance;
			}
		}
		
		return returnWinChance;
	}

	public void setTeamWinChance(String teamName, int teamWinChance) {
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				team1WinChance = teamWinChance;
				team2WinChance = 100 - teamWinChance;
				winChanceMode = WinChanceModeEnum.CUSTOM_SETTING;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				team2WinChance = teamWinChance;
				team1WinChance = 100 - teamWinChance;
				winChanceMode = WinChanceModeEnum.CUSTOM_SETTING;
			}
		}
	}

	public void calculateTeamWinChancesFromPowerRankings() {
		winChanceMode = WinChanceModeEnum.POWER_RANKINGS;
		int team1Ranking = team1.getPowerRanking();
		int team2Ranking = team2.getPowerRanking();
		
		boolean team1IsRankedHigher = true;
		if (team2Ranking < team1Ranking) {
			team1IsRankedHigher = false;
		}
		
		int rankingDifference = Math.abs(team1Ranking - team2Ranking);
		int rankingDifferenceComparedTo24Difference = rankingDifference - 24;
		
		int betterWinChance = (int) Math.round(BETTER_TEAM_WIN_CHANCE_WHEN_BETTER_BY_24_SPOTS + 
				(rankingDifferenceComparedTo24Difference * WIN_CHANCE_DIFFERENCE_BY_SPOT));
		
		if (team1IsRankedHigher) {
			team1WinChance = betterWinChance;
			team2WinChance = 100 - betterWinChance;
		} else {
			team2WinChance = betterWinChance;
			team1WinChance = 100 - betterWinChance;
		}
	}

	public WinChanceModeEnum getWinChanceMode() {
		return winChanceMode;
	}

}
