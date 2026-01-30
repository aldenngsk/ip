package sixseven;

/**
 * A task with no date/time.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    protected String getTypeIcon() {
        return "[T]";
    }

    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
