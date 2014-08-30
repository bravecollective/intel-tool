package de.schoar.braveintelserver.misc;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.Cookie;

public class Helper {

	public static long parseLongOrZero(boolean clean, String str) {
		if (str == null) {
			return 0;
		}
		if (clean) {
			str = str.replaceAll("[^0-9]", "");
		}
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int parseIntOrZero(boolean clean, String str) {
		if (str == null) {
			return 0;
		}
		if (clean) {
			str = str.replaceAll("[^0-9]", "");
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static URL toURL(String link) {
		try {
			return new URL(link);
		} catch (MalformedURLException e) {
			return null;
		}

	}

	public static boolean equalsNull(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1.equals(o2);
	}

	public static String getCookie(String name, Cookie cookies[]) {
		if (cookies == null) {
			return null;
		}
		for (Cookie c : cookies) {
			if (name.equals(c.getName())) {
				return c.getValue();
			}
		}
		return null;
	}
}
