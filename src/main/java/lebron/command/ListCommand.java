package lebron.command;

import java.util.ArrayList;

import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.TaskList;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks The task list to display.
     * @param storage The storage (not used).
     * @return The response lines containing the task list.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) {
        return ResponseFormatter.respondTaskList(tasks.getTasks());
    }
}
