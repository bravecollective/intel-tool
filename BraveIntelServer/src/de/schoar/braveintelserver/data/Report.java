package de.schoar.braveintelserver.data;

import java.util.Set;
import java.util.TreeSet;

public class Report {

	public long submittedAt = 0;
	public Set<String> systems = new TreeSet<String>();
	public String textRaw = "";
	public String textInterpreted = "";
	public String reporter = "";
	public int submittedCount = 0;

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		return textRaw.equals(((Report) o).textRaw);
	}
}
