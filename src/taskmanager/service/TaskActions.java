package taskmanager.service;

public interface TaskActions {

    void onEdit();
    void onDelete();
    void onStatusChange(String newStatus);
}
