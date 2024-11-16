package org.example;

import java.sql.*;

public class DbConnect {
    public static String DB_url = "jdbc:sqlite:tasks.db";
    public static Connection con;

    public static void main(String[] args) throws SQLException {
        Connection con = null;

        try{
            con = DriverManager.getConnection(DB_url);
            System.out.println("Connection open");

            try{
                System.out.println("deletes table");
            }catch (Exception ignored){
                System.out.println("do nothing");
            }
            createTable(con);
            System.out.println("Table created....");
            System.out.println("Inserting data now");
            insertTask(con, 1, "Cook lunch", true);
            insertTask(con, 2, "Clean", true);

            insertTask(con, 3, "Laundry", true);
            System.out.println("Viewing table");
            selectFromTable(con);

        }catch (SQLException e){
            System.out.println(e);
        }
    }



    public static void selectFromTable(Connection conn) throws SQLException {
        String selectQuery = "SELECT * FROM tasks;";
        Statement stmt = conn.createStatement();
        stmt.execute(selectQuery);
        ResultSet res = stmt.executeQuery(selectQuery) ;
        stmt.executeQuery(selectQuery);

        while(res.next()){
            System.out.println("Res: ");
        }


    }

    public static void createTable(Connection con) throws SQLException {
        final String  createQuery = "CREATE TABLE IF NOT EXISTS tasks ("
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "	description text NOT NULL,"
                + "	status text NOT NULL"
                + ");";
        Statement stmt = con.createStatement();
        stmt.execute(createQuery);
    }

    public static void insertTask(Connection conn, int taskId, String description, boolean status) throws SQLException {
       final String insertString = " INSERT into tasks(description, id, status) VALUES(?,?,?)";
       PreparedStatement pstm = conn.prepareStatement(insertString);
       pstm.setInt(1,taskId);
       pstm.setString(2, description);
       pstm.setBoolean(3, status);
    }

    public static Connection getConnection() {
        return con;
    }
}
