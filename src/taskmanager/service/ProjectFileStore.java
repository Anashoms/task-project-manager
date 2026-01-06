package taskmanager.service;

import taskmanager.model.Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectFileStore {

    private static final String FILE_PATH = "projects.csv";

    // Save Projects
    public static void saveProjects(List<Project> projects) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Project p : projects) {
                pw.println(
                        p.getName() + "," +
                        p.getGoal() + "," +
                        p.getType() + "," +
                        p.getCreatedDate()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Uplode a Projects
    public static List<Project> loadProjects() {
        List<Project> projects = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) return projects;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                Project project = new Project(
                        data[0],
                        data[1],
                        data[2]
                );
                projects.add(project);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projects;
    }
}
