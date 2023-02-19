package edu.kit.informatik.uxnvp.cardgame.model;

/**
 * Different types of animals that the player needs to fight against in the card game.
 * @author Max Schweikart
 * @version 1.0
 */
public enum AnimalType {
    /**
     * A spider.
     */
    SPIDER("spider", Die.FOUR),
    /**
     * A snake.
     */
    SNAKE("snake", Die.SIX),
    /**
     * A tiger.
     */
    TIGER("tiger", Die.EIGHT);

    /**
     * The unique code name of this animal.
     */
    private final String codeName;
    /**
     * The die that needs to be rolled in a fight against this animal.
     */
    private final Die die;

    /**
     * Constructs an animal type with all of its attributes.
     * @param codeName the unique code name of this animal.
     * @param die the die that needs to be rolled in a fight against this animal.
     */
    AnimalType(String codeName, Die die) {
        this.codeName = codeName;
        this.die = die;
    }

    /**
     * Returns the unique code name of this animal.
     * @return the unique code name of this animal.
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * Returns the die that needs to be rolled in a fight against this animal.
     * @return the die that needs to be rolled in a fight against this animal.
     */
    public Die getDie() {
        return die;
    }
}
