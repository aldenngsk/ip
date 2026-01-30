package sixseven;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void parse_bye_returnsByeCommand() throws DukeException {
        ParseResult r = Parser.parse("bye");
        assertEquals("bye", r.getCommand());
    }

    @Test
    public void parse_list_returnsListCommand() throws DukeException {
        ParseResult r = Parser.parse("list");
        assertEquals("list", r.getCommand());
    }

    @Test
    public void parse_findWithKeyword_returnsFindWithDescription() throws DukeException {
        ParseResult r = Parser.parse("find book");
        assertEquals("find", r.getCommand());
        assertEquals("book", r.getDescription());
    }

    @Test
    public void parse_todoWithDescription_returnsTodoWithDescription() throws DukeException {
        ParseResult r = Parser.parse("todo read book");
        assertEquals("todo", r.getCommand());
        assertEquals("read book", r.getDescription());
    }

    @Test
    public void parse_todoEmpty_throwsDukeException() {
        assertThrows(DukeException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void parse_todoOnlyWhitespace_throwsDukeException() {
        assertThrows(DukeException.class, () -> Parser.parse("todo   "));
    }

    @Test
    public void parse_markValidIndex_returnsMarkWithIndex() throws DukeException {
        ParseResult r = Parser.parse("mark 1");
        assertEquals("mark", r.getCommand());
        assertEquals(0, r.getIndex());
    }

    @Test
    public void parse_markInvalidIndex_throwsDukeException() {
        assertThrows(DukeException.class, () -> Parser.parse("mark abc"));
    }

    @Test
    public void parse_deleteValidIndex_returnsDeleteWithIndex() throws DukeException {
        ParseResult r = Parser.parse("delete 2");
        assertEquals("delete", r.getCommand());
        assertEquals(1, r.getIndex());
    }

    @Test
    public void parse_deadlineValid_returnsDeadlineWithDescriptionAndDate() throws DukeException {
        ParseResult r = Parser.parse("deadline return book /by 2019-12-02");
        assertEquals("deadline", r.getCommand());
        assertEquals("return book", r.getDescription());
        assertEquals(java.time.LocalDate.of(2019, 12, 2), r.getByDate());
    }

    @Test
    public void parse_deadlineMissingBy_throwsDukeException() {
        assertThrows(DukeException.class, () -> Parser.parse("deadline return book"));
    }

    @Test
    public void parse_deadlineInvalidDateFormat_throwsDukeException() {
        assertThrows(DukeException.class, () -> Parser.parse("deadline return book /by not-a-date"));
    }

    @Test
    public void parse_eventValid_returnsEventWithFromAndTo() throws DukeException {
        ParseResult r = Parser.parse("event meeting /from 2pm /to 4pm");
        assertEquals("event", r.getCommand());
        assertEquals("meeting", r.getDescription());
        assertEquals("2pm", r.getFrom());
        assertEquals("4pm", r.getTo());
    }

    @Test
    public void parse_eventMissingFromTo_throwsDukeException() {
        assertThrows(DukeException.class, () -> Parser.parse("event meeting"));
    }

    @Test
    public void parse_emptyInput_throwsDukeException() {
        assertThrows(DukeException.class, () -> Parser.parse(""));
    }

    @Test
    public void parse_unknownCommand_throwsDukeException() {
        assertThrows(DukeException.class, () -> Parser.parse("unknown thing"));
    }
}
