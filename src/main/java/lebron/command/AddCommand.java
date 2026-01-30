package lebron.command;

import lebron.LebronException;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.task.TaskList;
import lebron.ui.Ui;

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
     * @param ui The UI to display the result.
     * @param storage The storage to save the updated list.
     * @throws LebronException If there is an error saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LebronException {
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
