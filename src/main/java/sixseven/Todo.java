package sixseven;

/** Task with no date or time. */
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
