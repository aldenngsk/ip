package sixseven;

/**
 * Base class for todo, deadline and event tasks.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void markUndone() {
        isDone = false;
    }

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
