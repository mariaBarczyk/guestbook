package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionToDB {

    private static ConnectionToDB instance = null;
    private static final String DB_NAME = "JDBC:sqlite:src/main/resources/form.db";

    private ConnectionToDB() {
    }

    public static ConnectionToDB getInstance() {
        if (instance == null) {
            instance = new ConnectionToDB();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("No JDBC driver.");
        } catch (SQLException e) {
            System.out.println("SQL exception occur.");
        }
        return connection;
    }
}
