package lebron;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that needs to be done before a specific date/time.
 * A deadline task has both a description and a due date.
 */
public class Deadline extends Task {

    protected LocalDate by;

    /**
     * Creates a new deadline with the given description and due date.
     *
     * @param description The description of the deadline.
     * @param by The due date/time of the deadline.
     * @throws LebronException If the date format is invalid.
     */
    public Deadline(String description, String by) throws LebronException {
        super(description);

        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new LebronException("Not sure what that date means my guy, "
                    + "try formatting your dates in YYYY-MM-DD");
        }
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by.toString();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
