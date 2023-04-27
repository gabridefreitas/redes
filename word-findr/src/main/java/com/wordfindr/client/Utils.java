package com.wordfindr.client;

import com.wordfindr.commom.Player;

public abstract class Utils {
   static final private int GUESS_MAX_SIZE = 1;

   static public void log(String message) {
      System.out.print("\n" + message.toUpperCase() + "\n");
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

   static public void renderHeader(Player player, String hint, String secret, boolean isGuesser) {
      log("\n\n\n\n\n\n\n\n\n\n");
      System.out.println("==========================================");
      log("WordFindr\n");
      System.out.println("==========================================");
      System.out.printf("Player: %s\n", player.getName());

      if (isGuesser) {
         System.out.printf("Guesses: %s\n", player.toStringGuesses());
         System.out.printf("Errors: %d\n", player.getNumberErrors());
      }

      if (!hint.isBlank()) {
         System.out.printf("Hint: %s\n", hint);
      }

      System.out.println("==========================================\n");

      if (!secret.isBlank()) {
         System.out.printf("Secret: %s\n", secret);
         System.out.println("\n==========================================\n");
      }
   }
}
