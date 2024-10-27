package exec1;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class _09_IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());

        Connection connection = DBConnection.getConnection("minions_db");

        PreparedStatement statement = connection.prepareCall("""
                CALL usp_get_older(?)"""
        );
        statement.setInt(1, id);
        statement.execute();

        statement = connection.prepareStatement("SELECT name, age FROM minions WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }

        connection.close();
    }
}
