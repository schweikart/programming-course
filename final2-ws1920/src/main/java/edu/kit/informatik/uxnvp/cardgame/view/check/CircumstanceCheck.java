package edu.kit.informatik.uxnvp.cardgame.view.check;

import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;

/**
 * Checks for a certain circumstance.
 * @author Max Schweikart
 * @version 1.0
 */
public interface CircumstanceCheck {
    /**
     * Checks for a certain circumstance and prints and error if the circumstance is not met.
     * @param cli the cli instance to check against.
     * @return true if the circumstance is met and false if it is not.
     */
    boolean checkCircumstance(CardGameCLI cli);
}
