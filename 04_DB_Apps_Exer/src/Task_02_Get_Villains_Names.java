import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Task_02_Get_Villains_Names {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {

        Connection connection = getConnection();

        PreparedStatement prepareStatement = connection
                .prepareStatement(
                        "SELECT v.name, COUNT(DISTINCT mv.minion_id) AS `m_count` FROM villains AS v\n" +
                        "join minions_villains mv on v.id = mv.villain_id\n" +
                        "GROUP BY v.name\n" +
                        "HAVING `m_count` > ?;");

        prepareStatement.setInt(1, 15);

        ResultSet resultSet = prepareStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d %n", resultSet.getString(1),
                    resultSet.getInt(2));
        }
    }

    private static Connection getConnection() throws IOException, SQLException {
//        System.out.println("Enter user:");
//        String user = bufferedReader.readLine();
//        System.out.println("Enter password:");
//        String password = bufferedReader.readLine();

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "");

        return DriverManager.getConnection(CONNECTION_STRING + DB_NAME, props);
    }

}
