package sixseven;

import java.io.File;

public class SixSeven {
    // Level-8: Supports Deadline and Event task types with date/time
    private static final String DATA_FILE = "data" + File.separator + "duke.txt";
    private static final String MESSAGE_TASK_ADDED = "Got it. I've added this task:";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

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
        assert tasks != null : "Task list should be initialized";
        assert storage != null : "Storage should be initialized";
        assert ui != null : "Ui should be initialized";
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                ParseResult pr = Parser.parse(fullCommand);
                assert pr != null : "ParseResult should not be null";
                String cmd = pr.getCommand();
                assert cmd != null : "Command should not be null";

                if ("bye".equals(cmd)) {
                    ui.showMessage("Bye. Hope to see you again soon!");
                    isExit = true;
                    continue;
                }

                if ("list".equals(cmd)) {
                    ui.showMessage("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.getSize(); i++) {
                        Task task = tasks.getTask(i);
                        assert task != null : "Task at index " + i + " should not be null";
                        ui.showMessage((i + 1) + "." + task);
                    }
                    continue;
                }

                if ("find".equals(cmd)) {
                    String keyword = pr.getDescription() == null ? "" : pr.getDescription();
                    ui.showMessage("Here are the matching tasks in your list:");
                    int num = 1;
                    for (int i = 0; i < tasks.getSize(); i++) {
                        Task t = tasks.getTask(i);
                        if (!keyword.isEmpty() && t.getDescription().contains(keyword)) {
                            ui.showMessage(num + "." + t);
                            num++;
                        }
                    }
                    continue;
                }

                if ("mark".equals(cmd)) {
                    Task t = tasks.getTask(pr.getIndex());
                    assert t != null : "Task at valid index should not be null";
                    t.markDone();
                    saveTasks();
                    ui.showMessage("Nice! I've marked this task as done:");
                    ui.showMessage(t.toString());
                    continue;
                }

                if ("unmark".equals(cmd)) {
                    Task t = tasks.getTask(pr.getIndex());
                    assert t != null : "Task at valid index should not be null";
                    t.markUndone();
                    saveTasks();
                    ui.showMessage("OK, I've marked this task as not done yet:");
                    ui.showMessage(t.toString());
                    continue;
                }

                if ("delete".equals(cmd)) {
                    Task t = tasks.removeTask(pr.getIndex());
                    assert t != null : "Removed task should not be null";
                    saveTasks();
                    ui.showMessage("Noted. I've removed this task:");
                    ui.showMessage(t.toString());
                    ui.showMessage("Now you have " + tasks.getSize() + " tasks in the list.");
                    continue;
                }

                if ("todo".equals(cmd)) {
                    Task t = new Todo(pr.getDescription());
                    assert t != null : "New Todo task should not be null";
                    tasks.addTask(t);
                    assert tasks.getSize() > 0 : "Task list should not be empty after adding task";
                    saveTasks();
                    ui.showMessage(MESSAGE_TASK_ADDED);
                    ui.showMessage(t.toString());
                    ui.showMessage("Now you have " + tasks.getSize() + " tasks in the list.");
                    continue;
                }

                if ("deadline".equals(cmd)) {
                    Task t = new Deadline(pr.getDescription(), pr.getByDate());
                    assert t != null : "New Deadline task should not be null";
                    tasks.addTask(t);
                    assert tasks.getSize() > 0 : "Task list should not be empty after adding task";
                    saveTasks();
                    ui.showMessage(MESSAGE_TASK_ADDED);
                    ui.showMessage(t.toString());
                    ui.showMessage("Now you have " + tasks.getSize() + " tasks in the list.");
                    continue;
                }

                if ("event".equals(cmd)) {
                    Task t = new Event(pr.getDescription(), pr.getFrom(), pr.getTo());
                    assert t != null : "New Event task should not be null";
                    tasks.addTask(t);
                    assert tasks.getSize() > 0 : "Task list should not be empty after adding task";
                    saveTasks();
                    ui.showMessage(MESSAGE_TASK_ADDED);
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

    public static void main(String[] args) {
        new SixSeven(DATA_FILE).run();
    }
}
