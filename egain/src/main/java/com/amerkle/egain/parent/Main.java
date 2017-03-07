package com.amerkle.egain.parent;

import com.amerkle.egain.scraper.guid.GuidScraper;

public class Main {
	public static void main(String[] args) {
		GuidScraper gs = new GuidScraper();
		gs.fetchGuid(94000000, 94003000);
		gs.fetchGuid(93999000, 94000000);
	}
}
