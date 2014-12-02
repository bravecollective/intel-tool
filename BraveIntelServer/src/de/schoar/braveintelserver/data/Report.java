package de.schoar.braveintelserver.data;

import java.util.Set;
import java.util.TreeSet;

import de.schoar.braveintelserver.misc.Helper;
import de.schoar.braveintelserver.servlet.ServletListener;

public class Report {

	transient private Set<String> submitters = new TreeSet<String>();
	transient private String textRaw;
	transient private int submitterCountAtCreation = 0;
	transient private boolean isValid = false;
	
	private String reporter = "";
	private long submittedAt = System.currentTimeMillis();
	private Set<String> systems = new TreeSet<String>();
	private String textInterpreted = "";
	
	public static Report createReport(String submitter, String reporter,
			String textRaw) {
		return new Report(submitter, reporter, textRaw, ServletListener
				.getUploaderCounter().getCount());
	}

	public static Report createAdmin(String reporter, String textRaw) {
		return new Report("admin", reporter, textRaw, 1);
	}

	private Report(String submitter, String reporter, String textRaw,
			int submitterCountAtCreation) {
		this.submitters.add(submitter);
		this.reporter = reporter;
		this.textRaw = textRaw;
		this.submitterCountAtCreation = submitterCountAtCreation;
		
		isValid = ServletListener.getAnalyzer().analyze(this);
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

	public boolean isValid() {
		return isValid;
		//return getSubmitterCount() >= getSubmitterCountAtCreation() / 2;
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
