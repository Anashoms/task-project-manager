package taskmanager.service;

import java.util.ArrayList;
import java.util.List;

public class ProjectTypeRepository {

    private static final List<String> projectTypes = new ArrayList<>();

    static {
        projectTypes.add("Study Project");
        projectTypes.add("Work Project");
        projectTypes.add("Private Project");
    }

    public static List<String> getAllTypes() {
        return projectTypes;
    }

    public static void addType(String type) {
        if (!projectTypes.contains(type)) {
            projectTypes.add(type);
        }
    }
}
