/**
 * Represents a task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Creates a new deadline with the given description and due date.
     *
     * @param description The description of the deadline.
     * @param by The due date/time of the deadline.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
