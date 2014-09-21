package de.schoar.braveintelserver;

public class C {
	public final static boolean DEBUG = false;

	public final static String DATABASE_CONN = "jdbc:mysql://localhost:3306/intel";
	public final static String DATABASE_USER = "root";
	public final static String DATABASE_PASS = "root";

	public final static String DATA_DIR = "/opt/brave-intel-server";

	public final static String AUTH_COOKIE_NAME = "brave-auth-intel";

	public final static long POLL_INTERVAL_BASE = 1000 * 6;
	public final static long POLL_INTERVAL_USER = 60;

	public final static long REPORT_CLEAN_INTERVAL = 1000 * 60 * 1;
	public final static long REPORT_CLEAN_EXPIRE = 1000 * 60 * 90;

	public final static long AUTH_CLEAN_INTERVAL = 1000 * 60 * 1;
	public final static long AUTH_CLEAN_EXPIRE = 1000 * 60 * 10;

	public final static long UPLOADER_COUNT_INTERVAL = 1000 * 60 * 1;
	public final static long UPLOADER_COUNT_EXPIRE = 1000 * 60 * 20;

	public final static long VIEWER_COUNT_INTERVAL = 1000 * 60 * 1;
	public final static long VIEWER_COUNT_EXPIRE = 1000 * 60 * 1;

}
