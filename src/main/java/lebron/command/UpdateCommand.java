package lebron.command;

import java.util.ArrayList;

import lebron.LebronException;
import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.Deadline;
import lebron.task.Event;
import lebron.task.Task;
import lebron.task.TaskList;
import lebron.task.Todo;

/**
 * Represents a command to update the fields of an existing task.
 * Fields not provided by the user are preserved from the original task.
 * The task type cannot be changed.
 */
public class UpdateCommand extends Command {
    private int index;
    private String newDescription;
    private String newBy;
    private String newFrom;
    private String newTo;

    /**
     * Creates an UpdateCommand with the specified index and new field values.
     * Any field set to null will be preserved from the original task.
     *
     * @param index The 0-based index of the task to update.
     * @param newDescription The new description, or null to keep existing.
     * @param newBy The new due date string for deadlines, or null to keep existing.
     * @param newFrom The new start date string for events, or null to keep existing.
     * @param newTo The new end date string for events, or null to keep existing.
     */
    public UpdateCommand(int index, String newDescription,
            String newBy, String newFrom, String newTo) {
        this.index = index;
        this.newDescription = newDescription;
        this.newBy = newBy;
        this.newFrom = newFrom;
        this.newTo = newTo;
    }

    /**
     * Executes the update command by replacing the task at the given index
     * with a new task constructed from the updated fields.
     *
     * @param tasks The task list to update.
     * @param storage The storage to save the updated list.
     * @return The response lines showing the before and after.
     * @throws LebronException If the index is invalid, the field types don't match
     *                         the task type, or there is an error saving.
     */
    @Override
    public ArrayList<String> execute(TaskList tasks, Storage storage) throws LebronException {
        Task oldTask = tasks.get(index);
        Task newTask = buildUpdatedTask(oldTask);
        if (oldTask.isDone()) {
            newTask.markAsDone();
        }
        tasks.set(index, newTask);
        storage.save(tasks.getTasks());
        return ResponseFormatter.respondTaskUpdated(oldTask, newTask);
    }

    /**
     * Constructs the updated task based on the original task's type and the provided fields.
     *
     * @param oldTask The original task.
     * @return The new task with updated fields.
     * @throws LebronException If the provided fields don't match the task type,
     *                         or the date format is invalid.
     */
    private Task buildUpdatedTask(Task oldTask) throws LebronException {
        if (oldTask instanceof Todo) {
            return buildUpdatedTodo((Todo) oldTask);
        } else if (oldTask instanceof Deadline) {
            return buildUpdatedDeadline((Deadline) oldTask);
        } else if (oldTask instanceof Event) {
            return buildUpdatedEvent((Event) oldTask);
        }
        throw new LebronException("Unknown task type, can't update.");
    }

    private Task buildUpdatedTodo(Todo old) throws LebronException {
        if (newBy != null || newFrom != null || newTo != null) {
            throw new LebronException("That's the wrong playbook! "
                    + "Todos don't have dates. Just update the description.");
        }
        String description = newDescription != null ? newDescription : old.getDescription();
        return new Todo(description);
    }

    private Task buildUpdatedDeadline(Deadline old) throws LebronException {
        if (newFrom != null || newTo != null) {
            throw new LebronException("That's the wrong playbook! "
                    + "Deadlines use /by, not /from or /to.");
        }
        String description = newDescription != null ? newDescription : old.getDescription();
        String by = newBy != null ? newBy : old.getBy().toString();
        return new Deadline(description, by);
    }

    private Task buildUpdatedEvent(Event old) throws LebronException {
        if (newBy != null) {
            throw new LebronException("That's the wrong playbook! "
                    + "Events use /from and /to, not /by.");
        }
        String description = newDescription != null ? newDescription : old.getDescription();
        String from = newFrom != null ? newFrom : old.getFrom().toString();
        String to = newTo != null ? newTo : old.getTo().toString();
        return new Event(description, from, to);
    }
}
