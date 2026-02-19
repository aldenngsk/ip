package sixseven;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds tasks and provides add, remove, and get operations with index checks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the given index. Throws if the index is invalid.
     */
    public Task getTask(int index) throws DukeException {
        if (!isValidIndex(index)) {
            throw new DukeException("That task number does not exist.");
        }
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the given index. Throws if the index is invalid.
     */
    public Task removeTask(int index) throws DukeException {
        if (!isValidIndex(index)) {
            throw new DukeException("That task number does not exist.");
        }
        return tasks.remove(index);
    }

    // Cursor helped extract this to remove duplicate validation (A-AiAssisted)
    private boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    public int getSize() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
