package dao;

import model.Entry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntryDao {

    private ConnectionToDB connectionToDB;

    public EntryDao() {
        connectionToDB = ConnectionToDB.getInstance();
    }

    public List<Entry> getAllEntries() {

        List<Entry> entries = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Comment")) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                String message = result.getString("message");
                String date = result.getString("date");
                Entry entry = new Entry(name, message, date);
                entries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public void saveEntry(Entry newEntry) {
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Comment VALUES (?, ?, ?)")) {
            statement.setString(1, newEntry.getName());
            statement.setString(2, newEntry.getMessage());
            statement.setString(3, newEntry.getDate().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
