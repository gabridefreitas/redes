package com.wordfindr.server;

import static com.wordfindr.commom.Commands.FINISH;
import static com.wordfindr.commom.Commands.GUESS;
import static com.wordfindr.commom.Commands.HINT;
import static com.wordfindr.commom.Commands.SECRET;

import java.util.ArrayList;

import com.wordfindr.commom.Message;
import com.wordfindr.commom.Player;

import lombok.Data;

@Data
public class Game {

    private static final int NUMBER_ERRORS_MAX = 3;
    private static final int NUMBER_ERRORS_REQUEST_HINT = 2;

    private String hint;
    private String secret;
    private boolean finished;
    private Player challenger;
    private ArrayList<Player> players;
    private ArrayList<String> guesses;

    protected Game(ArrayList<Player> players) {
        this.finished = false;
        this.players = players;
        this.guesses = new ArrayList<>();
    }

    protected void start() {

        setChallenger();
        System.out.printf("Challenger: %s\n", challenger.getName());

        requestSecret();
        System.out.printf("Secret: %s\n", secret);

        Player player = players.stream().filter(p -> !p.equals(challenger)).findFirst().get();

        while (!finished) {
            requestGuess(player);
            validateFinishGame(player);
        }
    }

    private void setChallenger() {
        int randomPlayer = (int) (Math.random() * players.size());
        challenger = players.get(randomPlayer);
    }

    private void requestSecret() {
        Message.sendMessage(SECRET, "", challenger);
        secret = Message.readMessage(challenger.getConnection()).getBody();
    }

    private void requestHint() {
        Message.sendMessage(HINT, "", challenger);
        hint = Message.readMessage(challenger.getConnection()).getBody();
    }

    private void requestGuess(Player player) {
        Message.sendMessage(GUESS, "", player);
        Message message = Message.readMessage(player.getConnection());

        switch (message.getType()) {
            case GUESS:
                Character guess = message.getBody().charAt(0);
                player.getGuesses().add(guess);

                if (!secret.contains(String.valueOf(guess))) {
                    player.setNumberErrors(player.getNumberErrors() + 1);
                }

                Message.sendMessage(GUESS, getGuessResponse(player), player);
                break;
            case HINT:
                if (player.getNumberErrors() >= NUMBER_ERRORS_REQUEST_HINT) {
                    requestHint();
                    Message.sendMessage(HINT, hint, player);
                    requestGuess(player);
                }
                break;
            default:
                break;
        }
    }

    private String getGuessResponse(Player player) {
        StringBuilder response = new StringBuilder();

        for (int i = 0; i < secret.length(); i++) {
            char c = secret.charAt(i);
            if (player.getGuesses().contains(c)) {
                response.append(c);
            } else {
                response.append("_");
            }
            response.append(" ");
        }
        return response.toString();
    }

    private void validateFinishGame(Player player) {
        if (player.getNumberErrors() >= NUMBER_ERRORS_MAX || !getGuessResponse(player).contains("_")) {
            Player winner = null;
            Player loser = null;

            finished = true;

            if (player.getNumberErrors() >= NUMBER_ERRORS_MAX) {
                winner = challenger;
                loser = player;
            }

            if (!getGuessResponse(player).contains("_")) {
                winner = player;
                loser = challenger;
            }

            Message.sendMessage(FINISH, "Winner", winner);
            Message.sendMessage(FINISH, "Loser", loser);
        }
    }
}
