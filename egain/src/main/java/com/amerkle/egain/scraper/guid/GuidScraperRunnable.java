package com.amerkle.egain.scraper.guid;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class GuidScraperRunnable implements Runnable {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		String guid = loadFile(getId());
		String json = loadCheckInstalled(guid);
		if (json == null) {
			return;
		}
		JSONObject obj = new JSONObject(json);
		String cName = obj.getString("CName");
		JSONObject appartmentInfo = obj.getJSONObject("AppartmentInfo");
		if (cName != null && cName.contains("Baugenossenschaft")) {
			System.out.print("OK:  ");
			System.out.println(getId() + "/" + guid + " > " + appartmentInfo.getString("Address") + ", "
					+ appartmentInfo.getString("Appartment"));
		}
	}

	/**
	 * 
	 * @param id
	 *            in the following form: 94002154
	 * @return the guid
	 */
	private String loadFile(int id) {
		URLConnection con;
		try {
			con = new URL(GuidScraper.BASE_URL + id).openConnection();
			con.connect();
			con.getInputStream();
			return con.getURL().toString().split("gid=")[1];
		} catch (Exception e) {
			return null;
		}
	}

	private String loadCheckInstalled(String guid) {
		try {
			String urlParameters = "guid=" + guid;
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			URL url = new URL(GuidScraper.CHECK_INSTALLED_URL);
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
			return IOUtils.toString(conn.getInputStream(), "UTF-8");

		} catch (IOException e) {
			return null;
		}
	}
}
