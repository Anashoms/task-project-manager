package taskmanager.ui;

import javax.swing.*;
import java.awt.*;
import taskmanager.model.Project;

public class ProjectDetailsUI extends JFrame {

    private Project project;

    private JPanel todoColumn;
    private JPanel processingColumn;
    private JPanel testingColumn;
    private JPanel completedColumn;

    public ProjectDetailsUI(Project project) {
        this.project = project;

        setTitle("Project Details - " + project.getName());
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== Header =====
        JLabel title = new JLabel(
                "Project: " + project.getName(),
                SwingConstants.CENTER
        );
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // ===== Create Task Button =====
        JButton createTaskBtn = new JButton("Create Task");
        createTaskBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        createTaskBtn.addActionListener(e -> {
            new CreateTaskUI(this).setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createTaskBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // ===== Kanban Board =====
        JPanel board = new JPanel(new GridLayout(1, 4, 15, 15));
        board.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        todoColumn = createColumn("To Do", new Color(220, 235, 255));
        processingColumn = createColumn("Processing", new Color(255, 245, 220));
        testingColumn = createColumn("Testing", new Color(235, 255, 235));
        completedColumn = createColumn("Completed", new Color(235, 235, 235));

        board.add(todoColumn);
        board.add(processingColumn);
        board.add(testingColumn);
        board.add(completedColumn);

        add(board, BorderLayout.CENTER);
    }

    // ===== إنشاء عمود =====
    private JPanel createColumn(String title, Color bg) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(label, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(bg);

        panel.add(new JScrollPane(content), BorderLayout.CENTER);

        // ⬅️ هذا هو المهم
        panel.putClientProperty("content", content);

        return panel;
    }

    // ===== تستدعى من CreateTaskUI =====
    public void addTaskCard(
            String taskName,
            String assignee,
            int power,
            String deadline,
            String status
    ) {
        JPanel taskCard = new JPanel();
        taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS));
        taskCard.setBackground(Color.WHITE);
        taskCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        taskCard.add(new JLabel("📝 " + taskName));
        taskCard.add(new JLabel("👤 " + assignee));
        taskCard.add(new JLabel("⚡ Power: " + power));
        taskCard.add(new JLabel("📅 " + deadline));

        JPanel targetColumn;

        switch (status) {
            case "Processing":
                targetColumn = processingColumn;
                break;
            case "Testing":
                targetColumn = testingColumn;
                break;
            case "Completed":
                targetColumn = completedColumn;
                break;
            default:
                targetColumn = todoColumn;
        }

        targetColumn.add(taskCard);
        targetColumn.revalidate();
        targetColumn.repaint();
    }
}
