package de.schoar.braveintelserver.eveapi;

import java.util.HashMap;
import java.util.Map;

public class EveResponse {
	public Map<String, Integer> jumps = new HashMap<String, Integer>();
	public Map<String, KillData> kills = new HashMap<String, KillData>();
}
