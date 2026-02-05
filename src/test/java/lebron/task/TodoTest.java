package lebron.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    public void constructor_validDescription_createsTask() {
        Todo todo = new Todo("Read book");
        assertEquals("[ ] Read book", todo.toString().substring(3));
    }

    @Test
    public void toString_unmarkedTask_correctFormat() {
        Todo todo = new Todo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    public void toString_markedTask_correctFormat() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    public void toFileString_unmarkedTask_correctFormat() {
        Todo todo = new Todo("Read book");
        assertEquals("T | 0 | Read book", todo.toFileString());
    }

    @Test
    public void toFileString_markedTask_correctFormat() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        assertEquals("T | 1 | Read book", todo.toFileString());
    }

    @Test
    public void markAsDone_unmarkedTask_becomesMarked() {
        Todo todo = new Todo("Read book");
        assertEquals("[ ]", todo.getStatusIcon());
        todo.markAsDone();
        assertEquals("[X]", todo.getStatusIcon());
    }

    @Test
    public void markAsNotDone_markedTask_becomesUnmarked() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        assertEquals("[X]", todo.getStatusIcon());
        todo.markAsNotDone();
        assertEquals("[ ]", todo.getStatusIcon());
    }
}
