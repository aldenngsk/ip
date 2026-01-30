package sixseven;

import java.io.File;

/**
 * Main app. Ties together Ui, Storage, Parser and TaskList.
 */
public class SixSeven {
    private static final String DATA_FILE = "data" + File.separator + "duke.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /** Sets up Ui, Storage and TaskList (loads from file or empty). */
    public SixSeven(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            loaded = new TaskList();
        }
        tasks = loaded;
    }

    /** Runs the main command loop until bye. */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                ParseResult pr = Parser.parse(fullCommand);
                String cmd = pr.getCommand();

                if ("bye".equals(cmd)) {
                    ui.showMessage("Bye. Hope to see you again soon!");
                    isExit = true;
                    continue;
                }

                if ("list".equals(cmd)) {
                    ui.showMessage("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.getSize(); i++) {
                        ui.showMessage((i + 1) + "." + tasks.getTask(i));
                    }
                    continue;
                }

                if ("mark".equals(cmd)) {
                    Task t = tasks.getTask(pr.getIndex());
                    t.markDone();
                    saveTasks();
                    ui.showMessage("Nice! I've marked this task as done:");
                    ui.showMessage(t.toString());
                    continue;
                }

                if ("unmark".equals(cmd)) {
                    Task t = tasks.getTask(pr.getIndex());
                    t.markUndone();
                    saveTasks();
                    ui.showMessage("OK, I've marked this task as not done yet:");
                    ui.showMessage(t.toString());
                    continue;
                }

                if ("delete".equals(cmd)) {
                    Task t = tasks.removeTask(pr.getIndex());
                    saveTasks();
                    ui.showMessage("Noted. I've removed this task:");
                    ui.showMessage(t.toString());
                    ui.showMessage("Now you have " + tasks.getSize() + " tasks in the list.");
                    continue;
                }

                if ("todo".equals(cmd)) {
                    Task t = new Todo(pr.getDescription());
                    tasks.addTask(t);
                    saveTasks();
                    ui.showMessage("Got it. I've added this task:");
                    ui.showMessage(t.toString());
                    ui.showMessage("Now you have " + tasks.getSize() + " tasks in the list.");
                    continue;
                }

                if ("deadline".equals(cmd)) {
                    Task t = new Deadline(pr.getDescription(), pr.getByDate());
                    tasks.addTask(t);
                    saveTasks();
                    ui.showMessage("Got it. I've added this task:");
                    ui.showMessage(t.toString());
                    ui.showMessage("Now you have " + tasks.getSize() + " tasks in the list.");
                    continue;
                }

                if ("event".equals(cmd)) {
                    Task t = new Event(pr.getDescription(), pr.getFrom(), pr.getTo());
                    tasks.addTask(t);
                    saveTasks();
                    ui.showMessage("Got it. I've added this task:");
                    ui.showMessage(t.toString());
                    ui.showMessage("Now you have " + tasks.getSize() + " tasks in the list.");
                    continue;
                }

            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Something went wrong.");
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    private void saveTasks() {
        try {
            storage.save(tasks);
        } catch (DukeException e) {
            ui.showSaveError();
        }
    }

    /** Entry point. */
    public static void main(String[] args) {
        new SixSeven(DATA_FILE).run();
    }
}
