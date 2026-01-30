import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SixSeven {
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = "data" + File.separator + "duke.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = loadTasks(tasks);

        System.out.println("Hello! I'm SixSeven");
        System.out.println("What can I do for you?");

        while (true) {
            try {
                String input = scanner.nextLine();

                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }

                if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                    continue;
                }

                if (input.startsWith("mark ")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index < 0 || index >= taskCount) {
                        throw new DukeException("That task number does not exist.");
                    }
                    tasks[index].markDone();
                    saveTasks(tasks, taskCount);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index]);
                    continue;
                }

                if (input.startsWith("delete ")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                
                    if (index < 0 || index >= taskCount) {
                        throw new DukeException("That task number does not exist.");
                    }
                
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(tasks[index]);
                
                    for (int i = index; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }
                
                    taskCount--;
                    saveTasks(tasks, taskCount);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    continue;
                }
                

                if (input.startsWith("unmark ")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= taskCount) {
                        throw new DukeException("That task number does not exist.");
                    }
                    tasks[index].markUndone();
                    saveTasks(tasks, taskCount);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index]);
                    continue;
                }

                if (input.equals("todo")) {
                    throw new DukeException("The description of a todo cannot be empty.");
                }

                if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("The description of a todo cannot be empty.");
                    }
                    tasks[taskCount++] = new Todo(description);
                    saveTasks(tasks, taskCount);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    continue;
                }

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
                    continue;
                }

                if (input.startsWith("event ")) {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new DukeException("Please specify an event using /from and /to.");
                    }
                    tasks[taskCount++] = new Event(parts[0], parts[1], parts[2]);
                    saveTasks(tasks, taskCount);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    continue;
                }

                throw new DukeException("I don't understand that command.");

            } catch (DukeException e) {
                System.out.println("Oops! " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Oops! Something went wrong.");
            }
        }

        scanner.close();
    }

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
    }
}
