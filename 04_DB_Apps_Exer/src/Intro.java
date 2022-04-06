import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Intro {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {

        Connection connection = getConnection();

        PreparedStatement prepareStatement = connection
                .prepareStatement("SELECT * FROM minions WHERE id < ?;");

        System.out.println("Enter max id");
        int maxId = Integer.parseInt(bufferedReader.readLine());

        prepareStatement.setInt(1, maxId);

        ResultSet resultSet = prepareStatement.executeQuery();


        while (resultSet.next()) {
            System.out.printf("%s %d %n", resultSet.getString("name"),
                    resultSet.getInt("age"));
        }
    }

    private static Connection getConnection() throws IOException, SQLException {
        System.out.println("Enter user:");
        String user = bufferedReader.readLine();
        System.out.println("Enter password:");
        String password = bufferedReader.readLine();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        return DriverManager.getConnection(CONNECTION_STRING + DB_NAME, props);
    }

}
