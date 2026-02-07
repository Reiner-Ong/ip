package lebron.command;

import java.util.ArrayList;

import lebron.LebronException;
import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.task.TaskList;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private Task task;

    /**
     * Creates an AddCommand with the specified task.
     *
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add command by adding the task to the list and saving.
     *
     * @param tasks The task list to add to.
     * @param storage The storage to save the updated list.
     * @return The response lines confirming the task was added.
     * @throws LebronException If there is an error saving.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) throws LebronException {
        tasks.add(task);
        storage.save(tasks.getTasks());
        return ResponseFormatter.respondTaskAdded(task, tasks.size());
    }
}
