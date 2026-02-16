package lebron.task;

import java.util.ArrayList;

import lebron.LebronException;

/**
 * Represents a list of tasks and provides operations to manage them.
 * This class encapsulates the task list and provides methods to add, delete,
 * and retrieve tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        assert task != null : "Null task should never be passed to add()";
        tasks.add(task);
    }

    /**
     * Deletes a task from the list at the specified index.
     *
     * @param index The index of the task to delete (0-based).
     * @return The deleted task.
     * @throws LebronException If the index is invalid.
     */
    public Task delete(int index) throws LebronException {
        validateIndex(index);
        assert index >= 0 && index < tasks.size() : "validateIndex should have caught invalid index";
        return tasks.remove(index);
    }

    /**
     * Gets a task from the list at the specified index.
     *
     * @param index The index of the task to get (0-based).
     * @return The task at the specified index.
     * @throws LebronException If the index is invalid.
     */
    public Task get(int index) throws LebronException {
        validateIndex(index);
        assert index >= 0 && index < tasks.size() : "validateIndex should have caught invalid index";
        return tasks.get(index);
    }

    /**
     * Marks a task as done at the specified index.
     *
     * @param index The index of the task to mark (0-based).
     * @return The marked task.
     * @throws LebronException If the index is invalid.
     */
    public Task mark(int index) throws LebronException {
        validateIndex(index);
        assert index >= 0 && index < tasks.size() : "validateIndex should have caught invalid index";
        Task task = tasks.get(index);
        task.markAsDone();
        return task;
    }

    /**
     * Unmarks a task at the specified index.
     *
     * @param index The index of the task to unmark (0-based).
     * @return The unmarked task.
     * @throws LebronException If the index is invalid.
     */
    public Task unmark(int index) throws LebronException {
        validateIndex(index);
        assert index >= 0 && index < tasks.size() : "validateIndex should have caught invalid index";
        Task task = tasks.get(index);
        task.markAsNotDone();
        return task;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns a list of tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return An ArrayList of tasks that match the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    /**
     * Validates that the given index is within the valid range.
     *
     * @param index The index to validate (0-based).
     * @throws LebronException If the index is invalid.
     */
    private void validateIndex(int index) throws LebronException {
        if (tasks.isEmpty()) {
            throw new LebronException("The roster's empty! Add some tasks first, my guy.");
        }
        if (index < 0 || index >= tasks.size()) {
            throw new LebronException("Task " + (index + 1) + " doesn't exist! "
                    + "You got " + tasks.size() + " task(s). "
                    + "Pick a number between 1 and " + tasks.size() + ".");
        }
    }
}
