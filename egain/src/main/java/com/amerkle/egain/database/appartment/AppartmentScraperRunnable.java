package com.amerkle.egain.database.appartment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.amerkle.egain.database.Database;
import com.amerkle.egain.parent.Configuration;

public class AppartmentScraperRunnable implements Runnable {
	private String guid = null;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Override
	public void run() {
		String json = loadCheckInstalled(guid);
		if (json == null) {
			return;
		}
		JSONObject obj = new JSONObject(json);
		JSONObject appartmentInfo = obj.getJSONObject("AppartmentInfo");
		String address = appartmentInfo.getString("Address");
		String city = appartmentInfo.getString("City");
		String appartment = appartmentInfo.getString("Appartment");
		Database.Appartment.add(getGuid(), address, city, appartment);
		System.out.println(getGuid() + ": " + appartment + ", " + address + ", " + city);
	}

	private String loadCheckInstalled(String guid) {
		try {
			String urlParameters = "guid=" + guid;
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			URL url = new URL(Configuration.getCheckInstalledUrl());
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
}
