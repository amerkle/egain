package com.amerkle.egain.scraper.guid;

import java.util.stream.Stream;

public class GuidScraper {
	public static final String BASE_URL = "http://install.egain.se/?id=";
	public static final String CHECK_INSTALLED_URL = "http://install.egain.se/Home/CheckInstalled";
	public static final String LIST_SENSOR_VALUES_URL = "http://install.egain.se/Home/ListSensorValues";

	// private static final String GUID_DIR = "c:\\temp\\egain\\guid";

	public void fetchGuid(int lower, int upper) {
		Stream
		.iterate(lower, x -> x + 1)
		.limit(upper - lower)
		.forEach(GuidScraper::run);
	}

	private static void run(int x) {
		GuidScraperRunnable obj = new GuidScraperRunnable();
		obj.setId(x);
		Thread tobj = new Thread(obj);
		tobj.start();

	}
}
