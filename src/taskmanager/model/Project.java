package taskmanager.model;

import java.time.LocalDate;

public class Project {

    private String name;
    private String goal;
    private String type;
    private LocalDate createdDate;

    public Project(String name, String goal, String type) {
        this.name = name;
        this.goal = goal;
        this.type = type;
        this.createdDate = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    public String getGoal() {
        return goal;
    }

    public String getType() {
        return type;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
