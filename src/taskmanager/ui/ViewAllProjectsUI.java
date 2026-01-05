package taskmanager.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import taskmanager.model.Project;
import taskmanager.service.ProjectStore;
import taskmanager.ui.table.ButtonRenderer;
import taskmanager.ui.table.ButtonEditor;

public class ViewAllProjectsUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public ViewAllProjectsUI() {
        setTitle("All Projects");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== Header =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("All Projects");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton createBtn = new JButton("Create Project");
        JButton dashboardBtn = new JButton("Dashboard");

        createBtn.addActionListener(e -> {
            new CreateProjectUI().setVisible(true);
            dispose();
        });

        dashboardBtn.addActionListener(e -> {
            new DashboardUI().setVisible(true);
            dispose();
        });

        actions.add(createBtn);
        actions.add(dashboardBtn);

        header.add(title, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ===== Table =====
        String[] columns = {
                "Project Name",
                "Project Type",
                "Created Date",
                "Project Goal",
                "Action"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        for (Project project : ProjectStore.getProjects()) {
            model.addRow(new Object[]{
                    project.getName(),
                    project.getType(),
                    project.getCreatedDate(),
                    project.getGoal(),
                    "Details"
            });
        }

        table = new JTable(model);
        table.setRowHeight(36);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(this));

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void openProject(int row) {
        Project project = ProjectStore.getProjects().get(row);
        new ProjectDetailsUI(project).setVisible(true);
        dispose();
    }
}
