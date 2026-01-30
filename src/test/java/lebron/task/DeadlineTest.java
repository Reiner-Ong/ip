package lebron.task;

import org.junit.jupiter.api.Test;

import lebron.LebronException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineTest {

    @Test
    public void constructor_validDate_createsDeadline() throws LebronException {
        Deadline deadline = new Deadline("Submit report", "2024-12-31");
        assertEquals("[D][ ] Submit report (by: Dec 31 2024)", deadline.toString());
    }

    @Test
    public void constructor_invalidDateFormat_exceptionThrown() {
        assertThrows(LebronException.class, () -> new Deadline("Submit report", "tomorrow"));
        assertThrows(LebronException.class, () -> new Deadline("Submit report", "12-31-2024"));
        assertThrows(LebronException.class, () -> new Deadline("Submit report", "31/12/2024"));
    }

    @Test
    public void toString_unmarkedDeadline_correctFormat() throws LebronException {
        Deadline deadline = new Deadline("Submit report", "2024-01-15");
        assertEquals("[D][ ] Submit report (by: Jan 15 2024)", deadline.toString());
    }

    @Test
    public void toString_markedDeadline_correctFormat() throws LebronException {
        Deadline deadline = new Deadline("Submit report", "2024-01-15");
        deadline.markAsDone();
        assertEquals("[D][X] Submit report (by: Jan 15 2024)", deadline.toString());
    }

    @Test
    public void toFileString_unmarkedDeadline_correctFormat() throws LebronException {
        Deadline deadline = new Deadline("Submit report", "2024-12-31");
        assertEquals("D | 0 | Submit report | 2024-12-31", deadline.toFileString());
    }

    @Test
    public void toFileString_markedDeadline_correctFormat() throws LebronException {
        Deadline deadline = new Deadline("Submit report", "2024-12-31");
        deadline.markAsDone();
        assertEquals("D | 1 | Submit report | 2024-12-31", deadline.toFileString());
    }

    @Test
    public void markAsDone_unmarkedDeadline_becomesMarked() throws LebronException {
        Deadline deadline = new Deadline("Submit report", "2024-12-31");
        assertEquals("[ ]", deadline.getStatusIcon());
        deadline.markAsDone();
        assertEquals("[X]", deadline.getStatusIcon());
    }
}
