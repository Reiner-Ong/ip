package lebron;

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
     * @param ui The UI to display the result.
     * @param storage The storage to save the updated list.
     * @throws LebronException If the index is invalid or there is an error saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LebronException {
        Task task = tasks.unmark(index);
        storage.save(tasks.getTasks());
        ui.showTaskUnmarked(task);
    }
}
