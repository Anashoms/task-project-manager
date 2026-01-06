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
        setTitle("Create New Project");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ================= Background ================= */
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(235, 240, 243));
        add(background, BorderLayout.CENTER);

        /* ================= Card Wrapper ================= */
        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setPreferredSize(new Dimension(620, 520));
        cardWrapper.setBackground(Color.WHITE);
        cardWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(140, 90, 255), 2),
                BorderFactory.createEmptyBorder(20, 30, 25, 30)
        ));

        /* ================= Top Bar ================= */
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setOpaque(false);

        JButton backBtn = new JButton("← Back to Dashboard");
        backBtn.addActionListener(e -> {
            new DashboardUI().setVisible(true);
            dispose();
        });

        topBar.add(backBtn);
        cardWrapper.add(topBar, BorderLayout.NORTH);

        /* ================= Form ================= */
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Create New Project", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 25, 0));
        form.add(title);

        /* ===== Project Name ===== */
        form.add(leftLabel("Project Name"));
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(480, 34));
        form.add(nameField);

        form.add(Box.createVerticalStrut(15));

        /* ===== Project Goal ===== */
        form.add(leftLabel("Project Goal"));
        goalArea = new JTextArea(4, 20);
        goalArea.setLineWrap(true);
        goalArea.setWrapStyleWord(true);

        JScrollPane goalScroll = new JScrollPane(goalArea);
        goalScroll.setMaximumSize(new Dimension(480, 110));
        form.add(goalScroll);

        form.add(Box.createVerticalStrut(15));

        /* ===== Project Type ===== */
        form.add(leftLabel("Project Type"));

        JPanel typeRow = new JPanel(new BorderLayout(10, 0));
        typeRow.setOpaque(false);
        typeRow.setMaximumSize(new Dimension(480, 34));

        typeCombo = new JComboBox<>(
                ProjectTypeRepository.getAllTypes().toArray(new String[0])
        );

        JButton addTypeBtn = new JButton("+ Add Type");
        addTypeBtn.addActionListener(e -> addNewType());

        typeRow.add(typeCombo, BorderLayout.CENTER);
        typeRow.add(addTypeBtn, BorderLayout.EAST);
        form.add(typeRow);

        form.add(Box.createVerticalStrut(30));

        /* ===== Save Button ===== */
        JButton saveBtn = new JButton("Save Project");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveBtn.setBackground(new Color(45, 93, 123));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setPreferredSize(new Dimension(220, 45));

        saveBtn.addActionListener(e -> saveProject());

        JPanel saveWrapper = new JPanel();
        saveWrapper.setOpaque(false);
        saveWrapper.add(saveBtn);

        form.add(saveWrapper);

        cardWrapper.add(form, BorderLayout.CENTER);
        background.add(cardWrapper);
    }

    /* ================= Helpers ================= */
    private JLabel leftLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        return label;
    }

    /* ================= Save Project ================= */
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

        new ViewAllProjectsUI().setVisible(true);
        dispose();
    }

    /* ================= Add New Type ================= */
    private void addNewType() {
        String newType = JOptionPane.showInputDialog(this, "Enter new project type:");
        if (newType != null && !newType.trim().isEmpty()) {
            ProjectTypeRepository.addType(newType);
            typeCombo.addItem(newType);
            typeCombo.setSelectedItem(newType);
        }
    }
}
