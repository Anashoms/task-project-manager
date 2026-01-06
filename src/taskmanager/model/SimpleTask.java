package taskmanager.model;

import java.util.Date;

public class SimpleTask extends Task {

    public SimpleTask(String name, String assignee, int power, Date deadline, String status) {
        super(name, assignee, power, deadline, status);
    }

    @Override
    public boolean isOverdue() {
        return new Date().after(deadline);
    }

    @Override
    public boolean isNearDeadline() {
        long diff = deadline.getTime() - System.currentTimeMillis();
        return diff > 0 && diff <= 60 * 60 * 1000;
    }
}
