package lebron.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lebron.LebronException;

/**
 * Represents a task that starts and ends at specific date/times.
 * An event task has a description, a start time, and an end time.
 */
public class Event extends Task {

    protected LocalDate from;
    protected LocalDate to;

    /**
     * Creates a new event with the given description, start and end times.
     *
     * @param description The description of the event.
     * @param from The start date/time of the event.
     * @param to The end date/time of the event.
     * @throws LebronException If the date format is invalid.
     */
    public Event(String description, String from, String to) throws LebronException {
        super(description);

        try {
            this.from = LocalDate.parse(from);
            this.to = LocalDate.parse(to);
        } catch (DateTimeParseException e) {
            throw new LebronException("Not sure what that date means my guy, "
                    + "try formatting your dates in YYYY-MM-DD");
        }

        if (this.to.isBefore(this.from)) {
            throw new LebronException("Bruh, the event can't end before it starts.");
        }
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | "
                + from.toString() + " | " + to.toString();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
