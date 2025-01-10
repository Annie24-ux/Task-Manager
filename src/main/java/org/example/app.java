package org.example;

import io.javalin.Javalin;


public class app {

    private static final int PORT_NUMBER = 7000;
    private static Javalin server;

    public static void main(String[] args) {
        server = configServer();
        server.start(PORT_NUMBER);
        System.out.println("App server started at port "+ PORT_NUMBER);
    }


    /**
     * Configures the server for the program/app.
     * @return
     */
    public static Javalin configServer(){
        server = Javalin.create();
        DbConnect.createTable();

        server.get("/tasks", TaskController::getAllTasks);
        server.get("/tasks/{id}", TaskController::getTaskById);
        server.post("/tasks", TaskController::addTask);
        server.patch("/task/{taskId}", TaskController::editTask);
        server.delete("/tasks/{taskId}", TaskController::removeTask);
//        server.put("/task/{taskId}", TaskController::updateTask);

        return server;
        }
    }


