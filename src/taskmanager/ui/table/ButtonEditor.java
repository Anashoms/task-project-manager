package taskmanager.ui.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import taskmanager.ui.ViewAllProjectsUI;

public class ButtonEditor extends DefaultCellEditor {

    private JButton button;
    private int row;
    private ViewAllProjectsUI parent;

    public ButtonEditor(ViewAllProjectsUI parent) {
        super(new JCheckBox());
        this.parent = parent;

        button = new JButton("Details");
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            fireEditingStopped();
            parent.openProject(row);
        });
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value,
            boolean isSelected, int row, int column) {

        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Details";
    }
}
