package edu.kit.informatik.uxnvp.cardgame.model;

/**
 * Different types of catastrophes that can occur in the card game.<br/>
 * Note: this enum currently only contains one catastrophe type. However, the assignment hints that there could be
 * multiple catastrophe types so this enum was created for extendability.
 * @author Max Schweikart
 * @version 1.0
 */
public enum CatastropheType {
    /**
     * A thunderstorm which destroys unprotected resources and fireplaces.
     */
    THUNDERSTORM("thunderstorm");

    /**
     * The unique code name of this catastrophe type.
     */
    private final String codeName;

    /**
     * Constructs a catastrophe with all of its attributes.
     * @param codeName the unique code name of this catastrophe type.
     */
    CatastropheType(String codeName) {
        this.codeName = codeName;
    }

    /**
     * Returns the unique code name of this catastrophe type.
     * @return the unique code name of this catastrophe type.
     */
    public String getCodeName() {
        return codeName;
    }
}
