package sixseven.gui;

import java.util.ArrayList;
import java.util.List;

import sixseven.Deadline;
import sixseven.DukeException;
import sixseven.Event;
import sixseven.ParseResult;
import sixseven.Parser;
import sixseven.Storage;
import sixseven.Task;
import sixseven.TaskList;
import sixseven.Todo;

/**
 * Processes commands in the GUI and returns responses for display.
 */
public class CommandHandler {
    private final TaskList tasks;
    private final Storage storage;

    public CommandHandler(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Returns the response containing messages to display and whether the app should exit.
     * Processes the given command.
     */
    public GuiResponse processCommand(String fullCommand) {
        List<String> messages = new ArrayList<>();
        boolean shouldExit = false;
        try {
            ParseResult pr = Parser.parse(fullCommand.trim());
            String cmd = pr.getCommand();

            if ("bye".equals(cmd)) {
                messages.add("Bye. Hope to see you again soon!");
                shouldExit = true;
                return new GuiResponse(messages, shouldExit);
            }

            if ("list".equals(cmd)) {
                messages.add("Here are the tasks in your list:");
                for (int i = 0; i < tasks.getSize(); i++) {
                    messages.add((i + 1) + "." + tasks.getTask(i));
                }
                return new GuiResponse(messages, false);
            }

            if ("find".equals(cmd)) {
                String keyword = pr.getDescription() == null ? "" : pr.getDescription();
                messages.add("Here are the matching tasks in your list:");
                int num = 1;
                for (int i = 0; i < tasks.getSize(); i++) {
                    Task t = tasks.getTask(i);
                    if (!keyword.isEmpty() && t.getDescription().contains(keyword)) {
                        messages.add(num + "." + t);
                        num++;
                    }
                }
                return new GuiResponse(messages, false);
            }

            if ("mark".equals(cmd)) {
                Task t = tasks.getTask(pr.getIndex());
                t.markDone();
                if (!saveTasks(messages)) {
                    return new GuiResponse(messages, false);
                }
                messages.add("Nice! I've marked this task as done:");
                messages.add(t.toString());
                return new GuiResponse(messages, false);
            }

            if ("unmark".equals(cmd)) {
                Task t = tasks.getTask(pr.getIndex());
                t.markUndone();
                if (!saveTasks(messages)) {
                    return new GuiResponse(messages, false);
                }
                messages.add("OK, I've marked this task as not done yet:");
                messages.add(t.toString());
                return new GuiResponse(messages, false);
            }

            if ("delete".equals(cmd)) {
                Task t = tasks.removeTask(pr.getIndex());
                if (!saveTasks(messages)) {
                    return new GuiResponse(messages, false);
                }
                messages.add("Noted. I've removed this task:");
                messages.add(t.toString());
                messages.add("Now you have " + tasks.getSize() + " tasks in the list.");
                return new GuiResponse(messages, false);
            }

            if ("todo".equals(cmd)) {
                Task t = new Todo(pr.getDescription());
                tasks.addTask(t);
                if (!saveTasks(messages)) {
                    return new GuiResponse(messages, false);
                }
                messages.add("Got it. I've added this task:");
                messages.add(t.toString());
                messages.add("Now you have " + tasks.getSize() + " tasks in the list.");
                return new GuiResponse(messages, false);
            }

            if ("deadline".equals(cmd)) {
                Task t = new Deadline(pr.getDescription(), pr.getByDate());
                tasks.addTask(t);
                if (!saveTasks(messages)) {
                    return new GuiResponse(messages, false);
                }
                messages.add("Got it. I've added this task:");
                messages.add(t.toString());
                messages.add("Now you have " + tasks.getSize() + " tasks in the list.");
                return new GuiResponse(messages, false);
            }

            if ("event".equals(cmd)) {
                Task t = new Event(pr.getDescription(), pr.getFrom(), pr.getTo());
                tasks.addTask(t);
                if (!saveTasks(messages)) {
                    return new GuiResponse(messages, false);
                }
                messages.add("Got it. I've added this task:");
                messages.add(t.toString());
                messages.add("Now you have " + tasks.getSize() + " tasks in the list.");
                return new GuiResponse(messages, false);
            }

        } catch (DukeException e) {
            messages.add("Oops! " + e.getMessage());
        } catch (Exception e) {
            messages.add("Oops! Something went wrong.");
        }
        return new GuiResponse(messages, shouldExit);
    }

    private boolean saveTasks(List<String> messages) {
        try {
            storage.save(tasks);
            return true;
        } catch (DukeException e) {
            messages.add("Oops! Could not save tasks to file.");
            return false;
        }
    }
}
