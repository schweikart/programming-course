package edu.kit.informatik.uxnvp.cardgame.model;

/**
 * Different types of resources that can be used to build items.
 * @author Max Schweikart
 * @version 1.0
 */
public enum ResourceType {
    /**
     * Wooden resource.
     */
    WOOD("wood"),
    /**
     * Metal resource.
     */
    METAL("metal"),
    /**
     * Plastic resource.
     */
    PLASTIC("plastic");

    /**
     * The unique code name of this resource type.
     */
    private final String codeName;

    /**
     * Constructs a resource type with all of its attributes.
     * @param codeName the unique code name of this resource type.
     */
    ResourceType(String codeName) {
        this.codeName = codeName;
    }

    /**
     * Returns the unique code name of this resource type.
     * @return the unique code name of this resource type.
     */
    public String getCodeName() {
        return codeName;
    }
}
