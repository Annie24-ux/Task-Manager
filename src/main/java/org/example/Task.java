package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Task {
    private String description;
    private int taskId;
    private boolean isComplete;

    public Task(int taskId, String description, boolean isComplete) {
        this.description = description;
        this.taskId = taskId;
        this.isComplete = isComplete;
    }

    @JsonCreator
    public Task(@JsonProperty("description") String description,
                @JsonProperty("isComplete") boolean isComplete) {
        this.description = description;
        this.isComplete = isComplete;
    }

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
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

    @Override
    public String toString() {
        return "To-Do{" +
                "id=" + taskId +
                ", description='" + description + '\'' +
                ", status='" + isComplete + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, taskId, isComplete);
    }
}
