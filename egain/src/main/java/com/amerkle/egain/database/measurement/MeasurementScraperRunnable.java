package com.amerkle.egain.database.measurement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amerkle.egain.database.Database;
import com.amerkle.egain.scraper.guid.GuidScraper;

public class MeasurementScraperRunnable implements Runnable {
	private static final String PATTERN = "\\{\"Temp\":([0-9\\.]+),\"Hum\":([0-9\\.]+),\"Date\":\"([0-9\\- :]+)\"\\}";
	private String guid = null;
	private int days;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Override
	public void run() {
		String json = loadListSensorValues();
		writeToFile(json);
		if (json == null) {
			return;
		}
		int count = 0;
		Pattern pattern = Pattern.compile(PATTERN);

		Matcher matcher = pattern.matcher(json);
		StringBuilder sb = new StringBuilder();
		sb.append("insert into measurement (guid,  created,  temperature,  humidity) values ");
		while (matcher.find()) {
			count++;
			sb.append("(");
			sb.append("'").append(getGuid()).append("', ");
			sb.append("'").append(matcher.group(3)).append("', ");
			sb.append(matcher.group(1)).append(", ");
			sb.append(matcher.group(2));
			sb.append("),\n");
		}
		if (count > 0) {
			String substring = sb.substring(0, sb.length() - 2);
			try (Statement createStatement = Database.getConnection().createStatement()) {
				int executeUpdate = createStatement.executeUpdate(substring + ";");
				System.out.println(getGuid() + " done (" + executeUpdate + " rows).");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(getGuid() + " done (" + 0 + " rows).");
		}
	}

	private String loadListSensorValues() {
		try {
			String urlParameters = "guid=" + getGuid() + "&daysAgo=" + getDays();
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			URL url = new URL(GuidScraper.LIST_SENSOR_VALUES_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setUseCaches(false);
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(postData);
			}
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int c = in.read(); c != -1; c = in.read()) {
				sb.append((char) c);
			}
			return sb.toString();

		} catch (Exception e) {
			return null;
		}
	}

	private void writeToFile(String json) {
		try (PrintWriter out = new PrintWriter(
				new BufferedWriter(new FileWriter("c:/temp/egain/" + getGuid() + ".txt", true)))) {
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
