package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskController {
    private String task;
    private List<Task> tasks = new ArrayList<>();

    static DbConnect db = new DbConnect();

    TaskController(){
    }

    public static void getAllTasks(Context context) {
        List<Task> tasks = db.selectAllTasks();
        if (tasks.isEmpty()) {
            context.status(200);
            context.json("No tasks available, add new tasks!.");
        } else {
            context.json(tasks);
        }

    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }


    public static void addTask(Context context) {
        try{
        String jsonBodyString = context.body();

        if(jsonBodyString instanceof String){
                System.out.println("Type string....");
                System.out.println(jsonBodyString);
            }

        ObjectMapper obj = new ObjectMapper();
        Task task = obj.readValue(jsonBodyString,Task.class);
            System.out.println("TASK: "+ task.getDescription());
            System.out.println("Status: "+ task.isComplete());
        DbConnect.insertTask(task.getDescription(), task.isComplete());
        context.status(201).json("Task added successfully");
        } catch(Exception e){
            e.printStackTrace();
            context.status(500).result("Failed to convert json object into Task object");
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


    public static void getASingleTask(Context context) {
    }
}