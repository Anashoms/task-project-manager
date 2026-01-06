package taskmanager.ui;

import taskmanager.model.Project;
import taskmanager.model.Task;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProjectDetailsUI extends JFrame {

    private final Project project;

    private JPanel todoColumn;
    private JPanel processingColumn;
    private JPanel testingColumn;
    private JPanel completedColumn;

    private final SimpleDateFormat DEADLINE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    public ProjectDetailsUI(Project project) {
        this.project = project;

        setTitle("Project Details - " + project.getName());
        setSize(1200, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        /* ================= Header ================= */
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel navLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton backProjectsBtn = new JButton("← Back to Projects");
        JButton backDashboardBtn = new JButton("🏠 Dashboard");

        backProjectsBtn.addActionListener(e -> {
            new ViewAllProjectsUI().setVisible(true);
            dispose();
        });

        backDashboardBtn.addActionListener(e -> {
            new DashboardUI().setVisible(true);
            dispose();
        });

        navLeft.add(backProjectsBtn);
        navLeft.add(backDashboardBtn);

        JLabel title = new JLabel("Project: " + project.getName(), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(navLeft, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        /* ================= Board ================= */
        JPanel board = new JPanel(new GridLayout(1, 4, 15, 15));
        board.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        todoColumn = createColumn("To Do", new Color(220, 235, 255));
        processingColumn = createColumn("Processing", new Color(255, 245, 220));
        testingColumn = createColumn("Testing", new Color(235, 255, 235));
        completedColumn = createColumn("Completed", new Color(235, 235, 235));

        board.add(todoColumn);
        board.add(processingColumn);
        board.add(testingColumn);
        board.add(completedColumn);

        add(board, BorderLayout.CENTER);

        /* ================= Bottom ================= */
        JButton createTaskBtn = new JButton("+ Create Task");
        createTaskBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createTaskBtn.addActionListener(e -> new CreateTaskUI(this).setVisible(true));

        JPanel bottom = new JPanel();
        bottom.add(createTaskBtn);
        add(bottom, BorderLayout.SOUTH);

        /* ===== Load existing tasks (important) ===== */
        for (Task task : project.getTasks()) {
            addTaskCard(task);
        }
    }

    /* ================= Column ================= */
    private JPanel createColumn(String title, Color bg) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(bg);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);

        panel.add(label, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.putClientProperty("content", content);

        return panel;
    }

    /* ================= ADD TASK (MODEL ENTRY POINT) ================= */
    public void addTask(Task task) {
        project.addTask(task);
        addTaskCard(task);
    }

    /* ================= ADD TASK CARD ================= */
    private void addTaskCard(Task task) {

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        JPanel taskCard = new JPanel();
        taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS));
        taskCard.setBackground(Color.WHITE);
        taskCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel nameLabel = new JLabel("📝 " + task.getName());
        JLabel assigneeLabel = new JLabel("👤 " + task.getAssignee());
        JLabel powerLabel = new JLabel("⚡ Power: " + task.getPower());
        JLabel dateLabel = new JLabel("📅 " + DEADLINE_FORMAT.format(task.getDeadline()));

        JComboBox<String> statusCombo = new JComboBox<>(
                new String[]{"To Do", "Processing", "Testing", "Completed"}
        );
        statusCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        statusCombo.setSelectedItem(task.getStatus());

        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnRow.add(editBtn);
        btnRow.add(deleteBtn);

        taskCard.add(nameLabel);
        taskCard.add(assigneeLabel);
        taskCard.add(powerLabel);
        taskCard.add(dateLabel);
        taskCard.add(Box.createVerticalStrut(5));
        taskCard.add(statusCombo);
        taskCard.add(Box.createVerticalStrut(5));
        taskCard.add(btnRow);

        wrapper.add(taskCard);
        wrapper.add(Box.createVerticalStrut(8));

        JPanel startColumn = getColumnContentByStatus(task.getStatus());
        startColumn.add(wrapper);
        startColumn.revalidate();
        startColumn.repaint();

        /* ===== Change Status ===== */
        statusCombo.addActionListener(e -> {
            String newStatus = (String) statusCombo.getSelectedItem();
            task.setStatus(newStatus);

            JPanel oldParent = (JPanel) wrapper.getParent();
            JPanel newParent = getColumnContentByStatus(newStatus);

            if (oldParent != null && oldParent != newParent) {
                oldParent.remove(wrapper);
                newParent.add(wrapper);

                oldParent.revalidate();
                oldParent.repaint();

                newParent.revalidate();
                newParent.repaint();
            }
        });


        /* ===== Edit ===== */
        editBtn.addActionListener(e ->
                new EditTaskDialog(
                        this,
                        task,
                        nameLabel,
                        assigneeLabel,
                        powerLabel,
                        dateLabel
                ).setVisible(true)
        );

        /* ===== Delete ===== */
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this task?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                project.removeTask(task);
                JPanel parent = (JPanel) wrapper.getParent();
                parent.remove(wrapper);
                parent.revalidate();
                parent.repaint();
            }
        });
    }

    private JPanel getColumnContentByStatus(String status) {
        switch (status) {
            case "Processing":
                return (JPanel) processingColumn.getClientProperty("content");
            case "Testing":
                return (JPanel) testingColumn.getClientProperty("content");
            case "Completed":
                return (JPanel) completedColumn.getClientProperty("content");
            default:
                return (JPanel) todoColumn.getClientProperty("content");
        }
    }
}
