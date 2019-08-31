package nfl.season.league;

public enum NFLTeamEnum {

	PATRIOTS("Patriots", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 1, 1639, 18),
	DOLPHINS("Dolphins", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 32, 1413, 16),
	JETS("Jets", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 22, 1383, 12),
	BILLS("Bills", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 24, 1447, 19),
	
	STEELERS("Steelers", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 9, 1571, 20),
	RAVENS("Ravens", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 16, 1569, 23),
	BENGALS("Bengals", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 30, 1427, 15),
	BROWNS("Browns", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 11, 1454, 17),
	
	TEXANS("Texans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 13, 1514, 17),
	COLTS("Colts", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 10, 1559, 14),
	TITANS("Titans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 21, 1517, 14),
	JAGUARS("Jaguars", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 19, 1453, 20),
	
	BRONCOS("Broncos", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 23, 1447, 16),
	RAIDERS("Raiders", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 27, 1404, 13),
	CHIEFS("Chiefs", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 3, 1601, 15),
	CHARGERS("Chargers", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 7, 1584, 10),
	
	GIANTS("Giants", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 28, 1441, 10),
	COWBOYS("Cowboys", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 8, 1545, 1),
	EAGLES("Eagles", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 5, 1580, 12),
	REDSKINS("Redskins", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 29, 1439, 13),
	
	PACKERS("Packers", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 12, 1453, 23),
	VIKINGS("Vikings", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 14, 1537, 21),
	BEARS("Bears", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 6, 1587, 8),
	LIONS("Lions", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 25, 1489, 14),
	
	FALCONS("Falcons", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 17, 1519, 8),
	SAINTS("Saints", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 2, 1609, 11),
	PANTHERS("Panthers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 18, 1518, 15),
	BUCCANEERS("Buccaneers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 26, 1443, 8),
	
	SEAHAWKS("Seahawks", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 15, 1564, 18),
	CARDINALS("Cardinals", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 31, 1382, 14),
	NINERS("49ers", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 20, 1421, 18),
	RAMS("Rams", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 4, 1597, 6);
	
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
