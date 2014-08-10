package de.schoar.braveintelserver.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ExpiringCounter {

	private final Timer timerClean = new Timer(this.getClass()
			.getCanonicalName(), true);

	private final Map<String, Long> count = new HashMap<String, Long>();

	private long expire = 0;

	public ExpiringCounter(long interval, long expire) {
		this.expire = expire;

		timerClean.schedule(new TimerTask() {
			@Override
			public void run() {
				clean();
			}
		}, interval, interval);
	}

	public void stop() {
		timerClean.cancel();
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

	private void clean() {
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
