package taskmanager.ui;

import taskmanager.model.Project;
import taskmanager.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ViewProjectTasksUI extends JFrame {

    private Project project;

    private final SimpleDateFormat DEADLINE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    public ViewProjectTasksUI(Project project) {
        this.project = project;

        setTitle("Tasks - " + project.getName());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ===== Table columns ===== */
        String[] columns = {
                "Task Name",
                "Power",
                "Status",
                "Deadline",
                "Assignee"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        /* ===== Fill table from MODEL ===== */
        for (Task task : project.getTasks()) {
            model.addRow(new Object[]{
                    task.getName(),
                    task.getPower(),
                    task.getStatus(),
                    DEADLINE_FORMAT.format(task.getDeadline()),
                    task.getAssignee()
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
