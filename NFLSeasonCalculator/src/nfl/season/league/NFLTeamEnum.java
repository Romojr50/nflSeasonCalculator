package nfl.season.league;

public enum NFLTeamEnum {

	PATRIOTS("Patriots", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 1, 18),
	DOLPHINS("Dolphins", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 13, 4),
	JETS("Jets", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 32, 13),
	BILLS("Bills", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 26, 15),
	
	STEELERS("Steelers", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 3, 19),
	RAVENS("Ravens", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 21, 33),
	BENGALS("Bengals", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 25, 18),
	BROWNS("Browns", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 29, 18),
	
	TEXANS("Texans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 14, 20),
	COLTS("Colts", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 18, 10),
	TITANS("Titans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 11, 6),
	JAGUARS("Jaguars", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 27, 16),
	
	BRONCOS("Broncos", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 9, 13),
	RAIDERS("Raiders", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 5, 5),
	CHIEFS("Chiefs", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 12, 5),
	CHARGERS("Chargers", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 24, 16),
	
	GIANTS("Giants", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 8, 5),
	COWBOYS("Cowboys", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 6, 0),
	EAGLES("Eagles", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 16, 2),
	REDSKINS("Redskins", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 19, 7),
	
	PACKERS("Packers", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 4, 23),
	VIKINGS("Vikings", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 23, 29),
	BEARS("Bears", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 31, 10),
	LIONS("Lions", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 17, 19),
	
	FALCONS("Falcons", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 2, 16),
	SAINTS("Saints", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 22, 14),
	PANTHERS("Panthers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 15, 12),
	BUCCANEERS("Buccaneers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 10, 4),
	
	SEAHAWKS("Seahawks", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 7, 28),
	CARDINALS("Cardinals", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 20, 26),
	NINERS("49ers", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 30, 19),
	RAMS("Rams", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 28, 7);
	
	private String teamName;
	private NFLConferenceEnum conference;
	private NFLDivisionEnum division;
	private int defaultPowerRanking;
	private int defaultHomeFieldAdvantage;
	
	private NFLTeamEnum(String teamName, NFLConferenceEnum conference, 
			NFLDivisionEnum division, int defaultPowerRanking, 
			int defaultHomeFieldAdvantage) {
		this.teamName = teamName;
		this.conference = conference;
		this.division = division;
		this.defaultPowerRanking = defaultPowerRanking;
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
	
	public int getDefaultHomeFieldAdvantage() {
		return defaultHomeFieldAdvantage;
	}

	public void setDefaultHomeFieldAdvantage(int defaultHomeFieldAdvantage) {
		this.defaultHomeFieldAdvantage = defaultHomeFieldAdvantage;
	}
	
}
