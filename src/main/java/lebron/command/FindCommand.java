package lebron.command;

import java.util.ArrayList;

import lebron.LebronException;
import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.task.TaskList;

/**
 * Represents a command to find tasks containing a specific keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Creates a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks with the keyword.
     *
     * @param tasks The task list to search in.
     * @param storage The storage (not used in this command).
     * @return The response lines containing the found tasks.
     * @throws LebronException If there is an error during execution.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) throws LebronException {
        ArrayList<Task> foundTasks = tasks.findTasks(keyword);
        return ResponseFormatter.respondFoundTasks(foundTasks);
    }
}
