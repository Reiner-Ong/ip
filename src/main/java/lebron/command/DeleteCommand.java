package lebron.command;

import java.util.ArrayList;

import lebron.LebronException;
import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.task.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Creates a DeleteCommand with the specified task index.
     *
     * @param index The 0-based index of the task to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete command by removing the task and saving.
     *
     * @param tasks The task list to delete from.
     * @param storage The storage to save the updated list.
     * @return The response lines confirming the task was deleted.
     * @throws LebronException If the index is invalid or there is an error saving.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) throws LebronException {
        Task task = tasks.delete(index);
        storage.save(tasks.getTasks());
        return ResponseFormatter.respondTaskDeleted(task, tasks.size());
    }
}
