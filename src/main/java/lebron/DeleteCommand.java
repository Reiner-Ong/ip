package lebron;

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
     * @param ui The UI to display the result.
     * @param storage The storage to save the updated list.
     * @throws LebronException If the index is invalid or there is an error saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LebronException {
        Task task = tasks.delete(index);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(task, tasks.size());
    }
}
