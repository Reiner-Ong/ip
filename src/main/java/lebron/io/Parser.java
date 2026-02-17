package lebron.io;

import lebron.LebronException;
import lebron.command.AddCommand;
import lebron.command.Command;
import lebron.command.DeleteCommand;
import lebron.command.ExitCommand;
import lebron.command.FindCommand;
import lebron.command.ListCommand;
import lebron.command.MarkCommand;
import lebron.command.UnmarkCommand;
import lebron.task.Deadline;
import lebron.task.Event;
import lebron.task.Todo;

/**
 * Parses user input and extracts command information.
 * This class handles making sense of user commands and extracting relevant
 * data.
 */
public class Parser {

    /**
     * Parses the user input and returns the appropriate Command object.
     *
     * @param input The user input string.
     * @return The Command object to execute.
     * @throws LebronException If the command is invalid or has invalid arguments.
     */
    public static Command parse(String input) throws LebronException {
        String commandType = getCommandType(input);

        switch (commandType) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "mark":
            return new MarkCommand(parseTaskNumber(input, "mark"));
        case "unmark":
            return new UnmarkCommand(parseTaskNumber(input, "unmark"));
        case "delete":
            return new DeleteCommand(parseTaskNumber(input, "delete"));
        case "todo":
            return parseAddTodo(input);
        case "deadline":
            return parseAddDeadline(input);
        case "event":
            return parseAddEvent(input);
        case "find":
            return parseFindCommand(input);
        default:
            throw new LebronException("I don't know what '" + input + "' means, my guy. "
                    + "Try: todo, deadline, event, list, mark, unmark, delete, or bye.");
        }
    }

    /**
     * Parses the command type from user input.
     *
     * @param input The user input string.
     * @return The command type as a String.
     */
    private static String getCommandType(String input) {
        String[] parts = input.split(" ", 2);
        return parts[0].toLowerCase();
    }

    /**
     * Parses a todo command and returns the corresponding AddCommand.
     *
     * @param input The user input string.
     * @return The AddCommand containing the parsed Todo task.
     * @throws LebronException If the description is empty.
     */
    private static Command parseAddTodo(String input) throws LebronException {
        if (input.equals("todo")) {
            throw new LebronException("You can't score without the ball! "
                    + "Give me a description: todo <description>");
        }
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new LebronException("You can't score without the ball! "
                    + "Give me a description: todo <description>");
        }
        return new AddCommand(new Todo(description));
    }

    /**
     * Parses a deadline command and returns the corresponding AddCommand.
     *
     * @param input The user input string.
     * @return The AddCommand containing the parsed Deadline task.
     * @throws LebronException If the format is invalid.
     */
    private static Command parseAddDeadline(String input) throws LebronException {
        if (input.equals("deadline")) {
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
        return new AddCommand(new Deadline(description, parts[1].trim()));
    }

    /**
     * Parses an event command and returns the corresponding AddCommand.
     *
     * @param input The user input string.
     * @return The AddCommand containing the parsed Event task.
     * @throws LebronException If the format is invalid.
     */
    private static Command parseAddEvent(String input) throws LebronException {
        if (input.equals("event")) {
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
        return new AddCommand(new Event(description, parts[1].trim(), parts[2].trim()));
    }

    /**
     * Parses a task number from a mark/unmark/delete command.
     *
     * @param input   The user input string.
     * @param command The command name (mark, unmark, or delete).
     * @return The task index (0-based).
     * @throws LebronException If the format is invalid.
     */
    private static int parseTaskNumber(String input, String command) throws LebronException {
        int commandLength = command.length();
        if (input.equals(command) || input.equals(command + " ")) {
            throw new LebronException(getTaskNumberError(command));
        }
        String numberStr = input.substring(commandLength + 1).trim();
        try {
            return Integer.parseInt(numberStr) - 1;
        } catch (NumberFormatException e) {
            throw new LebronException("'" + numberStr + "' ain't a number! "
                    + "Give me a real number like 1, 2, or 3.");
        }
    }

    /**
     * Parses a find command and returns the corresponding FindCommand.
     *
     * @param input The user input string.
     * @return The FindCommand containing the parsed keyword.
     * @throws LebronException If the keyword is empty.
     */
    private static Command parseFindCommand(String input) throws LebronException {
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new LebronException("Gotta give me something to look for! "
                    + "Try: find <keyword>");
        }
        return new FindCommand(keyword);
    }

    /**
     * Returns the appropriate error message for missing task number.
     *
     * @param command The command name.
     * @return The error message.
     */
    private static String getTaskNumberError(String command) {
        switch (command) {
            case "mark":
                return "Which play we running? Tell me the task number: mark <number>";
            case "unmark":
                return "Which one we taking back? Tell me the task number: unmark <number>";
            case "delete":
                return "Who we cutting from the roster? Tell me the task number: delete <number>";
            default:
                return "Tell me the task number!";
        }
    }
}
