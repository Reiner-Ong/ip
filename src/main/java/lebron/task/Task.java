package lebron.task;

/**
 * Represents a task with a description and completion status.
 * This is the base class for all task types in the Lebron chatbot.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing the completion state of this task.
     *
     * @return "[X]" if task is done, "[ ]" otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the file format representation of this task.
     *
     * @return The string representation for saving to file.
     */
    public String toFileString() {
        String result = (isDone ? "1" : "0") + " | " + description;
        assert result.contains(" | ") : "File format should always contain delimiter";
        return result;
    }

    /**
     * Returns a clone of the current task as a fresh undone copy.
     * Subclasses must return a new instance of their own type with the same
     * description and task-specific fields, always with isDone set to false.
     *
     * @return A new undone Task with the same fields.
     */
    public abstract Task clone();

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
