package de.schoar.braveintelserver.data;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.misc.TimerHelper;

public class ReportStorage extends TimerHelper {

	private final List<Report> reports = new LinkedList<Report>();

	private static final Pattern patternIntel = Pattern
			.compile("^\\[\\ [0-9\\.]*\\ [0-9\\:]*\\ \\]\\ ([a-zA-z0-9\\'\\ \\-]*)\\ >\\ (.*)");

	public ReportStorage() {
		timerInit(C.REPORT_CLEAN_INTERVAL);
	}

	public boolean add(String submitter, String text) {
		Matcher matcher = patternIntel.matcher(text);
		if (!matcher.matches()) {
			return false;
		}

		String reporter = matcher.group(1);
		String intel = matcher.group(2);

		synchronized (reports) {
			for (Report re : new LinkedList<Report>(reports)) {
				if (intel.equals(re.getTextRaw())) {
					re.addSubmitter(submitter);
					return true;
				}
			}

			reports.add(Report.createReport(submitter, reporter, intel));
			return true;
		}
	}

	public void inject(String reporter, String text) {
		synchronized (reports) {
			reports.add(Report.createAdmin(reporter, text));
		}
	}

	public List<Report> get(long from, long to) {
		List<Report> matched = new LinkedList<Report>(reports);

		for (Report r : new LinkedList<Report>(matched)) {
			if (!r.isValid()) {
				matched.remove(r);
				continue;
			}

			if (r.getSubmittedAt() <= from) {
				matched.remove(r);
				continue;
			}
			if (r.getSubmittedAt() > to) {
				matched.remove(r);
				continue;
			}
		}
		return matched;
	}

	@Override
	protected void timerTick() {
		long now = System.currentTimeMillis();
		for (Report r : new LinkedList<Report>(reports)) {
			if (now - r.getSubmittedAt() < C.REPORT_CLEAN_EXPIRE) {
				continue;
			}
			reports.remove(r);
		}
	}

}
