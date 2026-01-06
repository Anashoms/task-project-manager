package taskmanager.ui;

import com.toedter.calendar.JDateChooser;
import taskmanager.model.Task;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTaskDialog extends JDialog {

    private final SimpleDateFormat DEADLINE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    public EditTaskDialog(
            JFrame parent,
            Task task,
            JLabel nameLabel,
            JLabel assigneeLabel,
            JLabel powerLabel,
            JLabel dateLabel
    ) {
        super(parent, "Edit Task", true);
        setSize(420, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField nameField = new JTextField(task.getName());
        JTextField assigneeField = new JTextField(task.getAssignee());
        JTextField powerField = new JTextField(String.valueOf(task.getPower()));

        /* ===== Deadline Date ===== */
        form.add(new JLabel("Deadline Date"));
        JDateChooser deadlineChooser = new JDateChooser();
        deadlineChooser.setLocale(Locale.ENGLISH);
        deadlineChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextField) deadlineChooser.getDateEditor().getUiComponent()).setEditable(false);
        deadlineChooser.setMinSelectableDate(new Date());
        deadlineChooser.setDate(task.getDeadline());
        form.add(deadlineChooser);

        form.add(Box.createVerticalStrut(10));

        /* ===== Deadline Time ===== */
        form.add(new JLabel("Deadline Time"));
        SpinnerDateModel timeModel =
                new SpinnerDateModel(task.getDeadline(), null, null, Calendar.MINUTE);

        JSpinner timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor =
                new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeEditor.getTextField().setEditable(false);
        timeEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        form.add(timeSpinner);

        form.add(Box.createVerticalStrut(12));

        /* ===== Other Fields ===== */
        form.add(new JLabel("Task Name"));
        form.add(nameField);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Assignee"));
        form.add(assigneeField);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Power"));
        form.add(powerField);

        /* ===== Save Button ===== */
        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        saveBtn.addActionListener(e -> {

            if (nameField.getText().trim().isEmpty() ||
                assigneeField.getText().trim().isEmpty() ||
                powerField.getText().trim().isEmpty() ||
                deadlineChooser.getDate() == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int power;
            try {
                power = Integer.parseInt(powerField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Power must be a number",
                        "Invalid Power",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Date finalDateTime =
                    combineDateTime(deadlineChooser.getDate(),
                            (Date) timeSpinner.getValue());

            if (finalDateTime.before(new Date())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Deadline cannot be in the past",
                        "Invalid Deadline",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            /* ===== Update MODEL ===== */
            task.setName(nameField.getText().trim());
            task.setAssignee(assigneeField.getText().trim());
            task.setPower(power);
            task.setDeadline(finalDateTime);

            /* ===== Update UI ===== */
            nameLabel.setText("📝 " + task.getName());
            assigneeLabel.setText("👤 " + task.getAssignee());
            powerLabel.setText("⚡ Power: " + task.getPower());
            dateLabel.setText("📅 " + DEADLINE_FORMAT.format(task.getDeadline()));

            dispose();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(saveBtn);

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private Date combineDateTime(Date date, Date time) {
        Calendar dateCal = Calendar.getInstance(Locale.ENGLISH);
        dateCal.setTime(date);

        Calendar timeCal = Calendar.getInstance(Locale.ENGLISH);
        timeCal.setTime(time);

        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        dateCal.set(Calendar.SECOND, 0);
        dateCal.set(Calendar.MILLISECOND, 0);

        return dateCal.getTime();
    }
}
