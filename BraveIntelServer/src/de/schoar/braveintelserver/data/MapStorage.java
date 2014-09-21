package de.schoar.braveintelserver.data;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.misc.LineReader;

public class MapStorage {

	private Map<String, String> maps = new HashMap<String, String>();
	private Map<String, List<String>> systems = new HashMap<String, List<String>>();

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

		parseSystems();
	}

	public String get(String region) {
		return maps.get(region);
	}

	public List<String> getSystems(String region) {
		return systems.get(region);
	}

	private void parseSystems() {
		Map<String, List<String>> systemsNew = new HashMap<String, List<String>>();
		for (String key : maps.keySet()) {
			if (!key.matches("[A-Z].*")) {
				continue;
			}
			SVGData data = new Gson().fromJson(maps.get(key), SVGData.class);
			List<String> ids = new LinkedList<String>();
			for (SVGSystem sys : data.map.systems) {
				ids.add(sys.id);
			}
			systemsNew.put(key, ids);
		}
		systems = systemsNew;
	}

	class SVGData {
		public SVGMap map;
	}

	class SVGMap {
		public SVGSystem systems[];
	}

	class SVGSystem {
		public String id;
	}

}
