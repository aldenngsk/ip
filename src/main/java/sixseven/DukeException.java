package sixseven;

/**
 * Thrown when the user gives invalid input or something goes wrong.
 */
public class DukeException extends Exception {
    /** Creates an exception with the given message. */
    public DukeException(String message) {
        super(message);
    }
}
