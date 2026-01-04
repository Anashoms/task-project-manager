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
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== Background =====
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(230, 235, 240));

        // ===== Main Card =====
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(700, 520));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // ===== Header =====
        JLabel title = new JLabel("Create New Project", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        // ===== Back Button =====
        JButton backBtn = createSecondaryButton("← Back to Dashboard");
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> {
            new DashboardUI().setVisible(true);
            dispose(); // ⭐ إغلاق الصفحة الحالية
        });

        // ===== Project Name =====
        JLabel nameLabel = new JLabel("Project Name");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // ===== Project Goal =====
        JLabel goalLabel = new JLabel("Project Goal");
        goalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        goalArea = new JTextArea(5, 30);
        goalArea.setLineWrap(true);
        goalArea.setWrapStyleWord(true);
        JScrollPane goalScroll = new JScrollPane(goalArea);

        // ===== Project Type =====
        JLabel typeLabel = new JLabel("Project Type");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        typeCombo = new JComboBox<>(
                ProjectTypeRepository.getAllTypes().toArray(new String[0])
        );
        typeCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton addTypeBtn = createSecondaryButton("+ Add New Type");
        addTypeBtn.addActionListener(e -> addNewType());

        // ===== Save Button =====
        JButton saveBtn = createPrimaryButton("Save Project");
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.addActionListener(e -> saveProject());

        // ===== Assemble Card =====
        card.add(backBtn);
        card.add(Box.createVerticalStrut(15));
        card.add(title);

        card.add(nameLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(nameField);

        card.add(Box.createVerticalStrut(15));
        card.add(goalLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(goalScroll);

        card.add(Box.createVerticalStrut(15));
        card.add(typeLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(typeCombo);
        card.add(Box.createVerticalStrut(10));
        card.add(addTypeBtn);

        card.add(Box.createVerticalStrut(30));
        card.add(saveBtn);

        background.add(card);
        add(background);
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
                "Project Created Successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        // ⭐ الانتقال مع إغلاق الصفحة الحالية
        new ViewAllProjectsUI().setVisible(true);
        dispose();
    }

    // ================== Add New Project Type ==================
    private void addNewType() {
        String newType = JOptionPane.showInputDialog(
                this,
                "Enter new project type:"
        );

        if (newType != null && !newType.trim().isEmpty()) {
            ProjectTypeRepository.addType(newType.trim());
            typeCombo.addItem(newType.trim());
            typeCombo.setSelectedItem(newType.trim());
        }
    }

    // ================== Button Styles ==================
    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(45, 93, 123));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(250, 45));
        btn.setMaximumSize(new Dimension(250, 45));
        return btn;
    }

    private JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(230, 230, 230));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
