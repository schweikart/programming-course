package edu.kit.informatik.uxnvp.trainsimulation;

import edu.kit.informatik.uxnvp.trainsimulation.cli.TrainSimulationCLI;

/**
 * Program entry point for the train simulation.
 * @author Max Schweikart
 * @version 1.0
 */
public final class Main {
    /**
     * Private constructor that prevents this utility class from being initialized.
     */
    private Main() {
        throw new AssertionError("Utility Classes must not be instantiated!");
    }

    /**
     * Entry point method called when launching this program.
     * @param args the command line arguments used when launching this program. Won't be used.
     */
    public static void main(String[] args) {
        TrainSimulationCLI cli = new TrainSimulationCLI();
        cli.startPromptLoop();
    }
}
