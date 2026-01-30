import java.io.File;
<<<<<<< Updated upstream
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
=======
>>>>>>> Stashed changes

public class SixSeven {
    private static final String DATA_FILE = "data" + File.separator + "duke.txt";

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
    }

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

<<<<<<< Updated upstream
                if (input.startsWith("deadline ")) {
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2) {
                        throw new DukeException("Please specify a deadline using /by.");
                    }
                    tasks[taskCount++] = new Deadline(parts[0], parts[1]);
                    saveTasks(tasks, taskCount);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
=======
                if ("todo".equals(cmd)) {
                    Task t = new Todo(pr.getDescription());
                    tasks.addTask(t);
                    saveTasks();
                    ui.showMessage("Got it. I've added this task:");
                    ui.showMessage(t.toString());
                    ui.showMessage("Now you have " + tasks.getSize() + " tasks in the list.");
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    /**
     * Loads tasks from the data file. Creates data directory if it does not exist.
     * Returns the number of tasks loaded. Returns 0 if file does not exist or on error.
     */
    private static int loadTasks(Task[] tasks) {
        File dataDir = new File(DATA_DIR);
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < tasks.length) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks[count++] = task;
                }
            }
            return count;
        } catch (IOException e) {
            return 0;
        }
    }


    private static Task parseTaskFromFile(String line) {
        String[] parts = line.split(" \\| ", 5);
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0].trim();
        boolean isDone = "1".equals(parts[1].trim());
        Task task = null;
        if ("T".equals(type) && parts.length >= 3) {
            task = new Todo(parts[2].trim());
        } else if ("D".equals(type) && parts.length >= 4) {
            task = new Deadline(parts[2].trim(), parts[3].trim());
        } else if ("E".equals(type) && parts.length >= 5) {
            task = new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
        }
        if (task != null && isDone) {
            task.markDone();
        }
        return task;
    }

    /**
     * Saves all tasks to the data file. Creates data directory if it does not exist.
     */
    private static void saveTasks(Task[] tasks, int taskCount) {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            for (int i = 0; i < taskCount; i++) {
                Task t = tasks[i];
                String line;
                if (t instanceof Todo) {
                    line = ((Todo) t).toFileString();
                } else if (t instanceof Deadline) {
                    line = ((Deadline) t).toFileString();
                } else if (t instanceof Event) {
                    line = ((Event) t).toFileString();
                } else {
                    continue;
                }
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Oops! Could not save tasks to file.");
        }
=======
    private void saveTasks() {
        try {
            storage.save(tasks);
        } catch (DukeException e) {
            ui.showSaveError();
        }
    }

    public static void main(String[] args) {
        new SixSeven(DATA_FILE).run();
>>>>>>> Stashed changes
    }
}
