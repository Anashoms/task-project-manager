package taskmanager.ui;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.toedter.calendar.JDateChooser;

public class CreateTaskUI extends JDialog {

    private JTextField nameField;
    private JTextField assigneeField;
    private JComboBox<Integer> powerCombo;
    private JComboBox<String> statusCombo;
    private JDateChooser deadlineChooser;

    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;

    private ProjectDetailsUI parent;

    public CreateTaskUI(ProjectDetailsUI parent) {
        super(parent, "Create Task", true);
        this.parent = parent;

        setSize(420, 560);
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

        // ===== Deadline Date =====
        panel.add(new JLabel("Deadline Date:"));
        deadlineChooser = new JDateChooser();
        deadlineChooser.setLocale(Locale.US);
        deadlineChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextField) deadlineChooser.getDateEditor()
                .getUiComponent()).setEditable(false);
        deadlineChooser.setMinSelectableDate(new Date());
        deadlineChooser.setDate(new Date());
        panel.add(deadlineChooser);
        panel.add(Box.createVerticalStrut(10));

        // ===== Deadline Time =====
        panel.add(new JLabel("Deadline Time:"));

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        hourSpinner = createEnglishNumberSpinner(0, 23, new Date().getHours());
        minuteSpinner = createEnglishNumberSpinner(0, 59, new Date().getMinutes());

        timePanel.add(hourSpinner);
        timePanel.add(new JLabel(":"));
        timePanel.add(minuteSpinner);

        panel.add(timePanel);
        panel.add(Box.createVerticalStrut(15));

        // ===== Status =====
        panel.add(new JLabel("Task Status:"));
        statusCombo = new JComboBox<>(new String[]{
                "To Do", "Processing", "Testing", "Completed"
        });
        panel.add(statusCombo);
        panel.add(Box.createVerticalStrut(25));

        // ===== Save Button =====
        JButton saveBtn = new JButton("Save Task");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.addActionListener(e -> saveTask());
        panel.add(saveBtn);

        add(panel, BorderLayout.CENTER);
    }

    // ===== Spinner with ENGLISH digits ONLY =====
    private JSpinner createEnglishNumberSpinner(int min, int max, int value) {

        SpinnerNumberModel model =
                new SpinnerNumberModel(value, min, max, 1);

        JSpinner spinner = new JSpinner(model);

        DecimalFormatSymbols symbols =
                new DecimalFormatSymbols(Locale.US);

        DecimalFormat format = new DecimalFormat("00", symbols);

        JSpinner.NumberEditor editor =
                new JSpinner.NumberEditor(spinner);

        editor.getFormat().setDecimalFormatSymbols(symbols);
        editor.getTextField().setEditable(false);

        spinner.setEditor(editor);

        return spinner;
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

        Date date = deadlineChooser.getDate();
        int hour = (int) hourSpinner.getValue();
        int minute = (int) minuteSpinner.getValue();

        Date finalDateTime = new Date(
                date.getYear(),
                date.getMonth(),
                date.getDate(),
                hour,
                minute
        );

        if (finalDateTime.before(new Date())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Deadline cannot be in the past",
                    "Invalid Deadline",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

        String deadline = sdf.format(finalDateTime);

        parent.addTaskCard(name, assignee, power, deadline, status);
        dispose();
    }
}
