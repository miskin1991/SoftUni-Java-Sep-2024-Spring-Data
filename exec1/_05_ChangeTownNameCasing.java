package exec1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class _05_ChangeTownNameCasing {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();

        Connection connection = DBConnection.getConnection("minions_db");

        // find all towns for given country
        PreparedStatement select = connection.prepareStatement(
                """
                        SELECT name
                        FROM towns
                        WHERE country = ?;"""
        );
        select.setString(1, country);
        ResultSet resultSet = select.executeQuery();
        List<String> towns = new ArrayList<>();
        while (resultSet.next()) {
            towns.add(resultSet.getString("name"));
        }

        PreparedStatement update = connection.prepareStatement(
                """
                        UPDATE towns
                        SET name = UPPER(name)
                        WHERE country = ?;"""
        );
        update.setString(1, country);
        int i = update.executeUpdate();

        if (i > 0) {
            System.out.printf("%d town names were affected.%n", i);
            System.out.println(towns
                    .stream()
                    .map(String::toUpperCase)
                    .collect(Collectors.joining(", ", "[", "]"))
            );
        } else {
            System.out.println("No town names were affected.");
        }

        connection.close();
    }
}
