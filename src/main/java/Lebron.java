import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the Lebron chatbot application.
 */
public class Lebron {
    private static ArrayList<String> itemList = new ArrayList<>();

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
        } else {
            addToList(input);
        }
        System.out.println("---------------------------");
    }

    private static void addToList(String item) {
        itemList.add(item);
        System.out.println("added: " + item);
    }

    private static void showList() {
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println((i + 1) + ". " + itemList.get(i));
        }
    }
}
