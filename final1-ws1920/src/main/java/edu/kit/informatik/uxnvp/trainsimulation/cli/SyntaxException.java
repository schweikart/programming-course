package edu.kit.informatik.uxnvp.trainsimulation.cli;

/**
 * A syntax exception that is thrown if a text input does not match the syntax of a command or placeholder.
 * @author Max Schweikart
 * @version 1.0
 */
public class SyntaxException extends Exception {
    /**
     * Constructs a new exception with the specified message.
     * @param message a description for the cause of this exception.
     */
    public SyntaxException(String message) {
        super(message);
    }
}
