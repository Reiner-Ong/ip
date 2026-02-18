package lebron.io;

import java.util.ArrayList;

import lebron.task.Task;

/**
 * Formats response messages for the Lebron chatbot.
 */
public class ResponseFormatter {

    /**
     * Formats the goodbye message when the chatbot exits.
     *
     * @return The goodbye response lines.
     */
    public static ArrayList<String> replyGoodbye() {
        ArrayList<String> response = new ArrayList<>();
        response.add("Alright, I'm out. Stay locked in, we got more wins to chase!");
        return response;
    }

    /**
     * Formats an error message for the user.
     *
     * @param message The error message to format.
     * @return The error response lines.
     */
    public static ArrayList<String> respondError(String message) {
        ArrayList<String> response = new ArrayList<>();

        response.add("Ayo that's a brick! " + message);

        return response;
    }

    /**
     * Formats a message when a task is added.
     *
     * @param task The task that was added.
     * @param size The total number of tasks after adding.
     * @return The response lines.
     */
    public static ArrayList<String> respondTaskAdded(Task task, int size) {
        ArrayList<String> response = new ArrayList<>();

        response.add("Locked in! I added this to the game plan:");
        response.add("  " + task);
        response.add("Now you got " + size + " tasks on the board.");

        return response;
    }

    /**
     * Formats a message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param size The total number of tasks after deletion.
     * @return The response lines.
     */
    public static ArrayList<String> respondTaskDeleted(Task task, int size) {
        ArrayList<String> response = new ArrayList<>();

        response.add("Traded away! I've removed this task:");
        response.add("  " + task);
        response.add("Now you got " + size + " tasks on the board.");

        return response;
    }

    /**
     * Formats a message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     * @return The response lines.
     */
    public static ArrayList<String> respondTaskMarked(Task task) {
        ArrayList<String> response = new ArrayList<>();

        response.add("That's a W! Task complete:");
        response.add("  " + task);

        return response;
    }

    /**
     * Formats a message when a task is unmarked.
     *
     * @param task The task that was unmarked.
     * @return The response lines.
     */
    public static ArrayList<String> respondTaskUnmarked(Task task) {
        ArrayList<String> response = new ArrayList<>();

        response.add("Aight, we running it back. Task unmarked:");
        response.add("  " + task);

        return response;
    }

    /**
     * Formats the list of all tasks.
     *
     * @param tasks The list of tasks to format.
     * @return The response lines.
     */
    public static ArrayList<String> respondTaskList(ArrayList<Task> tasks) {
        ArrayList<String> response = new ArrayList<>();

        response.add("Here's the game plan:");
        for (int i = 0; i < tasks.size(); i++) {
            response.add((i + 1) + "." + tasks.get(i));
        }

        return response;
    }

    /**
     * Formats a message when a task is updated.
     *
     * @param oldTask The task before the update.
     * @param newTask The task after the update.
     * @return The response lines.
     */
    public static ArrayList<String> respondTaskUpdated(Task oldTask, Task newTask) {
        ArrayList<String> response = new ArrayList<>();

        response.add("Adjusted the game plan! Task updated:");
        response.add("  Before: " + oldTask);
        response.add("  After:  " + newTask);

        return response;
    }

    /**
     * Formats the list of found tasks.
     *
     * @param tasks The list of found tasks to format.
     * @return The response lines.
     */
    public static ArrayList<String> respondFoundTasks(ArrayList<Task> tasks) {
        ArrayList<String> response = new ArrayList<>();

        response.add("Found these tasks in the game plan:");
        for (int i = 0; i < tasks.size(); i++) {
            response.add((i + 1) + "." + tasks.get(i));
        }

        return response;
    }
}
