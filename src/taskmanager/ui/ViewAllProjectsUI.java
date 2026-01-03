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
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== أعمدة الجدول =====
        String[] columns = {
        	    "Project Name",
        	    "Project Type",
        	    "Created Date",
        	    "Project Goal",
        	    "Action"
        	};

        model = new DefaultTableModel(columns, 0);

        // ===== تعبئة البيانات =====
        for (Project project : ProjectStore.getProjects()) {
            model.addRow(new Object[]{
                    project.getName(),
                    project.getType(),
                    project.getCreatedDate(),
                    project.getGoal()
            });
        }

        table = new JTable(model);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // زر Details داخل الجدول
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(table));


        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);

        // ===== زر فتح المشروع =====
        JButton openBtn = new JButton("Open Project");
        openBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        openBtn.addActionListener(e -> openSelectedProject());

        // ===== تخطيط =====
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(openBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ================== فتح المشروع المحدد ==================
    private void openSelectedProject() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a project first",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // نفس ترتيب الإدخال في ProjectStore
        Project selectedProject = ProjectStore.getProjects().get(selectedRow);

        new ProjectDetailsUI(selectedProject).setVisible(true);
    }
}
