package taskmanager.ui;

import javax.swing.*;
import java.awt.*;

public class CreateTaskUI extends JDialog {

    private JTextField nameField;
    private JTextField deadlineField;
    private JComboBox<Integer> powerCombo;
    private JComboBox<String> statusCombo;
    private JComboBox<String> assigneeCombo;

    private ProjectDetailsUI parent;

    public CreateTaskUI(ProjectDetailsUI parent) {
        super(parent, "Create Task", true);
        this.parent = parent;

        setSize(450, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        form.add(new JLabel("Task Name:"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Task Power:"));
        powerCombo = new JComboBox<>(new Integer[]{2, 3, 5, 8, 13});
        form.add(powerCombo);

        form.add(new JLabel("Task Status:"));
        statusCombo = new JComboBox<>(
                new String[]{"To Do", "Processing", "Testing", "Completed"}
        );
        form.add(statusCombo);

        form.add(new JLabel("Deadline (YYYY-MM-DD):"));
        deadlineField = new JTextField();
        form.add(deadlineField);

        form.add(new JLabel("Assignee:"));
        assigneeCombo = new JComboBox<>(
                new String[]{"Ahmed", "Sara", "Ali", "Mona"}
        );
        form.add(assigneeCombo);

        JButton saveBtn = new JButton("Save Task");
        saveBtn.addActionListener(e -> saveTask());

        add(form, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }

    private void saveTask() {
        String name = nameField.getText().trim();
        String deadline = deadlineField.getText().trim();
        int power = (int) powerCombo.getSelectedItem();
        String status = (String) statusCombo.getSelectedItem();
        String assignee = (String) assigneeCombo.getSelectedItem();

        if (name.isEmpty() || deadline.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        parent.addTaskCard(name, assignee, power, deadline, status);
        dispose();
    }
}
