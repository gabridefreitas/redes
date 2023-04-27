package com.wordfindr.client;

import com.wordfindr.commom.Player;

public abstract class Utils {
   static final private int GUESS_MAX_SIZE = 1;

   static public void log(String message) {
      System.out.print("\n\n" + message.toUpperCase() + "\n\n");
   }

   static public void input(String message) {
      System.out.print(message.toUpperCase());
   }

   static public String validateGuess(String input) {
      if (input.isBlank()) {
         return "Error: Is blank.";
      }

      if (input.length() > GUESS_MAX_SIZE) {
         return "Error: Only send 1 character.";
      }

      return "";
   }

   static public void renderHeader(Player player, String hint) {
      log("WordFindr");
      System.out.println("==========================================");
      System.out.printf("Player: %s\n", player.getName());
      System.out.printf("Guesses: %s\n", player.toStringGuesses());

      if (!hint.isBlank()) {
         System.out.printf("Hint: %s\n", hint);
      }

      System.out.printf("Errors: %d\n", player.getNumberErrors());
      System.out.println("==========================================");
   }
}