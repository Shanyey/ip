package nova.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UiTest {

    @Test
    void testReturnMessageWithError() {
        Ui ui = new Ui();
        String input = "ERROR: some error occurred";
        String expected = "BOII! some error occurred.\nType help for more information.";
        String actual = ui.returnMessage(input);
        assertEquals(expected, actual, "The method should format error messages correctly.");
    }

    @Test
    public void testReturnMessageWithoutError() {
        Ui ui = new Ui();
        String input = "Task added successfully";
        String expected = "Your wish is my command UwU\nTask added successfully";
        String actual = ui.returnMessage(input);
        assertEquals(expected, actual, "The method should format non-error messages correctly.");
    }
}