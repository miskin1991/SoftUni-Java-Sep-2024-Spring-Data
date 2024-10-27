package exec1;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class _08_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        int[] ids = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        Connection connection = DBConnection.getConnection("minions_db");

        for (int id : ids) {
            PreparedStatement statement = connection.prepareStatement("""
                    UPDATE minions
                    SET age = age + 1
                    WHERE id = ?"""
            );
            statement.setInt(1, id);
            statement.executeUpdate();
        }

        PreparedStatement statement = connection.prepareStatement("SELECT name, age FROM minions");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }

        connection.close();
    }
}
