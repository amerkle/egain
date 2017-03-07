package com.amerkle.egain.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static final String dbUrl = "jdbc:sqlite:sample.db";
	private static Connection connection = null;

	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(dbUrl);
		}
		return connection;
	}

	public static void main(String[] args) {
		Measurement.initialize();
	}

	public static class Measurement {

		public static void drop() {
			try {
				Statement statement = getConnection().createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				statement.executeUpdate("drop table if exists measurement");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public static void initialize() {
			try {
				Statement statement = getConnection().createStatement();
				statement.setQueryTimeout(5); // set timeout to 30 sec.
				statement.executeUpdate(
						"create table measurement (guid text, created text, temperature real, humidity real, constraint measurementUnique unique (guid, created))");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public static void add(String guid, double temperature, double humidity, String evt_create) {
			try {
				Statement statement = getConnection().createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				statement.executeUpdate("insert into measurement (guid,  created,  temperature,  humidity) values ("
						+ "'" + guid + "'," + "'" + evt_create + "','" + temperature + "','" + humidity + "')");
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static class Appartment {
		public static void drop() {
			try {
				Statement statement = getConnection().createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				statement.executeUpdate("drop table appartment");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public static void initialize() {
			try {
				Statement statement = getConnection().createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				statement
						.executeUpdate("create table appartment (guid text, address text, city text, appartment text)");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public static void add(String guid, String address, String city, String appartment) {
			try {
				Statement statement = getConnection().createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				statement.executeUpdate("insert into appartment (" + "guid, " + "address, " + "city, " + "appartment"
						+ ") values (" + "'" + guid + "'," + "'" + address + "','" + city + "','" + appartment + "')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
