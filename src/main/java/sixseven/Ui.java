package sixseven;

import java.util.Scanner;

/**
 * Handles reading user input and printing output.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /** Prints the welcome message. */
    public void showWelcome() {
        System.out.println("Hello! I'm SixSeven");
        System.out.println("What can I do for you?");
    }

    /** Reads one line of user input. */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /** Prints an error message. */
    public void showError(String message) {
        System.out.println("Oops! " + message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println("Oops! Could not load tasks from file. Starting with empty list.");
    }

    public void showSaveError() {
        System.out.println("Oops! Could not save tasks to file.");
    }

    public void close() {
        scanner.close();
    }
}
