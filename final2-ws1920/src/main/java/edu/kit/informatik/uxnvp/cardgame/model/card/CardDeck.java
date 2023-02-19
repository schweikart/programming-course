package edu.kit.informatik.uxnvp.cardgame.model.card;

import edu.kit.informatik.uxnvp.cardgame.model.AnimalType;
import edu.kit.informatik.uxnvp.cardgame.model.CatastropheType;
import edu.kit.informatik.uxnvp.cardgame.model.ResourceType;
import edu.kit.informatik.uxnvp.cardgame.util.MapBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A card deck stores the types and amounts of available cards.
 * @author Max Schweikart
 * @version 1.0
 */
public class CardDeck {
    /**
     * The default card deck as defined in the assignment.
     */
    public static final CardDeck STANDARD_DECK = new CardDeck(new MapBuilder<Card, Integer>()
            .put(new ResourceCard(ResourceType.WOOD), 16)
            .put(new ResourceCard(ResourceType.METAL), 16)
            .put(new ResourceCard(ResourceType.PLASTIC), 16)
            .put(new AnimalCard(AnimalType.SPIDER), 5)
            .put(new AnimalCard(AnimalType.SNAKE), 5)
            .put(new AnimalCard(AnimalType.TIGER), 5)
            .put(new CatastropheCard(CatastropheType.THUNDERSTORM), 1)
            .getMap());

    private final Map<Card, Integer> cardAmounts;

    /**
     * Constructs a card deck based on a map of cards and their amounts.
     * @param cardAmounts the card to card-amount map.
     */
    public CardDeck(Map<Card, Integer> cardAmounts) {
        this.cardAmounts = new HashMap<>(cardAmounts);
    }

    /**
     * Returns the amount of cards of the given type in this deck.
     * @param card the card to find out an amount for.
     * @return the amount of cards of the given type in this deck.
     */
    public int getAmountOfCard(Card card) {
        return cardAmounts.get(card);
    }

    /**
     * Finds a card by its code name.
     * @param codeName the unique code name of the card to find.
     * @return the card with the given code name or null if no card in this deck has the given code name.
     */
    public Card getCardByCodeName(String codeName) {
        for (Card card : cardAmounts.keySet()) {
            if (card.getCodeName().equals(codeName)) {
                return card;
            }
        }
        return null;
    }

    /**
     * Returns all types of cards in this deck.
     * @return all types of cards in this deck.
     */
    public Set<Card> getCards() {
        return Collections.unmodifiableSet(cardAmounts.keySet());
    }
}
