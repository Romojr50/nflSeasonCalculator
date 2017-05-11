package nfl.season.league;

public enum NFLTeamEnum {

	PATRIOTS("Patriots", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 1, 1686, 18),
	DOLPHINS("Dolphins", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 13, 1507, 4),
	JETS("Jets", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 32, 1450, 13),
	BILLS("Bills", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 26, 1483, 15),
	
	STEELERS("Steelers", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 3, 1597, 19),
	RAVENS("Ravens", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 21, 1489, 33),
	BENGALS("Bengals", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 25, 1514, 18),
	BROWNS("Browns", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 29, 1334, 18),
	
	TEXANS("Texans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 14, 1501, 20),
	COLTS("Colts", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 18, 1512, 10),
	TITANS("Titans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 11, 1458, 6),
	JAGUARS("Jaguars", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 27, 1380, 16),
	
	BRONCOS("Broncos", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 9, 1554, 13),
	RAIDERS("Raiders", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 5, 1528, 5),
	CHIEFS("Chiefs", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 12, 1611, 5),
	CHARGERS("Chargers", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 24, 1435, 16),
	
	GIANTS("Giants", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 8, 1529, 5),
	COWBOYS("Cowboys", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 6, 1568, 0),
	EAGLES("Eagles", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 16, 1509, 2),
	REDSKINS("Redskins", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 19, 1503, 7),
	
	PACKERS("Packers", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 4, 1585, 23),
	VIKINGS("Vikings", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 23, 1497, 29),
	BEARS("Bears", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 31, 1383, 10),
	LIONS("Lions", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 17, 1499, 19),
	
	FALCONS("Falcons", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 2, 1615, 16),
	SAINTS("Saints", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 22, 1497, 14),
	PANTHERS("Panthers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 15, 1525, 12),
	BUCCANEERS("Buccaneers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 10, 1504, 4),
	
	SEAHAWKS("Seahawks", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 7, 1569, 28),
	CARDINALS("Cardinals", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 20, 1535, 26),
	NINERS("49ers", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 30, 1352, 19),
	RAMS("Rams", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 28, 1397, 7);
	
	private String teamName;
	private NFLConferenceEnum conference;
	private NFLDivisionEnum division;
	private int defaultPowerRanking;
	private int defaultEloRating;
	private int defaultHomeFieldAdvantage;
	
	private NFLTeamEnum(String teamName, NFLConferenceEnum conference, 
			NFLDivisionEnum division, int defaultPowerRanking, int defaultEloRating, 
			int defaultHomeFieldAdvantage) {
		this.teamName = teamName;
		this.conference = conference;
		this.division = division;
		this.defaultPowerRanking = defaultPowerRanking;
		this.defaultEloRating = defaultEloRating;
		this.defaultHomeFieldAdvantage = defaultHomeFieldAdvantage;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public NFLConferenceEnum getConference() {
		return conference;
	}

	public void setConference(NFLConferenceEnum conference) {
		this.conference = conference;
	}

	public NFLDivisionEnum getDivision() {
		return division;
	}

	public void setDivision(NFLDivisionEnum division) {
		this.division = division;
	}
	
	public int getDefaultPowerRanking() {
		return defaultPowerRanking;
	}
	
	public void setDefaultPowerRanking(int defaultPowerRanking) {
		this.defaultPowerRanking = defaultPowerRanking;
	}
	
	public int getDefaultEloRating() {
		return defaultEloRating;
	}
	
	public void setDefaultEloRating(int defaultEloRating) {
		this.defaultEloRating = defaultEloRating;
	}
	
	public int getDefaultHomeFieldAdvantage() {
		return defaultHomeFieldAdvantage;
	}

	public void setDefaultHomeFieldAdvantage(int defaultHomeFieldAdvantage) {
		this.defaultHomeFieldAdvantage = defaultHomeFieldAdvantage;
	}
	
}
