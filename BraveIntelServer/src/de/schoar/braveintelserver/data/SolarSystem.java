package de.schoar.braveintelserver.data;

public class SolarSystem {
	private String system;
	private String region;

	public SolarSystem(String system, String region) {
		this.system = system;
		this.region = region;
	}

	public String getSystem() {
		return system;
	}

	public String getRegion() {
		return region;
	}

}
