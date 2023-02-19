package edu.kit.informatik.uxnvp.trainsimulation.model.train;

import edu.kit.informatik.uxnvp.trainsimulation.model.AsciiImage;

/**
 * Various types of engines.
 * @author Max Schweikart
 * @version 1.0
 */
public enum EngineType {
    /**
     * The type of an electrical engine.
     */
    ELECTRICAL("electrical", new AsciiImage(
            "               ___    ",
                    "                 \\    ",
                    "  _______________/__  ",
                    " /_| ____________ |_\\ ",
                    "/   |____________|   \\",
                    "\\                    /",
                    " \\__________________/ ",
                    "  (O)(O)      (O)(O)  "
    )),
    /**
     * The type of a steam engine.
     */
    STEAM("steam", new AsciiImage(
            "     ++      +------",
                    "     ||      |+-+ | ",
                    "   /---------|| | | ",
                    "  + ========  +-+ | ",
                    " _|--/~\\------/~\\-+ ",
                    "//// \\_/      \\_/   "
    )),
    /**
     * The type of a diesel engine.
     */
    DIESEL("diesel", new AsciiImage(
            "  _____________|____  ",
                    " /_| ____________ |_\\ ",
                    "/   |____________|   \\",
                    "\\                    /",
                    " \\__________________/ ",
                    "  (O)(O)      (O)(O)  "
    ));

    private final String codeName;
    private final AsciiImage graphicalRepresentation;

    /**
     * Constructs an engine type with all of it's attributes.
     * @param codeName the unique code name used for identifying this engine type.
     * @param graphicalRepresentation the graphical representation of this engine type.
     */
    EngineType(String codeName, AsciiImage graphicalRepresentation) {
        this.codeName = codeName;
        this.graphicalRepresentation = graphicalRepresentation;
    }

    /**
     * The code name of an engine type is a unique String that represents this type.
     * @return the code name of this engine type.
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * Finds an engine type based on a code name.
     * @param codeName the code name of the engine type to find.
     * @return the engine type that fits to the given code name or null if no engine type with the code name exists.
     */
    public static EngineType getByCodeName(String codeName) {
        for (EngineType type : values()) {
            if (type.getCodeName().equals(codeName)) {
                return type;
            }
        }
        return null;
    }

    /**
     * The abbreviated code name is the first letter of the code name.
     * @return the abbreviated code name of this engine type.
     */
    public char getAbbreviatedCodeName() {
        return codeName.charAt(0);
    }

    /**
     * Returns the graphical representation of this engine type.
     * @return the graphical representation of this engine type.
     */
    public AsciiImage getGraphicalRepresentation() {
        return graphicalRepresentation;
    }
}
