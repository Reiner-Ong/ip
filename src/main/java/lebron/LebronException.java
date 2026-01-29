package lebron;

/**
 * Represents exceptions specific to the Lebron chatbot.
 * This exception is thrown when the chatbot encounters invalid user input
 * or errors during task operations.
 */
public class LebronException extends Exception {

    /**
     * Creates a new LebronException with the given message.
     *
     * @param message The error message.
     */
    public LebronException(String message) {
        super(message);
    }
}
