package nfl.season.league.enums;

public enum NFLTeamEnum {

	PATRIOTS("Patriots", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST),
	DOLPHINS("Dolphins", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST),
	JETS("Jets", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST),
	BILLS("Bills", NFLConferenceEnum.AFC, NFLDivisionEnum.EAST),
	
	STEELERS("Steelers", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH),
	RAVENS("Ravens", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH),
	BENGALS("Bengals", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH),
	BROWNS("Browns", NFLConferenceEnum.AFC, NFLDivisionEnum.NORTH),
	
	TEXANS("Texans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH),
	COLTS("Colts", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH),
	TITANS("Titans", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH),
	JAGUARS("Jaguars", NFLConferenceEnum.AFC, NFLDivisionEnum.SOUTH),
	
	BRONCOS("Broncos", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST),
	RAIDERS("Raiders", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST),
	CHIEFS("Chiefs", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST),
	CHARGERS("Chargers", NFLConferenceEnum.AFC, NFLDivisionEnum.WEST),
	
	GIANTS("Giants", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST),
	COWBOYS("Cowboys", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST),
	EAGLES("Eagles", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST),
	REDSKINS("Redskins", NFLConferenceEnum.NFC, NFLDivisionEnum.EAST),
	
	PACKERS("Packers", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH),
	VIKINGS("Vikings", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH),
	BEARS("Bears", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH),
	LIONS("Lions", NFLConferenceEnum.NFC, NFLDivisionEnum.NORTH),
	
	FALCONS("Falcons", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH),
	SAINTS("Saints", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH),
	PANTHERS("Panthers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH),
	BUCCANEERS("Buccaneers", NFLConferenceEnum.NFC, NFLDivisionEnum.SOUTH),
	
	SEAHAWKS("Seahawks", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST),
	CARDINALS("Cardinals", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST),
	NINERS("49ers", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST),
	RAMS("Rams", NFLConferenceEnum.NFC, NFLDivisionEnum.WEST);
	
	private String teamName;
	private NFLConferenceEnum conference;
	private NFLDivisionEnum division;
	
	private NFLTeamEnum(String teamName, NFLConferenceEnum conference, NFLDivisionEnum division) {
		this.teamName = teamName;
		this.conference = conference;
		this.division = division;
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
	
}
