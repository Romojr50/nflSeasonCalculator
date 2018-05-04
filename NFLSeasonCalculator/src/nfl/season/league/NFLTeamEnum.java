package nfl.season.league;

public enum NFLTeamEnum {

	PATRIOTS("Patriots", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 2, 1649, 14),
	DOLPHINS("Dolphins", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 30, 1448, 8),
	JETS("Jets", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 28, 1431, 17),
	BILLS("Bills", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 20, 1501, 20),
	
	STEELERS("Steelers", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 7, 1594, 13),
	RAVENS("Ravens", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 18, 1533, 30),
	BENGALS("Bengals", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 24, 1473, 19),
	BROWNS("Browns", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 32, 1300, 13),
	
	TEXANS("Texans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 14, 1396, 19),
	COLTS("Colts", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 31, 1405, 12),
	TITANS("Titans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 12, 1494, 8),
	JAGUARS("Jaguars", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 6, 1533, 18),
	
	BRONCOS("Broncos", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 25, 1449, 17),
	RAIDERS("Raiders", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 22, 1463, 11),
	CHIEFS("Chiefs", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 13, 1569, 10),
	CHARGERS("Chargers", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 11, 1543, 14),
	
	GIANTS("Giants", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 23, 1410, 12),
	COWBOYS("Cowboys", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 19, 1545, -9),
	EAGLES("Eagles", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 1, 1645, 8),
	REDSKINS("Redskins", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 21, 1470, 15),
	
	PACKERS("Packers", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 9, 1470, 21),
	VIKINGS("Vikings", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 4, 1601, 28),
	BEARS("Bears", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 27, 1442, 6),
	LIONS("Lions", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 15, 1522, 16),
	
	FALCONS("Falcons", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 8, 1599, 8),
	SAINTS("Saints", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 5, 1583, 21),
	PANTHERS("Panthers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 10, 1548, 15),
	BUCCANEERS("Buccaneers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 26, 1468, 4),
	
	SEAHAWKS("Seahawks", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 17, 1543, 18),
	CARDINALS("Cardinals", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 29, 1481, 21),
	NINERS("49ers", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 16, 1468, 13),
	RAMS("Rams", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 3, 1528, 3);
	
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
