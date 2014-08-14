package de.schoar.braveintelserver.data;

import java.util.Set;
import java.util.TreeSet;

import de.schoar.braveintelserver.misc.Helper;
import de.schoar.braveintelserver.servlet.ServletListener;

public class Report {

	transient private Set<String> submitters = new TreeSet<String>();
	private String reporter;
	transient private String textRaw;

	private long submittedAt = System.currentTimeMillis();
	transient private int submitterCountAtCreation = ServletListener
			.getUploaderCounter().getCount();

	private Set<String> systems = new TreeSet<String>();
	private String textInterpreted = "";

	public Report(String submitter, String reporter, String textRaw) {
		this.reporter = reporter;
		this.textRaw = textRaw;
		this.submitters.add(submitter);

		ServletListener.getAnalyzer().analyze(this);
	}

	public String getReporter() {
		return reporter;
	}

	public String getTextRaw() {
		return textRaw;
	}

	public long getSubmittedAt() {
		return submittedAt;
	}

	public int getSubmitterCount() {
		return submitters.size();
	}

	public void addSubmitter(String submitter) {
		submitters.add(submitter);
	}

	public int getSubmitterCountAtCreation() {
		return submitterCountAtCreation;
	}

	public Set<String> getSystems() {
		return systems;
	}

	public String getTextInterpreted() {
		return textInterpreted;
	}

	public void setTextInterpreted(String textInterpreted) {
		this.textInterpreted = textInterpreted;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Report)) {
			return false;
		}

		Report r = (Report) o;
		if (!Helper.equalsNull(this.textRaw, r.getTextRaw())) {
			return false;
		}
		if (!Helper.equalsNull(this.reporter, r.getReporter())) {
			return false;
		}

		return true;
	}

}
