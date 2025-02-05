package nova.parser;

import nova.exceptions.NovaException;

/**
 * The Parser class provides utility methods for parsing user input.
 * It supports splitting input commands by spaces and slashes to extract meaningful components.
 */
public class Parser {

    /**
     * Splits a given action string by the first space encountered.
     * Ensures that the input contains at least two components.
     *
     * @param action The input string to be parsed.
     * @return An array of two elements, where the first element is the command and the second is the argument.
     * @throws NovaException If the input string does not contain enough arguments.
     */
    public String[] parseBySpace(String action) throws NovaException {
        action = action.trim();
        String[] splitAction = action.split(" ", 2);
        if (splitAction.length == 1) {
            throw new NovaException("too little arguments or invalid command");
        }
        return splitAction;
    }

    /**
     * Splits a given action string by the '/' character.
     * Commonly used to separate task details from date/time information.
     *
     * @param action The input string to be split.
     * @return An array of substrings split by the '/' delimiter.
     */
    public String[] splitBySlash(String action) {
        return action.split("/");
    }
}
