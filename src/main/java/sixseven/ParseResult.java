package sixseven;

import java.time.LocalDate;

/**
 * Holds the parsed command type and arguments from user input.
 */
public class ParseResult {
    private final String command;
    private final int index;
    private final String description;
    private final LocalDate byDate;
    private final String from;
    private final String to;

    public ParseResult(String command, int index, String description, LocalDate byDate, String from, String to) {
        this.command = command;
        this.index = index;
        this.description = description;
        this.byDate = byDate;
        this.from = from;
        this.to = to;
    }

    public String getCommand() {
        return command;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getByDate() {
        return byDate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
