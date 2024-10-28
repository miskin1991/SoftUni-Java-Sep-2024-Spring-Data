package exec1;

import java.sql.*;

public class _02_GetVillainsNames {

    public static void main(String[] args) throws SQLException {

        Connection connection = DBConnection.getConnection("minions_db");

        PreparedStatement statement = connection.prepareStatement(
                """
                        SELECT v.name, COUNT(*) AS count
                        FROM villains v
                        JOIN minions_villains mv ON v.id = mv.villain_id
                        JOIN minions m ON mv.minion_id = m.id
                        GROUP BY v.name
                        HAVING count > 15
                        ORDER BY count DESC;"""
        );

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.println(
                    resultSet.getString("name") + " " + resultSet.getInt("count")
            );
        }

        connection.close();
    }
}
