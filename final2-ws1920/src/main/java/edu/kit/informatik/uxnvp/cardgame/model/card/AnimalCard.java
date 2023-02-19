package edu.kit.informatik.uxnvp.cardgame.model.card;

import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;
import edu.kit.informatik.uxnvp.cardgame.model.AnimalType;

/**
 * A card that starts a fight against an animal when drawn.
 * @author Max Schweikart
 * @version 1.0
 */
public class AnimalCard implements Card {
    private final AnimalType animal;

    /**
     * Constructs an animal card with an animal type.
     * @param animal the type of animal on the card.
     */
    public AnimalCard(AnimalType animal) {
        this.animal = animal;
    }

    @Override
    public void onDraw(CardGame game) {
        game.startAnimalEncounter(animal);
    }

    @Override
    public String getCodeName() {
        return animal.getCodeName();
    }
}
