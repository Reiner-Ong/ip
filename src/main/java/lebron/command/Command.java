package lebron.command;

import java.util.ArrayList;

import lebron.LebronException;
import lebron.storage.Storage;
import lebron.task.TaskList;

/**
 * Represents an abstract command that can be executed by the chatbot.
 * All specific commands inherit from this class.
 */
public abstract class Command {

    /**
     * Executes the command with the given task list and storage.
     *
     * @param tasks The task list to operate on.
     * @param storage The storage to save/load tasks.
     * @return The response lines to display to the user.
     * @throws LebronException If there is an error executing the commands.
     */
    public abstract ArrayList<String> execute(TaskList tasks, Storage storage)
            throws LebronException;

    /**
     * Returns whether this command should exit the application.
     *
     * @return True if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
