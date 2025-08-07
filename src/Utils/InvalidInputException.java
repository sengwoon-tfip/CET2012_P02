package Utils;

/**
 * Exception thrown to indicate that user input is invalid.
 *
 * <p>This runtime exception is typically used in command classes to signal
 * input validation failures, such as invalid parameter formats or out-of-
 * bounds indices.</p>
 */
public class InvalidInputException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidInputException} with the specified
     * detail message.
     *
     * @param message the detail message explaining the reason for the error
     */
    public InvalidInputException(String message) {
        super(message);
    }
}