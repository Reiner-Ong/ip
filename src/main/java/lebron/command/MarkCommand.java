package lebron.command;

import java.util.ArrayList;

import lebron.LebronException;
import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.task.TaskList;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int index;

    /**
     * Creates a MarkCommand with the specified task index.
     *
     * @param index The 0-based index of the task to mark.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark command by marking the task as done and saving.
     *
     * @param tasks The task list containing the task.
     * @param storage The storage to save the updated list.
     * @return The response lines confirming the task was marked.
     * @throws LebronException If the index is invalid or there is an error saving.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) throws LebronException {
        Task task = tasks.mark(index);
        storage.save(tasks.getTasks());
        return ResponseFormatter.respondTaskMarked(task);
    }
}
