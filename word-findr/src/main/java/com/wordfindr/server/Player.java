package com.wordfindr.server;

import java.util.ArrayList;

public class Player {
    private int id;
    private String name;
    private int score;
    private int requestedHints;
    private boolean isChallenger;
    private ArrayList<String> guesses;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
        this.requestedHints = 0;
        this.isChallenger = false;
        this.guesses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRequestedHints() {
        return requestedHints;
    }

    public void setRequestedHints(int requestedHints) {
        this.requestedHints = requestedHints;
    }

    public boolean isChallenger() {
        return isChallenger;
    }

    public void setChallenger(boolean isChallenger) {
        this.isChallenger = isChallenger;
    }

    public ArrayList<String> getGuesses() {
        return guesses;
    }
}
