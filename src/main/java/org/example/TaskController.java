package org.example;

import io.javalin.http.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskController {
    private String task;

    public static void getAllTasks(Context context) {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement ptsm = connection.prepareStatement("SELECT * FROM tasks")) {
            ResultSet res = ptsm.executeQuery();
            while (res.next()) {
                tasks.add(new Task(
                        res.getInt("id"),
                        res.getString("description"),
                        res.getBoolean("completed")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        context.json(tasks);
    }


    public Task getTaskById(int id) {
        return new Task(2,"",true);
    }

    public static void addTask(Context context) {
        Task task = context.bodyAsClass(Task.class);

        final String insertString = " INSERT into tasks(description, id, complete) VALUES(?,?,?)";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement ptsm = connection.prepareStatement(insertString)) {
            ptsm.setInt(1, task.getTaskId());
            ptsm.setString(2, task.getDescription());
            ptsm.setBoolean(3, task.isComplete());
            ptsm.executeUpdate();
            context.status(201);
        } catch (SQLException e) {
            context.status(500);
            throw new RuntimeException(e);
        }
    }

    public static void removeTask(Context context) {
        Task task = context.bodyAsClass(Task.class);
        int id = Integer.parseInt(context.pathParam("id"));

        final String deleteQuery = " DELETE FROM tasks WHERE id = ?";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement ptsm = connection.prepareStatement(deleteQuery)) {
            ptsm.setInt(1,id);
            int rows = ptsm.executeUpdate();

            if(rows > 0){
                context.status(200).result("Task deleted successfully.");
            }else{
                context.status(404).result("Task not found");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            context.status(500).result("Error deleting task");
        }
    }


}