package exec1;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class _07_PrintAllMinionNames {

    public static void main(String[] args) throws SQLException {

        Connection connection = DBConnection.getConnection("minions_db");

        PreparedStatement stmt = connection.prepareStatement("""
                SELECT name FROM minions""");
        Deque<String> minionNames = new ArrayDeque<>();

        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            minionNames.offer(resultSet.getString("name"));
        }

        while (!minionNames.isEmpty()) {
            System.out.println(minionNames.pollFirst());
            System.out.println(minionNames.pollLast());
        }

        connection.close();
    }
}
