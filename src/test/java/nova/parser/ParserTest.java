package nova.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nova.exceptions.NovaException;


public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    public void testParseBySpaceValidInput() throws NovaException {
        String input = "todo read book";
        String[] result = parser.parseBySpace(input);
        assertEquals(2, result.length, "Expected exactly two parts.");
        assertEquals("todo", result[0], "First part should be the command.");
        assertEquals("read book", result[1], "Second part should be the argument.");
    }

    @Test
    public void testParseBySpaceInsufficientArguments() {
        String input = "todo";
        NovaException exception = assertThrows(NovaException.class, () -> {
            parser.parseBySpace(input);
        });
        assertEquals("ERROR: too little arguments or invalid command", exception.getMessage());
    }

    @Test
    public void testParseBySpaceWithExtraWhitespace() throws NovaException {
        String input = "   deadline    finish assignment   ";
        String[] result = parser.parseBySpace(input);

        // After trim and splitting, result[0] = "deadline", result[1] = "   finish assignment"
        assertEquals("deadline", result[0]);
        assertEquals("finish assignment", result[1].trim());
    }

    @Test
    public void testSplitBySlashMultipleTokens() {
        String input = "first/second/third";
        String[] result = parser.splitBySlash(input);
        assertEquals(3, result.length, "Expected three tokens split by '/'");
        assertEquals("first", result[0]);
        assertEquals("second", result[1]);
        assertEquals("third", result[2]);
    }
}
