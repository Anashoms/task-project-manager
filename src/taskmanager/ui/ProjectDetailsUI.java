package taskmanager.ui;

import taskmanager.model.Project;

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

        /* ================= Kanban Board ================= */
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
    }

    /* ================= Column ================= */
    private JPanel createColumn(String title, Color bg) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

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

    /* ================= Add Task Card ================= */
    public void addTaskCard(String taskName, String assignee, int power, String deadline, String status) {

        // Wrapper حتى نحذف (الكرت + المسافة) سوا
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 205));

        JPanel taskCard = new JPanel();
        taskCard.setLayout(new BoxLayout(taskCard, BoxLayout.Y_AXIS));
        taskCard.setBackground(Color.WHITE);
        taskCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        taskCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        JLabel nameLabel = new JLabel("📝 " + taskName);
        JLabel assigneeLabel = new JLabel("👤 " + assignee);
        JLabel powerLabel = new JLabel("⚡ Power: " + power);
        JLabel dateLabel = new JLabel("📅 " + deadline);

        JComboBox<String> statusCombo = new JComboBox<>(
                new String[]{"To Do", "Processing", "Testing", "Completed"}
        );
        statusCombo.setSelectedItem(status);

        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnRow.add(editBtn);
        btnRow.add(deleteBtn);

        taskCard.add(nameLabel);
        taskCard.add(Box.createVerticalStrut(4));
        taskCard.add(assigneeLabel);
        taskCard.add(powerLabel);
        taskCard.add(dateLabel);
        taskCard.add(Box.createVerticalStrut(6));
        taskCard.add(new JLabel("Status:"));
        taskCard.add(statusCombo);
        taskCard.add(Box.createVerticalStrut(5));
        taskCard.add(btnRow);

        wrapper.add(taskCard);
        wrapper.add(Box.createVerticalStrut(8));

        JPanel startColumn = getColumnContentByStatus(status);
        startColumn.add(wrapper);
        startColumn.revalidate();
        startColumn.repaint();

        /* ===== Change Status ===== */
        statusCombo.addActionListener(e -> {
            String newStatus = (String) statusCombo.getSelectedItem();

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

            // إذا صارت Completed رجّع الوضع طبيعي
            if ("Completed".equals(newStatus)) {
                unlockTask(taskCard, statusCombo, editBtn, deleteBtn);
                taskCard.putClientProperty("alerted", null);
            }
        });

        /* ===== Edit ===== */
        editBtn.addActionListener(e -> {
            EditTaskDialog dialog = new EditTaskDialog(
                    this,
                    nameLabel,
                    assigneeLabel,
                    powerLabel,
                    dateLabel
            );
            dialog.setVisible(true);
        });

        /* ===== Delete ===== */
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this task?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                JPanel parentPanel = (JPanel) wrapper.getParent();
                if (parentPanel != null) {
                    parentPanel.remove(wrapper);
                    parentPanel.revalidate();
                    parentPanel.repaint();
                }
            }
        });

        /* ===== Deadline Monitor ===== */
        startDeadlineMonitor(taskCard, statusCombo, editBtn, deleteBtn, dateLabel);
    }

    /* ================= Deadline Monitor ================= */
    private void startDeadlineMonitor(
            JPanel taskCard,
            JComboBox<String> statusCombo,
            JButton editBtn,
            JButton deleteBtn,
            JLabel dateLabel
    ) {
        // Parse من الليبل دائماً (لأنه ممكن يتغير بعد Edit)
        Timer timer = new Timer(30_000, null); // كل 30 ثانية
        timer.addActionListener(e -> {
            Date deadline = parseDeadlineFromLabel(dateLabel);
            if (deadline == null) return;

            Date now = new Date();
            long diffMs = deadline.getTime() - now.getTime();
            long diffMinutes = diffMs / 60000;

            String currentStatus = (String) statusCombo.getSelectedItem();

            // لو Completed ما بدنا أي تحذير/قفل
            if ("Completed".equals(currentStatus)) {
                unlockTask(taskCard, statusCombo, editBtn, deleteBtn);
                return;
            }

            // انتهى الوقت ولسا مش completed => أحمر + قفل كامل
            if (diffMs <= 0) {
                lockTask(taskCard, statusCombo, editBtn, deleteBtn);
                return;
            }

            // تبقى ساعة أو أقل => لون أحمر فاتح + تنبيه مرة واحدة
            if (diffMinutes <= 60) {
                taskCard.setBackground(new Color(255, 170, 170));

                if (taskCard.getClientProperty("alerted") == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "⚠ Only 1 hour (or less) left until the deadline!",
                            "Deadline Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                    taskCard.putClientProperty("alerted", true);
                }
            } else {
                // بعيد عن الساعة: رجّع اللون طبيعي
                taskCard.setBackground(Color.WHITE);
                taskCard.putClientProperty("alerted", null);
            }
        });

        timer.setInitialDelay(0); // نفّذ فوراً أول مرة
        timer.start();
    }

    private Date parseDeadlineFromLabel(JLabel dateLabel) {
        try {
            String s = dateLabel.getText().replace("📅 ", "").trim();
            return DEADLINE_FORMAT.parse(s);
        } catch (Exception ex) {
            return null;
        }
    }

    private void lockTask(JPanel taskCard, JComboBox<String> statusCombo, JButton editBtn, JButton deleteBtn) {
        taskCard.setBackground(new Color(180, 60, 60));
        statusCombo.setEnabled(false);
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }

    private void unlockTask(JPanel taskCard, JComboBox<String> statusCombo, JButton editBtn, JButton deleteBtn) {
        taskCard.setBackground(Color.WHITE);
        statusCombo.setEnabled(true);
        editBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }

    /* ================= Column by Status ================= */
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
