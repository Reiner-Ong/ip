package lebron;

import java.util.ArrayList;

import lebron.command.Command;
import lebron.io.Parser;
import lebron.io.ResponseFormatter;
import lebron.storage.Storage;
import lebron.task.TaskList;

/**
 * Main class for the Lebron chatbot application.
 * This chatbot helps users manage their tasks with a basketball-themed
 * personality.
 */
public class Lebron {
    private final Storage storage;
    private TaskList tasks;
    private String loadingError;
    private boolean isExit;

    /**
     * Creates a new Lebron chatbot instance.
     *
     * @param filePath The path to the file for storing tasks.
     */
    public Lebron(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (LebronException e) {
            loadingError = e.getMessage();
            tasks = new TaskList();
        }
    }

    /**
     * Returns the loading error message, if any occurred during initialization.
     *
     * @return The error message, or null if no error occurred.
     */
    public String getLoadingError() {
        return loadingError;
    }

    /**
     * Returns whether the last command was an exit command.
     *
     * @return True if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Returns the welcome message displayed when the chatbot starts.
     *
     * @return The welcome message string.
     */
    public String getWelcomeMessage() {
        return "Yo, what's good! I'm King James."
                + "\nLet's get this W. What you need?";
    }

    /**
     * Processes a user command and returns the chatbot's response.
     *
     * @param fullCommand The full command string entered by the user.
     * @return The formatted response string.
     */
    public String getResponse(String fullCommand) {
        ArrayList<String> responses;
        try {
            Command c = Parser.parse(fullCommand);
            responses = c.execute(tasks, storage);
            isExit = c.isExit();
        } catch (LebronException e) {
            responses = ResponseFormatter.respondError(e.getMessage());
        }

        return String.join("\n", responses);
    }
}
