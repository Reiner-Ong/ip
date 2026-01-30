package lebron;

/**
 * Main class for the Lebron chatbot application.
 * This chatbot helps users manage their tasks with a basketball-themed personality.
 */
public class Lebron {
    private static final String FILE_PATH = "./src/main/java/data/lebron.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a new Lebron chatbot instance.
     *
     * @param filePath The path to the file for storing tasks.
     */
    public Lebron(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (LebronException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the chatbot main loop.
     */
    public void run() {
        ui.showWelcome();

        boolean isRunning = true;
        while (isRunning) {
            String input = ui.readCommand();
            ui.showLine();
            try {
                isRunning = processCommand(input);
            } catch (LebronException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }

        ui.close();
    }

    /**
     * Processes a user command and executes the appropriate action.
     *
     * @param input The user input string.
     * @return True if the chatbot should continue running, false to exit.
     * @throws LebronException If there is an error processing the command.
     */
    private boolean processCommand(String input) throws LebronException {
        String commandType = Parser.getCommandType(input);

        switch (commandType) {
        case "bye":
            ui.showGoodbye();
            return false;
        case "list":
            ui.showTaskList(tasks.getTasks());
            break;
        case "mark":
            handleMark(input);
            break;
        case "unmark":
            handleUnmark(input);
            break;
        case "todo":
            handleTodo(input);
            break;
        case "deadline":
            handleDeadline(input);
            break;
        case "event":
            handleEvent(input);
            break;
        case "delete":
            handleDelete(input);
            break;
        default:
            throw new LebronException("I don't know what '" + input + "' means, my guy. "
                    + "Try: todo, deadline, event, list, mark, unmark, delete, or bye.");
        }
        return true;
    }

    /**
     * Handles adding a todo task.
     *
     * @param input The user input string.
     * @throws LebronException If the format is invalid.
     */
    private void handleTodo(String input) throws LebronException {
        String description = Parser.parseTodo(input);
        Task task = new Todo(description);
        tasks.add(task);
        saveTasks();
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles adding a deadline task.
     *
     * @param input The user input string.
     * @throws LebronException If the format is invalid.
     */
    private void handleDeadline(String input) throws LebronException {
        String[] parsed = Parser.parseDeadline(input);
        Task task = new Deadline(parsed[0], parsed[1]);
        tasks.add(task);
        saveTasks();
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles adding an event task.
     *
     * @param input The user input string.
     * @throws LebronException If the format is invalid.
     */
    private void handleEvent(String input) throws LebronException {
        String[] parsed = Parser.parseEvent(input);
        Task task = new Event(parsed[0], parsed[1], parsed[2]);
        tasks.add(task);
        saveTasks();
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles marking a task as done.
     *
     * @param input The user input string.
     * @throws LebronException If the format is invalid.
     */
    private void handleMark(String input) throws LebronException {
        int index = Parser.parseTaskNumber(input, "mark");
        Task task = tasks.mark(index);
        saveTasks();
        ui.showTaskMarked(task);
    }

    /**
     * Handles unmarking a task.
     *
     * @param input The user input string.
     * @throws LebronException If the format is invalid.
     */
    private void handleUnmark(String input) throws LebronException {
        int index = Parser.parseTaskNumber(input, "unmark");
        Task task = tasks.unmark(index);
        saveTasks();
        ui.showTaskUnmarked(task);
    }

    /**
     * Handles deleting a task.
     *
     * @param input The user input string.
     * @throws LebronException If the format is invalid.
     */
    private void handleDelete(String input) throws LebronException {
        int index = Parser.parseTaskNumber(input, "delete");
        Task task = tasks.delete(index);
        saveTasks();
        ui.showTaskDeleted(task, tasks.size());
    }

    /**
     * Saves the current task list to storage.
     */
    private void saveTasks() {
        try {
            storage.save(tasks.getTasks());
        } catch (LebronException e) {
            ui.showError("Couldn't save: " + e.getMessage());
        }
    }

    /**
     * Main entry point for the Lebron chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Lebron(FILE_PATH).run();
    }
}
