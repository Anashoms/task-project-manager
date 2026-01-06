package taskmanager.model;

import java.util.Date;

public abstract class Task {

    protected String name;
    protected String assignee;
    protected int power;
    protected Date deadline;
    protected String status;

    public Task(String name, String assignee, int power, Date deadline, String status) {
        this.name = name;
        this.assignee = assignee;
        this.power = power;
        this.deadline = deadline;
        this.status = status;
    }

    // ===== Getters & Setters =====

    public String getName() {
        return name;
    }

    public String getAssignee() {
        return assignee;
    }

    public int getPower() {
        return power;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ===== Abstract Methods =====
    public abstract boolean isOverdue();
    public abstract boolean isNearDeadline();
}
