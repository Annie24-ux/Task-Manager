package org.example;

public class Task {
    private String description;
    private int taskId;
    private boolean isComplete;

    public Task(int taskId, String description, boolean isComplete){
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

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
