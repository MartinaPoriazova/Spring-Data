import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class Task_03_Get_Minion_Names {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {

        System.out.println("Enter villain id:");
        int villainId = Integer.parseInt(bufferedReader.readLine());
        String villainName = findVillainNameById(villainId);
        System.out.println("Villain: " + villainName);

        Set<String> allMinionByVillainName = getAllMinionByVillainName(villainId);
        allMinionByVillainName.forEach(System.out::print);

        getConnection().close();
    }

    private static Set<String> getAllMinionByVillainName(int villainId) throws SQLException, IOException {
        Set<String> result = new LinkedHashSet<>();

        Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT m.name, m.age FROM minions AS m\n" +
                "JOIN minions_villains mv on m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?;");
        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();
        int counter = 0;

        while (resultSet.next()) {
            result.add(String.format("%d. %s %d%n", ++counter,
                    resultSet.getString("name"),
                    resultSet.getInt("age")
            ));
        }
        return result;
    }

    private static String findVillainNameById(int villainId) throws SQLException, IOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name FROM villains\n" +
                        "WHERE id = ?;");

        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getString("name");
        } else {
            return String.format("No villain with ID %d exists in the database.", villainId);

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
