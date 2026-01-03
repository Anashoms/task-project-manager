package taskmanager.ui.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import taskmanager.model.Project;
import taskmanager.service.ProjectStore;
import taskmanager.ui.ProjectDetailsUI;

public class ButtonEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {

    private JButton button;
    private JTable table;
    private int row;

    public ButtonEditor(JTable table) {
        this.table = table;
        button = new JButton("Details");
        button.setFocusPainted(false);
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column) {

        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Details";
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // جلب المشروع الصحيح من ProjectStore
        Project selectedProject = ProjectStore.getProjects().get(row);

        // فتح صفحة تفاصيل المشروع
        new ProjectDetailsUI(selectedProject).setVisible(true);

        fireEditingStopped();
    }
}
