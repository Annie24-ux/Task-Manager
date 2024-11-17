package org.example;

import io.javalin.Javalin;


public class app {

    private static final int PORT_NUMBER = 7000;
    private static Javalin server;

    public static void main(String[] args) {
        server = configServer();
        server.start(PORT_NUMBER);
        System.out.println("App server started....");
    }


    public static Javalin configServer(){
        server = Javalin.create();
        server.get("/", TaskController::getAllTasks);
        server.get("/tasks", TaskController::getAllTasks);
        server.post("/task/{taskId}/{description}/{isComplete}", TaskController::addTask);
        server.put("/task/{taskId}/{description}/{isComplete}", TaskController::addTask);
        server.get("/task/{id}", TaskController::getASingleTask);

        server.put("/task/{taskId}", TaskController::removeTask);


        return server;
        }
    }


