package com.wordfindr.server;

import lombok.Data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

    public void start() throws InterruptedException {
        setChallenger();
        setSecret();

        System.out.printf("Game started - Challenger: %s, Secret: %s\n", challenger.getName(), secret);

        while (!finished) {
            round++;
            System.out.println("Round: " + round);

            for (Player player : players) {
                if (player.equals(challenger)) {
                    System.out.printf("Player %s is challenger\n", player.getName());
                } else {
                    System.out.printf("Player %s is not challenger\n", player.getName());
                }
            }

            Thread.sleep(5000);

            // Ask for hint
            // Ask for guess
            // Check if guess is correct
            // Check if game is finished
        }
    }

    public void setChallenger() {
        int randomPlayer = (int) (Math.random() * players.size());
        challenger = players.get(randomPlayer);
    }

    private void setSecret() {
        try {
            DataOutputStream serverStream = new DataOutputStream(challenger.getConnection().getOutputStream());
            BufferedReader clientBuffer = new BufferedReader(
                    new InputStreamReader(challenger.getConnection().getInputStream()));

            serverStream.writeBytes("You are challenger. Define secret word:\n");
            secret = clientBuffer.readLine();
        } catch (IOException e) {
            System.out.println("Error to get secret from challenger");
        }
    }
}
