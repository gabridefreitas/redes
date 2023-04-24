package com.wordfindr.server;

import java.net.Socket;
import java.util.ArrayList;

public class Game {
    private String secret;
    private boolean finished;
    private int round;
    private ArrayList<Player> players;
    private ArrayList<String> hints;
    private ArrayList<String> guesses;

    public Game(ArrayList<Player> players) {
        this.players = players;
        this.secret = "";
        this.finished = false;
        this.round = 0;
        this.hints = new ArrayList<>();
        this.guesses = new ArrayList<>();
    }

    public void start(String secret, ArrayList<Socket> connections) {
        this.setSecret(secret);

        while (!finished) {
            round++;
            System.out.println("Round: " + round);

            for (Player player : players) {
                if (player.isChallenger()) {
                    System.out.printf("Player %s is challenger\n", player.getName());
                } else {
                    System.out.printf("Player %s is not challenger\n", player.getName());
                }
            }

            // Ask for hint
            // Ask for guess
            // Check if guess is correct
            // Check if game is finished
        }
    }

    public Player setChallenger() {
        int randomPlayer = (int) (Math.random() * players.size());

        Player challenger = players.get(randomPlayer);

        challenger.setChallenger(true);

        return challenger;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<String> getHints() {
        return hints;
    }

    public void setHints(ArrayList<String> hints) {
        this.hints = hints;
    }

    public ArrayList<String> getGuesses() {
        return guesses;
    }

    public void setGuesses(ArrayList<String> guesses) {
        this.guesses = guesses;
    }
}
