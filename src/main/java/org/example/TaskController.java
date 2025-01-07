package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskController {
    static DbConnect db = new DbConnect();
    TaskController(){
    }

    public static void getAllTasks(Context context) {
        List<Task> tasks = db.selectAllTasks();
        if (tasks.isEmpty()) {
            context.status(HttpStatus.OK);
            context.json("No tasks available, add new tasks!.");
        } else {
            context.status(HttpStatus.OK);
            context.json(tasks);
        }
    }

    public static void getTaskById(Context context) {
        Task task = null;
        try{
            int taskId = Integer.parseInt(context.pathParam("id"));
            task = db.selectTaskById(taskId);
            context.status(HttpStatus.OK);
            System.out.println("This is the task: "+task);
            context.json(task);
        } catch (NumberFormatException err){
            err.printStackTrace();
            System.out.println("Id is not of the int class.");
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    public static void addTask(Context context) {
        try{
        String jsonBodyString = context.body();

        ObjectMapper obj = new ObjectMapper();
        Task task = obj.readValue(jsonBodyString,Task.class);
        DbConnect.insertTask(task.getDescription(), task.isComplete());
        context.status(HttpStatus.CREATED).json("Task added successfully");
        } catch(Exception e){
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Failed to convert json object into Task object");
        }

    }

    public static void removeTask(Context context) {
        Task task = null;

        try {
            int taskId = Integer.parseInt(context.pathParam("id"));
//            task = db.deleteTaskById(taskId);
        } catch (Exception e) {

        } catch (NumberFormatException er) {
            er.printStackTrace();
        }

    }

//        final String deleteQuery = " DELETE FROM tasks WHERE id = ?";
//        try (Connection connection = DbConnect.getConnection();
//             PreparedStatement ptsm = connection.prepareStatement(deleteQuery)) {
//            ptsm.setInt(1,id);
//            int rows = ptsm.executeUpdate();
//
//            if(rows > 0){
//                context.status(200).result("Task deleted successfully.");
//            }else{
//                context.status(404).result("Task not found");
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            context.status(500).result("Error deleting task");
//        }
//    }

    public static void updateTask(Context context){

    }


    public static void getASingleTask(Context context) {
    }
}