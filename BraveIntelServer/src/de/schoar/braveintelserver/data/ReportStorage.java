package de.schoar.braveintelserver.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.servlet.ServletListener;

public class ReportStorage {

	private final List<Report> reports = new LinkedList<Report>();
	private final Timer timerClean = new Timer(true);

	private static final Pattern patternIntel = Pattern
			.compile("^\\[\\ [0-9\\.]*\\ [0-9\\:]*\\ \\]\\ ([a-zA-z0-9\\'\\ \\-]*)\\ >\\ (.*)");

	public ReportStorage() {
		timerClean.schedule(new TimerTask() {
			@Override
			public void run() {
				clean();
			}
		}, C.REPORT_CLEAN_INTERVAL, C.REPORT_CLEAN_INTERVAL);
	}

	public void stop() {
		timerClean.cancel();
	}

	public boolean add(String text) {
		Matcher matcher = patternIntel.matcher(text);
		if (!matcher.matches()) {
			return false;
		}

		String reporter = matcher.group(1);
		String intel = matcher.group(2);

		synchronized (reports) {
			for (Report re : new LinkedList<Report>(reports)) {
				if (intel.equals(re.textRaw)) {
					re.submittedCount++;
					return false;
				}
			}

			Report r = new Report();
			r.reporter = reporter;
			r.submittedAt = System.currentTimeMillis();
			r.submittedCount = 1;
			r.textRaw = intel;
			ServletListener.getAnalyzer().analyze(r);
			reports.add(r);
			return true;
		}
	}

	public List<Report> get(long from, long to) {
		List<Report> matched = new LinkedList<Report>(reports);

		for (Report r : new LinkedList<Report>(matched)) {
			if (r.submittedAt <= from) {
				matched.remove(r);
			}
			if (r.submittedAt > to) {
				matched.remove(r);
			}
		}
		return matched;
	}

	private void clean() {
		long now = System.currentTimeMillis();
		for (Report r : new LinkedList<Report>(reports)) {
			if (now - r.submittedAt < C.REPORT_CLEAN_EXPIRE) {
				continue;
			}
			reports.remove(r);
		}
	}
}
