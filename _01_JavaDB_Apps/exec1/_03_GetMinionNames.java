package exec1;

import java.sql.*;
import java.util.Scanner;

public class _03_GetMinionNames {
    public static void main(String[] args) throws SQLException {

        Connection connection = DBConnection.getConnection("minions_db");

        PreparedStatement statement = connection.prepareStatement(
                """
                        SELECT
                            v.name AS villain,
                            m.name AS minion,
                            m.age
                        FROM villains v
                        JOIN minions_villains mv ON v.id = mv.villain_id
                        JOIN minions m ON mv.minion_id = m.id
                        WHERE v.id = ?;"""
        );

        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.printf("Villain: %s%n", resultSet.getString("villain"));
            int count = 0;
            do {
                count++;
                System.out.printf("%d. %s %d%n",
                        count,
                        resultSet.getString("minion"),
                        resultSet.getInt("age")
                );
            } while (resultSet.next());
        } else {
            System.out.printf("No villain with ID %d exists in the database.", id);
        }

        connection.close();
    }
}
