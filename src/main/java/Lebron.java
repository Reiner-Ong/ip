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
        if (input.equals("bye")) {
            System.out.println("Bye. Hope to see you again soon!");
        } else if (input.equals("list")) {
            showList();
        } else if (input.startsWith("mark ")) {
            int index = Integer.parseInt(input.substring(5)) - 1;
            markItemAsDone(index);
        } else if (input.startsWith("unmark ")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            markItemAsNotDone(index);
        } else {
            addToList(input);
        }
        System.out.println("---------------------------");
    }

    private static void addToList(String item) {
        Task newTask = new Task(item);
        itemList.add(newTask);
        System.out.println("added: " + newTask.getDescription());
    }

    private static void showList() {
        for (int i = 0; i < itemList.size(); i++) {
            Task item = itemList.get(i);
            System.out.println((i + 1) + "." + item.getStatusIcon()
                    + " " + item.getDescription());
        }
    }

    private static void markItemAsDone(int index) {
        if (index >= 0 && index < itemList.size()) {
            Task item = itemList.get(index);
            item.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + item.getStatusIcon() + " " + item.getDescription());
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void markItemAsNotDone(int index) {
        if (index >= 0 && index < itemList.size()) {
            Task item = itemList.get(index);
            item.markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + item.getStatusIcon() + " " + item.getDescription());
        } else {
            System.out.println("Invalid task number.");
        }
    }
}
