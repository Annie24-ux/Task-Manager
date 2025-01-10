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
            context.json(task);
        } catch (NumberFormatException err){
            err.printStackTrace();
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

        try {
//            int taskId = Integer.parseInt(context.pathParam("taskId"));

            int taskId = Integer.parseInt(context.pathParam("taskId"));
            boolean isDeleted = db.deleteTaskById(taskId);
            if(isDeleted){
                context.status(HttpStatus.NO_CONTENT).result("Task has been successfully deleted.");
                System.out.println("Task has been deleted successfully!.");
            }else{
                context.status(HttpStatus.NOT_FOUND).result("Failed to delete task.");
            }
        } catch (NumberFormatException er) {
            er.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR).result(
                    "Error while deleting a task.");
        } catch (Exception e) {
        e.printStackTrace();
        context.status(HttpStatus.NOT_FOUND).result("Task does not exist.");

    }}



    public static void editTask(Context context){

        try{
            int id = Integer.parseInt(context.pathParam("taskId"));
            db.updateTask(id);
        } catch(NumberFormatException err){
            err.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Failed to convert into a number/Interger.");
        } catch(Exception e){
            e.printStackTrace();
            context.status(HttpStatus.NOT_FOUND).result("Faced error while tring to update task");

        }


    }


    public static void getASingleTask(Context context) {
    }
}