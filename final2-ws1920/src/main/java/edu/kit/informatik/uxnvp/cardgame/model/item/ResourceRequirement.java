package edu.kit.informatik.uxnvp.cardgame.model.item;

import edu.kit.informatik.uxnvp.cardgame.controller.Inventory;
import edu.kit.informatik.uxnvp.cardgame.model.ResourceType;
import edu.kit.informatik.uxnvp.cardgame.util.MapBuilder;
import edu.kit.informatik.uxnvp.cardgame.util.StackUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This requirement checks and deducts the resource cost of an item.
 * @author Max Schweikart
 * @version 1.0
 */
public class ResourceRequirement implements ItemRequirement {
    private final Map<ResourceType, Integer> costs;

    /**
     * Constructs a new resource requirement with its wood, metal and plastic cost values.
     * @param woodCost the wood cost.
     * @param metalCost the metal cost.
     * @param plasticCost the plastic cost.
     */
    public ResourceRequirement(int woodCost, int metalCost, int plasticCost) {
        this(new MapBuilder<ResourceType, Integer>()
                .put(ResourceType.WOOD, woodCost)
                .put(ResourceType.METAL, metalCost)
                .put(ResourceType.PLASTIC, plasticCost)
                .getMap());
    }

    /**
     * Constructs a new resource requirement with its costs.
     * @param costs the cost map (resource type -> amount).
     */
    public ResourceRequirement(Map<ResourceType, Integer> costs) {
        this.costs = new HashMap<>(costs);
    }

    @Override
    public boolean isFulfilled(Inventory inventory, Item item) {
        Stack<ResourceType> resources = inventory.getObtainedResources();
        boolean fulfilled = true;
        for (ResourceType type : costs.keySet()) {
            fulfilled &= StackUtility.count(resources, type) >= costs.get(type);
        }
        return fulfilled;
    }

    @Override
    public void deductResources(Inventory inventory) {
        for (Map.Entry<ResourceType, Integer> cost : costs.entrySet()) {
            inventory.removeResources(cost.getKey(), cost.getValue());
        }
    }

    @Override
    public int hashCode() {
        return costs.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            ResourceRequirement that = (ResourceRequirement) obj;
            return this.costs.equals(that.costs);
        }
    }
}
