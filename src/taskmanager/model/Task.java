package taskmanager.model;

import java.time.LocalDate;

public class Task {

    private String name;
    private int power;                 // 2,3,5,8,13
    private String status;              // To Do, Processing, Testing, Completed
    private LocalDate deadline;
    private String assignee;            // المسؤول

    public Task(String name, int power, String status,
                LocalDate deadline, String assignee) {
        this.name = name;
        this.power = power;
        this.status = status;
        this.deadline = deadline;
        this.assignee = assignee;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getAssignee() {
        return assignee;
    }
}
