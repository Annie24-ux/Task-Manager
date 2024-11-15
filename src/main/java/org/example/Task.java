package org.example;

public class Task {
    private String description;
    private int taskId;
    private boolean isComplete;

    public Task(String description, int taskId, boolean isComplete){
        this.description = description;
        this.taskId = taskId;
        this.isComplete = isComplete;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
