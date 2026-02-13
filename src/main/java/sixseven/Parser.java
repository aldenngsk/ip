package sixseven;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static ParseResult parse(String fullCommand) throws DukeException {
        String input = fullCommand.trim();
        if (input.isEmpty()) {
            throw new DukeException("I don't understand that command.");
        }

        if (input.equals("bye")) {
            return new ParseResult("bye", -1, null, null, null, null);
        }
        if (input.equals("list")) {
            return new ParseResult("list", -1, null, null, null, null);
        }
        if (input.equals("help")) {
            return new ParseResult("help", -1, null, null, null, null);
        }
        if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            return new ParseResult("find", -1, keyword.isEmpty() ? null : keyword, null, null, null);
        }

        if (input.startsWith("mark ")) {
            String rest = input.substring(5).trim();
            int index = parseIndex(rest, "mark");
            return new ParseResult("mark", index, null, null, null, null);
        }
        if (input.startsWith("unmark ")) {
            String rest = input.substring(7).trim();
            int index = parseIndex(rest, "unmark");
            return new ParseResult("unmark", index, null, null, null, null);
        }
        if (input.startsWith("delete ")) {
            String rest = input.substring(7).trim();
            int index = parseIndex(rest, "delete");
            return new ParseResult("delete", index, null, null, null, null);
        }

        if (input.equals("todo")) {
            throw new DukeException("The description of a todo cannot be empty.");
        }
        if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new DukeException("The description of a todo cannot be empty.");
            }
            return new ParseResult("todo", -1, description, null, null, null);
        }

        if (input.startsWith("deadline ")) {
            String rest = input.substring(9).trim();
            String[] parts = rest.split(" /by ", 2);
            if (parts.length < 2) {
                throw new DukeException("Please specify a deadline using /by.");
            }
            LocalDate byDate;
            try {
                byDate = LocalDate.parse(parts[1].trim());
            } catch (DateTimeParseException e) {
                throw new DukeException("Please use date format yyyy-mm-dd (e.g. 2019-10-15).");
            }
            return new ParseResult("deadline", -1, parts[0].trim(), byDate, null, null);
        }

        if (input.startsWith("event ")) {
            String rest = input.substring(6).trim();
            String[] parts = rest.split(" /from | /to ", 3);
            if (parts.length < 3) {
                throw new DukeException("Please specify an event using /from and /to.");
            }
            return new ParseResult("event", -1, parts[0].trim(), null, parts[1].trim(), parts[2].trim());
        }

        throw new DukeException("I don't understand that command.");
    }

    private static int parseIndex(String rest, String commandName) throws DukeException {
        try {
            return Integer.parseInt(rest) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please provide a valid task number.");
        }
    }
}
