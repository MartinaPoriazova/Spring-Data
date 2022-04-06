import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "Tearsforfears01!");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo", props);

        PreparedStatement statement = connection.prepareStatement("SELECT user_name, first_name, last_name," +
                " COUNT(users_games.id)" +
                " FROM users" +
                " JOIN users_games ON users_games.user_id = users.id" +
                " WHERE user_name = ?" +
                " GROUP BY users_games.user_id");

        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();

        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            resultSet.getMetaData();
            String dbUsername = resultSet.getString("user_name");
            String dbFirstName = resultSet.getString("first_name");
            String dbLastName = resultSet.getString("last_name");
            int dbGameCount = resultSet.getInt("COUNT(users_games.id)");
            // Integer dbGameCount2 = Integer.parseInt(resultSet.getString(4));

            System.out.printf("User: %s\n%s %S has played %d games\n",
                    dbUsername, dbFirstName, dbLastName, dbGameCount);
        } else {
            System.out.println("No such user exists");
        }
    }
}
