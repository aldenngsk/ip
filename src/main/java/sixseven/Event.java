package sixseven;

/** Task with a start and end time. */
public class Event extends Task {
    private String from;
    private String to;

    /** Creates an event with description, start, and end. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    protected String getTypeIcon() {
        return "[E]";
    }

    @Override
    public String toString() {
        return getTypeIcon() + getStatusIcon() + " " + description
                + " (/from " + from + " /to " + to + ")";
    }

    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }
}
