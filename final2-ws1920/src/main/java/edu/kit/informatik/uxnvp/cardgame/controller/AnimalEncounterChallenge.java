package edu.kit.informatik.uxnvp.cardgame.controller;

import edu.kit.informatik.uxnvp.cardgame.model.AnimalType;

/**
 * A die rolling challenge for a fight against an animal. The player loses his resources when losing this challenge.
 * @author Max Schweikart
 * @version 1.0
 */
public class AnimalEncounterChallenge extends DieRollingChallenge {
    /**
     * Constructs an encounter challenge in a game with an animal.
     * @param game the game this challenge takes place in.
     * @param animal the animal to fight against in this challenge.
     */
    public AnimalEncounterChallenge(CardGame game, AnimalType animal) {
        super(game, animal.getDie());
    }

    /**
     * Rolls the die to decide whether the player has succeeded in this challenge.<br/>
     * Note: In this challenge, the player gains a bonus based on the tools/weapons he has built.
     * @param rolledNumber the number that the player rolled.
     * @return true if the player succeeded and false otherwise.
     */
    @Override
    public boolean roll(int rolledNumber) {
        int bonus = getGame().getInventory().getDamageBonus();
        return super.roll(rolledNumber + bonus);
    }

    @Override
    public void onSucceed() {
        getGame().setPhase(Phase.SCAVENGE);
    }

    @Override
    public void onFail() {
        getGame().getInventory().loseResources();
        getGame().setPhase(Phase.SCAVENGE);
    }
}
