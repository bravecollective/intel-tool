package de.schoar.braveintelserver.misc;

import java.util.HashMap;
import java.util.Map;

public class ExpiringCounter extends TimerHelper {

	private final Map<String, Long> count = new HashMap<String, Long>();

	private long expire = 0;

	public ExpiringCounter(long interval, long expire) {
		this.expire = expire;
		timerInit(interval);
	}

	public void add(String key) {
		count.put(key, System.currentTimeMillis());
	}

	public void remove(String key) {
		count.remove(key);
	}

	public int getCount() {
		return count.size();
	}

	@Override
	protected void timerTick() {
		long now = System.currentTimeMillis();
		Map<String, Long> copy = new HashMap<String, Long>(count);

		for (String key : copy.keySet()) {
			if (now - copy.get(key) < this.expire) {
				continue;
			}
			count.remove(key);
		}
	}

}
