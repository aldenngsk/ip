package sixseven.gui;

import sixseven.Deadline;
import sixseven.DukeException;
import sixseven.Event;
import sixseven.ParseResult;
import sixseven.Parser;
import sixseven.Storage;
import sixseven.Task;
import sixseven.TaskList;
import sixseven.Todo;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    private final TaskList tasks;
    private final Storage storage;

    public CommandHandler(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    public GuiResponse processCommand(String fullCommand) {
        List<String> out = new ArrayList<>();
        boolean exit = false;
        try {
            ParseResult pr = Parser.parse(fullCommand.trim());
            String cmd = pr.getCommand();

            if ("bye".equals(cmd)) {
                out.add("Bye. Hope to see you again soon!");
                exit = true;
                return new GuiResponse(out, exit);
            }

            if ("list".equals(cmd)) {
                out.add("Here are the tasks in your list:");
                for (int i = 0; i < tasks.getSize(); i++) {
                    out.add((i + 1) + "." + tasks.getTask(i));
                }
                return new GuiResponse(out, false);
            }

            if ("find".equals(cmd)) {
                String keyword = pr.getDescription() == null ? "" : pr.getDescription();
                out.add("Here are the matching tasks in your list:");
                int num = 1;
                for (int i = 0; i < tasks.getSize(); i++) {
                    Task t = tasks.getTask(i);
                    if (!keyword.isEmpty() && t.getDescription().contains(keyword)) {
                        out.add(num + "." + t);
                        num++;
                    }
                }
                return new GuiResponse(out, false);
            }

            if ("mark".equals(cmd)) {
                Task t = tasks.getTask(pr.getIndex());
                t.markDone();
                saveTasks();
                out.add("Nice! I've marked this task as done:");
                out.add(t.toString());
                return new GuiResponse(out, false);
            }

            if ("unmark".equals(cmd)) {
                Task t = tasks.getTask(pr.getIndex());
                t.markUndone();
                saveTasks();
                out.add("OK, I've marked this task as not done yet:");
                out.add(t.toString());
                return new GuiResponse(out, false);
            }

            if ("delete".equals(cmd)) {
                Task t = tasks.removeTask(pr.getIndex());
                saveTasks();
                out.add("Noted. I've removed this task:");
                out.add(t.toString());
                out.add("Now you have " + tasks.getSize() + " tasks in the list.");
                return new GuiResponse(out, false);
            }

            if ("todo".equals(cmd)) {
                Task t = new Todo(pr.getDescription());
                tasks.addTask(t);
                saveTasks();
                out.add("Got it. I've added this task:");
                out.add(t.toString());
                out.add("Now you have " + tasks.getSize() + " tasks in the list.");
                return new GuiResponse(out, false);
            }

            if ("deadline".equals(cmd)) {
                Task t = new Deadline(pr.getDescription(), pr.getByDate());
                tasks.addTask(t);
                saveTasks();
                out.add("Got it. I've added this task:");
                out.add(t.toString());
                out.add("Now you have " + tasks.getSize() + " tasks in the list.");
                return new GuiResponse(out, false);
            }

            if ("event".equals(cmd)) {
                Task t = new Event(pr.getDescription(), pr.getFrom(), pr.getTo());
                tasks.addTask(t);
                saveTasks();
                out.add("Got it. I've added this task:");
                out.add(t.toString());
                out.add("Now you have " + tasks.getSize() + " tasks in the list.");
                return new GuiResponse(out, false);
            }

        } catch (DukeException e) {
            out.add("Oops! " + e.getMessage());
        } catch (Exception e) {
            out.add("Oops! Something went wrong.");
        }
        return new GuiResponse(out, exit);
    }

    private void saveTasks() {
        try {
            storage.save(tasks);
        } catch (DukeException e) {
        }
    }
}
