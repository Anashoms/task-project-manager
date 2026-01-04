package taskmanager.ui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.toedter.calendar.JDateChooser;

public class EditTaskDialog extends JDialog {

    public EditTaskDialog(
            JFrame parent,
            JLabel nameLabel,
            JLabel assigneeLabel,
            JLabel powerLabel,
            JLabel dateLabel
    ) {
        super(parent, "Edit Task", true);
        setSize(380, 360);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextField nameField = new JTextField(nameLabel.getText().replace("📝 ", ""));
        JTextField assigneeField = new JTextField(assigneeLabel.getText().replace("👤 ", ""));
        JTextField powerField = new JTextField(powerLabel.getText().replace("⚡ Power: ", ""));

        // ===== Date Picker =====
        JDateChooser deadlineChooser = new JDateChooser();
        deadlineChooser.setLocale(Locale.ENGLISH);
        deadlineChooser.setDateFormatString("yyyy-MM-dd");

        // منع الكتابة اليدوية
        ((JTextField) deadlineChooser.getDateEditor().getUiComponent()).setEditable(false);
        deadlineChooser.setMinSelectableDate(new Date());


        // تحويل النص الحالي إلى Date
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(dateLabel.getText().replace("📅 ", ""));
            deadlineChooser.setDate(d);
        } catch (Exception ignored) {}

        form.add(new JLabel("Task Name"));
        form.add(nameField);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Assignee"));
        form.add(assigneeField);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Power"));
        form.add(powerField);
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Deadline"));
        form.add(deadlineChooser);

        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        saveBtn.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            nameLabel.setText("📝 " + nameField.getText());
            assigneeLabel.setText("👤 " + assigneeField.getText());
            powerLabel.setText("⚡ Power: " + powerField.getText());
            dateLabel.setText("📅 " + sdf.format(deadlineChooser.getDate()));

            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(saveBtn);

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
