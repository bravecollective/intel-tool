package de.schoar.braveintelserver.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.misc.LineReader;

public class MapStorage {

	private Map<String, String> maps = new HashMap<String, String>();

	private void load(String name, String key, String file) {
		final StringBuffer sb = new StringBuffer();
		new LineReader() {
			@Override
			public void line(String line) {
				sb.append(line);
			}
		}.load(name, file);
		maps.put(key, sb.toString());
	}

	public void load() {
		maps.clear();
		File dir = new File(C.DATA_DIR + "/maps/");
		for (File f : dir.listFiles()) {
			if (!f.isFile()) {
				continue;
			}
			load("Map " + f.getName(), f.getName(), f.getAbsolutePath());
		}
	}

	public String get(String region) {
		return maps.get(region);
	}

}
