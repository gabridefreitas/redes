package com.wordfindr.server;

import com.wordfindr.commom.Message;
import com.wordfindr.commom.Player;
import lombok.Data;

import java.util.ArrayList;

import static com.wordfindr.commom.Commands.SECRET_GUESS;
import static com.wordfindr.commom.Commands.SET_SECRET;

@Data
public class Game {

    private int round;
    private String secret;
    private boolean finished;
    private Player challenger;
    private ArrayList<String> hints;
    private ArrayList<Player> players;
    private ArrayList<String> guesses;

    public Game(ArrayList<Player> players) {
        this.round = 0;
        this.finished = false;
        this.players = players;
        this.hints = new ArrayList<>();
        this.guesses = new ArrayList<>();
    }

    public void start() {

        setChallenger();
        System.out.printf("Challenger: %s\n", challenger.getName());

        requestSecret();
        System.out.printf("Secret: %s\n", secret);

        while (!finished) {
            System.out.println("Round: " + round++);

            for (Player player : players) {
                if (!player.equals(challenger)) {
                    requestGuess(player);
                }
            }
        }
    }

    public void setChallenger() {
        int randomPlayer = (int) (Math.random() * players.size());
        challenger = players.get(randomPlayer);
    }

    private void requestSecret() {
        Message.sendMessage(SET_SECRET, "", challenger);
        secret = Message.readMessage(challenger.getConnection()).getBody();
    }

    private void requestGuess(Player player) {
        Message.sendMessage(SECRET_GUESS, "", player);
        player.getGuesses().add(Message.readMessage(player.getConnection()).getBody());
    }
}
