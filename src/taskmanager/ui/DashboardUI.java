package taskmanager.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    public DashboardUI() {
        setTitle("Task Manager Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== background =====
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(220, 232, 236));

        // =====  Central card =====
        JPanel card = new JPanel(new GridLayout(4, 1, 25, 25));
        card.setPreferredSize(new Dimension(600, 450));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // ===== Address =====
        JLabel title = new JLabel("Task Manager", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(40, 70, 100));

        // ===== Buttons =====
        JButton createProjectBtn = createButton(
                "Create Project",
                new Color(45, 93, 123)
        );

        JButton viewProjectsBtn = createButton(
                "View All Projects",
                new Color(60, 120, 160)
        );

        JButton exitBtn = createButton(
                "Exit",
                new Color(200, 90, 90)
        );

        // ===== Fasten the buttons =====
        createProjectBtn.addActionListener(e -> {
            new CreateProjectUI().setVisible(true);
            this.dispose(); 
        });

        viewProjectsBtn.addActionListener(e -> {
            new ViewAllProjectsUI().setVisible(true);
            this.dispose();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        // ===== Add items =====
        card.add(title);
        card.add(createProjectBtn);
        card.add(viewProjectsBtn);
        card.add(exitBtn);

        background.add(card);
        add(background);
    }

    // ===== Unified button =====
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(400, 60));
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        return button;
    }
}
