/**
 * Represents a todo task without any date/time attached.
 */
public class Todo extends Task {

    /**
     * Creates a new todo with the given description.
     *
     * @param description The description of the todo.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
