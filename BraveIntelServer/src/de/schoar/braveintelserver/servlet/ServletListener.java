package de.schoar.braveintelserver.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.schoar.braveintelserver.C;
import de.schoar.braveintelserver.auth.SessionLookup;
import de.schoar.braveintelserver.data.MapStorage;
import de.schoar.braveintelserver.data.ReportStorage;
import de.schoar.braveintelserver.data.SolarSystemStorage;
import de.schoar.braveintelserver.eveapi.EveJumpDownloader;
import de.schoar.braveintelserver.eveapi.EveKillDownloader;
import de.schoar.braveintelserver.misc.Analyzer;
import de.schoar.braveintelserver.misc.ExpiringCounter;

public class ServletListener implements ServletContextListener {

	private static boolean RUNNING = false;

	private static final SessionLookup users = new SessionLookup("session");
	private static final SessionLookup uploaders = new SessionLookup("uploader");

	private static final ReportStorage reports = new ReportStorage();
	private static final Analyzer analyzer = new Analyzer();
	private static final MapStorage maps = new MapStorage();
	private static final SolarSystemStorage systems = new SolarSystemStorage();

	private static final ExpiringCounter uploaderCount = new ExpiringCounter(
			C.UPLOADER_COUNT_INTERVAL, C.UPLOADER_COUNT_EXPIRE);
	private static final ExpiringCounter viewerCount = new ExpiringCounter(
			C.VIEWER_COUNT_INTERVAL, C.VIEWER_COUNT_EXPIRE);

	private static EveJumpDownloader ejd = new EveJumpDownloader();
	private static EveKillDownloader ekd = new EveKillDownloader();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		maps.load();
		analyzer.load();
		systems.load();

		reports.inject("kiu Nakamura",
				"The Brave Intel Map server has been restarted. Sorry for the inconvenience...");

		System.err.println("*** Started "
				+ sce.getServletContext().getContextPath() + " ***");

		RUNNING = true;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		RUNNING = false;

		users.timerStop();
		uploaders.timerStop();

		reports.timerStop();

		viewerCount.timerStop();
		uploaderCount.timerStop();

		System.err.println("*** Stopped "
				+ sce.getServletContext().getContextPath() + " ***");
	}

	public static boolean isRunning() {
		return RUNNING;
	}

	public static SessionLookup getUserLookup() {
		return users;
	}

	public static SessionLookup getUploaderLookup() {
		return uploaders;
	}

	public static ReportStorage getReportStorage() {
		return reports;
	}

	public static SolarSystemStorage getSystemStorage() {
		return systems;
	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	public static MapStorage getMapStorage() {
		return maps;
	}

	public static ExpiringCounter getUploaderCounter() {
		return uploaderCount;
	}

	public static ExpiringCounter getViewerCounter() {
		return viewerCount;
	}

	public static EveJumpDownloader getEveJumps() {
		return ejd;
	}

	public static EveKillDownloader getEveKills() {
		return ekd;
	}
}