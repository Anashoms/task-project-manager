package taskmanager.service;

import java.util.ArrayList;
import java.util.List;
import taskmanager.model.Project;

public class ProjectStore {

    private static final List<Project> projects = new ArrayList<>();

    public static void addProject(Project project) {
        projects.add(project);
    }

    public static List<Project> getProjects() {
        return projects;
    }
}
