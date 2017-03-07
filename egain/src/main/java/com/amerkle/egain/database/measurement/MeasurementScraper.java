package com.amerkle.egain.database.measurement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.amerkle.egain.database.Database;
import com.amerkle.egain.parent.Configuration;

public class MeasurementScraper {

	public static void main(String[] args) {
		Database.Measurement.drop();
		Database.Measurement.initialize();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT guid FROM appartment;");
			// .executeQuery("SELECT guid FROM appartment where guid =
			// '0b7e9215-1a85-4bb4-92a5-bda8798386d3';");
			while (resultSet.next()) {
				String guid = resultSet.getString("guid");
				MeasurementScraperRunnable runner = new MeasurementScraperRunnable();
				runner.setGuid(guid);
				runner.setDays(Configuration.getDays());
				Thread thread = new Thread(runner);
				thread.start();
			}
			resultSet.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
