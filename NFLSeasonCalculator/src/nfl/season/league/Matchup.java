package nfl.season.league;

import java.io.Serializable;


public class Matchup implements Serializable {

	private static final long serialVersionUID = 4033723027647117852L;

	public enum WinChanceModeEnum {
		CUSTOM_SETTING("Custom Setting"), POWER_RANKINGS("Power Rankings"), 
		ELO_RATINGS("Elo Ratings");
		public String winChanceModeDescription;
		
		private WinChanceModeEnum(String winChanceModeDescription) {
			this.winChanceModeDescription = winChanceModeDescription;
		}
	}
	
	public enum HomeAwayWinChanceModeEnum {
		CUSTOM_SETTING("Custom Setting"), HOME_FIELD_ADVANTAGE("Home Field Advantage");
		public String winChanceModeDescription;
		
		private HomeAwayWinChanceModeEnum(String winChanceModeDescription) {
			this.winChanceModeDescription = winChanceModeDescription;
		}
	}
	
	private static final int BETTER_TEAM_WIN_CHANCE_WHEN_BETTER_BY_24_SPOTS = 90;

	private static final double WIN_CHANCE_DIFFERENCE_BY_SPOT = 1.523;
	
	private Team team1;
	
	private Team team2;
	
	private int team1NeutralWinChance;
	
	private int team2NeutralWinChance;
	
	private int team1HomeWinChance;
	
	private int team2HomeWinChance;
	
	private int team1AwayWinChance;
	
	private int team2AwayWinChance;
	
	private WinChanceModeEnum winChanceMode;
	
	private HomeAwayWinChanceModeEnum team1HomeWinChanceMode;
	
	private HomeAwayWinChanceModeEnum team2HomeWinChanceMode;
	
	public Matchup(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
		team1NeutralWinChance = 50;
		team2NeutralWinChance = (100 - team1NeutralWinChance);
		team1HomeWinChance = 50;
		team2AwayWinChance = (100 - team1HomeWinChance);
		team2HomeWinChance = 50;
		team1AwayWinChance = (100 - team2HomeWinChance);
		winChanceMode = WinChanceModeEnum.CUSTOM_SETTING;
		team1HomeWinChanceMode = HomeAwayWinChanceModeEnum.CUSTOM_SETTING;
		team2HomeWinChanceMode = HomeAwayWinChanceModeEnum.CUSTOM_SETTING;
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
		
		recalculateHomeWinChanceIfNeeded(teamName);
	}
	
