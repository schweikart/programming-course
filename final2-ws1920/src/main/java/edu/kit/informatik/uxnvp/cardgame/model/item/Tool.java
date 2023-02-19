package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;

/**
 * Tool items which add damage bonus.
 * @author Max Schweikart
 * @version 1.0
 * @see #AXE
 * @see #CLUB
 */
public class Tool extends Item {
    /**
     * A tool with a damage bonus of 2.
     */
    public static final Tool AXE = new Tool("axe", (byte) 2,
            new UniquenessRequirement(),
            new ResourceRequirement(0, 3, 0));
    /**
     * A tool with a damage bonus of 1.
     */
    public static final Tool CLUB = new Tool("club", (byte) 1,
            new UniquenessRequirement(),
            new ResourceRequirement(3, 0, 0));

    private final byte damageBonus;

    /**
     * Constructs a new tool item.
     * @param codeName the unique code name of the item.
     * @param damageBonus the damage bonus of this item.
     * @param itemRequirements the requirements for building this item.
     */
    public Tool(String codeName, byte damageBonus, ItemRequirement... itemRequirements) {
        super(codeName, itemRequirements);
        this.damageBonus = damageBonus;
    }

    @Override
    public void onBuild(CardGame game) {
        game.getInventory().increaseDamageBonusTo(damageBonus);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + damageBonus;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((Tool) obj).damageBonus == damageBonus;
    }
}
