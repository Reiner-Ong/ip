package lebron.command;

import lebron.storage.Storage;
import lebron.task.TaskList;
import lebron.ui.Ui;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by displaying the goodbye message.
     *
     * @param tasks The task list (not used).
     * @param ui The UI to display the goodbye message.
     * @param storage The storage (not used).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Returns true since this command exits the application.
     *
     * @return True.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
