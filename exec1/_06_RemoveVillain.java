package exec1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _06_RemoveVillain {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());

        Connection connection = DBConnection.getConnection("minions_db");

        PreparedStatement statement = connection.prepareStatement(
                """
                        SELECT name
                        FROM villains
                        WHERE id = ?;
                        """
        );
        statement.setInt(1, villainId);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = resultSet.getString("name");

        statement = connection.prepareStatement("""
                DELETE FROM minions_villains
                WHERE villain_id = ?""");
        statement.setInt(1, villainId);
        int released = statement.executeUpdate();
        statement = connection.prepareStatement("""
                DELETE FROM villains WHERE id = ?""");
        statement.setInt(1, villainId);
        statement.executeUpdate();

        System.out.printf("%s was deleted%n", villainName);
        System.out.printf("%d minions released%n", released);

        connection.close();
    }
}
