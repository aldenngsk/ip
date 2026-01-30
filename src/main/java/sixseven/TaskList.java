package sixseven;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the list of tasks and add/get/remove operations.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Appends a task to the list. */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /** Returns the task at index; throws if index invalid. */
    public Task getTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("That task number does not exist.");
        }
        return tasks.get(index);
    }

    /** Removes and returns the task at index; throws if index invalid. */
    public Task removeTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("That task number does not exist.");
        }
        return tasks.remove(index);
    }

    public int getSize() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
