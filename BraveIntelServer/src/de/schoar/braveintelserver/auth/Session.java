package de.schoar.braveintelserver.auth;

public class Session {

	private int charId = 0;
	private String charName = "";
	private String sessionId = "";
	private long createdAt = 0;

	public Session(int charId, String charName, String sessionId, long createdAt) {
		this.charId = charId;
		this.charName = charName;
		this.sessionId = sessionId;
		this.createdAt = createdAt;
	}

	public int getCharId() {
		return charId;
	}

	public String getCharName() {
		return charName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public long getCreatedAt() {
		return createdAt;
	}

}
