package taskmanager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private String name;
    private String goal;
    private String type;
    private LocalDate createdDate;
    private List<Task> tasks = new ArrayList<>();


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
    
    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
