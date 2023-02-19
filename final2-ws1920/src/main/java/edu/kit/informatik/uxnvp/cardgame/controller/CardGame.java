package edu.kit.informatik.uxnvp.cardgame.controller;

import edu.kit.informatik.uxnvp.cardgame.model.AnimalType;
import edu.kit.informatik.uxnvp.cardgame.model.Die;
import edu.kit.informatik.uxnvp.cardgame.model.card.Card;
import edu.kit.informatik.uxnvp.cardgame.model.item.ItemStore;
import edu.kit.informatik.uxnvp.cardgame.util.StackUtility;

import java.util.Stack;

/**
 * A card game about surviving.
 * @author Max Schweikart
 * @version 1.0
 */
public class CardGame {
    private final Stack<Card> initialCardStack;
    private final ItemStore itemStore;

    private Stack<Card> cardStack;
    private Inventory inventory;
    private Phase phase;
    private DieRollingChallenge currentChallenge = null;
    private Boolean hasWon;

    /**
     * Starts a new card game with a given card stack.
     * @param cardStack the initial card stack to draw from.
     * @param itemStore the item store for this game.
     */
    public CardGame(Stack<Card> cardStack, ItemStore itemStore) {
        this.initialCardStack = cardStack;
        this.itemStore = itemStore;
        reset();
    }

    /**
     * Resets this game to its initial state, removing all crafted items and drawn cards.
     */
    public void reset() {
        cardStack = StackUtility.clone(initialCardStack);
        inventory = new Inventory();
        phase = Phase.SCAVENGE;
        currentChallenge = null;
        hasWon = null;
    }

    /**
     * Draws a card from the card stack. The availability of another card must be checked before.
     * @see #isCardStackEmpty()
     * @return the drawn card.
     */
    public Card draw() {
        Card card = cardStack.pop();
        card.onDraw(this);
        checkForEnd();
        return card;
    }

    private void checkForEnd() {
        // only in the scavenge phase, the player can run out of options
        if (phase == Phase.SCAVENGE) {
            boolean outOfCards = cardStack.isEmpty();
            boolean outOfBuildableItems = itemStore.getItems().stream()
                    .noneMatch(item -> item.canBeBuilt(inventory));

            if (outOfCards && outOfBuildableItems) {
                hasWon = false;
                phase = Phase.END;
            }
        }
    }

    /**
     * Checks whether this game is active.
     * @return false if this game has ended and true otherwise.
     */
    public boolean isActive() {
        return phase != Phase.END;
    }

    /**
     * Returns the phase this game is currently in.
     * @return the phase this game is currently in.
     */
    public Phase getCurrentPhase() {
        return phase;
    }

    /**
     * Checks whether the card stack is empty.
     * @return true if there are no cards left on the stack and false otherwise.
     */
    public boolean isCardStackEmpty() {
        return cardStack.isEmpty();
    }

    /**
     * Builds an item with resources from the inventory.
     * @param item the item to build
     */
    public void build(edu.kit.informatik.uxnvp.cardgame.model.item.Item item) {
        inventory.addItem(item);
        item.deductResources(inventory);
        item.onBuild(this);
        checkForEnd();
    }

    /**
     * Rolls a die.
     * @param result the rolled number.
     * @return true if the result is enough
     * @throws IllegalStateException if there is no active die rolling challenge.
     */
    public boolean roll(int result) {
        if (currentChallenge == null) {
            throw new IllegalStateException("there is no active die rolling challenge.");
        }

        boolean success = currentChallenge.roll(result);
        currentChallenge = null;
        return success;
    }

    /**
     * Returns the die to currently roll with.
     * @return the die to currently roll with.
     */
    public Die getCurrentDie() {
        return currentChallenge.getDie();
    }

    /**
     * Returns the inventory of the player.
     * @return the inventory of the player.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns whether the player has won.
     * @return whether the player has won.
     */
    public boolean hasWon() {
        return hasWon;
    }

    /**
     * Starts an endeavour situation.
     */
    public void startEndeavour() {
        phase = Phase.ENDEAVOR;
        currentChallenge = new EndeavourChallenge(this);
    }

    /**
     * Starts an animal encounter situation.
     * @param type the animal that the player encounters.
     */
    public void startAnimalEncounter(AnimalType type) {
        phase = Phase.ENCOUNTER;
        currentChallenge = new AnimalEncounterChallenge(this, type);
    }

    /**
     * Marks this game as won.
     */
    public void win() {
        phase = Phase.END;
        hasWon = true;
    }

    /**
     * Returns the item store of this game.
     * @return the item store of this game.
     */
    public ItemStore getItemStore() {
        return itemStore;
    }

    /**
     * Updates the current phase and checks whether the game is over.
     * @param newPhase the new phase.
     */
    public void setPhase(Phase newPhase) {
        this.phase = newPhase;
        checkForEnd();
    }
}
