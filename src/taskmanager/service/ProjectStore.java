package taskmanager.service;

import taskmanager.model.Project;
import java.util.List;

public class ProjectStore {

    // Loading projects from the file when running the program
    private static List<Project> projects =
            ProjectFileStore.loadProjects();

    // Add project + save to file
    public static void addProject(Project project) {
        projects.add(project);
        ProjectFileStore.saveProjects(projects);
    }

    public static List<Project> getProjects() {
        return projects;
    }
}
