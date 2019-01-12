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
		
		Timestamp start = java.sql.Timestamp.valueOf("2018-01-01 00:47:00");
		Timestamp end = java.sql.Timestamp.valueOf("2018-12-29 00:47:00");
		
		String zone[] = {"Ochota", "Rembertow", "Mokotow", "Bialoleka"};
		String vehicle[] = {"HPHCK0O", "HCLAQ1Z"};

		ResultSet result = j.raport(zone, vehicle, start, end);
		
		while (result.next()) {
			String getZone = result.getString("ZONE.Name");
			Timestamp startDate = result.getTimestamp("PRESENCE.Start_date");
			Timestamp endDate = result.getTimestamp("PRESENCE.End_date");
			String vehicleReg = result.getString("PRESENCE.Registration_number");
			
			System.out.println(getZone + " " + startDate + " " +  endDate + " " +  vehicleReg);
		}
		
		int id = j.getUserId();
		System.out.println(id);
		
		//j.addVehicle("DDDDD", "samochod_osobowy");
		
		DBConnection admin = new DBConnection("123");
		admin.closeConnection();
		
		j.closeConnection();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
