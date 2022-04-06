import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task_08_Increase_Minions_Age {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";

    public static void main(String[] args) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DB_NAME, properties);

        Scanner scanner = new Scanner(System.in);
        List<Integer> minionsIds = Arrays.stream(scanner.nextLine()
                .split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        PreparedStatement updateMinionsAge = connection.prepareStatement(
                "UPDATE minions SET age = age + 1, name = LOWER(name) WHERE id = ?");

        for (Integer id : minionsIds) {
            updateMinionsAge.setInt(1, id);
            updateMinionsAge.executeUpdate();
        }

        PreparedStatement printMinionsNameAndAge = connection.prepareStatement(
                "SELECT name, age FROM minions");
        ResultSet allMinions = printMinionsNameAndAge.executeQuery();

        while (allMinions.next()) {
            System.out.println(allMinions.getString("name")
                    + " "
                    + allMinions.getInt("age"));
        }

        connection.close();
    }
}
