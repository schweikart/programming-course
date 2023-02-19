package edu.kit.informatik.uxnvp.trainsimulation.model.train;

/**
 * An engine is a locomotive rolling stock.
 * @author Max Schweikart
 * @version 1.0
 */
public class Engine extends RollingStock implements Comparable<Engine> {
    /**
     * The type of this engine.
     */
    private final EngineType type;
    /**
     * The series of this engine. Is an alphanumerical value but not "W".
     */
    private final String series;
    /**
     * The name of this engine. Is an alphanumerical value.
     */
    private final String name;

    /**
     * Constructs an engine with all of it's attributes.
     * @param type the type of the engine.
     * @param series the series of the engine. Must be alphanumerical but not "W".
     * @param name the name of the engine. Must be alphanumerical.
     * @param length the length of the engine.
     * @param hasFrontCoupling whether the engine has a coupling on the front.
     * @param hasBackCoupling whether the engine has a coupling on the back.
     */
    public Engine(EngineType type, String series, String name, int length, boolean hasFrontCoupling,
                  boolean hasBackCoupling) {
        super(type.getGraphicalRepresentation(),
                String.format("%s-%s", series, name),
                String.format("%s engine", type.getCodeName()),
                length,
                true,
                hasFrontCoupling,
                hasBackCoupling);
        this.type = type;
        this.series = series;
        this.name = name;
    }

    /**
     * The type of the engine.
     * @return the type of this engine.
     */
    public EngineType getType() {
        return type;
    }

    /**
     * The alphanumerical series of this engine (can not be "W").
     * @return the series of this engine.
     */
    public String getSeries() {
        return series;
    }

    /**
     * The alphanumerical name of this engine.
     * @return the name of this engine.
     */
    public String getName() {
        return name;
    }

    /**
     * The identifier of an engine is a unique composition of it's series and it's name in the following format:
     * <code>&lt;series&gt;-&lt;name&gt;</code>
     * @return the identifier of this engine.
     */
    public String getId() {
        return getSeries() + "-" + getName();
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s", getTrain() == null ? "none" : getTrain().getId(),
                getType().getAbbreviatedCodeName(), series, name, getLength(), hasFrontCoupling(), hasBackCoupling());
    }

    @Override
    public int compareTo(Engine o) {
        return getId().compareTo(o.getId());
    }
}
