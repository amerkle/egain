package com.amerkle.egain.database.appartment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.amerkle.egain.database.Database;

public class AppartmentScraper {

	public static void main(String[] args) {
		Database.Appartment.drop();
		Database.Appartment.initialize();

		String line;
		try (InputStream fis = new FileInputStream("C:\\Users\\Adrian\\Dropbox\\egain\\guid.txt");
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);) {
			while ((line = br.readLine()) != null) {
				AppartmentScraperRunnable runner = new AppartmentScraperRunnable();
				runner.setGuid(line);
				Thread thread = new Thread(runner);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
