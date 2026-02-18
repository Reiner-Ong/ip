package lebron.command;

import java.util.ArrayList;

import lebron.LebronException;
import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.task.TaskList;

/**
 * Represents a command to clone an existing task.
 * The CloneCommand creates a fresh undone duplicate of the specified task
 * and adds it to the end of the task list.
 */
public class CloneCommand extends Command {
    private int index;

    /**
     * Creates a new CloneCommand for the specified task index.
     *
     * @param index The 0-based index of the task to clone.
     */
    public CloneCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the clone command by creating a copy of the specified task and saving.
     *
     * @param tasks The task list to clone from and add to.
     * @param storage The storage to save the updated list.
     * @return The response lines confirming the task was cloned.
     * @throws LebronException If the index is invalid or there is an error saving.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) throws LebronException {
        Task newTask = tasks.get(index).clone();
        tasks.add(newTask);
        storage.save(tasks.getTasks());
        return ResponseFormatter.respondTaskAdded(newTask, tasks.size());
    }
}
