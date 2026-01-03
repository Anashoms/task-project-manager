package taskmanager.ui;

import javax.swing.*;
import java.awt.*;

import taskmanager.model.Project;
import taskmanager.service.ProjectStore;
import taskmanager.service.ProjectTypeRepository;

public class CreateProjectUI extends JFrame {

    private JTextField nameField;
    private JTextArea goalArea;
    private JComboBox<String> typeCombo;

    public CreateProjectUI() {
        setTitle("Create Project");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // ===== Project Name =====
        panel.add(new JLabel("Project Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(Box.createVerticalStrut(15));

        // ===== Project Goal =====
        panel.add(new JLabel("Project Goal:"));
        goalArea = new JTextArea(6, 40);
        goalArea.setLineWrap(true);
        goalArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(goalArea));

        panel.add(Box.createVerticalStrut(15));

        // ===== Project Type =====
        panel.add(new JLabel("Project Type:"));
        typeCombo = new JComboBox<>(
                ProjectTypeRepository.getAllTypes().toArray(new String[0])
        );
        panel.add(typeCombo);

        panel.add(Box.createVerticalStrut(10));

        // ===== Add New Type Button =====
        JButton addTypeBtn = new JButton("Add New Type");
        addTypeBtn.addActionListener(e -> addNewType());
        panel.add(addTypeBtn);

        panel.add(Box.createVerticalStrut(20));

        // ===== Save Button =====
        JButton saveBtn = new JButton("Save Project");
        saveBtn.addActionListener(e -> saveProject());
        panel.add(saveBtn);

        add(panel);
    }

    // ================== Save Project ==================
    private void saveProject() {
        String name = nameField.getText().trim();
        String goal = goalArea.getText().trim();
        String type = (String) typeCombo.getSelectedItem();

        if (name.isEmpty() || goal.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Project project = new Project(name, goal, type);
        ProjectStore.addProject(project);

        JOptionPane.showMessageDialog(
                this,
                "Project Created Successfully!\n\n" +
                        "Name: " + project.getName() + "\n" +
                        "Type: " + project.getType() + "\n" +
                        "Created At: " + project.getCreatedDate(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }

    // ================== Add New Project Type ==================
    private void addNewType() {
        String newType = JOptionPane.showInputDialog(
                this,
                "Enter new project type:"
        );

        if (newType != null && !newType.trim().isEmpty()) {
            ProjectTypeRepository.addType(newType);
            typeCombo.addItem(newType);
            typeCombo.setSelectedItem(newType);
        }
    }
}
