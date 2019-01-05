package DBConnection;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class DBConnection {

	private final static String DBURL = "jdbc:mariadb://s70.hekko.net.pl:3306/qtom97_bd2zones";
	private final static String DBUSER = "qtom97_bd2client";
	private final static String DBPASS = "PxC#210a";
	private final static String DBDRIVER = "org.mariadb.jdbc.Driver";

	private Connection connection;
	private java.sql.Statement statement;
	private int login;

	public DBConnection(int phoneNumber, String password) {
		try {
			Class.forName(DBDRIVER).newInstance();
			connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			if (!verify(phoneNumber, password))
				closeConnection();
			login = phoneNumber;
			System.out.println("ZALOGOWANY: " + login);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Connection doesn't exist");
		}
	}

	boolean verify(int telNumber, String password) {
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT TelNumber, Password From USER");
			statement.close();
			while (result.next()) {
				int TelNumber = result.getInt("TelNumber");
				if (TelNumber != telNumber)
					continue;
				String Password = result.getString("Password");
				return Password.equals(password);
			}

		} catch (SQLException e) {
			System.out.println("Nie jestes zalogowany");
		}

		return false;
	}

	ResultSet raport(String zone, String vehicle, Timestamp start, Timestamp end) {
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(
					"select ZONE.Name, PRESENCE.Start_date, PRESENCE.End_date, VEHICLE.Type from PRESENCE\r\n"
							+ "INNER JOIN ZONE ON PRESENCE.Zone_ID = ZONE.id\r\n"
							+ "INNER JOIN VEHICLE ON PRESENCE.Registration_number = VEHICLE.Registration_number\r\n"
							+ "INNER JOIN USER ON PRESENCE.id = USER.id\r\n" + "WHERE Start_date >= \"" + start
							+ "\" && End_date <= \"" + end + "\" && VEHICLE.Type = \"" + vehicle + "\" &&"
							+ " ZONE.Name = \"Ochota\" && USER.TelNumber = " + login + "\r\n" + "\r\n" + "");
			statement.close();
			return result;
		} catch (SQLException e) {
			System.out.println("Nie jestes zalogowany");
		}
		return null;

	}

	ResultSet vehicleList() {
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT Type from TARIFF_VEHICLE");
			statement.close();
			return result;
		} catch (SQLException e) {
			System.out.println("Nie jestes zalogowany");
		}
		return null;
	}
	
	ResultSet zoneList() {
		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT Name from ZONE");
			statement.close();
			return result;
		} catch (SQLException e) {
			System.out.println("Nie jestes zalogowany");
		}
		return null;
	}

}
