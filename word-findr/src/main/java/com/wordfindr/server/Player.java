package com.wordfindr.server;

import lombok.Data;

import java.net.Socket;
import java.util.ArrayList;

@Data
public class Player {

    private String name;
    private int score;
    private int requestedHints;
    private Socket connection;
    private ArrayList<String> guesses;

    public Player(String name, Socket connection) {
        this.name = name;
        this.score = 0;
        this.requestedHints = 0;
        this.connection = connection;
        this.guesses = new ArrayList<>();
    }
}
