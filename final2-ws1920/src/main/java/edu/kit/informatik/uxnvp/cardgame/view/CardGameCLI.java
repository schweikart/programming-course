package edu.kit.informatik.uxnvp.cardgame.view;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.controller.CardGame;
import edu.kit.informatik.uxnvp.cardgame.view.command.BuildCommand;
import edu.kit.informatik.uxnvp.cardgame.view.command.DrawCommand;
import edu.kit.informatik.uxnvp.cardgame.view.command.ListBuildableItemsCommand;
import edu.kit.informatik.uxnvp.cardgame.view.command.ListBuildingsCommand;
import edu.kit.informatik.uxnvp.cardgame.view.command.ListResourcesCommand;
import edu.kit.informatik.uxnvp.cardgame.view.command.QuitCommand;
import edu.kit.informatik.uxnvp.cardgame.view.command.ResetCommand;
import edu.kit.informatik.uxnvp.cardgame.view.command.RollDieCommand;
import edu.kit.informatik.uxnvp.cardgame.view.command.StartCommand;

/**
 * A command-line-interface for interacting with the card game.
 * @author Max Schweikart
 * @version 1.0
 */
public class CardGameCLI {
    /**
     * All registered commands in this cli.
     */
    private final Command[] commands;
    /**
     * The card game to interact with.
     */
    private CardGame cardGame;
    /**
     * Whether the prompt loop is currently running.
     */
    private boolean promptLoopRunning;

    /**
     * Constructs a new CLI for interacting with card games.
     */
    public CardGameCLI() {
        this.promptLoopRunning = false;

        commands = new Command[]{
            new StartCommand(this),
            new DrawCommand(this),
            new ListResourcesCommand(this),
            new BuildCommand(this),
            new ListBuildingsCommand(this),
            new ListBuildableItemsCommand(this),
            new RollDieCommand(this),
            new ResetCommand(this),
            new QuitCommand(this)
        };
    }

    /**
     * Tries to parse and execute a command from the given input. Will print feedback to the terminal.
     * @param input the input that should be parsed as a command.
     */
    private void handleInput(String input) {
        if (input == null) {
            Terminal.printError("please enter a command.");
        } else {
            Command command = findCommandForInput(input);
            if (command == null) {
                Terminal.printError("can not find a matching command.");
            } else {
                command.invoke(input);
            }
        }
    }

    /**
     * Starts the prompt loop if it is not already running.
     */
    public void startPromptLoop() {
        if (!promptLoopRunning) {
            promptLoopRunning = true;

            while (promptLoopRunning) {
                String input = Terminal.readLine();
                handleInput(input);
            }
        }
    }

    /**
     * Stops the prompt loop if it is running and does nothing otherwise. The prompt loop will finish the current
     * iteration and stop afterwards.
     */
    public void stopPromptLoop() {
        promptLoopRunning = false;
    }

    /**
     * Finds a command whose prefix is the prefix if the given input.
     * @param input the input to check command prefixes at.
     * @return the command with the matching prefix or null if no command has a matching prefix.
     */
    private Command findCommandForInput(String input) {
        for (Command command : commands) {
            if (input.startsWith(command.getPrefix())) {
                return command;
            }
        }
        return null;
    }

    /**
     * Returns the card game this cli is currently running.
     * @return the card game this cli is currently running.
     */
    public CardGame getCardGame() {
        return cardGame;
    }

    /**
     * Sets the currently run card game to another game.
     * @param cardGame the game to be run by this cli.
     */
    public void setCardGame(CardGame cardGame) {
        this.cardGame = cardGame;
    }
}
