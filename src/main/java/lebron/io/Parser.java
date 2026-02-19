package lebron.io;

import lebron.LebronException;
import lebron.command.AddCommand;
import lebron.command.CloneCommand;
import lebron.command.Command;
import lebron.command.DeleteCommand;
import lebron.command.ExitCommand;
import lebron.command.FindCommand;
import lebron.command.ListCommand;
import lebron.command.MarkCommand;
import lebron.command.UnmarkCommand;
import lebron.command.UpdateCommand;
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
            return parseTodo(input);
        case "deadline":
            return parseDeadline(input);
        case "event":
            return parseEvent(input);
        case "find":
            return parseFindCommand(input);
        case "clone":
            return new CloneCommand(parseTaskNumber(input, "clone"));
        case "update":
            return parseUpdateCommand(input);
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
    private static Command parseTodo(String input) throws LebronException {
        assert input.startsWith("todo") : "parseTodo should only be called for todo commands";

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
    private static Command parseDeadline(String input) throws LebronException {
        assert input.startsWith("deadline") : "parseDeadline should only be called for deadline commands";

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
        assert parts.length == 2 : "parseDeadline should split into exactly 2 parts";

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
    private static Command parseEvent(String input) throws LebronException {
        assert input.startsWith("event") : "parseEvent should only be called for event commands";

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
        assert parts.length == 3 : "parseEvent should split into exactly 3 parts";

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
     * Parses an update command and returns the corresponding UpdateCommand.
     * Fields not provided by the user are passed as null, meaning they will be
     * preserved from the original task.
     *
     * @param input The user input string.
     * @return The UpdateCommand with the parsed index and field values.
     * @throws LebronException If the format is invalid.
     */
    private static Command parseUpdateCommand(String input) throws LebronException {
        assert input.startsWith("update") : "parseUpdateCommand should only be called for update commands";

        if (input.equals("update") || input.strip().equals("update")) {
            throw new LebronException("Who are we updating? "
                    + "Try: update <number> [new description] [/by <date>] "
                    + "or [/from <date> /to <date>]");
        }

        String content = input.substring(7).trim();
        String[] firstSplit = content.split(" ", 2);

        int index;
        try {
            index = Integer.parseInt(firstSplit[0]) - 1;
        } catch (NumberFormatException e) {
            throw new LebronException("'" + firstSplit[0] + "' ain't a number! "
                    + "Give me a real number like 1, 2, or 3.");
        }

        if (firstSplit.length < 2 || firstSplit[1].trim().isEmpty()) {
            throw new LebronException("What are we changing? "
                    + "Provide at least one field to update.");
        }

        String rest = firstSplit[1].trim();
        String newDescription = null;
        String newBy = null;
        String newFrom = null;
        String newTo = null;

        if (rest.contains("/from") && rest.contains("/to")) {
            String[] parts = rest.split(" ?/from ?| ?/to ?", 3);
            if (!parts[0].trim().isEmpty()) {
                newDescription = parts[0].trim();
            }
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new LebronException("When's tip-off? I need a start date after /from.");
            }
            if (parts.length < 3 || parts[2].trim().isEmpty()) {
                throw new LebronException("When's the final buzzer? I need an end date after /to.");
            }
            newFrom = parts[1].trim();
            newTo = parts[2].trim();
        } else if (rest.contains("/by")) {
            String[] parts = rest.split(" ?/by ?", 2);
            if (!parts[0].trim().isEmpty()) {
                newDescription = parts[0].trim();
            }
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new LebronException("When's the buzzer? I need a date after /by.");
            }
            newBy = parts[1].trim();
        } else {
            newDescription = rest;
        }

        return new UpdateCommand(index, newDescription, newBy, newFrom, newTo);
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
        case "clone":
            return "Who we tryna secretly clone? Tell me the task number: clone <number>";
        default:
            return "Tell me the task number!";
        }
    }
}
