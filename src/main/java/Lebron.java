import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        System.out.println("---------------------------");
        System.out.println("Hello! I'm Lebron James");
        System.out.println("What can I do for you?");
        System.out.println("---------------------------");
        
        Scanner scanner = new Scanner(System.in);

        String input;
        while (true) {
            input = scanner.nextLine();
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
        } else {
            System.out.println(input);
        }
        System.out.println("---------------------------");
    }
}
