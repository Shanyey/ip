package nova.ui;

/**
 * The Ui class is responsible for formatting messages
 * for display to the user.
 */
public class Ui {
    /**
     * Processes the given message and returns a formatted string.
     *
     * @param message the message to process and format
     * @return a formatted message string based on the content of the input
     */
    public String returnMessage(String message) {
        if (message.contains("ERROR: ")) {
            return "BOII! " + message.replace("ERROR: ", "") + ".\nType help for more information.";
        }
        return "Your wish is my command UwU\n" + message;
    }
}
