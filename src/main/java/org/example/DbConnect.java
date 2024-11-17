package org.example;

import java.sql.*;

public class DbConnect {
    public static String DB_url = "jdbc:sqlite:taskDb.db";
//    public static Connection con;

    public DbConnect(){

    }

    public static void main(String[] args) throws SQLException {

        try(Connection con = DbConnect.getConnection()) {
            if (con != null) {
                System.out.println("Connection open");

                createTable(con);
                System.out.println("Table created....");
                System.out.println("Inserting data now");
                insertTask(con, 1, "Cook lunch", true);
                insertTask(con, 2, "Clean", true);

                System.out.println("Viewing table");
                selectFromTable(con);
                System.out.println("Done selecting...");
            } else{
                System.out.println("Could not establish connection.");
            }
        } catch (SQLException e){
        System.out.println(e);
    }}


    public static void selectFromTable(Connection conn) throws SQLException {
        String selectQuery = "SELECT * FROM tasks;";
        Statement stmt = conn.createStatement();
        stmt.execute(selectQuery);
        ResultSet res = stmt.executeQuery(selectQuery);
        stmt.executeUpdate(selectQuery);


        while(res.next()){
            System.out.println("Task: "+ res.getString("task"));
            System.out.println("Status: "+ res.getBoolean("isComplete"));
        }
    }

    public static void createTable(Connection con) throws SQLException {
        final String  createQuery = "CREATE TABLE IF NOT EXISTS tasks ("
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "	description text NOT NULL,"
                + "	isComplete BOOLEAN NOT NULL DEFAULT 0\n"
                + ");";
        Statement stmt = con.createStatement();
        stmt.execute(createQuery);
    }

    public static void insertTask(Connection conn, int taskId, String description, boolean isComplete) throws SQLException {
       final String insertString = " INSERT into tasks(id, description, isComplete) VALUES(?,?,?)";
       PreparedStatement pstm = conn.prepareStatement(insertString);
       pstm.setInt(1,taskId);
       pstm.setString(2, description);
       pstm.setBoolean(3, isComplete);
       pstm.executeUpdate();

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
}
