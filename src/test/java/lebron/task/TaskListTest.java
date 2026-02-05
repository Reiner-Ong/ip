package lebron.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lebron.LebronException;

public class TaskListTest {
    @Test
    public void get_emptyList_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(LebronException.class, () -> taskList.get(0));
    }

    @Test
    public void get_negativeIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task"));
        assertThrows(LebronException.class, () -> taskList.get(-1));
    }

    @Test
    public void get_indexOutOfBounds_exceptionThrown() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task"));
        assertThrows(LebronException.class, () -> taskList.get(5));
    }

    @Test
    public void delete_validIndex_taskRemoved() throws LebronException {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Task to delete");
        taskList.add(todo);
        Task deleted = taskList.delete(0);
        assertEquals(todo, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void delete_emptyList_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(LebronException.class, () -> taskList.delete(0));
    }

    @Test
    public void delete_invalidIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task"));
        assertThrows(LebronException.class, () -> taskList.delete(5));
    }

    @Test
    public void mark_validIndex_taskMarked() throws LebronException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task"));
        Task marked = taskList.mark(0);
        assertEquals("[X]", marked.getStatusIcon());
    }

    @Test
    public void mark_emptyList_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(LebronException.class, () -> taskList.mark(0));
    }

    @Test
    public void unmark_validIndex_taskUnmarked() throws LebronException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task"));
        taskList.mark(0);
        Task unmarked = taskList.unmark(0);
        assertEquals("[ ]", unmarked.getStatusIcon());
    }

    @Test
    public void unmark_emptyList_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(LebronException.class, () -> taskList.unmark(0));
    }

    @Test
    public void getTasks_returnsUnderlyingList() {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Task");
        taskList.add(todo);
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(todo, tasks.get(0));
    }
}
