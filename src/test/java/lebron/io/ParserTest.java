package lebron.io;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import lebron.LebronException;
import lebron.command.AddCommand;
import lebron.command.Command;
import lebron.command.DeleteCommand;
import lebron.command.ExitCommand;
import lebron.command.ListCommand;
import lebron.command.MarkCommand;
import lebron.command.UnmarkCommand;


public class ParserTest {

    @Test
    public void parse_todoWithDescription_returnsAddCommand() throws LebronException {
        Command command = Parser.parse("todo Make a Free Throw");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void parse_todoEmpty_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("todo"));
        assertThrows(LebronException.class, () -> Parser.parse("todo "));
    }

    @Test
    public void parse_deadlineValid_returnsAddCommand() throws LebronException {
        Command command = Parser.parse("deadline Trade AD away /by 2024-12-31");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void parse_deadlineMissingBy_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("deadline Trade AD away"));
    }

    @Test
    public void parse_deadlineEmpty_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("deadline"));
        assertThrows(LebronException.class, () -> Parser.parse("deadline "));
    }

    @Test
    public void parse_deadlineMissingDescription_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("deadline /by 2024-12-31"));
    }

    @Test
    public void parse_deadlineMissingDate_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("deadline Submit report /by"));
        assertThrows(LebronException.class, () -> Parser.parse("deadline Submit report /by "));
    }

    @Test
    public void parse_deadlineInvalidDateFormat_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("deadline Sleep /by tomorrow"));
        assertThrows(LebronException.class, () -> Parser.parse("deadline Sleep /by 12-31-2024"));
    }

    @Test
    public void parse_eventValid_returnsAddCommand() throws LebronException {
        Command command = Parser.parse("event Taco Tuesday /from 2024-12-01 /to 2024-12-02");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void parse_eventMissingFrom_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("event Taco Tuesday /to 4pm"));
    }

    @Test
    public void parse_eventMissingTo_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("Taco Tuesday /from 2pm"));
    }

    @Test
    public void parse_eventEmpty_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("event"));
        assertThrows(LebronException.class, () -> Parser.parse("event "));
    }

    @Test
    public void parse_eventEndDateBeforeStartDate_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("event Taco Tuesday /from 2024-12-05 /to 2024-12-01"));
    }

    @Test
    public void parse_list_returnsListCommand() throws LebronException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void parse_bye_returnsExitCommand() throws LebronException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    public void parse_markWithNumber_returnsMarkCommand() throws LebronException {
        Command command = Parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void parse_markEmpty_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("mark"));
        assertThrows(LebronException.class, () -> Parser.parse("mark "));
    }

    @Test
    public void parse_markInvalidNumber_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("mark abc"));
    }

    @Test
    public void parse_unmarkWithNumber_returnsUnmarkCommand() throws LebronException {
        Command command = Parser.parse("unmark 1");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    public void parse_unmarkEmpty_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("unmark"));
    }

    @Test
    public void parse_deleteWithNumber_returnsDeleteCommand() throws LebronException {
        Command command = Parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void parse_deleteEmpty_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("delete"));
    }

    @Test
    public void parse_invalidCommand_exceptionThrown() {
        assertThrows(LebronException.class, () -> Parser.parse("invalid"));
        assertThrows(LebronException.class, () -> Parser.parse("blah blah"));
    }
}
