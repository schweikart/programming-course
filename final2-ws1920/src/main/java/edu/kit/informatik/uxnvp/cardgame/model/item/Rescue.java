package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;

/**
 * Rescue items initiate a rescue when built.
 * @author Max Schweikart
 * @version 1.0
 */
public class Rescue extends Item {
    /**
     * The sailing raft leads to an endeavour situation.
     */
    public static final Rescue SAILING_RAFT = new Rescue("sailingraft", false,
            new UniquenessRequirement(),
            new ResourceRequirement(4, 2, 2));
    /**
     * The hang glider leads to an endeavour situation.
     */
    public static final Rescue HANG_GLIDER = new Rescue("hangglider", false,
            new UniquenessRequirement(),
            new ResourceRequirement(2, 2, 4));
    /**
     * The steamboat leads to a guaranteed rescue.
     */
    public static final Rescue STEAMBOAT = new Rescue("steamboat", true,
            new UniquenessRequirement(),
            new ResourceRequirement(0, 6, 1),
            new FireplaceRequirement());
    /**
     * The balloon leads to a guaranteed rescue.
     */
    public static final Rescue BALLOON = new Rescue("ballon", true,
            new UniquenessRequirement(),
            new ResourceRequirement(1, 0, 6),
            new FireplaceRequirement());

    private final boolean isInstantRescue;

    /**
     * Constructs a rescue item.
     * @param codeName the code name of the item.
     * @param isInstantRescue whether building this item leads to a guaranteed rescue (true) or
     *                        an endeavour situation (false).
     * @param itemRequirements the requirements for building this item.
     */
    public Rescue(String codeName, boolean isInstantRescue, ItemRequirement... itemRequirements) {
        super(codeName, itemRequirements);
        this.isInstantRescue = isInstantRescue;
    }

    @Override
    public void onBuild(CardGame game) {
        if (isInstantRescue) {
            game.win();
        } else {
            game.startEndeavour();
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Boolean.hashCode(isInstantRescue);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((Rescue) obj).isInstantRescue == isInstantRescue;
    }
}
