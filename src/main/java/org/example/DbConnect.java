package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnect {
    public static String DB_url = "jdbc:sqlite:tasksDB.db";

    public DbConnect(){
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_url);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static void checkConnection(Connection con){
        if (con != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Failed to establish connection.");
        }
    }

    public static void main(String[] args){
        try(Connection con = DbConnect.getConnection()) {
            if (con != null) {
                System.out.println("Connection open");
            } else{
                System.out.println("Could not establish connection.");
            }
        } catch (SQLException e){
        System.out.println(e);
    }}



    public static void createTable() {
        final String createQuery = "CREATE TABLE IF NOT EXISTS tasks ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " description TEXT NOT NULL,"
                + " isComplete BOOLEAN NOT NULL DEFAULT 0"
                + ");";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(createQuery)) {
            stmt.execute();
            System.out.println("Table has been created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertTask(String description, boolean isComplete) {
        final String insertQuery = "INSERT INTO tasks (description, isComplete) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setString(1, description);
            stmt.setBoolean(2, isComplete);
            stmt.executeUpdate();
            System.out.println("Task inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Task> selectAllTasks() {
        List<Task> tasks = new ArrayList<>();
        final String query = "SELECT * FROM tasks;";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet res = stmt.executeQuery()) {
            while (res.next()) {
                tasks.add(new Task(
                        res.getInt("id"),
                        res.getString("description"),
                        res.getBoolean("isComplete")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static Task selectTaskById(int id) {
        final String query = "SELECT * FROM tasks WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return new Task(
                        res.getInt("id"),
                        res.getString("description"),
                        res.getBoolean("isComplete")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteTaskById(int id) {
        final String deleteQuery = "DELETE FROM tasks WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
