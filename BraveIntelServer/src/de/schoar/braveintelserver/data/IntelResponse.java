package de.schoar.braveintelserver.data;

import java.util.LinkedList;
import java.util.List;

import de.schoar.braveintelserver.C;

public class IntelResponse {
	public long timestamp = System.currentTimeMillis();
	public List<Report> reports = new LinkedList<Report>();
	public int submitterCount = 0;
	public int viewerCount = 0;
	public long pollInterval = C.POLL_INTERVAL_BASE;
}
