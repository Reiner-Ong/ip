package lebron;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interface interactions for the Lebron chatbot.
 * This class deals with reading user input and displaying messages to the user.
 */
public class Ui {
    private static final String LINE = "---------------------------";
    private Scanner scanner;

    /**
     * Creates a new Ui instance with a Scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the chatbot starts.
     */
    public void showWelcome() {
        showLine();
        System.out.println("Yo, what's good! I'm King James.");
        System.out.println("Let's get this W. What you need?");
        showLine();
    }

    /**
     * Displays the goodbye message when the chatbot exits.
     */
    public void showGoodbye() {
        System.out.println("Alright, I'm out. Stay locked in, we got more wins to chase!");
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("Ayo that's a brick! " + message);
    }

    /**
     * Displays an error message when loading fails.
     *
     * @param message The error message to display.
     */
    public void showLoadingError(String message) {
        System.out.println("Ayo, couldn't load the save file: " + message);
        System.out.println("Starting fresh!");
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task The task that was added.
     * @param size The total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println("Locked in! I added this to the game plan:");
        System.out.println("  " + task);
        System.out.println("Now you got " + size + " tasks on the board.");
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param size The total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int size) {
        System.out.println("Traded away! I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you got " + size + " tasks on the board.");
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("That's a W! Task complete:");
        System.out.println("  " + task);
    }

    /**
     * Displays a message when a task is unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("Aight, we running it back. Task unmarked:");
        System.out.println("  " + task);
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Here's the game plan:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Reads a command from the user.
     *
     * @return The user's input as a String.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}
