package edu.kit.informatik.uxnvp.cardgame.model.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * An item store is a collection of items that can be built in a game.
 * @author Max Schweikart
 * @version 1.0
 */
public class ItemStore {
    /**
     * An item store with all standard items.
     */
    public static final ItemStore STANDARD_ITEM_STORE = new ItemStore(
            Tool.AXE, Tool.CLUB,
            new Shack(), new Fireplace(),
            Rescue.BALLOON, Rescue.SAILING_RAFT, Rescue.HANG_GLIDER, Rescue.STEAMBOAT
    );

    /**
     * All items available in this store.
     */
    private final Set<Item> items;

    /**
     * Constructs a new item store.
     * @param items the items that should be contained in the item store. Duplicate items will only be contained once.
     */
    public ItemStore(Item... items) {
        this.items = new HashSet<>(Arrays.asList(items));
    }

    /**
     * Returns a read-only view on the set of this store's items.
     * @return a read-only view on the set of this store's items.
     */
    public Set<Item> getItems() {
        return Collections.unmodifiableSet(items);
    }

    /**
     * Finds an item by its code name.
     * @param codeName the code name of the item to find.
     * @return the item with the given code name or null if no item in this store has the given code name.
     */
    public Item getItemByCodeName(String codeName) {
        for (Item item : items) {
            if (item.getCodeName().equals(codeName)) {
                return item;
            }
        }
        return null;
    }
}
