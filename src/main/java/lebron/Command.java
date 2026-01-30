package lebron;

/**
 * Represents an abstract command that can be executed by the chatbot.
 * All specific commands inherit from this class.
 */
public abstract class Command {

    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks The task list to operate on.
     * @param ui The UI to display messages.
     * @param storage The storage to save/load tasks.
     * @throws LebronException If there is an error executing the command.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LebronException;

    /**
     * Returns whether this command should exit the application.
     *
     * @return True if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
