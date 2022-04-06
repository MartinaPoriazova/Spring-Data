import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Task_09_Increase_Age_Stored_Procedure {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";

    public static void main(String[] args) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DB_NAME, properties);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter minion ID: ");
        int minionId = Integer.parseInt(scanner.nextLine());

        CallableStatement getOlderProcedure = connection.prepareCall("CALL usp_get_older(?)");
        getOlderProcedure.setInt(1, minionId);
        getOlderProcedure.executeUpdate();

        PreparedStatement printMinionNameAndAge = connection.prepareStatement(
                "SELECT name, age FROM minions WHERE id = ?");
        printMinionNameAndAge.setInt(1, minionId);
        ResultSet resultSet = printMinionNameAndAge.executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getString("name")
                    + " "
                    + resultSet.getInt("age"));
        }
        connection.close();
    }
}
