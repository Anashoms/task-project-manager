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

    public abstract boolean isOverdue();
    public abstract boolean isNearDeadline();

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
}
