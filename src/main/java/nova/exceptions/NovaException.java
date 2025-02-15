package nova.exceptions;

/**
 * Represents a custom exception made for the Nova chatbot
 *
 * @author Shanyey
 */
public class NovaException extends Exception {
    public NovaException() {}
    /**
     * Constructs a new NovaException with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public NovaException(String message) {
        super(message);
    }


}
