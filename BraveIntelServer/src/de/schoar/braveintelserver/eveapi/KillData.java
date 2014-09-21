package de.schoar.braveintelserver.eveapi;

public class KillData {
	private int ships = 0;
	private int pods = 0;
	private int rats = 0;

	public KillData(int ships, int pods, int rats) {
		this.ships = ships;
		this.pods = pods;
		this.rats = rats;
	}

	public int getShips() {
		return ships;
	}

	public int getPods() {
		return pods;
	}

	public int getRats() {
		return rats;
	}

}
