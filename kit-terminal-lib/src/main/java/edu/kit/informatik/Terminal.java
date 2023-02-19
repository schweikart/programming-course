package edu.kit.informatik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is a re-implementation of the terminal class we had to use for interacting with
 * {@link System#in}, {@link System#out} and {@link System#err}.
 * The implementation is probably incomplete and was reconstructed only from the method signatures
 * used in my code.
 * It might be erroneous and has not been testet properly as it was only implemented to get my
 * code to run again.
 */
public class Terminal {
  private static final BufferedReader INPUT_READER =
      new BufferedReader((new InputStreamReader(System.in)));

  private Terminal() {
    throw new UnsupportedOperationException("This utility class should not be instantiated!");
  }

  @SuppressWarnings("java:S106") // for using System.err
  public static void printError(String message) {
    // original implementation printed to System.err too
    System.err.println(message);
  }

  @SuppressWarnings("java:S106") // for using System.out
  public static void printLine(String message) {
    // original implementation printed to System.out too
    System.out.println(message);
  }

  public static void printLine(int id) {
    printLine(String.valueOf(id));
  }

  @SuppressWarnings("java:S112") // for rethrowing as runtime exception
  public static String readLine() {
    try {
      return INPUT_READER.readLine();
    } catch (IOException e) {
      // original implementation made the program crash too
      throw new RuntimeException("Could not read input", e);
    }
  }
}
