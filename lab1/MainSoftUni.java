package lab1;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class MainSoftUni {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "12345");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni", props);

        PreparedStatement statement = connection.prepareStatement(
                "SELECT first_name, last_name FROM employees WHERE salary > ?"
        );

        Scanner scanner = new Scanner(System.in);
        String salary = scanner.nextLine();
        statement.setDouble(1, Double.parseDouble(salary));

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s  %s%n",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")
            );
        }
    }
}
