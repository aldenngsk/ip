package sixseven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;
    private final String dataDir;

    public Storage(String filePath) {
        this.filePath = filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        this.dataDir = lastSep >= 0 ? filePath.substring(0, lastSep) : ".";
    }

    public List<Task> load() throws DukeException {
        File dataFile = new File(filePath);
        if (!dataFile.exists()) {
            return new ArrayList<>();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            List<Task> tasks = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new DukeException("Could not read from file: " + e.getMessage());
        }
    }

    public void save(TaskList taskList) throws DukeException {
        File dir = new File(dataDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task t : taskList.getTasks()) {
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
            throw new DukeException("Could not save tasks to file.");
        }
    }

    private Task parseTaskFromFile(String line) {
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
            try {
                task = new Deadline(parts[2].trim(), LocalDate.parse(parts[3].trim()));
            } catch (DateTimeParseException e) {
                return null;
            }
        } else if ("E".equals(type) && parts.length >= 5) {
            task = new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
        }
        if (task != null && isDone) {
            task.markDone();
        }
        return task;
    }
}
