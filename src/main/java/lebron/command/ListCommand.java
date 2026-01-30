package lebron.command;

import lebron.storage.Storage;
import lebron.task.TaskList;
import lebron.ui.Ui;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks The task list to display.
     * @param ui The UI to display the task list.
     * @param storage The storage (not used).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks.getTasks());
    }
}
