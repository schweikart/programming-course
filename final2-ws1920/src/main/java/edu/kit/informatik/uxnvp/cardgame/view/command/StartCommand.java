package edu.kit.informatik.uxnvp.cardgame.view.command;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;
import edu.kit.informatik.uxnvp.cardgame.model.card.Card;
import edu.kit.informatik.uxnvp.cardgame.model.card.CardDeck;
import edu.kit.informatik.uxnvp.cardgame.model.item.ItemStore;
import edu.kit.informatik.uxnvp.cardgame.util.StackUtility;
import edu.kit.informatik.uxnvp.cardgame.view.CardGameCLI;
import edu.kit.informatik.uxnvp.cardgame.view.Command;
import edu.kit.informatik.uxnvp.cardgame.view.check.NoActiveGameCheck;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Handles the 'start' command which starts a new game.
 * @author Max Schweikart
 * @version 1.0
 */
public class StartCommand extends Command {
    /**
     * Constructs a command instance of this command.
     * @param cli the CLI instance this command should be registered in.
     */
    public StartCommand(CardGameCLI cli) {
        super(cli, "start", Pattern.compile("start ([^, ]+(?:,[^, ]+)*)"),
                new NoActiveGameCheck());
    }

    @Override
    public void execute(String[] args) {
        String[] cardNames = args[0].split(",");

        CardDeck deck = CardDeck.STANDARD_DECK;

        // parse cards
        Card[] cards = new Card[cardNames.length];
        for (int i = 0; i < cardNames.length; i++) {
            cards[i] = deck.getCardByCodeName(cardNames[i]);
            if (cards[i] == null) {
                Terminal.printError(String.format("'%s' is not a valid card.", cardNames[i]));
                return;
            }
        }

        // create card stack from cards
        Stack<Card> cardStack = new Stack<>();
        // cards[0] should be on top -> push cards in reverse order
        for (int i = cards.length - 1; i >= 0; i--) {
            cardStack.push(cards[i]);
        }

        // validate card amounts - the total amount is implicitly checked
        for (Card card : deck.getCards()) {
            int expectedAmount = deck.getAmountOfCard(card);
            int actualAmount = StackUtility.count(cardStack, card);
            if (expectedAmount != actualAmount) {
                Terminal.printError(String.format("there must be exactly %s cards of type %s",
                        expectedAmount, card.getCodeName()));
                return;
            }
        }

        // initialize game
        getCli().setCardGame(new CardGame(cardStack, ItemStore.STANDARD_ITEM_STORE));
        Terminal.printLine("OK");
    }
}
