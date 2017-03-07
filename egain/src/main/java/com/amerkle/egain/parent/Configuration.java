package com.amerkle.egain.parent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Configuration {
	private static final Properties props = new Properties();

	private Configuration() {
	}

	static {
		try (InputStream s = Main.class.getResourceAsStream("/config.properties")) {

			// now can use this input stream as usually, i.e. to load as
			// properties
			props.load(s);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getDays() {
		return Integer.valueOf(props.getProperty("days"));
	}

	public static String getBaseUrl() {
		return props.getProperty("url.base");
	}

	public static String getCheckInstalledUrl() {
		return props.getProperty("url.checkInstalled");
	}

	public static String getListSensorValuesUrl() {
		return props.getProperty("url.listSensorValues");
	}
}
