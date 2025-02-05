package nova.parser;

import nova.exceptions.NovaException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parseBySpace_exceptionThrown() {
        try {
            Parser p = new Parser();
            String invalidInput = "todo";
            p.parseBySpace(invalidInput);
        } catch (NovaException e) {
            assertEquals("too little arguments or invalid command", e.getMessage());
        }
    }

    @Test
    public void parseBySpace_success() {
        Parser p = new Parser();
        String[] words;
        try {
            String correctInput = "todo read book";
            words = p.parseBySpace(correctInput);
            assertEquals("todo", words[0]);
            assertEquals("read book", words[1]);
        } catch (NovaException e) {
            throw new RuntimeException(e);
        }

    }

}
