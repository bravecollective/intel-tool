package de.schoar.braveintelserver.misc;

import java.util.Timer;
import java.util.TimerTask;

abstract public class TimerHelper {

	private final Timer timer = new Timer(this.getClass().getCanonicalName(),
			true);

	protected void timerInit(long interval) {
		timerInit(interval, interval);
	}

	protected void timerInit(long start, long interval) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timerTick();
			}
		}, start, interval);
	}

	abstract protected void timerTick();

	public void timerStop() {
		timer.cancel();
	}

}
