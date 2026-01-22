import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the Lebron chatbot application.
 */
public class Lebron {
    private static ArrayList<Task> itemList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("---------------------------");
        System.out.println("Yo, what's good! I'm King James.");
        System.out.println("Let's get this W. What you need?");
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
            System.out.println("Ayo that's a brick! " + e.getMessage());
        }
        System.out.println("---------------------------");
    }

    private static void processCommand(String input) throws LebronException {
        if (input.equals("bye")) {
            System.out.println("Alright, I'm out. Stay locked in, we got more wins to chase!");
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
            throw new LebronException("I don't know what '" + input + "' means, my guy. "
                    + "Try: todo, deadline, event, list, mark, unmark, or bye.");
        }
    }

    private static void handleTodo(String input) throws LebronException {
        if (input.equals("todo") || input.equals("todo ")) {
            throw new LebronException("You can't score without the ball! "
                    + "Give me a description: todo <description>");
        }
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new LebronException("You can't score without the ball! "
                    + "Give me a description: todo <description>");
        }
        addTodo(description);
    }

    private static void handleDeadline(String input) throws LebronException {
        if (input.equals("deadline") || input.equals("deadline ")) {
            throw new LebronException("Empty plays don't win championships! "
                    + "Try: deadline <description> /by <date>");
        }
        String content = input.substring(9).trim();
        if (!content.contains("/by")) {
            throw new LebronException("When's the buzzer? I need a deadline! "
                    + "Try: deadline <description> /by <date>");
        }
        String[] parts = content.split(" ?/by ?", 2);
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new LebronException("What's the play? Give me a description! "
                    + "Try: deadline <description> /by <date>");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LebronException("When's the buzzer? I need a due date! "
                    + "Try: deadline <description> /by <date>");
        }
        addDeadline(description, parts[1].trim());
    }

    private static void handleEvent(String input) throws LebronException {
        if (input.equals("event") || input.equals("event ")) {
            throw new LebronException("Can't show up to a game with no game plan! "
                    + "Try: event <description> /from <start> /to <end>");
        }
        String content = input.substring(6).trim();
        if (!content.contains("/from")) {
            throw new LebronException("When's tip-off? I need a start time! "
                    + "Try: event <description> /from <start> /to <end>");
        }
        if (!content.contains("/to")) {
            throw new LebronException("When's the final buzzer? I need an end time! "
                    + "Try: event <description> /from <start> /to <end>");
        }
        String[] parts = content.split(" ?/from ?| ?/to ?", 3);
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new LebronException("What's the event? Give me a description! "
                    + "Try: event <description> /from <start> /to <end>");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new LebronException("When's tip-off? I need a start time! "
                    + "Try: event <description> /from <start> /to <end>");
        }
        if (parts.length < 3 || parts[2].trim().isEmpty()) {
            throw new LebronException("When's the final buzzer? I need an end time! "
                    + "Try: event <description> /from <start> /to <end>");
        }
        addEvent(description, parts[1].trim(), parts[2].trim());
    }

    private static void handleMark(String input) throws LebronException {
        if (input.equals("mark") || input.equals("mark ")) {
            throw new LebronException("Which play we running? "
                    + "Tell me the task number: mark <number>");
        }
        String numberStr = input.substring(5).trim();
        int index = parseTaskNumber(numberStr) - 1;
        validateTaskIndex(index);
        markItemAsDone(index);
    }

    private static void handleUnmark(String input) throws LebronException {
        if (input.equals("unmark") || input.equals("unmark ")) {
            throw new LebronException("Which one we taking back? "
                    + "Tell me the task number: unmark <number>");
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
            throw new LebronException("'" + numberStr + "' ain't a number! "
                    + "Give me a real number like 1, 2, or 3.");
        }
    }

    private static void validateTaskIndex(int index) throws LebronException {
        if (itemList.isEmpty()) {
            throw new LebronException("The roster's empty! Add some tasks first, my guy.");
        }
        if (index < 0 || index >= itemList.size()) {
            throw new LebronException("Task " + (index + 1) + " doesn't exist! "
                    + "You got " + itemList.size() + " task(s). "
                    + "Pick a number between 1 and " + itemList.size() + ".");
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
        System.out.println("Locked in! I added this to the game plan:");
        System.out.println("  " + task);
        System.out.println("Now you got " + itemList.size() + " tasks on the board.");
    }

    private static void showList() {
        System.out.println("Here's the game plan:");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println((i + 1) + "." + itemList.get(i));
        }
    }

    private static void markItemAsDone(int index) {
        Task item = itemList.get(index);
        item.markAsDone();
        System.out.println("That's a W! Task complete:");
        System.out.println("  " + item);
    }

    private static void markItemAsNotDone(int index) {
        Task item = itemList.get(index);
        item.markAsNotDone();
        System.out.println("Aight, we running it back. Task unmarked:");
        System.out.println("  " + item);
    }
}
