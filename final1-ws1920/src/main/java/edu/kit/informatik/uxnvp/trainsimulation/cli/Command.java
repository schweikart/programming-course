package edu.kit.informatik.uxnvp.trainsimulation.cli;

/**
 * A command that can be run from the terminal.
 * @author Max Schweikart
 * @version 1.0
 */
public abstract class Command {
    private static final String ARGUMENTS_TAIL_REGEX = "( \\S+)*";
    private final TrainSimulationCLI cli;
    private final String prefix;
    private final int expectedArgumentAmount;

    /**
     * Constructs a new command.
     * @param cli the instance of the CLI this command is run with.
     * @param prefix the prefix of the command.
     * @param expectedArgumentAmount the amount of arguments expected by this command.
     */
    public Command(TrainSimulationCLI cli, String prefix, int expectedArgumentAmount) {
        this.cli = cli;
        this.prefix = prefix;
        this.expectedArgumentAmount = expectedArgumentAmount;
    }

    /**
     * Invokes this command's command handler with a command.
     * @param input the command that the user ran.
     * @throws SyntaxException if the syntax of the command is incorrect.
     */
    public void invoke(String input) throws SyntaxException {
        if (input == null || !input.startsWith(prefix)) {
            throw new SyntaxException("command invoked with invalid prefix.");
        } else if (!input.matches(prefix + ARGUMENTS_TAIL_REGEX)) {
            throw new SyntaxException("arguments must be separated by one space.");
        }

        // remove prefix and cut off leading whitespace, if present ("prefix arg1 arg2" -> "arg1 arg2")
        String argumentsString = input.substring(prefix.length()).trim();
        // split arguments with the default whitespace character
        String[] arguments = argumentsString.isEmpty() ? new String[0] : argumentsString.split(" ");

        if (arguments.length > expectedArgumentAmount) {
            throw new SyntaxException("too many arguments.");
        } else if (arguments.length < expectedArgumentAmount) {
            throw new SyntaxException("not enough arguments.");
        } else {
            execute(arguments);
        }
    }

    /**
     * Returns the CLI instance this command is run on.
     * @return the CLI instance this command is run on.
     */
    public TrainSimulationCLI getCli() {
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
     * @throws SyntaxException if the syntax of the arguments is correct.
     */
    public abstract void execute(String[] args) throws SyntaxException;
}
