package sixseven;

/** Exception thrown when user input is invalid or an operation fails. */
public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }
}
