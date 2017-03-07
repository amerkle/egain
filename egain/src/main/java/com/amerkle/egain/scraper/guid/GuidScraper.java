package com.amerkle.egain.scraper.guid;

import java.util.stream.Stream;

public class GuidScraper {

	public void fetchGuid(int lower, int upper) {
		Stream.iterate(lower, x -> x + 1).limit(upper - lower).forEach(GuidScraper::run);
	}

	private static void run(int x) {
		GuidScraperRunnable obj = new GuidScraperRunnable();
		obj.setId(x);
		Thread tobj = new Thread(obj);
		tobj.start();

	}
}
