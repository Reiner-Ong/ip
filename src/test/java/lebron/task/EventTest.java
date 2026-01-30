package lebron.task;

import org.junit.jupiter.api.Test;

import lebron.LebronException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventTest {

    @Test
    public void constructor_validDates_createsEvent() throws LebronException {
        Event event = new Event("Team meeting", "2024-12-01", "2024-12-02");
        assertEquals("[E][ ] Team meeting (from: Dec 1 2024 to: Dec 2 2024)", event.toString());
    }

    @Test
    public void constructor_sameDates_createsEvent() throws LebronException {
        Event event = new Event("Conference", "2024-12-15", "2024-12-15");
        assertEquals("[E][ ] Conference (from: Dec 15 2024 to: Dec 15 2024)", event.toString());
    }

    @Test
    public void constructor_invalidFromDate_exceptionThrown() {
        assertThrows(LebronException.class, () -> new Event("Meeting", "tomorrow", "2024-12-02"));
    }

    @Test
    public void constructor_invalidToDate_exceptionThrown() {
        assertThrows(LebronException.class, () -> new Event("Meeting", "2024-12-01", "next week"));
    }

    @Test
    public void constructor_endBeforeStart_exceptionThrown() {
        assertThrows(LebronException.class, () -> new Event("Meeting", "2024-12-10", "2024-12-05"));
    }

    @Test
    public void toString_unmarkedEvent_correctFormat() throws LebronException {
        Event event = new Event("Workshop", "2024-06-01", "2024-06-03");
        assertEquals("[E][ ] Workshop (from: Jun 1 2024 to: Jun 3 2024)", event.toString());
    }

    @Test
    public void toString_markedEvent_correctFormat() throws LebronException {
        Event event = new Event("Workshop", "2024-06-01", "2024-06-03");
        event.markAsDone();
        assertEquals("[E][X] Workshop (from: Jun 1 2024 to: Jun 3 2024)", event.toString());
    }

    @Test
    public void toFileString_unmarkedEvent_correctFormat() throws LebronException {
        Event event = new Event("Meeting", "2024-12-01", "2024-12-02");
        assertEquals("E | 0 | Meeting | 2024-12-01 | 2024-12-02", event.toFileString());
    }

    @Test
    public void toFileString_markedEvent_correctFormat() throws LebronException {
        Event event = new Event("Meeting", "2024-12-01", "2024-12-02");
        event.markAsDone();
        assertEquals("E | 1 | Meeting | 2024-12-01 | 2024-12-02", event.toFileString());
    }

    @Test
    public void markAsDone_unmarkedEvent_becomesMarked() throws LebronException {
        Event event = new Event("Meeting", "2024-12-01", "2024-12-02");
        assertEquals("[ ]", event.getStatusIcon());
        event.markAsDone();
        assertEquals("[X]", event.getStatusIcon());
    }
}
