import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Task_04_Add_Minion {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";

    public static void main(String[] args) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");

        Connection connection = DriverManager.getConnection(CONNECTION_STRING + DB_NAME, properties);

        Scanner scanner = new Scanner(System.in);
        String[] splitMinion = scanner.nextLine().split(": ");
        String[] minionInfo = splitMinion[1].split(" ");
        String minionName = minionInfo[0];
        int minionAge = Integer.parseInt(minionInfo[1]);
        String minionTown = minionInfo[2];
        String[] splitVillain = scanner.nextLine().split(": ");
        String villainName = splitVillain[1];

        int townId = getOrInsertTownId(connection, minionTown);
        int villainId = getOrInsertVillainId(connection, villainName);

        PreparedStatement insertMinion = connection.prepareStatement(
                "INSERT INTO minions(name, age, town_id) VALUE (?, ?, ?);");
        insertMinion.setString(1, minionName);
        insertMinion.setInt(2, minionAge);
        insertMinion.setInt(3, townId);
        insertMinion.executeUpdate();

        PreparedStatement getLastMinion = connection.prepareStatement(
                "SELECT id FROM minions ORDER BY id DESC LIMIT 1;");
        ResultSet lastMinionSet = getLastMinion.executeQuery();
        lastMinionSet.next();
        int lastMinionId = lastMinionSet.getInt("id");

        PreparedStatement insertMinionsVillains = connection.prepareStatement(
                "INSERT INTO minions_villains VALUES (?, ?);");

        insertMinionsVillains.setInt(1, lastMinionId);
        insertMinionsVillains.setInt(2, villainId);
        insertMinionsVillains.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s.", minionName, villainName);
        connection.close();
    }

    private static int getOrInsertVillainId(Connection connection, String villainName) throws SQLException {
        PreparedStatement selectVillain = connection.prepareStatement(
                "SELECT id FROM villains WHERE name = ?");
        selectVillain.setString(1, villainName);

        ResultSet villainSet = selectVillain.executeQuery();
        int villainId = 0;
        if (!villainSet.next()) {
            PreparedStatement insertVillain = connection.prepareStatement(
                    "INSERT INTO villains(name, evilness_factor) VALUE (?, ?);");
            insertVillain.setString(1, villainName);
            insertVillain.setString(2, "evil");
            insertVillain.executeUpdate();

            ResultSet newVillainSet = selectVillain.executeQuery();
            newVillainSet.next();
            villainId = newVillainSet.getInt("id");
            System.out.printf("Villain %s was added to the database.\n", villainName);
        } else {
            villainId = villainSet.getInt("id");
        }
        return villainId;

    }

    private static int getOrInsertTownId(Connection connection, String minionTown) throws SQLException {
        PreparedStatement selectTown = connection.prepareStatement(
                "SELECT id FROM towns WHERE name = ?");
        selectTown.setString(1, minionTown);

        ResultSet townSet = selectTown.executeQuery();
        int townId = 0;
        if (!townSet.next()) {
            PreparedStatement insertTown = connection.prepareStatement(
                    "INSERT INTO towns(name) VALUE (?);");
            insertTown.setString(1, minionTown);
            insertTown.executeUpdate();

            ResultSet newTownSet = selectTown.executeQuery();
            newTownSet.next();
            townId = newTownSet.getInt("id");
            System.out.printf("Town %s was added to the database.\n", minionTown);
        } else {
            townId = townSet.getInt("id");
        }
        return townId;
    }
}
