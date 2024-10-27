package lab1;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class MainDiablo {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "12345");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", props);

        Scanner scanner = new Scanner(System.in);

        String username = scanner.nextLine();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) as games, " +
                        "u.user_name, u.first_name, u.last_name " +
                        "FROM users_games ug " +
                        "JOIN users u ON ug.user_id = u.id " +
                        "GROUP BY user_id " +
                        "HAVING user_id = (SELECT id FROM users WHERE user_name = ?);"
        );
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println("User: " + resultSet.getString("user_name"));
            System.out.printf("%s %s has played %d games",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("games"));
        } else {
            System.out.println("No such user exists");
        }
    }
}
