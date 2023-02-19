package edu.kit.informatik.uxnvp.cardgame.view;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.uxnvp.cardgame.view.check.CircumstanceCheck;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A command that can be run from the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public abstract class Command {
    private final CardGameCLI cli;
    private final String prefix;
    private final Pattern syntax;
    private final CircumstanceCheck[] circumstanceChecks;

    /**
     * Constructs a new command.
     * @param cli the instance of the CLI this command is run with.
     * @param prefix the prefix of the command.
     * @param syntax a regex pattern representing the syntax of this command und defining the positions of arguments.
     * @param circumstanceChecks an array of circumstance checks that will
     */
    public Command(CardGameCLI cli, String prefix, Pattern syntax, CircumstanceCheck... circumstanceChecks) {
        this.cli = cli;
        this.prefix = prefix;
        this.syntax = syntax;
        this.circumstanceChecks = circumstanceChecks;
    }

    /**
     * Invokes this command's command handler with a command and prints an error message if there are syntactical or
     * semantic errors.
     * @param input the command that the user ran.
     */
    public void invoke(String input) {
        if (input == null || !input.startsWith(prefix)) {
            Terminal.printError("command invoked with invalid prefix.");
        } else {
            Matcher inputMatcher = syntax.matcher(input);
            if (!inputMatcher.matches()) {
                Terminal.printError("invalid syntax.");
            } else if (checkCircumstances()) {
                int argumentAmount = inputMatcher.groupCount();
                String[] arguments = new String[argumentAmount];
                for (int i = 0; i < argumentAmount; i++) {
                    int groupIndex = i + 1; // group indexes start with 1, not 0
                    arguments[i] = inputMatcher.group(groupIndex);
                }
                execute(arguments);
            }
        }
    }

    /**
     * Runs this command's circumstance checks. Will abort once a check fails.
     * @return true if all checks passed and false otherwise.
     */
    private boolean checkCircumstances() {
        for (CircumstanceCheck checker : circumstanceChecks) {
            if (!checker.checkCircumstance(cli)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the CLI instance this command is run on.
     * @return the CLI instance this command is run on.
     */
    public CardGameCLI getCli() {
        return cli;
    }

    /**
     * Returns the prefix of this command.
     * @return the prefix of this command.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Execute the command with a given array of arguments. The amount of arguments should already have been checked.
     * @param args the arguments of this commands as strings.
     */
    public abstract void execute(String[] args);
}
