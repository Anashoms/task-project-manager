package taskmanager.ui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

public class CreateTaskUI extends JDialog {

    private JTextField nameField;
    private JTextField assigneeField;
    private JComboBox<Integer> powerCombo;
    private JComboBox<String> statusCombo;
    private JDateChooser deadlineChooser;

    private ProjectDetailsUI parent;

    public CreateTaskUI(ProjectDetailsUI parent) {
        super(parent, "Create Task", true);
        this.parent = parent;

        setSize(420, 480);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ===== Task Name =====
        panel.add(new JLabel("Task Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(Box.createVerticalStrut(10));

        // ===== Assignee =====
        panel.add(new JLabel("Assignee:"));
        assigneeField = new JTextField();
        panel.add(assigneeField);

        panel.add(Box.createVerticalStrut(10));

        // ===== Power =====
        panel.add(new JLabel("Task Power:"));
        powerCombo = new JComboBox<>(new Integer[]{2, 3, 5, 8, 13});
        panel.add(powerCombo);

        panel.add(Box.createVerticalStrut(10));

        // ===== Deadline (Date Picker) =====
        panel.add(new JLabel("Deadline:"));

        deadlineChooser = new JDateChooser();
        deadlineChooser.setLocale(Locale.ENGLISH);              // ⭐ إنكليزي
        deadlineChooser.setDateFormatString("yyyy-MM-dd");

        // ⭐ منع الكتابة اليدوية
        ((JTextField) deadlineChooser.getDateEditor()
                .getUiComponent()).setEditable(false);
        deadlineChooser.setMinSelectableDate(new Date());


        panel.add(deadlineChooser);

        panel.add(Box.createVerticalStrut(10));

        // ===== Status =====
        panel.add(new JLabel("Task Status:"));
        statusCombo = new JComboBox<>(new String[]{
                "To Do",
                "Processing",
                "Testing",
                "Completed"
        });
        panel.add(statusCombo);

        panel.add(Box.createVerticalStrut(20));

        // ===== Save Button =====
        JButton saveBtn = new JButton("Save Task");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.addActionListener(e -> saveTask());
        panel.add(saveBtn);

        add(panel, BorderLayout.CENTER);
    }

    // ================== Save Task ==================
    private void saveTask() {
        String name = nameField.getText().trim();
        String assignee = assigneeField.getText().trim();
        int power = (int) powerCombo.getSelectedItem();
        String status = (String) statusCombo.getSelectedItem();

        if (name.isEmpty() || assignee.isEmpty() || deadlineChooser.getDate() == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String deadline = sdf.format(deadlineChooser.getDate());

        // ⭐ إضافة الكرت مباشرة إلى ProjectDetailsUI
        parent.addTaskCard(name, assignee, power, deadline, status);

        dispose();
    }
}
