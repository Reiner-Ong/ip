package lebron.command;

import java.util.ArrayList;

import lebron.LebronException;
import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.task.TaskList;

/**
 * Represents a command to unmark a task (mark as not done).
 */
public class UnmarkCommand extends Command {
    private int index;

    /**
     * Creates an UnmarkCommand with the specified task index.
     *
     * @param index The 0-based index of the task to unmark.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the unmark command by unmarking the task and saving.
     *
     * @param tasks The task list containing the task.
     * @param storage The storage to save the updated list.
     * @return The response lines confirming the task was unmarked.
     * @throws LebronException If the index is invalid or there is an error saving.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) throws LebronException {
        Task task = tasks.unmark(index);
        storage.save(tasks.getTasks());
        return ResponseFormatter.respondTaskUnmarked(task);
    }
}
