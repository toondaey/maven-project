package com.example;

/**
 * This is a class.
 */
public class Greeter {

  /**
   * This is a constructor.
   */
  public Greeter() {

  }

  /**
   * Greet someone
   * @param  someone The name of the person
   * @return         Greeting.
   */
  public final String greet(final String someone) {
    return String.format("Hello, %s!", someone);
  }
}
