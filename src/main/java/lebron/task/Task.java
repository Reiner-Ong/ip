package lebron.task;

/**
 * Represents a task with a description and completion status.
 * This is the base class for all task types in the Lebron chatbot.
 */
public class Task {
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
     * Returns the file format representation of this task.
     *
     * @return The string representation for saving to file.
     */
    public String toFileString() {
        String result = (isDone ? "1" : "0") + " | " + description;
        assert result.contains(" | ") : "File format should always contain delimiter";
        return result;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
