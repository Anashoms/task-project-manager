package taskmanager.test;

import org.junit.jupiter.api.Test;
import taskmanager.model.SimpleTask;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTaskTest {

    @Test
    void testIsOverdue() {
        SimpleTask task = new SimpleTask(
                "Late Task",
                "Ahmet",
                3,
                new Date(System.currentTimeMillis() - 3600000),
                "To Do"
        );

        assertTrue(task.isOverdue());
    }

    @Test
    void testIsNearDeadline() {
        SimpleTask task = new SimpleTask(
                "Near Task",
                "Mehmet",
                3,
                new Date(System.currentTimeMillis() + 1800000),
                "To Do"
        );

        assertTrue(task.isNearDeadline());
    }
}
