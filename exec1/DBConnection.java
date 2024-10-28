package exec1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection(String dbName) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "12345");

        return DriverManager.getConnection("""
                jdbc:mysql://localhost:3306/%s""".formatted(dbName), prop);
    }
}
