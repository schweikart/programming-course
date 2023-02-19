package edu.kit.informatik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {
  private static final BufferedReader INPUT_READER =
      new BufferedReader((new InputStreamReader(System.in)));

  public static void printError(String message) {
    System.err.println(message);
  }

  public static void printLine(String message) {
    System.out.println(message);
  }

  public static void printLine(int id) {
    printLine(String.valueOf(id));
  }

  public static String readLine() {
    try {
      return INPUT_READER.readLine();
    } catch (IOException e) {
      throw new RuntimeException("Could not read input", e);
    }
  }
}
