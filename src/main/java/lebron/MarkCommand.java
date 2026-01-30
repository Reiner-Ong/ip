package lebron;

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
     * @param ui The UI to display the result.
     * @param storage The storage to save the updated list.
     * @throws LebronException If the index is invalid or there is an error saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LebronException {
        Task task = tasks.mark(index);
        storage.save(tasks.getTasks());
        ui.showTaskMarked(task);
    }
}
