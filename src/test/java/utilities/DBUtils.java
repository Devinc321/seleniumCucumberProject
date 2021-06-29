package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

	static Connection connection;
	static Statement statement;
	static String searchQuery = "SELECT * FROM records WHERE symbol='DDD'";

	// executeQuery()
	// executeUpdate()
	// execute()
	
	
	public static void main(String[] args) throws SQLException {
		connection = DriverManager.getConnection(
				"jdbc:mysql://database-1.cbf9mjnqgnfr.us-east-2.rds.amazonaws.com:3306/stock_trading_tracker", "admin",
				"Password123!");
		System.out.println("DB connection established.");

		statement = connection.createStatement();
		statement.executeQuery(searchQuery);

		connection.close();
	}

}
