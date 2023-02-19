package edu.kit.informatik.uxnvp.cardgame.controller;

import edu.kit.informatik.uxnvp.cardgame.model.Die;

/**
 * A die rolling challenge for an endeavour situation. The player can win the game by succeeding in this challenge.
 * @author Max Schweikart
 * @version 1.0
 */
public class EndeavourChallenge extends DieRollingChallenge {
    private static final Die ENDEAVOUR_CHALLENGE_DIE = Die.SIX;

    /**
     * Constructs an endeavour challenge in a game.
     * @param game the game this challenge takes place in.
     */
    public EndeavourChallenge(CardGame game) {
        super(game, ENDEAVOUR_CHALLENGE_DIE);
    }

    @Override
    public void onSucceed() {
        getGame().win();
    }

    @Override
    public void onFail() {
        getGame().setPhase(Phase.SCAVENGE);
    }
}