	public int getTeamHomeWinChance(String teamName) {
		int returnWinChance = -1;
		
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				returnWinChance = team1HomeWinChance;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				returnWinChance = team2HomeWinChance;
			}
		}
		
		return returnWinChance;	
	}
	
	public void setTeamHomeWinChance(String teamName,
			int teamWinChance) {
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				team1HomeWinChance = teamWinChance;
				team2AwayWinChance = 100 - teamWinChance;
				team1HomeWinChanceMode = HomeAwayWinChanceModeEnum.CUSTOM_SETTING;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				team2HomeWinChance = teamWinChance;
				team1AwayWinChance = 100 - teamWinChance;
				team2HomeWinChanceMode = HomeAwayWinChanceModeEnum.CUSTOM_SETTING;
			}
		}
	}
	
	public int getTeamAwayWinChance(String teamName) {
		int returnWinChance = -1;
		
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				returnWinChance = team1AwayWinChance;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				returnWinChance = team2AwayWinChance;
			}
		}
		
		return returnWinChance;
	}
	
	public void setTeamAwayWinChance(String teamName,
			int teamWinChance) {
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				team1AwayWinChance = teamWinChance;
				team2HomeWinChance = 100 - teamWinChance;
				team2HomeWinChanceMode = HomeAwayWinChanceModeEnum.CUSTOM_SETTING;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				team2AwayWinChance = teamWinChance;
				team1HomeWinChance = 100 - teamWinChance;
				team1HomeWinChanceMode = HomeAwayWinChanceModeEnum.CUSTOM_SETTING;
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
	
	public int getTeamHomeFieldAdvantage(String teamName) {
		int returnAdvantage = -1;
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				returnAdvantage = team1.getHomeFieldAdvantage();
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				returnAdvantage = team2.getHomeFieldAdvantage();
			}
		}
		return returnAdvantage;
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
		
		recalculateHomeWinChanceIfNeeded(team1.getName());
		
		return success;
	}
	

	public int calculateSingleTeamWinChanceFromPowerRankings(String teamName) {
		int returnWinChance = 0;
		int team1Ranking = team1.getPowerRanking();
		int team2Ranking = team2.getPowerRanking();
		boolean teamNameIsTeam1 = team1.getName().equals(teamName);

		if (Team.CLEAR_RANKING != team1Ranking && Team.CLEAR_RANKING != team2Ranking) {
			boolean team1IsRankedHigher = true;
			if (team2Ranking < team1Ranking) {
				team1IsRankedHigher = false;
			}
			
			int betterWinChance = calculateBetterWinChance(team1Ranking,
					team2Ranking);
			
			if (team1IsRankedHigher) {
				if (teamNameIsTeam1) {
					returnWinChance = betterWinChance;
				} else {
					returnWinChance = 100 - betterWinChance;
				}
			} else {
				if (teamNameIsTeam1) {
					returnWinChance = 100 - betterWinChance;
				} else {
					returnWinChance = betterWinChance;
				}
			}
		}
		
		return returnWinChance;
	}
	
	public boolean calculateTeamWinChancesFromEloRatings() {
		// 1 / (1 + 10 ^ ((ratingA - ratingB) / 400))
		boolean success = false;
		
		
		int team1Rating = team1.getEloRating();
		int team2Rating = team2.getEloRating();
		
		if (team1Rating > 0 && team2Rating > 0) {
			winChanceMode = WinChanceModeEnum.ELO_RATINGS;
			
			team1NeutralWinChance = 
					calculateTeam1WinChanceFromEloRatings(team1Rating, team2Rating);
			team2NeutralWinChance = 100 - team1NeutralWinChance;
			success = true;
		}
		
		recalculateHomeWinChanceIfNeeded(team1.getName());
		
		return success;
	}

	public int calculateSingleTeamWinChanceFromEloRatings(String teamName) {
		int returnWinChance = 0;

		int team1Rating = team1.getEloRating();
		int team2Rating = team2.getEloRating();
		boolean teamNameIsTeam1 = team1.getName().equals(teamName);
		
		int team1WinChance = calculateTeam1WinChanceFromEloRatings(team1Rating, team2Rating);
		if (teamNameIsTeam1) {
			returnWinChance = team1WinChance;
		} else {
			returnWinChance = 100 - team1WinChance;
		}
		
		return returnWinChance;
	}
	
	public boolean calculateHomeWinChanceFromHomeFieldAdvantage(String teamName) {
		boolean success = false;
		
		if (teamName != null) {
			int homeFieldAdvantage = -1;
			if (teamName.equalsIgnoreCase(team1.getName())) {
				homeFieldAdvantage = team1.getHomeFieldAdvantage();
				int opponentHomeField = team2.getHomeFieldAdvantage();
				team1HomeWinChance = calculateHomeTeamWinChance(homeFieldAdvantage, 
						team1NeutralWinChance, opponentHomeField, team2NeutralWinChance);
				team2AwayWinChance = 100 - team1HomeWinChance;
				team1HomeWinChanceMode = HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				homeFieldAdvantage = team2.getHomeFieldAdvantage();
				int opponentHomeField = team1.getHomeFieldAdvantage();
				team2HomeWinChance = calculateHomeTeamWinChance(homeFieldAdvantage, 
						team2NeutralWinChance, opponentHomeField, team1NeutralWinChance);
				team1AwayWinChance = 100 - team2HomeWinChance;
				team2HomeWinChanceMode = HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE;
			}
			success = true;
			
		}
		return success;
	}
	

	public int calculateSingleHomeWinChanceFromHomeFieldAdvantage(
			String teamName) {
		int returnWinChance = 0;
		
		int homeFieldAdvantage = 0;
		int teamNeutralWinChance = 0;
		int opponentHomeField = 0;
		int opponentNeutralWinChance = 0;
		if (team1.getName().equals(teamName)) {
			homeFieldAdvantage = team1.getHomeFieldAdvantage();
			teamNeutralWinChance = team1NeutralWinChance;
			opponentHomeField = team2.getHomeFieldAdvantage();
			opponentNeutralWinChance = team2NeutralWinChance;
		} else {
			homeFieldAdvantage = team2.getHomeFieldAdvantage();
			teamNeutralWinChance = team2NeutralWinChance;
			opponentHomeField = team1.getHomeFieldAdvantage();
			opponentNeutralWinChance = team1NeutralWinChance;
		}
		
		returnWinChance = calculateHomeTeamWinChance(homeFieldAdvantage, 
				teamNeutralWinChance, opponentHomeField, opponentNeutralWinChance);
		
		return returnWinChance;
	}

	public WinChanceModeEnum getWinChanceMode() {
		return winChanceMode;
	}

	public void setWinChanceMode(WinChanceModeEnum winChanceMode) {
		this.winChanceMode = winChanceMode;
	}
	
	public HomeAwayWinChanceModeEnum getHomeAwayWinChanceMode(String teamName) {
		HomeAwayWinChanceModeEnum returnWinChance = HomeAwayWinChanceModeEnum.CUSTOM_SETTING;
		
		if (teamName != null) {
			if (teamName.equalsIgnoreCase(team1.getName())) {
				returnWinChance = team1HomeWinChanceMode;
			} else if (teamName.equalsIgnoreCase(team2.getName())) {
				returnWinChance = team2HomeWinChanceMode;;
			}
		}
		
		return returnWinChance;
	}

	private void recalculateHomeWinChanceIfNeeded(String teamName) {
		if (getHomeAwayWinChanceMode(teamName) == 
				HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE) {
			calculateHomeWinChanceFromHomeFieldAdvantage(teamName);
		}
		
		String opponentName = getOpponentName(teamName);
		if (getHomeAwayWinChanceMode(opponentName) == 
				HomeAwayWinChanceModeEnum.HOME_FIELD_ADVANTAGE) {
			calculateHomeWinChanceFromHomeFieldAdvantage(opponentName);
		}
	}
	
	private int calculateBetterWinChance(int team1Ranking, int team2Ranking) {
		int rankingDifference = Math.abs(team1Ranking - team2Ranking);
		int rankingDifferenceComparedTo24Difference = rankingDifference - 24;
		
		int betterWinChance = (int) Math.round(BETTER_TEAM_WIN_CHANCE_WHEN_BETTER_BY_24_SPOTS + 
				(rankingDifferenceComparedTo24Difference * WIN_CHANCE_DIFFERENCE_BY_SPOT));
		betterWinChance = Math.min(99, betterWinChance);
		return betterWinChance;
	}
	
	private int calculateTeam1WinChanceFromEloRatings(int team1Rating,
			int team2Rating) {
		int ratingDifference = team2Rating - team1Rating;
		double powerFor10 = ratingDifference / 400.0;
		double tenToThePower = Math.pow(10, powerFor10);
		double dividend = 1 + tenToThePower;
		double winChanceAsDecimal = 1 / dividend;
		
		return (int) Math.round(winChanceAsDecimal * 100);
	}

	private int calculateHomeTeamWinChance(int homeFieldAdvantage, int neutralWinChance,
			int opponentHomeField, int opponentNeutralWinChance) {
		int effectiveHomeField = homeFieldAdvantage;
		int opponentHomeTeamWinChance = opponentNeutralWinChance + 
				Math.round(opponentHomeField / 2);
		if (opponentHomeTeamWinChance > 99) {
			int difference = 99 - opponentNeutralWinChance;
			effectiveHomeField = Math.min(homeFieldAdvantage, difference * 2);
		}
		
		int homeTeamWinChance = neutralWinChance + Math.round(effectiveHomeField / 2);
		homeTeamWinChance = Math.min(99, homeTeamWinChance);
		
		return homeTeamWinChance;
	}

}
