package exec1;

import java.sql.*;
import java.util.Scanner;

public class _04_AddMinion {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        String[] minionStr = scanner.nextLine().split(" ");
        String minionName = minionStr[1];
        int minionAge = Integer.parseInt(minionStr[2]);
        String minionCity = minionStr[3];
        String villain = scanner.nextLine().split(" ")[1];

        Connection connection = DBConnection.getConnection("minions_db");

        // identify minion town_id
        PreparedStatement selectStatment = connection.prepareStatement(
                """
                        SELECT id
                        FROM towns
                        WHERE name = ?""");
        selectStatment.setString(1, minionCity);
        ResultSet resultSet = selectStatment.executeQuery();
        if (!resultSet.next()) {
            PreparedStatement updateStatement = connection.prepareStatement(
                    "INSERT INTO towns(name) VALUES (?)");
            updateStatement.setString(1, minionCity);
            updateStatement.executeUpdate();
            System.out.printf("Town %s was added to the database.%n", minionCity);
        }
        resultSet = selectStatment.executeQuery();
        resultSet.next();
        int townId = resultSet.getInt("id");

        // identify villain id
        selectStatment = connection.prepareStatement(
                """
                        SELECT id
                        FROM villains
                        WHERE name = ?""");
        selectStatment.setString(1, villain);
        resultSet = selectStatment.executeQuery();
        if (!resultSet.next()) {
            PreparedStatement updateStatement = connection.prepareStatement(
                    """
                            INSERT INTO villains(name, evilness_factor)
                            VALUES (?, ?);""");
            updateStatement.setString(1, villain);
            updateStatement.setString(2, "evil");
            updateStatement.executeUpdate();
            System.out.printf("Villain %s was added to the database.%n", villain);
        }
        resultSet = selectStatment.executeQuery();
        resultSet.next();
        int villainId = resultSet.getInt("id");

        PreparedStatement updateStatement = connection.prepareStatement(
                """
                        INSERT INTO minions(name, age, town_id)
                            VALUES (?, ?, ?);"""
        );
        updateStatement.setString(1, minionName);
        updateStatement.setInt(2, minionAge);
        updateStatement.setInt(3, townId);
        int status = updateStatement.executeUpdate();
        if (status > 0) {
            selectStatment = connection.prepareStatement(
                    """
                            SELECT id
                            FROM minions
                            WHERE name = ? AND age = ? AND town_id = ?;
                            """
            );
            selectStatment.setString(1, minionName);
            selectStatment.setInt(2, minionAge);
            selectStatment.setInt(3, townId);
            resultSet = selectStatment.executeQuery();
            resultSet.next();
            int minionId = resultSet.getInt("id");

            updateStatement = connection.prepareStatement(
                    """
                            INSERT INTO minions_villains(minion_id, villain_id)
                            VALUES (?, ?);
                            """
            );
            updateStatement.setInt(1, minionId);
            updateStatement.setInt(2, villainId);
            status = updateStatement.executeUpdate();
            if (status > 0) {
                System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villain);
            }

            connection.close();
        }
    }
}
