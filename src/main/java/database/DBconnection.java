package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static DBconnection instance;
    private final Connection connection;

    private DBconnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todoapp", "root", "Yasith@1.");
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBconnection getInstance() throws SQLException {
        return instance == null ? instance = new DBconnection() : instance;
    }
}
