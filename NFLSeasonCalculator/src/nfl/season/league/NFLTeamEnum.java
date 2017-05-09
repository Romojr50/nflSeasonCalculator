package nfl.season.league;

public enum NFLTeamEnum {

	PATRIOTS("Patriots", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 18),
	DOLPHINS("Dolphins", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 4),
	JETS("Jets", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 13),
	BILLS("Bills", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST, 15),
	
	STEELERS("Steelers", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 19),
	RAVENS("Ravens", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 33),
	BENGALS("Bengals", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 18),
	BROWNS("Browns", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH, 18),
	
	TEXANS("Texans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 20),
	COLTS("Colts", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 10),
	TITANS("Titans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 6),
	JAGUARS("Jaguars", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH, 16),
	
	BRONCOS("Broncos", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 13),
	RAIDERS("Raiders", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 5),
	CHIEFS("Chiefs", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 5),
	CHARGERS("Chargers", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST, 16),
	
	GIANTS("Giants", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 5),
	COWBOYS("Cowboys", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 0),
	EAGLES("Eagles", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 2),
	REDSKINS("Redskins", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST, 7),
	
	PACKERS("Packers", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 23),
	VIKINGS("Vikings", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 29),
	BEARS("Bears", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 10),
	LIONS("Lions", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH, 19),
	
	FALCONS("Falcons", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 16),
	SAINTS("Saints", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 14),
	PANTHERS("Panthers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 12),
	BUCCANEERS("Buccaneers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH, 4),
	
	SEAHAWKS("Seahawks", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 28),
	CARDINALS("Cardinals", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 26),
	NINERS("49ers", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 19),
	RAMS("Rams", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST, 7);
	
	private String teamName;
	private NFLConferenceEnum conference;
	private NFLDivisionEnum division;
	private int defaultHomeFieldAdvantage;
	
	private NFLTeamEnum(String teamName, NFLConferenceEnum conference, 
			NFLDivisionEnum division, int defaultHomeFieldAdvantage) {
		this.teamName = teamName;
		this.conference = conference;
		this.division = division;
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
	
	public int getDefaultHomeFieldAdvantage() {
		return defaultHomeFieldAdvantage;
	}

	public void setDefaultHomeFieldAdvantage(int defaultHomeFieldAdvantage) {
		this.defaultHomeFieldAdvantage = defaultHomeFieldAdvantage;
	}
	
}
