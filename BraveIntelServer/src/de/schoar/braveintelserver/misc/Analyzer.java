package de.schoar.braveintelserver.misc;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.data.Report;

public class Analyzer {

	private Set<String> ignores = new TreeSet<String>();
	private Set<String> highlights = new TreeSet<String>();
	private Set<String> systems = new TreeSet<String>();
	private Set<String> bans = new TreeSet<String>();
	private Map<String, String> replaces = new HashMap<String, String>();

	private final Pattern patternWords = Pattern
			.compile("(.*?)[\\ \\,\\.\\:\\;\\/\\*\\_\\&\\+\\!\\?\\r\\n\\(\\)\\[\\])]+");

	private final Pattern patternUrl = Pattern
			.compile("http(s)?://[a-zA-Z0-9\\-\\.\\/\\?\\&\\%\\=\\_]+");

	public void load() {
		bans.clear();
		new LineReader() {
			@Override
			public void line(String line) {
				bans.add(line);
			}
		}.load("Bans", C.DATA_DIR + "/filters/bans.lst");

		systems.clear();
		new LineReader() {
			@Override
			public void line(String line) {

				String split[] = line.split("=");
				if (split.length != 2) {
					return;
				}
				systems.add(split[0]);
			}
		}.load("Solar Systems", C.DATA_DIR + "/filters/systems.lst");

		ignores.clear();
		new LineReader() {
			@Override
			public void line(String line) {
				ignores.add(line);
			}
		}.load("Ignore List", C.DATA_DIR + "/filters/ignores.lst");

		highlights.clear();
		new LineReader() {
			@Override
			public void line(String line) {
				highlights.add(line);
			}
		}.load("Highlight List", C.DATA_DIR + "/filters/highlights.lst");

		replaces.clear();
		new LineReader() {
			@Override
			public void line(String line) {
				String split[] = line.split("=");
				if (split.length != 2) {
					return;
				}
				replaces.put(split[0], split[1]);
			}
		}.load("Replace List", C.DATA_DIR + "/filters/replaces.lst");

	}

	public boolean analyze(Report report) {
		String line = report.getTextRaw() + " ";
		line = line.replaceAll("<", "&lt;");
		line = line.replaceAll(">", "&gt;");
		line = line.replaceAll("'", "&apos;");
		line = line.replaceAll("`", "&apos;");
		line = line.replaceAll("\"", "&quot;");

		Matcher matcher;
		matcher = patternUrl.matcher(line);
		while (matcher.find()) {
			String all = matcher.group(0);

			URL url = Helper.toURL(all);
			if (url == null) {
				continue;
			}

			line = line.replace(all, "<a href='" + all + "' target='_blank'>"
					+ url.getHost() + "</a>");
		}

		matcher = patternWords.matcher(line);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String all = matcher.group(0);
			String part = matcher.group(1);
			String needle = part.toLowerCase();

			if (findInList(bans, needle)) {
				return false;
			}

			if (findInList(ignores, needle)) {
				sb.append(all);
				continue;
			}

			String sub = replaces.get(needle);
			if (sub != null) {
				needle = sub;
			}

			if (findInList(highlights, needle)) {
				sb.append(all.replaceAll(part, "<b>" + part + "</b>"));
				continue;
			}

			List<String> matchedSystems = findMatchingSystems(needle);
			report.getSystems().addAll(matchedSystems);
			if (!matchedSystems.isEmpty()) {
				String sys = new Gson().toJson(matchedSystems);
				sys = sys.replaceAll("\"", "\\\"");
				String rep = "<a href='javascript:logsSystemsClicked(" + sys
						+ ");'>" + part + "</a>";
				sb.append(all.replaceAll(part, rep));
				continue;
			}

			sb.append(all);
		}

		report.setTextInterpreted(sb.toString());
		return true;
	}

	private List<String> findMatchingSystems(String needle) {
		List<String> matched = new LinkedList<String>();

		if (needle.length() < 3) {
			return matched;
		}

		for (String name : systems) {
			if (!name.contains("-") && needle.length() < 4) {
				continue;
			}
			if (name.toLowerCase().startsWith(needle)) {
				matched.add(name);
			}
		}
		return matched;
	}

	private boolean findInList(Set<String> list, String needle) {
		for (String hay : list) {
			if (needle.matches(hay)) {
				return true;
			}
		}
		return false;
	}

}
