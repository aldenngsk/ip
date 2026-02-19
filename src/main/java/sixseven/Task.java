package sixseven;

/**
 * Base for all task types. Tracks description and completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        isDone = true;
    }

    public void markUndone() {
        isDone = false;
    }

    /** Returns the task description. */
    public String getDescription() {
        return description;
    }

    protected String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    protected abstract String getTypeIcon();

    @Override
    public String toString() {
        return getTypeIcon() + getStatusIcon() + " " + description;
    }
}
