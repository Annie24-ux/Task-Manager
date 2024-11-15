package org.example;

import java.util.List;

public class TaskController {
    private List<String> tasks;
    private String task;

    public List<String> getAllTasks(){
        return tasks;
    }
    
    public String getTaskById(int id){
        return task;
    }
}
