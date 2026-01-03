package taskmanager.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import taskmanager.model.Project;
import taskmanager.model.Task;

public class ViewProjectTasksUI extends JFrame {

    private Project project;

    public ViewProjectTasksUI(Project project) {
        this.project = project;

        setTitle("Tasks - " + project.getName());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== أعمدة الجدول =====
        String[] columns = {
                "Task Name",
                "Power",
                "Status",
                "Deadline",
                "Assignee"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // ===== تعبئة الجدول =====
        for (Task task : project.getTasks()) {
            model.addRow(new Object[]{
                    task.getName(),
                    task.getPower(),
                    task.getStatus(),
                    task.getDeadline(),
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
