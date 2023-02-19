package edu.kit.informatik.uxnvp.trainsimulation.model.train;

import edu.kit.informatik.uxnvp.trainsimulation.model.AsciiImage;

/**
 * Various coach types.
 * @author Max Schweikart
 * @version 1.0
 */
public enum CoachType {
    /**
     * A coach with space for passengers.
     */
    PASSENGER("passenger", new AsciiImage(
            "____________________",
                    "|  ___ ___ ___ ___ |",
                    "|  |_| |_| |_| |_| |",
                    "|__________________|",
                    "|__________________|",
                    "   (O)        (O)   "
    )),

    /**
     * A coach with space for freight.
     */
    FREIGHT("freight", new AsciiImage(
            "|                  |",
                    "|                  |",
                    "|                  |",
                    "|__________________|",
                    "   (O)        (O)   "
    )),

    /**
     * A special coach.
     */
    SPECIAL("special", new AsciiImage(
            "               ____",
                    "/--------------|  |",
                    "\\--------------|  |",
                    "  | |          |  |",
                    " _|_|__________|  |",
                    "|_________________|",
                    "   (O)       (O)   "
    ));

    /**
     * Unique code name for this coach type.
     */
    private final String codeName;
    /**
     * A graphical representation of this coach type.
     */
    private final AsciiImage graphicalRepresentation;

    /**
     * Creates a coach type with all of it's attributes.
     * @param codeName the unique code name of the coach type.
     * @param graphicalRepresentation a graphical representation for this coach type.
     */
    CoachType(String codeName, AsciiImage graphicalRepresentation) {
        this.codeName = codeName;
        this.graphicalRepresentation = graphicalRepresentation;
    }

    /**
     * The code name is a unique String that represents the type of a coach.
     * @return the code name of this coach type.
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * The abbreviated code name is the first letter of the code name.
     * @return the abbreviated code name of this engine type.
     */
    public char getAbbreviatedCodeName() {
        return codeName.charAt(0);
    }

    /**
     * Finds a coach type based on a code name.
     * @param codeName the code name for find a coach type for.
     * @return the coach type with the given code name.
     */
    public static CoachType getByCodeName(String codeName) {
        for (CoachType type : values()) {
            if (type.getCodeName().equals(codeName)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Returns the graphical representation of this coach type.
     * @return the graphical representation of this coach type.
     */
    public AsciiImage getGraphicalRepresentation() {
        return graphicalRepresentation;
    }
}
