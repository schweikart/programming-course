package edu.kit.informatik.uxnvp.cardgame;

import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;

/**
 * Entry point class for the card game cli application.
 * @author Max Schweikart
 * @version 1.0
 */
public final class Main {
    /**
     * Do not instantiate the Main class.
     * @throws AssertionError utility classes should not be instantiated.
     */
    private Main() {
        throw new AssertionError("Utility classes should not be instantiated.");
    }

    /**
     * Entry point for the card game cli application.
     * @param args command line arguments, will be ignored.
     */
    public static void main(String[] args) {
        CardGameCLI cli = new CardGameCLI();
        cli.startPromptLoop();
    }
}
