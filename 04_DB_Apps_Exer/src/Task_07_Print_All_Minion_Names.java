import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Task_07_Print_All_Minion_Names {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";

    public static void main(String[] args) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DB_NAME, properties);

        Scanner scanner = new Scanner(System.in);
        List<String> minions = new ArrayList<>();

        PreparedStatement selectAllMinionNames = connection.prepareStatement(
                "SELECT name FROM minions");
        ResultSet minionsNamesSet = selectAllMinionNames.executeQuery();

        while (minionsNamesSet.next()) {
            String minionName = minionsNamesSet.getString(1);
            minions.add(minionName);
        }

        int startIndex = 0;
        int endIndex = minions.size() - 1;

        for (int i = 0; i < minions.size(); i++) {
            if (i % 2 == 0 ) {
                System.out.println(minions.get(startIndex++));
            } else {
                System.out.println(minions.get(endIndex--));
            }
        }

        connection.close();
    }
}

