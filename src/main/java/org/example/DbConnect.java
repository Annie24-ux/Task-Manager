package org.example;

import java.sql.*;

public class DbConnect {
    public static String DB_url = "jdbc:sqlite:tasks.db";


    public static void main(String[] args) {


        String selectQuery = "SELECT * FROM tasks;";
        try(Connection conn = DriverManager.getConnection(DB_url);
            createTable(Connection conn);
            Statement stmt = conn.createStatement();
//            stmt.execute(createQuery);
            ResultSet res = stmt.executeQuery(selectQuery);) {
            stmt.executeQuery(selectQuery);
            // Extract data from result set
            while (res.next()) {
                // Retrieve by column name
                System.out.print("ID: ");
            }
        } catch (SQLException e)
        {
            System.out.println("Error connecting: "+e);
    }
    }

    public static void createTable(Connection con) throws SQLException {
        final String  createQuery = "CREATE TABLE IF NOT EXISTS tasks ("
                + "	id INTEGER PRIMARY KEY,"
                + "	description text NOT NULL,"
                + "	status text NOT NULL"
                + ");";
        Statement stmt = con.createStatement();
        stmt.execute(createQuery);
    }

    public static void insertTask(Connection conn, String description, int taskId, boolean status) throws SQLException {
       final String insertString = " INSERT into tasks(description, id, status) VALUES(?,?,?)";
       PreparedStatement pstm = conn.prepareStatement(insertString);
       pstm.setInt(1,taskId);
       pstm.setString(2, description);
       pstm.setString(3, String.valueOf(status));
    }
}
