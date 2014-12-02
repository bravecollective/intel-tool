package de.schoar.braveintelserver.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.misc.LineReader;

public class SolarSystemStorage {

	private Map<String, String> systems = new HashMap<String, String>();

	public void load() {
		systems.clear();
		new LineReader() {
			@Override
			public void line(String line) {
				String split[] = line.split("=");
				if (split.length != 2) {
					return;
				}
				systems.put(split[0], split[1]);
			}
		}.load("System List", C.DATA_DIR + "/filters/systems.lst");
	}

	public List<SolarSystem> findMatchingSystems(String needle) {
		List<SolarSystem> matched = new LinkedList<SolarSystem>();

		if (needle.length() < 3) {
			return matched;
		}

		for (String name : systems.keySet()) {
			if (name.toLowerCase().startsWith(needle.toLowerCase())) {
				matched.add(new SolarSystem(name, systems.get(name)));
			}
		}

		return matched;
	}

}
