package lebron.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import lebron.LebronException;
import lebron.task.Deadline;
import lebron.task.Event;
import lebron.task.Task;
import lebron.task.Todo;

/**
 * Handles loading and saving tasks to a file on disk.
 * This class provides functionality to persist tasks between application runs
 * by reading from and writing to a text file in a pipe-delimited format.
 */
public class Storage {
    private String filePath;

    /**
     * Creates a new Storage with the specified file path.
     *
     * @param filePath The path to the file for storing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * Creates the file and parent directories if they do not exist.
     *
     * @return The list of tasks loaded from the file.
     * @throws LebronException If there is an error reading or parsing the file.
     */
    public ArrayList<Task> load() throws LebronException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            createFileIfNotExists(file);
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new LebronException("Couldn't read the save file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves all tasks to the file.
     *
     * @param tasks The list of tasks to save.
     * @throws LebronException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws LebronException {
        File file = new File(filePath);
        createFileIfNotExists(file);

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new LebronException("Couldn't save to file: " + e.getMessage());
        }
    }

    /**
     * Creates the file and its parent directories if they do not exist.
     *
     * @param file The file to create.
     * @throws LebronException If there is an error creating the file or directories.
     */
    private void createFileIfNotExists(File file) throws LebronException {
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new LebronException("Couldn't create save file: " + e.getMessage());
        }
    }

    /**
     * Parses a single line from the save file and converts it to a Task object.
     *
     * @param line The line to parse in pipe-delimited format.
     * @return The parsed Task, or null if the line format is invalid.
     * @throws LebronException If the task-specific data is missing or malformed.
     */
    private Task parseTaskFromLine(String line) throws LebronException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length < 4) {
                throw new LebronException("Invalid deadline format in save file.");
            }
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            if (parts.length < 5) {
                throw new LebronException("Invalid event format in save file.");
            }
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
