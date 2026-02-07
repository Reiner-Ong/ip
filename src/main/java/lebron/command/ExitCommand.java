package lebron.command;

import java.util.ArrayList;

import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.TaskList;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by returning the goodbye message.
     *
     * @param tasks The task list (not used).
     * @param storage The storage (not used).
     * @return The response lines containing the goodbye message.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) {
        return ResponseFormatter.replyGoodbye();
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
