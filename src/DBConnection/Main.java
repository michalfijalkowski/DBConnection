package DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws SQLException {
	

		DBConnection j = new DBConnection(916361628, "BKi-I%Z(0mx");
		
		Timestamp start = java.sql.Timestamp.valueOf("2018-01-24 00:47:00");
		Timestamp end = java.sql.Timestamp.valueOf("2018-12-29 00:47:00");

		ResultSet result = j.raport("ochota", "motocykl", start, end);
		
		while (result.next()) {
			String zone = result.getString("ZONE.Name");
			String type = result.getString("VEHICLE.Type");
			Timestamp startDate = result.getTimestamp("PRESENCE.Start_date");
			Timestamp endDate = result.getTimestamp("PRESENCE.End_date");
			String vehicleType = result.getString("VEHICLE.Type");
			System.out.println(zone + " " + startDate + " " +  endDate + " " +  vehicleType);
		}
		
		ResultSet resultZone = j.zoneList();
		while(resultZone.next()) {
			String e = resultZone.getString(1);
			System.out.println(e);
		}

		j.closeConnection();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
