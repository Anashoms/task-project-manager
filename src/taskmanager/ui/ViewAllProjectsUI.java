package taskmanager.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import taskmanager.model.Project;
import taskmanager.service.ProjectStore;

public class ViewAllProjectsUI extends JFrame {

    public ViewAllProjectsUI() {
        setTitle("All Projects");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== أعمدة الجدول =====
        String[] columns = {
                "Project Name",
                "Project Type",
                "Created Date",
                "Project Goal"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // ===== تعبئة البيانات =====
        for (Project project : ProjectStore.getProjects()) {
            model.addRow(new Object[] {
                    project.getName(),
                    project.getType(),
                    project.getCreatedDate(),
                    project.getGoal()
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
