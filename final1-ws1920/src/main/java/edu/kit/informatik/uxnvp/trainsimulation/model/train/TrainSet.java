package edu.kit.informatik.uxnvp.trainsimulation.model.train;

import edu.kit.informatik.uxnvp.trainsimulation.model.AsciiImage;

/**
 * A train-set is a rolling stock that can push the train.
 * @author Max Schweikart
 * @version 1.0
 */
public class TrainSet extends RollingStock implements Comparable<TrainSet> {
    private static final AsciiImage GRAPHICAL_REPRESENTATION = new AsciiImage(
            "         ++         ",
                    "         ||         ",
                    "_________||_________",
                    "|  ___ ___ ___ ___ |",
                    "|  |_| |_| |_| |_| |",
                    "|__________________|",
                    "|__________________|",
                    "   (O)        (O)   "
    );

    private final String series;
    private final String name;

    /**
     * Constructs a new train-set.
     * @param series the series/class of this train-set.
     * @param name the name of this train-set.
     * @param length the length of this train-set.
     * @param hasFrontCoupling whether this train-set has a coupling in the front.
     * @param hasBackCoupling whether this train-set has a coupling in the back.
     */
    public TrainSet(String series, String name, int length, boolean hasFrontCoupling, boolean hasBackCoupling) {
        super(GRAPHICAL_REPRESENTATION,
                String.format("%s-%s", series, name),
                "train-set",
                length,
                true,
                hasFrontCoupling,
                hasBackCoupling);
        this.series = series;
        this.name = name;
    }

    /**
     * Returns the series/class of this train-set.
     * @return the series/class of this train-set.
     */
    public String getSeries() {
        return series;
    }

    /**
     * Returns the name of this train-set.
     * @return the name of this train-set.
     */
    public String getName() {
        return name;
    }

    /**
     * The identifier of a train-set is a unique composition of it's series and it's name in the following format:
     * <code>&lt;series&gt;-&lt;name&gt;</code>
     * @return the identifier of this train-set.
     */
    public String getId() {
        return getSeries() + "-" + getName();
    }

    @Override
    public int compareTo(TrainSet o) {
        return getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s", getTrain() == null ? "none" : getTrain().getId(),
                series, name, getLength(), hasFrontCoupling(), hasBackCoupling());
    }
}
