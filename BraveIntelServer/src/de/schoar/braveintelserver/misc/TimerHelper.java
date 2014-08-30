package de.schoar.braveintelserver.misc;

import java.util.Timer;
import java.util.TimerTask;

abstract public class TimerHelper {

	private final Timer timer = new Timer(this.getClass().getCanonicalName(),
			true);

	protected void timerInit(long interval) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timerTick();
			}
		}, interval, interval);
	}

	abstract protected void timerTick();

	public void timerStop() {
		timer.cancel();
	}

}
