package sixseven;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Task that must be done by a given date. */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDate by;

    /** Creates a deadline with description and due date. */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    protected String getTypeIcon() {
        return "[D]";
    }

    @Override
    public String toString() {
        return getTypeIcon() + getStatusIcon() + " " + description + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }

    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
