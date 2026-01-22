import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the Lebron chatbot application.
 */
public class Lebron {
    private static ArrayList<Task> itemList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("---------------------------");
        System.out.println("Hello! I'm Lebron James");
        System.out.println("What can I do for you?");
        System.out.println("---------------------------");
        
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            respondToInput(input);
            if (input.equals("bye")) {
                break;
            }
        }

        scanner.close();
    }

    private static void respondToInput(String input) {
        System.out.println("---------------------------");
        try {
            processCommand(input);
        } catch (LebronException e) {
            System.out.println("OOPS!!! " + e.getMessage());
        }
        System.out.println("---------------------------");
    }

    private static void processCommand(String input) throws LebronException {
        if (input.equals("bye")) {
            System.out.println("Bye. Hope to see you again soon!");
        } else if (input.equals("list")) {
            showList();
        } else if (input.startsWith("mark")) {
            handleMark(input);
        } else if (input.startsWith("unmark")) {
            handleUnmark(input);
        } else if (input.startsWith("todo")) {
            handleTodo(input);
        } else if (input.startsWith("deadline")) {
            handleDeadline(input);
        } else if (input.startsWith("event")) {
            handleEvent(input);
        } else {
            throw new LebronException("I don't know what '" + input + "' means. "
                    + "Try: todo, deadline, event, list, mark, unmark, or bye.");
        }
    }

    private static void handleTodo(String input) throws LebronException {
        if (input.equals("todo") || input.equals("todo ")) {
            throw new LebronException("The description of a todo cannot be empty. "
                    + "Usage: todo <description>");
        }
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new LebronException("The description of a todo cannot be empty. "
                    + "Usage: todo <description>");
        }
        addTodo(description);
    }

    private static void handleDeadline(String input) throws LebronException {
        if (input.equals("deadline") || input.equals("deadline ")) {
            throw new LebronException("The description of a deadline cannot be empty. "
                    + "Usage: deadline <description> /by <date>");
        }
        String content = input.substring(9).trim();
        if (!content.contains("/by")) {
            throw new LebronException("A deadline needs a due date. "
                    + "Usage: deadline <description> /by <date>");
        }
        String[] parts = content.split(" ?/by ?", 2);
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new LebronException("The description of a deadline cannot be empty. "
                    + "Usage: deadline <description> /by <date>");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LebronException("The due date of a deadline cannot be empty. "
                    + "Usage: deadline <description> /by <date>");
        }
        addDeadline(description, parts[1].trim());
    }

    private static void handleEvent(String input) throws LebronException {
        if (input.equals("event") || input.equals("event ")) {
            throw new LebronException("The description of an event cannot be empty. "
                    + "Usage: event <description> /from <start> /to <end>");
        }
        String content = input.substring(6).trim();
        if (!content.contains("/from")) {
            throw new LebronException("An event needs a start time. "
                    + "Usage: event <description> /from <start> /to <end>");
        }
        if (!content.contains("/to")) {
            throw new LebronException("An event needs an end time. "
                    + "Usage: event <description> /from <start> /to <end>");
        }
        String[] parts = content.split(" ?/from ?| ?/to ?", 3);
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new LebronException("The description of an event cannot be empty. "
                    + "Usage: event <description> /from <start> /to <end>");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LebronException("The start time of an event cannot be empty. "
                    + "Usage: event <description> /from <start> /to <end>");
        }
        if (parts.length < 3 || parts[2].trim().isEmpty()) {
            throw new LebronException("The end time of an event cannot be empty. "
                    + "Usage: event <description> /from <start> /to <end>");
        }
        addEvent(description, parts[1].trim(), parts[2].trim());
    }

    private static void handleMark(String input) throws LebronException {
        if (input.equals("mark") || input.equals("mark ")) {
            throw new LebronException("Please specify which task to mark. "
                    + "Usage: mark <task number>");
        }
        String numberStr = input.substring(5).trim();
        int index = parseTaskNumber(numberStr) - 1;
        validateTaskIndex(index);
        markItemAsDone(index);
    }

    private static void handleUnmark(String input) throws LebronException {
        if (input.equals("unmark") || input.equals("unmark ")) {
            throw new LebronException("Please specify which task to unmark. "
                    + "Usage: unmark <task number>");
        }
        String numberStr = input.substring(7).trim();
        int index = parseTaskNumber(numberStr) - 1;
        validateTaskIndex(index);
        markItemAsNotDone(index);
    }

    private static int parseTaskNumber(String numberStr) throws LebronException {
        try {
            return Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            throw new LebronException("'" + numberStr + "' is not a valid number. "
                    + "Please enter a task number (e.g., 1, 2, 3).");
        }
    }

    private static void validateTaskIndex(int index) throws LebronException {
        if (itemList.isEmpty()) {
            throw new LebronException("Your task list is empty. Add some tasks first!");
        }
        if (index < 0 || index >= itemList.size()) {
            throw new LebronException("Task number " + (index + 1) + " does not exist. "
                    + "You have " + itemList.size() + " task(s). "
                    + "Please enter a number between 1 and " + itemList.size() + ".");
        }
    }

    private static void addTodo(String description) {
        Task newTask = new Todo(description);
        itemList.add(newTask);
        printTaskAdded(newTask);
    }

    private static void addDeadline(String description, String by) {
        Task newTask = new Deadline(description, by);
        itemList.add(newTask);
        printTaskAdded(newTask);
    }

    private static void addEvent(String description, String from, String to) {
        Task newTask = new Event(description, from, to);
        itemList.add(newTask);
        printTaskAdded(newTask);
    }

    private static void printTaskAdded(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + itemList.size() + " tasks in the list.");
    }

    private static void showList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println((i + 1) + "." + itemList.get(i));
        }
    }

    private static void markItemAsDone(int index) {
        Task item = itemList.get(index);
        item.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + item);
    }

    private static void markItemAsNotDone(int index) {
        Task item = itemList.get(index);
        item.markAsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + item);
    }
}
