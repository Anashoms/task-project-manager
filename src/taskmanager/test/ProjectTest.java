package taskmanager.test;

import org.junit.jupiter.api.Test;
import taskmanager.model.Project;
import taskmanager.model.SimpleTask;
import taskmanager.model.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    void testAddTaskToProject() {
        Project project = new Project("Test Project", "Test Goal", "Software");
        Task task = new SimpleTask(
                "Task 1",
                "Ali",
                5,
                new Date(System.currentTimeMillis() + 3600000),
                "To Do"
        );

        project.addTask(task);

        assertEquals(1, project.getTasks().size());
    }

    @Test
    void testRemoveTaskFromProject() {
        Project project = new Project("Test Project", "Test Goal", "Software");
        Task task = new SimpleTask(
                "Task 1",
                "Ali",
                5,
                new Date(System.currentTimeMillis() + 3600000),
                "To Do"
        );

        project.addTask(task);
        project.removeTask(task);

        assertEquals(0, project.getTasks().size());
    }
}
